package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MarketingCouponBo;
import org.dromara.flower.domain.vo.MarketingCouponVo;
import org.dromara.flower.domain.MarketingCoupon;
import org.dromara.flower.mapper.MarketingCouponMapper;
import org.dromara.flower.service.IMarketingCouponService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 优惠卷管理Service业务层处理
 *
 * @author chy
 * @date 2025-01-08
 */
@RequiredArgsConstructor
@Service
public class MarketingCouponServiceImpl implements IMarketingCouponService {

    private final MarketingCouponMapper baseMapper;

    /**
     * 查询优惠卷管理
     *
     * @param id 主键
     * @return 优惠卷管理
     */
    @Override
    public MarketingCouponVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询优惠卷管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠卷管理分页列表
     */
    @Override
    public TableDataInfo<MarketingCouponVo> queryPageList(MarketingCouponBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MarketingCoupon> lqw = buildQueryWrapper(bo);
        lqw.orderByAsc(MarketingCoupon::getSort);

        Page<MarketingCouponVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);


        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的优惠卷管理列表
     *
     * @param bo 查询条件
     * @return 优惠卷管理列表
     */
    @Override
    public List<MarketingCouponVo> queryList(MarketingCouponBo bo) {
        LambdaQueryWrapper<MarketingCoupon> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MarketingCoupon> buildQueryWrapper(MarketingCouponBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MarketingCoupon> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MarketingCoupon::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getCouponName()), MarketingCoupon::getCouponName, bo.getCouponName());
        lqw.eq(StringUtils.isNotBlank(bo.getCouponDescription()), MarketingCoupon::getCouponDescription, bo.getCouponDescription());
        lqw.eq(bo.getApplicableCategory() != null, MarketingCoupon::getApplicableCategory, bo.getApplicableCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getClassificationId()), MarketingCoupon::getClassificationId, bo.getClassificationId());
        lqw.eq(StringUtils.isNotBlank(bo.getGoodsId()), MarketingCoupon::getGoodsId, bo.getGoodsId());
        lqw.eq(bo.getCouponType() != null, MarketingCoupon::getCouponType, bo.getCouponType());
        lqw.eq(bo.getCouponKind() != null, MarketingCoupon::getCouponKind, bo.getCouponKind());
        lqw.eq(bo.getStartTime() != null, MarketingCoupon::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, MarketingCoupon::getEndTime, bo.getEndTime());
        lqw.eq(bo.getFullReductionAmount() != null, MarketingCoupon::getFullReductionAmount, bo.getFullReductionAmount());
        lqw.eq(bo.getCouponSum() != null, MarketingCoupon::getCouponSum, bo.getCouponSum());
        lqw.eq(bo.getCouponNumber() != null, MarketingCoupon::getCouponNumber, bo.getCouponNumber());
        lqw.eq(bo.getSurplusNumber() != null, MarketingCoupon::getSurplusNumber, bo.getSurplusNumber());
        lqw.eq(bo.getSort() != null, MarketingCoupon::getSort, bo.getSort());
        lqw.eq(bo.getState() != null, MarketingCoupon::getState, bo.getState());
        lqw.eq(StringUtils.isNotBlank(bo.getSpecificMembershipLevel()), MarketingCoupon::getSpecificMembershipLevel, bo.getSpecificMembershipLevel());
        lqw.eq(StringUtils.isNotBlank(bo.getSpecificUsersId()), MarketingCoupon::getSpecificUsersId, bo.getSpecificUsersId());
        return lqw;
    }

    /**
     * 新增优惠卷管理
     *
     * @param bo 优惠卷管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MarketingCouponBo bo) {
        MarketingCoupon add = MapstructUtils.convert(bo, MarketingCoupon.class);
        validEntityBeforeSave(add);

        // 设置剩余数量
        add.setSurplusNumber(add.getCouponNumber());
        boolean flag = baseMapper.insert(add) > 0;


        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改优惠卷管理
     *
     * @param bo 优惠卷管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MarketingCouponBo bo) {
        MarketingCoupon update = MapstructUtils.convert(bo, MarketingCoupon.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MarketingCoupon entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除优惠卷管理信息
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
     * 删除单个优惠卷
     * @param id
     * @return
     */

    @Override
    public boolean deleteOneById(Long id) {
        MarketingCouponVo marketingCouponVo = baseMapper.selectVoById(id);
        if (ObjectUtils.isEmpty(marketingCouponVo)) throw new ServiceException("该数据不存在");

        return baseMapper.deleteById(id) >0 ;
    }


    /**
     * 切换优惠卷状态
     * @param id
     * @return
     */
    @Override
    public boolean updateState(Long id) {
        MarketingCoupon marketingCoupon = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(marketingCoupon)) throw new ServiceException("优惠券不存在，删除失败");

        // 设置优惠券为开放领取中
        if (marketingCoupon.getState()==0) {
            // 剩余数量已经用完，用户重新修改优惠券数量
            if (marketingCoupon.getSurplusNumber() == 0L)
                throw new ServiceException("优惠券数量派发完毕，请先修改数量！");


            //优惠券有效时间已过期，用户重新修改优惠券时间
            Date endTime = marketingCoupon.getEndTime();
            Date currentTime = new Date();
            if (endTime.before(currentTime)) throw new ServiceException("优惠券时间已过期，请重新修改优惠券使用时间");




        }


        return false;
    }



}
