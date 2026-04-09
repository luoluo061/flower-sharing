package org.dromara.flower.controller;

import org.dromara.flower.domain.vo.FolwerCreditGetrecordsVo;
import org.dromara.flower.domain.vo.FolwerCreditSetVo;
import org.dromara.flower.domain.vo.MemberPointsExchangeGoldVo;
import org.dromara.flower.service.IFolwerCreditGetrecordsService;
import org.dromara.flower.service.IFolwerCreditSetService;
import org.dromara.flower.service.IMemberPointsExchangeGoldService;
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
class PointsAssetControllerTest {

    @Mock
    private IFolwerCreditSetService creditSetService;
    @Mock
    private IFolwerCreditGetrecordsService creditGetrecordsService;
    @Mock
    private IMemberPointsExchangeGoldService exchangeGoldService;

    private MockMvc creditSetMvc;
    private MockMvc creditGetMvc;
    private MockMvc exchangeMvc;

    @BeforeEach
    void setUp() {
        creditSetMvc = MockMvcBuilders.standaloneSetup(new FolwerCreditSetController(creditSetService)).build();
        creditGetMvc = MockMvcBuilders.standaloneSetup(new FolwerCreditGetrecordsController(creditGetrecordsService)).build();
        exchangeMvc = MockMvcBuilders.standaloneSetup(new MemberPointsExchangeGoldController(exchangeGoldService)).build();
    }

    @Test
    void creditSetGetInfoShouldReturnSuccess() throws Exception {
        when(creditSetService.queryById(1L)).thenReturn(new FolwerCreditSetVo());

        creditSetMvc.perform(get("/flower/creditSet/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void creditGetrecordsGetInfoShouldReturnSuccess() throws Exception {
        when(creditGetrecordsService.queryById(2L)).thenReturn(new FolwerCreditGetrecordsVo());

        creditGetMvc.perform(get("/flower/creditGetrecords/2"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void pointsExchangeGetInfoShouldReturnSuccess() throws Exception {
        when(exchangeGoldService.queryById(3L)).thenReturn(new MemberPointsExchangeGoldVo());

        exchangeMvc.perform(get("/flower/pointsExchangeGold/3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void pointsExchangeGoldShouldReturnSuccess() throws Exception {
        when(exchangeGoldService.exchangeGoldByBo(any())).thenReturn(true);

        exchangeMvc.perform(put("/flower/pointsExchangeGold/exchangeGold")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":10,\"modifiedValue\":5}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void creditSetAddShouldReturnSuccess() throws Exception {
        when(creditSetService.insertByBo(any())).thenReturn(true);

        creditSetMvc.perform(post("/flower/creditSet")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"goodsPurchase\":1,\"goodsCredit\":2}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
