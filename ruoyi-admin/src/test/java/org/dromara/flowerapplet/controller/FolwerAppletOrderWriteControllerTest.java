package org.dromara.flowerapplet.controller;

import org.dromara.common.core.domain.R;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerAppletOrderWriteControllerTest {

    @Mock
    private IFolwerAppletOrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerAppletOrderController(orderService)).build();
    }

    @Test
    void addShouldReturnSuccess() throws Exception {
        when(orderService.insertByBo(any())).thenReturn(R.ok("created"));

        mockMvc.perform(post("/flowerapplet/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"UserId\":\"10\",\"skuId\":\"1001\",\"prodCount\":2}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void editShouldReturnSuccess() throws Exception {
        when(orderService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flowerapplet/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":1001,\"status\":5}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void updateByTransfeeShouldReturnSuccess() throws Exception {
        when(orderService.updateByOrderParam(any())).thenReturn(R.ok("updated"));

        mockMvc.perform(put("/flowerapplet/order/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"UserId\":\"10\",\"orderId\":\"1001\",\"dvyId\":\"1\",\"prodCount\":2}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void submitOrdersShouldReturnWrappedPaymentResponse() throws Exception {
        WxJsapiResponse response = new WxJsapiResponse();
        when(orderService.submitOrders(any())).thenReturn(R.ok(response));

        mockMvc.perform(post("/flowerapplet/order/submitOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderNumbers\":\"1001\",\"payType\":1}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void refundOrderShouldReturnSuccess() throws Exception {
        when(orderService.refundOrder(any())).thenReturn(R.ok("refunding"));

        mockMvc.perform(post("/flowerapplet/order/refundOrder")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"outTradeNo\":\"1001\",\"outRefundNo\":\"R-1\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void removeShouldReturnSuccess() throws Exception {
        when(orderService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flowerapplet/order/1001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
