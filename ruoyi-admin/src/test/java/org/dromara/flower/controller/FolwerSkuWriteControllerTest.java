package org.dromara.flower.controller;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerSkuWriteControllerTest {

    @Mock
    private IFolwerSkuService skuService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerSkuController(skuService)).build();
    }

    @Test
    void skuAddShouldReturnSuccess() throws Exception {
        when(skuService.insertByBo(any())).thenReturn(true);

        mockMvc.perform(post("/flower/sku")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"prodId\":100,\"price\":10.5,\"minPrice\":9.5,\"actualStocks\":8,\"status\":1}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void skuBatchAddShouldReturnSuccess() throws Exception {
        when(skuService.batchInsertByBo(any())).thenReturn(true);

        mockMvc.perform(post("/flower/sku/batchAdd")
                .contentType(MediaType.APPLICATION_JSON)
                .content("[{\"prodId\":100,\"price\":10.5,\"minPrice\":9.5,\"actualStocks\":8,\"status\":1}]"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void skuEditShouldReturnSuccess() throws Exception {
        when(skuService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flower/sku")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"skuId\":11,\"prodId\":100,\"price\":12.5,\"minPrice\":8.5,\"actualStocks\":3,\"status\":1}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void skuRemoveShouldReturnSuccess() throws Exception {
        when(skuService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flower/sku/11"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
