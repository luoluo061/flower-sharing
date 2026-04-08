package org.dromara.flower.controller;

import org.dromara.flower.service.IFolwerCategoryService;
import org.dromara.flower.service.IFolwerProductDetailService;
import org.dromara.flower.service.IFolwerProductService;
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
class FolwerCatalogWriteControllerTest {

    @Mock
    private IFolwerCategoryService categoryService;
    @Mock
    private IFolwerProductService productService;
    @Mock
    private IFolwerSkuService skuService;
    @Mock
    private IFolwerProductDetailService productDetailService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
            new FolwerCategoryController(categoryService),
            new FolwerProductController(productService),
            new FolwerSkuController(skuService),
            new FolwerProductDetailController(productDetailService)
        ).build();
    }

    @Test
    void categoryAddShouldReturnSuccess() throws Exception {
        when(categoryService.insertByBo(any())).thenReturn(true);

        mockMvc.perform(post("/flower/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"parentId\":0,\"categoryName\":\"fresh-category\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void categoryEditShouldReturnSuccess() throws Exception {
        when(categoryService.updateByBo(any())).thenReturn(true);

        mockMvc.perform(put("/flower/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":10,\"parentId\":0,\"categoryName\":\"updated-category\"}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void categoryRemoveShouldReturnSuccess() throws Exception {
        when(categoryService.deleteWithValidByIds(any(), any())).thenReturn(true);

        mockMvc.perform(delete("/flower/category/10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }

    @Test
    void productBatchUpdateStatusShouldReturnSuccess() throws Exception {
        when(productService.updateStatusByIds(any(), any())).thenReturn(true);

        mockMvc.perform(put("/flower/product/batchUpdateStatus/10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200));
    }
}
