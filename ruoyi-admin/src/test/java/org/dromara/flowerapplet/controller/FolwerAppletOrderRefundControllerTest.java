package org.dromara.flowerapplet.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderRefundVo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderRefundService;
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
class FolwerAppletOrderRefundControllerTest {

    @Mock
    private IFolwerAppletOrderRefundService orderRefundService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerAppletOrderRefundController(orderRefundService)).build();
    }

    @Test
    void listShouldReturnPagedRefunds() throws Exception {
        FolwerAppletOrderRefundVo vo = new FolwerAppletOrderRefundVo();
        vo.setRefundId(701L);
        when(orderRefundService.queryPageList(any(), any())).thenReturn(TableDataInfo.build(List.of(vo)));

        mockMvc.perform(get("/flowerapplet/orderRefund/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].refundId").value(701));
    }

    @Test
    void getInfoShouldReturnWrappedRefund() throws Exception {
        FolwerAppletOrderRefundVo vo = new FolwerAppletOrderRefundVo();
        vo.setRefundId(702L);
        when(orderRefundService.queryById(702L)).thenReturn(vo);

        mockMvc.perform(get("/flowerapplet/orderRefund/702"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.refundId").value(702));
    }

    @Test
    void addShouldReturnCreatedRefundId() throws Exception {
        when(orderRefundService.insertByBo(any())).thenReturn("703");

        mockMvc.perform(post("/flowerapplet/orderRefund")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":\"1001\",\"refundMsg\":\"broken\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.msg").value("703"))
            .andExpect(jsonPath("$.data").doesNotExist());
    }

    @Test
    void editShouldReturnSuccess() throws Exception {
        when(orderRefundService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flowerapplet/orderRefund")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"refundId\":704,\"orderId\":\"1001\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void removeShouldReturnSuccess() throws Exception {
        when(orderRefundService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flowerapplet/orderRefund/705"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
