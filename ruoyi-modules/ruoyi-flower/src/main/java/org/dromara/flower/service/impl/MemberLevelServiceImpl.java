package org.dromara.flower.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.system.domain.SysOss;
import org.dromara.system.domain.vo.SysOssVo;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MemberLevelBo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.MemberLevel;
import org.dromara.flower.mapper.MemberLevelMapper;
import org.dromara.flower.service.IMemberLevelService;

import java.util.List;
import java.util.Map;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 会员等级Service业务层处理
 *
 * @author chzl
 * @date 2024-12-24
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class MemberLevelServiceImpl implements IMemberLevelService {

    private final MemberLevelMapper baseMapper;
    private final ISysOssService sysOssService;

    /**
     * 查询会员等级
     *
     * @param id 主键
     * @return 会员等级
     */
    @Override
    public MemberLevelVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询会员等级列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员等级分页列表
     */
    @Override
    public TableDataInfo<MemberLevelVo> queryPageList(MemberLevelBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MemberLevel> lqw = buildQueryWrapper(bo);
        Page<MemberLevelVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!result.getRecords().isEmpty()){
            // 获取图片Url
            Map<String, String> longStringMap = sysOssService.listUrlByIds(
                result.getRecords().stream()
                    .map(MemberLevelVo::getGradeIcon) // 获取 gradeIcon
                    .filter(Objects::nonNull) // 过滤掉 null 值
                    .filter(gradeIcon -> {
                        try {
                            Long.parseLong(gradeIcon); // 尝试转换为 Long
                            return true; // 转换成功，保留
                        } catch (NumberFormatException e) {
                            return false; // 转换失败，过滤掉
                        }
                    })
                    .map(Long::parseLong) // 转换为 Long
                    .toList());
            if (!longStringMap.isEmpty()){
                // 设置图片Url
                result.getRecords().forEach(record ->
                    record.setGradeIconUrl(longStringMap.get(record.getGradeIcon()))
                );
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员等级列表
     *
     * @param bo 查询条件
     * @return 会员等级列表
     */
    @Override
    public List<MemberLevelVo> queryList(MemberLevelBo bo) {
        LambdaQueryWrapper<MemberLevel> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MemberLevel> buildQueryWrapper(MemberLevelBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MemberLevel> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MemberLevel::getDeptId, bo.getDeptId());
        lqw.eq(bo.getGrade() != null, MemberLevel::getGrade, bo.getGrade());
        lqw.like(StringUtils.isNotBlank(bo.getGradeName()), MemberLevel::getGradeName, bo.getGradeName());
        lqw.eq(StringUtils.isNotBlank(bo.getGradeIcon()), MemberLevel::getGradeIcon, bo.getGradeIcon());
        lqw.eq(bo.getDiscountRatio() != null, MemberLevel::getDiscountRatio, bo.getDiscountRatio());
        lqw.eq(bo.getPrice() != null, MemberLevel::getPrice, bo.getPrice());
        lqw.eq(bo.getCoupon() != null, MemberLevel::getCoupon, bo.getCoupon());
        lqw.eq(bo.getDisplay() != null, MemberLevel::getDisplay, bo.getDisplay());
        lqw.eq(StringUtils.isNotBlank(bo.getMemberTag()), MemberLevel::getMemberTag, bo.getMemberTag());
        return lqw;
    }

    /**
     * 新增会员等级
     *
     * @param bo 会员等级
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MemberLevelBo bo) {
        MemberLevel add = MapstructUtils.convert(bo, MemberLevel.class);
        LoginUser loginUser = getLoginUser();
        assert loginUser != null : "请登录！";
        add.setDeptId(loginUser.getDeptId());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改会员等级
     *
     * @param bo 会员等级
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MemberLevelBo bo) {
        MemberLevel update = MapstructUtils.convert(bo, MemberLevel.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MemberLevel entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员等级信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的信息，如果用户未登录则返回 null
     */
    private LoginUser getLoginUser() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return null;
        }
        return loginUser;
    }
}
