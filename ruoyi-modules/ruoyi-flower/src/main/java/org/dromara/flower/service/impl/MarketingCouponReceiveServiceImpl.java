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
import org.dromara.flower.domain.bo.MarketingCouponReceiveBo;
import org.dromara.flower.domain.vo.MarketingCouponReceiveVo;
import org.dromara.flower.domain.MarketingCouponReceive;
import org.dromara.flower.mapper.MarketingCouponReceiveMapper;
import org.dromara.flower.service.IMarketingCouponReceiveService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 优惠卷领取记录Service业务层处理
 *
 * @author chy
 * @date 2025-01-08
 */
@RequiredArgsConstructor
@Service
public class MarketingCouponReceiveServiceImpl implements IMarketingCouponReceiveService {

    private final MarketingCouponReceiveMapper baseMapper;

    /**
     * 查询优惠卷领取记录
     *
     * @param id 主键
     * @return 优惠卷领取记录
     */
    @Override
    public MarketingCouponReceiveVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询优惠卷领取记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠卷领取记录分页列表
     */
    @Override
    public TableDataInfo<MarketingCouponReceiveVo> queryPageList(MarketingCouponReceiveBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MarketingCouponReceive> lqw = buildQueryWrapper(bo);
        Page<MarketingCouponReceiveVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的优惠卷领取记录列表
     *
     * @param bo 查询条件
     * @return 优惠卷领取记录列表
     */
    @Override
    public List<MarketingCouponReceiveVo> queryList(MarketingCouponReceiveBo bo) {
        LambdaQueryWrapper<MarketingCouponReceive> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MarketingCouponReceive> buildQueryWrapper(MarketingCouponReceiveBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MarketingCouponReceive> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MarketingCouponReceive::getDeptId, bo.getDeptId());
        lqw.eq(bo.getCouponId() != null, MarketingCouponReceive::getCouponId, bo.getCouponId());
        lqw.eq(bo.getUserId() != null, MarketingCouponReceive::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), MarketingCouponReceive::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), MarketingCouponReceive::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), MarketingCouponReceive::getIcon, bo.getIcon());
        lqw.eq(bo.getState() != null, MarketingCouponReceive::getState, bo.getState());
        lqw.eq(bo.getStartTime() != null, MarketingCouponReceive::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, MarketingCouponReceive::getEndTime, bo.getEndTime());
        return lqw;
    }

    /**
     * 新增优惠卷领取记录
     *
     * @param bo 优惠卷领取记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MarketingCouponReceiveBo bo) {
        MarketingCouponReceive add = MapstructUtils.convert(bo, MarketingCouponReceive.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改优惠卷领取记录
     *
     * @param bo 优惠卷领取记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MarketingCouponReceiveBo bo) {
        MarketingCouponReceive update = MapstructUtils.convert(bo, MarketingCouponReceive.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MarketingCouponReceive entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除优惠卷领取记录信息
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
