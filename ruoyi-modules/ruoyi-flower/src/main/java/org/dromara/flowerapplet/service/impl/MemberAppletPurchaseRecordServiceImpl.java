package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.bo.MemberPurchaseRecordBo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flower.mapper.MemberPurchaseRecordMapper;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.service.IMemberPurchaseRecordService;
import org.dromara.flowerapplet.service.IMemberAppletPurchaseRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 会员购买记录Service业务层处理
 *
 * @author chzl
 * @date 2024-12-24
 */
@RequiredArgsConstructor
@Service
public class MemberAppletPurchaseRecordServiceImpl implements IMemberAppletPurchaseRecordService {

    private final MemberPurchaseRecordMapper baseMapper;
    private final AppletUserInformationMapper userInformationMapper;
    private final static Long ZERO = 0L;
    private final static Long ONE = 1L;
    /**
     * 查询会员购买记录
     *
     * @param id 主键
     * @return 会员购买记录
     */
    @Override
    public MemberPurchaseRecordVo queryById(Long createBy){
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MemberPurchaseRecord::getCreateBy, createBy);
        lqw.eq(MemberPurchaseRecord::getStatus, 1);
        return baseMapper.selectVoOne(lqw);
    }

    /**
     * 分页查询会员购买记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员购买记录分页列表
     */
    @Override
    public TableDataInfo<MemberPurchaseRecordVo> queryPageList(MemberPurchaseRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = buildQueryWrapper(bo);
        Page<MemberPurchaseRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员购买记录列表
     *
     * @param bo 查询条件
     * @return 会员购买记录列表
     */
    @Override
    public List<MemberPurchaseRecordVo> queryList(MemberPurchaseRecordBo bo) {
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MemberPurchaseRecord> buildQueryWrapper(MemberPurchaseRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MemberPurchaseRecord::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getOrderCode()), MemberPurchaseRecord::getOrderCode, bo.getOrderCode());
        lqw.like(StringUtils.isNotBlank(bo.getMemberId()), MemberPurchaseRecord::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), MemberPurchaseRecord::getMemberName, bo.getMemberName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), MemberPurchaseRecord::getPhone, bo.getPhone());
        lqw.eq(bo.getMemberLevelId() != null, MemberPurchaseRecord::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(StringUtils.isNotBlank(bo.getGrade()), MemberPurchaseRecord::getGrade, bo.getGrade());
        lqw.like(StringUtils.isNotBlank(bo.getGradeName()), MemberPurchaseRecord::getGradeName, bo.getGradeName());
        lqw.eq(bo.getPrice() != null, MemberPurchaseRecord::getPrice, bo.getPrice());
        return lqw;
    }

    /**
     * 新增会员购买记录
     *
     * @param bo 会员购买记录
     * @return 是否新增成功
     */
    @Override
    public MemberPurchaseRecordVo insertByBo(MemberPurchaseRecordBo bo) {
        MemberPurchaseRecord add = MapstructUtils.convert(bo, MemberPurchaseRecord.class);
        if (add == null){
            return new MemberPurchaseRecordVo();
        }
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());

        }
        return MapstructUtils.convert(add, MemberPurchaseRecordVo.class);
    }

    /**
     * 修改会员购买记录
     *
     * @param bo 会员购买记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MemberPurchaseRecordBo bo) {
        MemberPurchaseRecord update = MapstructUtils.convert(bo, MemberPurchaseRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MemberPurchaseRecord entity){
        Date date = new Date();
        entity.setCreateTime(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);

        // 获取增加一年后的日期
        Date nextYearDate = calendar.getTime();
        entity.setEndTime(nextYearDate);
        entity.setStatus(ZERO);
        entity.setPayStatus(ZERO);
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员购买记录信息
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
    @Transactional
    public Boolean payLaterUpdateByBo(MemberPurchaseRecordBo bo) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (loginUser == null){
            return false;
        }
        if (bo.getPayInfo() == null){
            return false;
        }
        if (!ONE.equals(bo.getPayStatus())){
            return false;
        }
        MemberPurchaseRecord update = MapstructUtils.convert(bo, MemberPurchaseRecord.class);
        update.setStatus(ONE);
        update.setPayStatus(ONE);
        baseMapper.updateById(update);
        // 修改其余会员购买记录状态为  0 关闭
        baseMapper.updateOtherMemberInfoByUserID(loginUser.getUserId(),update.getId());
        // 修改会员基础信息中的会员等级
        AppletUserInformation auf = new AppletUserInformation();
        auf.setUserId(loginUser.getUserId());
        auf.setMemberLevelId(update.getMemberLevelId());
        userInformationMapper.updateById(auf);
        return true;
    }
}
