package org.dromara.flowerapplet.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDetailService;
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
class FolwerAppletOrderDetailControllerTest {

    @Mock
    private IFolwerAppletOrderDetailService orderDetailService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerAppletOrderDetailController(orderDetailService)).build();
    }

    @Test
    void listShouldReturnPagedOrderDetails() throws Exception {
        FolwerAppletOrderDetailVo vo = new FolwerAppletOrderDetailVo();
        vo.setId(601L);
        when(orderDetailService.queryPageList(any(), any())).thenReturn(TableDataInfo.build(List.of(vo)));

        mockMvc.perform(get("/flowerapplet/orderDetail/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].id").value(601));
    }

    @Test
    void getInfoShouldReturnWrappedOrderDetail() throws Exception {
        FolwerAppletOrderDetailVo vo = new FolwerAppletOrderDetailVo();
        vo.setId(602L);
        when(orderDetailService.queryById(602L)).thenReturn(vo);

        mockMvc.perform(get("/flowerapplet/orderDetail/602"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").value(602));
    }

    @Test
    void addShouldReturnSuccess() throws Exception {
        when(orderDetailService.insertByBo(any())).thenReturn(true);

        mockMvc.perform(post("/flowerapplet/orderDetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":\"1001\",\"productName\":\"rose\",\"number\":2}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void editShouldReturnSuccess() throws Exception {
        when(orderDetailService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flowerapplet/orderDetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":603,\"orderId\":\"1001\",\"productName\":\"rose\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void removeShouldReturnSuccess() throws Exception {
        when(orderDetailService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flowerapplet/orderDetail/604"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
