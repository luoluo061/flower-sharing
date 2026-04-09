package org.dromara.flower.controller;

import org.dromara.common.core.domain.R;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flower.service.IMemberLevelPrivilegeService;
import org.dromara.flower.service.IMemberLevelService;
import org.dromara.flower.service.IMemberPurchaseRecordService;
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
class MemberAssetControllerTest {

    @Mock
    private IMemberLevelService memberLevelService;
    @Mock
    private IMemberLevelPrivilegeService memberLevelPrivilegeService;
    @Mock
    private IMemberPurchaseRecordService memberPurchaseRecordService;

    private MockMvc levelMvc;
    private MockMvc privilegeMvc;
    private MockMvc purchaseMvc;

    @BeforeEach
    void setUp() {
        levelMvc = MockMvcBuilders.standaloneSetup(new MemberLevelController(memberLevelService)).build();
        privilegeMvc = MockMvcBuilders.standaloneSetup(new MemberLevelPrivilegeController(memberLevelPrivilegeService)).build();
        purchaseMvc = MockMvcBuilders.standaloneSetup(new MemberPurchaseRecordController(memberPurchaseRecordService)).build();
    }

    @Test
    void memberLevelGetInfoShouldReturnSuccess() throws Exception {
        when(memberLevelService.queryById(1L)).thenReturn(new MemberLevelVo());

        levelMvc.perform(get("/flower/level/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void memberLevelTreeShouldReturnSuccess() throws Exception {
        when(memberLevelService.getMemberLevelTree()).thenReturn(R.ok(List.of(Map.of("id", "1", "label", "vip"))));

        levelMvc.perform(get("/flower/level/tree"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void memberLevelPrivilegeGetInfoShouldReturnSuccess() throws Exception {
        when(memberLevelPrivilegeService.queryById(2L)).thenReturn(new MemberLevelPrivilegeVo());

        privilegeMvc.perform(get("/flower/levelPrivilege/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void memberPurchaseRecordGetInfoShouldReturnSuccess() throws Exception {
        when(memberPurchaseRecordService.queryById(4L)).thenReturn(new MemberPurchaseRecordVo());

        purchaseMvc.perform(get("/flower/purchaseRecord/4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void memberPurchaseRecordAddShouldReturnSuccess() throws Exception {
        when(memberPurchaseRecordService.insertByBo(any())).thenReturn(true);

        purchaseMvc.perform(post("/flower/purchaseRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"memberLevelId\":1,\"memberName\":\"vip-user\",\"price\":99.00}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void memberPurchaseRecordEditShouldReturnSuccess() throws Exception {
        when(memberPurchaseRecordService.updateByBo(any())).thenReturn(true);

        purchaseMvc.perform(put("/flower/purchaseRecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":5,\"memberLevelId\":1,\"memberName\":\"vip-user\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
