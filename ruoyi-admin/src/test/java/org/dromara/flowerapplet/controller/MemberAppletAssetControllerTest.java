package org.dromara.flowerapplet.controller;

import org.dromara.common.core.domain.R;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flowerapplet.service.IMemberAppletLevelPrivilegeService;
import org.dromara.flowerapplet.service.IMemberAppletLevelService;
import org.dromara.flowerapplet.service.IMemberAppletPurchaseRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class MemberAppletAssetControllerTest {

    @Mock
    private IMemberAppletLevelService memberLevelService;
    @Mock
    private IMemberAppletLevelPrivilegeService memberLevelPrivilegeService;
    @Mock
    private IMemberAppletPurchaseRecordService memberPurchaseRecordService;

    private MockMvc levelMvc;
    private MockMvc privilegeMvc;
    private MockMvc purchaseMvc;

    @BeforeEach
    void setUp() {
        levelMvc = MockMvcBuilders.standaloneSetup(new MemberAppletLevelController(memberLevelService)).build();
        privilegeMvc = MockMvcBuilders.standaloneSetup(new MemberAppletLevelPrivilegeController(memberLevelPrivilegeService)).build();
        purchaseMvc = MockMvcBuilders.standaloneSetup(new MemberAppletPurchaseRecordController(memberPurchaseRecordService)).build();
    }

    @Test
    void appletMemberLevelGetInfoShouldReturnSuccess() throws Exception {
        when(memberLevelService.queryById(1L)).thenReturn(new MemberLevelVo());

        levelMvc.perform(get("/flowerApplet/level/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void appletMemberLevelTreeShouldReturnSuccess() throws Exception {
        when(memberLevelService.getMemberLevelTree()).thenReturn(R.ok(List.of(Map.of("id", "1", "label", "vip"))));

        levelMvc.perform(get("/flowerApplet/level/tree"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void appletMemberLevelPrivilegeGetInfoShouldReturnSuccess() throws Exception {
        when(memberLevelPrivilegeService.queryById(2L)).thenReturn(new MemberLevelPrivilegeVo());

        privilegeMvc.perform(get("/flowerApplet/levelPrivilege/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void appletMemberLevelPrivilegePurchaseShouldReturnSuccess() throws Exception {
        when(memberLevelPrivilegeService.getPurchasPrivilege(3L)).thenReturn(new MemberPurchaseRecordVo());

        privilegeMvc.perform(get("/flowerApplet/levelPrivilege/privilege/3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void appletMemberPurchaseGetInfoShouldReturnSuccess() throws Exception {
        when(memberPurchaseRecordService.queryById(4L)).thenReturn(new MemberPurchaseRecordVo());

        purchaseMvc.perform(get("/flowerApplet/purchaseRecord/4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void appletMemberPurchaseSubmitOrderShouldReturnSuccess() throws Exception {
        when(memberPurchaseRecordService.submitOrders(any())).thenReturn(R.ok(new WxJsapiResponse()));

        purchaseMvc.perform(post("/flowerApplet/purchaseRecord/submitOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderNumbers\":\"1001\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void appletMemberPurchaseRefundShouldReturnSuccess() throws Exception {
        when(memberPurchaseRecordService.refundOrder(any())).thenReturn(R.ok("refunding"));

        purchaseMvc.perform(post("/flowerApplet/purchaseRecord/refundOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"outTradeNo\":\"1001\",\"outRefundNo\":\"R-1\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
