package org.dromara.flower.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.vo.FolwerOrderInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.service.IFolwerOrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerOrderReadControllerTest {

    @Mock
    private IFolwerOrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerOrderController(orderService)).build();
    }

    @Test
    void listShouldReturnPagedOrders() throws Exception {
        FolwerOrderVo orderVo = new FolwerOrderVo();
        orderVo.setOrderId(101L);
        orderVo.setUserName("buyer");
        when(orderService.queryPageList(any(), any())).thenReturn(TableDataInfo.build(List.of(orderVo)));

        mockMvc.perform(get("/flower/order/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.total").value(1))
            .andExpect(jsonPath("$.rows[0].orderId").value(101));
    }

    @Test
    void getInfoShouldReturnWrappedOrder() throws Exception {
        FolwerOrderVo orderVo = new FolwerOrderVo();
        orderVo.setOrderId(102L);
        when(orderService.queryById(102L)).thenReturn(orderVo);

        mockMvc.perform(get("/flower/order/102"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.orderId").value(102));
    }

    @Test
    void getInfoByIdShouldReturnWrappedOrderInfo() throws Exception {
        FolwerOrderInfoVo orderInfoVo = new FolwerOrderInfoVo();
        when(orderService.queryInfoById(103L)).thenReturn(orderInfoVo);

        mockMvc.perform(get("/flower/order/info/103"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void createRefundShouldReturnRefundId() throws Exception {
        when(orderService.createRefund(anyLong())).thenReturn("5001");

        mockMvc.perform(post("/flower/order/createRefund/104"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.msg").value("5001"));
    }
}
