package org.dromara.flower.controller;

import org.dromara.flower.service.IFolwerOrderService;
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
class FolwerOrderWriteControllerTest {

    @Mock
    private IFolwerOrderService orderService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerOrderController(orderService)).build();
    }

    @Test
    void orderAddShouldReturnSuccess() throws Exception {
        when(orderService.insertByBo(any())).thenReturn(true);

        mockMvc.perform(post("/flower/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":10,\"memberLevelId\":1,\"status\":0,\"remarks\":\"create-order\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void orderEditShouldReturnSuccess() throws Exception {
        when(orderService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flower/order")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"orderId\":1001,\"userId\":10,\"status\":5,\"remarks\":\"updated-order\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void orderRemoveShouldReturnSuccess() throws Exception {
        when(orderService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flower/order/1001"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
