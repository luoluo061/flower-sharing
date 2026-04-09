package org.dromara.flower.controller;

import org.dromara.flower.service.IFolwerCouponReceiveService;
import org.dromara.flower.service.IFolwerCouponService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerLegacyCouponControllerTest {

    @Mock
    private IFolwerCouponService couponService;
    @Mock
    private IFolwerCouponReceiveService couponReceiveService;

    @Test
    void legacyCouponControllerAddShouldReturnSuccess() {
        when(couponService.insertByBo(any())).thenReturn(true);
        FolwerCouponController controller = new FolwerCouponController(couponService);
        assertEquals(200, controller.add(new org.dromara.flower.domain.bo.FolwerCouponBo()).getCode());
    }

    @Test
    void legacyCouponReceiveControllerAddShouldReturnSuccess() {
        when(couponReceiveService.insertByBo(any())).thenReturn(true);
        FolwerCouponReceiveController controller = new FolwerCouponReceiveController(couponReceiveService);
        assertEquals(200, controller.add(new org.dromara.flower.domain.bo.FolwerCouponReceiveBo()).getCode());
    }
}
