package org.dromara.flowerapplet.controller;

import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class WxPayCallbackControllerTest {

    @Mock
    private IPayService payService;

    @Mock
    private IFolwerAppletOrderService folwerAppletOrderService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        WxPayCallbackController controller = new WxPayCallbackController(payService, folwerAppletOrderService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void shouldReturnSuccessWhenPayCallbackCompletes() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setOutTradeNo("1001");
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(1001L);

        when(payService.confirmOrder(any(HttpServletRequest.class), any(HttpServletResponse.class))).thenReturn(transaction);
        when(folwerAppletOrderService.payCallbackOrder(transaction)).thenReturn(orderVo);

        mockMvc.perform(post("/wxpayback/pay/payCallback"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.orderId").value(1001));
    }

    @Test
    void shouldReturnFailWhenPayCallbackThrows() throws Exception {
        when(payService.confirmOrder(any(HttpServletRequest.class), any(HttpServletResponse.class)))
            .thenThrow(new RuntimeException("callback failed"));

        mockMvc.perform(post("/wxpayback/pay/payCallback"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(500))
            .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void shouldReturnSuccessWhenRefundCallbackCompletes() throws Exception {
        RefundNotification refundNotification = mock(RefundNotification.class);
        when(refundNotification.toString()).thenReturn("refund-notification");
        when(payService.refundNotify(any(HttpServletRequest.class))).thenReturn(refundNotification);

        mockMvc.perform(post("/wxpayback/pay/refundCallback"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data").value("refund-notification"));
    }

    @Test
    void shouldReturnFailWhenRefundCallbackThrows() throws Exception {
        when(payService.refundNotify(any(HttpServletRequest.class))).thenThrow(new RuntimeException("refund failed"));

        mockMvc.perform(post("/wxpayback/pay/refundCallback"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(500))
            .andExpect(jsonPath("$.data").value("refund failed"));
    }
}
