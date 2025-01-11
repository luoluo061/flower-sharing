package org.dromara.flower.mapper;

import org.dromara.flower.domain.MarketingCouponReceive;
import org.dromara.flower.domain.bo.AppCouponRecordBo;
import org.dromara.flower.domain.bo.AppIsFlowerCouponsBo;
import org.dromara.flower.domain.bo.AppOrderConsumeBo;
import org.dromara.flower.domain.vo.MarketingCouponReceiveVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 优惠卷领取记录Mapper接口
 *
 * @author chy
 * @date 2025-01-08
 */
public interface MarketingCouponReceiveMapper extends BaseMapperPlus<MarketingCouponReceive, MarketingCouponReceiveVo> {


    public List<MarketingCouponReceiveVo> queryUserStateList(AppCouponRecordBo appCouponRecordBo);


    public List<MarketingCouponReceiveVo> queryUserConsumeList(AppOrderConsumeBo appOrderConsumeBo);


    public List<MarketingCouponReceiveVo> isFlowerCoupons(AppIsFlowerCouponsBo appIsFlowerCouponsBo);

}
