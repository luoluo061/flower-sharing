package org.dromara.flowerapplet.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerAppletOrderReadControllerTest {

    @Mock
    private IFolwerAppletOrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerAppletOrderController(orderService)).build();
    }

    @Test
    void listShouldReturnPagedAppletOrders() throws Exception {
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(201L);
        when(orderService.queryPageList(any(), any())).thenReturn(TableDataInfo.build(List.of(orderVo)));

        mockMvc.perform(get("/flowerapplet/order/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].orderId").value(201));
    }

    @Test
    void getInfoShouldReturnWrappedAppletOrder() throws Exception {
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(202L);
        when(orderService.queryById(202L)).thenReturn(orderVo);

        mockMvc.perform(get("/flowerapplet/order/202"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.orderId").value(202));
    }

    @Test
    void queryOrderShouldReturnDirectOrderView() throws Exception {
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(203L);
        when(orderService.queryOrder("203")).thenReturn(orderVo);

        mockMvc.perform(post("/flowerapplet/order/queryOrder/203"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.orderId").value(203));
    }
}
