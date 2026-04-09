package org.dromara.flower.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.vo.FolwerOrderRefundInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.service.IFolwerOrderRefundService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerOrderRefundControllerTest {

    @Mock
    private IFolwerOrderRefundService refundService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerOrderRefundController(refundService)).build();
    }

    @Test
    void listShouldReturnPagedRefunds() throws Exception {
        FolwerOrderRefundVo refundVo = new FolwerOrderRefundVo();
        refundVo.setRefundId(401L);
        when(refundService.queryPageList(any(), any())).thenReturn(TableDataInfo.build(List.of(refundVo)));

        mockMvc.perform(get("/flower/orderRefund/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].refundId").value(401));
    }

    @Test
    void getInfoShouldReturnWrappedRefund() throws Exception {
        FolwerOrderRefundVo refundVo = new FolwerOrderRefundVo();
        refundVo.setRefundId(402L);
        when(refundService.queryById(402L)).thenReturn(refundVo);

        mockMvc.perform(get("/flower/orderRefund/402"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.refundId").value(402));
    }

    @Test
    void getInfoByIdShouldReturnWrappedRefundInfo() throws Exception {
        when(refundService.queryInfoById(403L)).thenReturn(new FolwerOrderRefundInfoVo());

        mockMvc.perform(get("/flower/orderRefund/info/403"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void addShouldReturnSuccess() throws Exception {
        when(refundService.insertByBo(any())).thenReturn(true);

        mockMvc.perform(post("/flower/orderRefund")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":\"O-1\",\"refundAmount\":18.8,\"refundStatus\":2}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void editShouldReturnSuccess() throws Exception {
        when(refundService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flower/orderRefund")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"refundId\":402,\"orderId\":\"O-1\",\"refundAmount\":18.8,\"refundStatus\":1}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void removeShouldReturnSuccess() throws Exception {
        when(refundService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flower/orderRefund/402"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void submitRefundOrderShouldReturnWrappedRefund() throws Exception {
        FolwerOrderRefundVo refundVo = new FolwerOrderRefundVo();
        refundVo.setRefundId(404L);
        when(refundService.submitRefundOrders(404L)).thenReturn(refundVo);

        mockMvc.perform(post("/flower/orderRefund/submitRefundOrder/404"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.refundId").value(404));
    }
}
