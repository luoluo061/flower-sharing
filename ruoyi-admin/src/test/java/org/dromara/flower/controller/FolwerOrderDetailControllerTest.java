package org.dromara.flower.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.vo.FolwerOrderDetailVo;
import org.dromara.flower.service.IFolwerOrderDetailService;
import org.dromara.flower.service.IFolwerSkuService;
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
class FolwerOrderDetailControllerTest {

    @Mock
    private IFolwerOrderDetailService orderDetailService;
    @Mock
    private IFolwerSkuService skuService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerOrderDetailController(orderDetailService, skuService)).build();
    }

    @Test
    void listShouldReturnPagedDetails() throws Exception {
        FolwerOrderDetailVo detailVo = new FolwerOrderDetailVo();
        detailVo.setId(301L);
        when(orderDetailService.queryPageList(any(), any())).thenReturn(TableDataInfo.build(List.of(detailVo)));

        mockMvc.perform(get("/flower/orderDetail/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].id").value(301));
    }

    @Test
    void getInfoShouldReturnWrappedDetail() throws Exception {
        FolwerOrderDetailVo detailVo = new FolwerOrderDetailVo();
        detailVo.setId(302L);
        when(orderDetailService.queryById(302L)).thenReturn(detailVo);

        mockMvc.perform(get("/flower/orderDetail/302"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").value(302));
    }

    @Test
    void addShouldReturnSuccess() throws Exception {
        when(orderDetailService.insertByBo(any())).thenReturn(true);

        mockMvc.perform(post("/flower/orderDetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"skuId\":10,\"orderId\":\"O-1\",\"productName\":\"rose\",\"number\":2}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void editShouldReturnSuccess() throws Exception {
        when(orderDetailService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flower/orderDetail")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":302,\"skuId\":10,\"orderId\":\"O-1\",\"productName\":\"rose-2\",\"number\":3}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void removeShouldReturnSuccess() throws Exception {
        when(orderDetailService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flower/orderDetail/302"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
