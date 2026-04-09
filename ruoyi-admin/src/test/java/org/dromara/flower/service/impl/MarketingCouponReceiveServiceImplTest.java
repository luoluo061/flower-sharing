package org.dromara.flower.service.impl;

import org.dromara.flower.domain.MarketingCouponReceive;
import org.dromara.flower.mapper.MarketingCouponMapper;
import org.dromara.flower.mapper.MarketingCouponReceiveMapper;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.service.domain.CouponAssetDomainService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class MarketingCouponReceiveServiceImplTest {

    @Mock
    private MarketingCouponReceiveMapper baseMapper;
    @Mock
    private MarketingCouponMapper marketingCouponMapper;
    @Mock
    private AppletUserInformationMapper appletUserInformationMapper;
    @Mock
    private CouponAssetDomainService couponAssetDomainService;

    @InjectMocks
    private MarketingCouponReceiveServiceImpl service;

    @Test
    void prepareReceiveRecordShouldUseCouponAssetReceivePreparation() {
        MarketingCouponReceive receive = new MarketingCouponReceive();
        receive.setCouponId(1L);
        AppletUserInformation user = new AppletUserInformation();
        user.setUserId(10L);
        user.setName("buyer");
        user.setPhone("13800138000");
        MarketingCouponReceive prepared = new MarketingCouponReceive();
        when(couponAssetDomainService.prepareCouponReceive(receive, user)).thenReturn(prepared);

        assertSame(prepared, service.prepareCouponReceiveRecord(receive, user));
        verify(couponAssetDomainService).prepareCouponReceive(receive, user);
    }
}
