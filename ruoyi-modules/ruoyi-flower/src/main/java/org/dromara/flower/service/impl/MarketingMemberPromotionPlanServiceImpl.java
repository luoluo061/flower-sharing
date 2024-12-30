package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MarketingMemberPromotionPlanBo;
import org.dromara.flower.domain.vo.MarketingMemberPromotionPlanVo;
import org.dromara.flower.domain.MarketingMemberPromotionPlan;
import org.dromara.flower.mapper.MarketingMemberPromotionPlanMapper;
import org.dromara.flower.service.IMarketingMemberPromotionPlanService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 营销推广-会员推广计划Service业务层处理
 *
 * @author chy
 * @date 2024-12-31
 */
@RequiredArgsConstructor
@Service
public class MarketingMemberPromotionPlanServiceImpl implements IMarketingMemberPromotionPlanService {

    private final MarketingMemberPromotionPlanMapper baseMapper;

    /**
     * 查询营销推广-会员推广计划
     *
     * @param id 主键
     * @return 营销推广-会员推广计划
     */
    @Override
    public MarketingMemberPromotionPlanVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询营销推广-会员推广计划列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 营销推广-会员推广计划分页列表
     */
    @Override
    public TableDataInfo<MarketingMemberPromotionPlanVo> queryPageList(MarketingMemberPromotionPlanBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MarketingMemberPromotionPlan> lqw = buildQueryWrapper(bo);
        Page<MarketingMemberPromotionPlanVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的营销推广-会员推广计划列表
     *
     * @param bo 查询条件
     * @return 营销推广-会员推广计划列表
     */
    @Override
    public List<MarketingMemberPromotionPlanVo> queryList(MarketingMemberPromotionPlanBo bo) {
        LambdaQueryWrapper<MarketingMemberPromotionPlan> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MarketingMemberPromotionPlan> buildQueryWrapper(MarketingMemberPromotionPlanBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MarketingMemberPromotionPlan> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MarketingMemberPromotionPlan::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getCode()), MarketingMemberPromotionPlan::getCode, bo.getCode());
        lqw.like(StringUtils.isNotBlank(bo.getName()), MarketingMemberPromotionPlan::getName, bo.getName());
        lqw.eq(bo.getActivityBegin() != null, MarketingMemberPromotionPlan::getActivityBegin, bo.getActivityBegin());
        lqw.eq(bo.getActivityEnd() != null, MarketingMemberPromotionPlan::getActivityEnd, bo.getActivityEnd());
        lqw.eq(bo.getStatus() != null, MarketingMemberPromotionPlan::getStatus, bo.getStatus());
        lqw.eq(bo.getNum() != null, MarketingMemberPromotionPlan::getNum, bo.getNum());
        lqw.eq(bo.getResidue() != null, MarketingMemberPromotionPlan::getResidue, bo.getResidue());
        lqw.eq(bo.getAward() != null, MarketingMemberPromotionPlan::getAward, bo.getAward());
        lqw.eq(bo.getRewardAmount() != null, MarketingMemberPromotionPlan::getRewardAmount, bo.getRewardAmount());
        lqw.eq(StringUtils.isNotBlank(bo.getUnit()), MarketingMemberPromotionPlan::getUnit, bo.getUnit());
        lqw.eq(bo.getMaxRewar() != null, MarketingMemberPromotionPlan::getMaxRewar, bo.getMaxRewar());
        lqw.eq(bo.getSuperposition() != null, MarketingMemberPromotionPlan::getSuperposition, bo.getSuperposition());
        lqw.eq(StringUtils.isNotBlank(bo.getDeclare()), MarketingMemberPromotionPlan::getDeclare, bo.getDeclare());
        return lqw;
    }

    /**
     * 新增营销推广-会员推广计划
     *
     * @param bo 营销推广-会员推广计划
     * @return 是否新增成功
     */
    @Override
    @Transactional
    public Boolean insertByBo(MarketingMemberPromotionPlanBo bo) {
        MarketingMemberPromotionPlan add = MapstructUtils.convert(bo, MarketingMemberPromotionPlan.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;





    }

    /**
     * 修改营销推广-会员推广计划
     *
     * @param bo 营销推广-会员推广计划
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MarketingMemberPromotionPlanBo bo) {
        MarketingMemberPromotionPlan update = MapstructUtils.convert(bo, MarketingMemberPromotionPlan.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MarketingMemberPromotionPlan entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除营销推广-会员推广计划信息
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
}
