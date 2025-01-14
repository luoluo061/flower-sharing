package org.dromara.flower.service;

import org.dromara.flower.domain.bo.AppCouponRecordBo;
import org.dromara.flower.domain.bo.AppIsFlowerCouponsBo;
import org.dromara.flower.domain.bo.AppOrderConsumeBo;
import org.dromara.flower.domain.vo.MarketingCouponReceiveVo;
import org.dromara.flower.domain.bo.MarketingCouponReceiveBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 优惠卷领取记录Service接口
 *
 * @author chy
 * @date 2025-01-08
 */
public interface IMarketingCouponReceiveService {

    /**
     * 查询优惠卷领取记录
     *
     * @param id 主键
     * @return 优惠卷领取记录
     */
    MarketingCouponReceiveVo queryById(Long id);

    /**
     * 分页查询优惠卷领取记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠卷领取记录分页列表
     */
    TableDataInfo<MarketingCouponReceiveVo> queryPageList(MarketingCouponReceiveBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的优惠卷领取记录列表
     *
     * @param bo 查询条件
     * @return 优惠卷领取记录列表
     */
    List<MarketingCouponReceiveVo> queryList(MarketingCouponReceiveBo bo);

    /**
     * 新增优惠卷领取记录
     *
     * @param bo 优惠卷领取记录
     * @return 是否新增成功
     */
    Boolean insertByBo(MarketingCouponReceiveBo bo);

    /**
     * 修改优惠卷领取记录
     *
     * @param bo 优惠卷领取记录
     * @return 是否修改成功
     */
    Boolean updateByBo(MarketingCouponReceiveBo bo);

    /**
     * 校验并批量删除优惠卷领取记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);


    /**
     * pc端，查询领取优惠券领取记录
     * @param id
     * @param pageQuery
     * @return
     */
    TableDataInfo<MarketingCouponReceiveVo> queryPageListByCouponId(Long id, PageQuery pageQuery);

    /**
     * 小程序用户，查询自己领取、已使用、已过期的优惠券
     * @param appCouponRecord
     * @return
     */
    TableDataInfo<MarketingCouponReceiveVo> queryUserStateList(AppCouponRecordBo appCouponRecord,PageQuery pageQuery);

    /**
     * 查询该商品可使用的所有花劵或者是优惠卷
     * @param appOrderConsumeBo
     * @return
     */
    List<MarketingCouponReceiveVo> queryUserConsumeList(AppOrderConsumeBo appOrderConsumeBo);

    /**
     * 查询该商品是否拥有花劵
     * @param appIsFlowerCouponsBo
     * @return
     */
    boolean isFlowerCoupons(AppIsFlowerCouponsBo appIsFlowerCouponsBo);
}
