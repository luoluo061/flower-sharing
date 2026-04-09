package org.dromara.flower.service.domain;

import org.dromara.flower.domain.MarketingCoupon;
import org.dromara.flower.domain.MarketingCouponReceive;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class CouponAssetDomainService {

    public MarketingCoupon prepareCouponPublish(MarketingCoupon coupon) {
        if (Objects.isNull(coupon)) {
            return null;
        }
        coupon.setSurplusNumber(coupon.getCouponNumber());
        return coupon;
    }

    public MarketingCouponReceive prepareCouponReceive(MarketingCouponReceive receive,
                                                       AppletUserInformation userInformation) {
        if (Objects.isNull(receive) || Objects.isNull(userInformation)) {
            return receive;
        }
        receive.setState(0L);
        receive.setUserName(userInformation.getName());
        receive.setPhone(userInformation.getPhone());
        return receive;
    }

    public boolean isCouponUsable(MarketingCoupon coupon, Date now) {
        if (Objects.isNull(coupon)) {
            return false;
        }
        if (Objects.nonNull(coupon.getSurplusNumber()) && coupon.getSurplusNumber().equals(0L)) {
            return false;
        }
        return Objects.nonNull(coupon.getEndTime()) && !coupon.getEndTime().before(now);
    }
}
