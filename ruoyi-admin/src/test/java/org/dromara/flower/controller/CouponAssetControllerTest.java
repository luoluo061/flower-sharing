package org.dromara.flower.controller;

import org.dromara.flower.domain.vo.MarketingCouponReceiveVo;
import org.dromara.flower.domain.vo.MarketingCouponVo;
import org.dromara.flower.service.IMarketingCouponReceiveService;
import org.dromara.flower.service.IMarketingCouponService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class CouponAssetControllerTest {

    @Mock
    private IMarketingCouponService marketingCouponService;
    @Mock
    private IMarketingCouponReceiveService marketingCouponReceiveService;

    private MockMvc couponMvc;
    private MockMvc couponReceiveMvc;

    @BeforeEach
    void setUp() {
        couponMvc = MockMvcBuilders.standaloneSetup(new MarketingCouponController(marketingCouponService)).build();
        couponReceiveMvc = MockMvcBuilders.standaloneSetup(new MarketingCouponReceiveController(marketingCouponReceiveService)).build();
    }

    @Test
    void marketingCouponGetInfoShouldReturnSuccess() throws Exception {
        when(marketingCouponService.queryById(1L)).thenReturn(new MarketingCouponVo());

        couponMvc.perform(get("/flower/coupon/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void marketingCouponAddShouldReturnSuccess() throws Exception {
        when(marketingCouponService.insertByBo(any())).thenReturn(true);

        couponMvc.perform(post("/flower/coupon")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"couponName\":\"spring\",\"applicableCategory\":0,\"couponNumber\":10,\"sorting\":1,\"couponKind\":0,\"couponType\":0,\"state\":0,\"startTime\":\"2026-04-01T00:00:00\",\"endTime\":\"2026-04-30T00:00:00\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void marketingCouponToggleStateShouldReturnSuccess() throws Exception {
        when(marketingCouponService.updateState(1L)).thenReturn(true);

        couponMvc.perform(put("/flower/coupon/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void marketingCouponReceiveGetInfoShouldReturnSuccess() throws Exception {
        when(marketingCouponReceiveService.queryById(2L)).thenReturn(new MarketingCouponReceiveVo());

        couponReceiveMvc.perform(get("/flower/couponReceive/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void marketingCouponReceiveAddShouldReturnSuccess() throws Exception {
        when(marketingCouponReceiveService.insertByBo(any())).thenReturn(true);

        couponReceiveMvc.perform(post("/flower/couponReceive")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"deptId\":1,\"couponId\":1,\"userId\":10,\"icon\":\"avatar.png\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
