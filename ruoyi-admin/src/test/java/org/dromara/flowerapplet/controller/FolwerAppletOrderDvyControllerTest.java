package org.dromara.flowerapplet.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDvyVo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDvyService;
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
class FolwerAppletOrderDvyControllerTest {

    @Mock
    private IFolwerAppletOrderDvyService orderDvyService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerAppletOrderDvyController(orderDvyService)).build();
    }

    @Test
    void listShouldReturnPagedOrderDvy() throws Exception {
        FolwerAppletOrderDvyVo vo = new FolwerAppletOrderDvyVo();
        vo.setOrderDevId(501L);
        when(orderDvyService.queryPageList(any(), any())).thenReturn(TableDataInfo.build(List.of(vo)));

        mockMvc.perform(get("/flower/orderDvy/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].orderDevId").value(501));
    }

    @Test
    void getInfoShouldReturnWrappedOrderDvy() throws Exception {
        FolwerAppletOrderDvyVo vo = new FolwerAppletOrderDvyVo();
        vo.setOrderDevId(502L);
        when(orderDvyService.queryById(502L)).thenReturn(vo);

        mockMvc.perform(get("/flower/orderDvy/502"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.orderDevId").value(502));
    }

    @Test
    void addShouldReturnWrappedOrderDvy() throws Exception {
        FolwerAppletOrderDvyVo vo = new FolwerAppletOrderDvyVo();
        vo.setOrderDevId(503L);
        when(orderDvyService.insertByBo(any())).thenReturn(vo);

        mockMvc.perform(post("/flower/orderDvy")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":1001,\"dvyId\":1,\"insulationNum\":1}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.orderDevId").value(503));
    }

    @Test
    void editShouldReturnWrappedOrderDvy() throws Exception {
        FolwerAppletOrderDvyVo vo = new FolwerAppletOrderDvyVo();
        vo.setOrderDevId(504L);
        when(orderDvyService.updateByBo(any())).thenReturn(vo);

        mockMvc.perform(put("/flower/orderDvy")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderDevId\":504,\"orderId\":1001,\"dvyId\":1,\"insulationNum\":2}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.orderDevId").value(504));
    }

    @Test
    void removeShouldReturnSuccess() throws Exception {
        when(orderDvyService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flower/orderDvy/505"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
