package org.dromara.flower.controller;

import org.dromara.flower.service.IFolwerProductService;
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
class FolwerProductWriteControllerTest {

    @Mock
    private IFolwerProductService productService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new FolwerProductController(productService)).build();
    }

    @Test
    void productAddShouldReturnSuccess() throws Exception {
        when(productService.insertByBo(any())).thenReturn(true);

        mockMvc.perform(post("/flower/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productName\":\"rose\",\"categoryId\":10,\"deliveryPrice\":\"12.50\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void productEditShouldReturnSuccess() throws Exception {
        when(productService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flower/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":11,\"productName\":\"updated-rose\",\"categoryId\":10,\"deliveryPrice\":\"8.80\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void productRemoveShouldReturnSuccess() throws Exception {
        when(productService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flower/product/11"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
