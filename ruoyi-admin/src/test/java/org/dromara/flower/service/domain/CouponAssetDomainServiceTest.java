package org.dromara.flower.service.domain;

import org.dromara.flower.domain.MarketingCoupon;
import org.dromara.flower.domain.MarketingCouponReceive;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("dev")
class CouponAssetDomainServiceTest {

    private final CouponAssetDomainService service = new CouponAssetDomainService();

    @Test
    void prepareCouponPublishShouldLockSurplusNumber() {
        MarketingCoupon coupon = new MarketingCoupon();
        coupon.setCouponNumber(9L);

        MarketingCoupon result = service.prepareCouponPublish(coupon);

        assertEquals(9L, result.getSurplusNumber());
    }

    @Test
    void prepareCouponReceiveShouldFillCurrentUserSnapshot() {
        MarketingCouponReceive receive = new MarketingCouponReceive();
        AppletUserInformation user = new AppletUserInformation();
        user.setName("tester");
        user.setPhone("13800000000");

        service.prepareCouponReceive(receive, user);

        assertEquals(0L, receive.getState());
        assertEquals("tester", receive.getUserName());
        assertEquals("13800000000", receive.getPhone());
    }

    @Test
    void isCouponUsableShouldRespectExpiryAndSurplus() {
        MarketingCoupon usable = new MarketingCoupon();
        usable.setSurplusNumber(1L);
        usable.setEndTime(new Date(System.currentTimeMillis() + 10_000));
        assertTrue(service.isCouponUsable(usable, new Date()));

        MarketingCoupon empty = new MarketingCoupon();
        empty.setSurplusNumber(0L);
        empty.setEndTime(new Date(System.currentTimeMillis() + 10_000));
        assertFalse(service.isCouponUsable(empty, new Date()));
    }
}
