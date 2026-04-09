package org.dromara.flower.service.impl;

import org.dromara.flower.domain.MarketingCoupon;
import org.dromara.flower.mapper.MarketingCouponMapper;
import org.dromara.flower.mapper.MarketingCouponReceiveMapper;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.service.domain.CouponAssetDomainService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class MarketingCouponServiceImplTest {

    @Mock
    private MarketingCouponMapper baseMapper;
    @Mock
    private AppletUserInformationMapper appletUserInformationMapper;
    @Mock
    private MarketingCouponReceiveMapper couponReceiveMapper;
    @Mock
    private CouponAssetDomainService couponAssetDomainService;

    @InjectMocks
    private MarketingCouponServiceImpl service;

    @Test
    void updateStateShouldUseCouponAssetDomainToggle() {
        MarketingCoupon coupon = new MarketingCoupon();
        coupon.setState(0L);
        coupon.setSurplusNumber(3L);
        coupon.setEndTime(new java.util.Date(System.currentTimeMillis() + 10000));
        when(baseMapper.selectById(1L)).thenReturn(coupon);
        when(couponAssetDomainService.toggleCouponState(0L)).thenReturn(1L);
        when(baseMapper.update(any())).thenReturn(1);

        assertTrue(service.updateState(1L));
        verify(couponAssetDomainService).toggleCouponState(0L);
    }
}
