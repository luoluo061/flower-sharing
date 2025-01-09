package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.MemberLevel;
import org.dromara.flower.domain.MemberLevelPrivilege;
import org.dromara.flower.domain.bo.MemberLevelBo;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.mapper.MemberLevelMapper;
import org.dromara.flower.mapper.MemberLevelPrivilegeMapper;
import org.dromara.flowerapplet.service.IMemberAppletLevelService;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 会员等级Service业务层处理
 *
 * @author chzl
 * @date 2024-12-24
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class MemberAppletLevelServiceImpl implements IMemberAppletLevelService {

    private final MemberLevelMapper baseMapper;
    private final ISysOssService sysOssService;
    private final MemberLevelPrivilegeMapper memberLevelPrivilegeMapper;

    /**
     * 查询会员等级
     *
     * @param id 主键
     * @return 会员等级
     */
    @Override
    public MemberLevelVo queryById(Long id){
        MemberLevelVo vo = baseMapper.selectVoById(id);
        if (vo != null){
            LambdaQueryWrapper<MemberLevelPrivilege> lqw = new LambdaQueryWrapper<>();
            lqw.eq(MemberLevelPrivilege::getMemberLevelId,id);
            List<MemberLevelPrivilegeVo> privilege = memberLevelPrivilegeMapper.selectVoList(lqw);
            vo.setPrivilegeVos(privilege);
        }
        return vo;
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
                    .distinct()
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
        return lqw;
    }

    /**
     * 新增会员等级
     *
     * @param bo 会员等级
     * @return 是否新增成功
     */
    @Override
    @Transactional
    public Boolean insertByBo(MemberLevelBo bo) {
        MemberLevel add = MapstructUtils.convert(bo, MemberLevel.class);
        LoginUser loginUser = getLoginUser();
        assert loginUser != null : "请登录！";
        add.setDeptId(loginUser.getDeptId());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            if (!bo.getPrivilegeBos().isEmpty()){
                bo.setId(add.getId());
                List<MemberLevelPrivilege> privileges = bo.getPrivilegeBos().stream()
                    .map(vo -> {
                        vo.setMemberLevelId(add.getId());
                        return MapstructUtils.convert(vo, MemberLevelPrivilege.class);
                    })
                    .toList();
                // 保存 会员权益
                if (!privileges.isEmpty()){
                    memberLevelPrivilegeMapper.insertBatch(privileges);
                }
            }
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
    @Transactional
    public Boolean updateByBo(MemberLevelBo bo) {
        MemberLevel update = MapstructUtils.convert(bo, MemberLevel.class);
        validEntityBeforeSave(update);
        // 修改会员权益
        if (!bo.getPrivilegeBos().isEmpty()){
            List<MemberLevelPrivilege> privileges = bo.getPrivilegeBos().stream()
                .map(vo -> {
                    vo.setId(null);
                    vo.setMemberLevelId(bo.getId());
                    return MapstructUtils.convert(vo, MemberLevelPrivilege.class);
                })
                .toList();
            LambdaQueryWrapper<MemberLevelPrivilege> mlp = new LambdaQueryWrapper<>();
            mlp.eq(MemberLevelPrivilege::getMemberLevelId, bo.getId());
            // 根据会员等级移除历史数据
            memberLevelPrivilegeMapper.delete(mlp);
            // 重新插入
            memberLevelPrivilegeMapper.insertOrUpdate(privileges);
        }
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

    @Override
    public R<List<Map<String,String>>> getMemberLevelTree() {
        MapResultHandler<Long, String> resultHandler = new MapResultHandler<>();
        baseMapper.selectIdMapGrade(resultHandler);
        Map<Long,String> map = resultHandler.getMappedResults();
        Map<Long, String> sortedMap = new TreeMap<>(map);
        List<Map<String, String>> resultList = sortedMap.entrySet().stream()
            .map(entry -> {
                Map<String, String> item = new HashMap<>();
                item.put("label", entry.getValue());
                item.put("value", entry.getKey().toString());
                return item;
            })
            .toList();
        return R.ok(resultList);
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
