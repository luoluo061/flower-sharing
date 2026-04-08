package org.dromara.flowerapplet.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductDetailBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCategoryVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductDetailVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.service.IFolwerAppletCategoryService;
import org.dromara.flowerapplet.service.IFolwerAppletProductDetailService;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
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
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerAppletCatalogReadControllerTest {

    @Mock
    private IFolwerAppletCategoryService categoryService;
    @Mock
    private IFolwerAppletProductService productService;
    @Mock
    private IFolwerAppletSkuService skuService;
    @Mock
    private IFolwerAppletProductDetailService productDetailService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(
            new FolwerAppletCategoryController(categoryService),
            new FolwerAppletProductController(productService),
            new FolwerAppletSkuController(skuService),
            new FolwerAppletProductDetailController(productDetailService)
        ).build();
    }

    @Test
    void categoryListShouldReturnPagedRows() throws Exception {
        FolwerAppletCategoryVo categoryVo = new FolwerAppletCategoryVo();
        categoryVo.setId(11L);
        categoryVo.setCategoryName("rose");
        when(categoryService.queryPageList(any(FolwerAppletCategoryBo.class), any())).thenReturn(TableDataInfo.build(List.of(categoryVo)));

        mockMvc.perform(get("/flowerapplet/category/list").param("pageNum", "1").param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].id").value(11))
            .andExpect(jsonPath("$.rows[0].categoryName").value("rose"));
    }

    @Test
    void categoryAllListShouldReturnRootCategories() throws Exception {
        FolwerAppletCategoryVo categoryVo = new FolwerAppletCategoryVo();
        categoryVo.setId(12L);
        categoryVo.setParentId(0L);
        categoryVo.setCategoryName("gift");
        when(categoryService.queryList(any(FolwerAppletCategoryBo.class))).thenReturn(List.of(categoryVo));

        mockMvc.perform(get("/flowerapplet/category/allList"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data[0].id").value(12))
            .andExpect(jsonPath("$.data[0].parentId").value(0))
            .andExpect(jsonPath("$.data[0].categoryName").value("gift"));
    }

    @Test
    void categoryDetailShouldReturnCategory() throws Exception {
        FolwerAppletCategoryVo categoryVo = new FolwerAppletCategoryVo();
        categoryVo.setId(13L);
        categoryVo.setCategoryName("seasonal");
        when(categoryService.queryById(13L)).thenReturn(categoryVo);

        mockMvc.perform(get("/flowerapplet/category/13"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").value(13))
            .andExpect(jsonPath("$.data.categoryName").value("seasonal"));
    }

    @Test
    void productListShouldReturnPagedRows() throws Exception {
        FolwerAppletProductVo productVo = new FolwerAppletProductVo();
        productVo.setId(21L);
        productVo.setProductName("rose bouquet");
        when(productService.queryPageList(any(FolwerAppletProductBo.class), any())).thenReturn(TableDataInfo.build(List.of(productVo)));

        mockMvc.perform(get("/flowerapplet/product/list").param("pageNum", "1").param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].id").value(21))
            .andExpect(jsonPath("$.rows[0].productName").value("rose bouquet"));
    }

    @Test
    void productDetailShouldReturnProduct() throws Exception {
        FolwerAppletProductVo productVo = new FolwerAppletProductVo();
        productVo.setId(22L);
        productVo.setProductName("tulip box");
        when(productService.queryById(22L)).thenReturn(productVo);

        mockMvc.perform(get("/flowerapplet/product/22"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").value(22))
            .andExpect(jsonPath("$.data.productName").value("tulip box"));
    }

    @Test
    void productQueryCategoryShouldReturnCategoryProducts() throws Exception {
        FolwerAppletProductVo productVo = new FolwerAppletProductVo();
        productVo.setId(23L);
        productVo.setCategoryId(12L);
        productVo.setProductName("category item");
        when(productService.queryAllBycategoryId(12L, 1, 5)).thenReturn(List.of(productVo));

        mockMvc.perform(get("/flowerapplet/product/queryCategory/12/1/5"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data[0].id").value(23))
            .andExpect(jsonPath("$.data[0].categoryId").value(12));
    }

    @Test
    void skuListShouldReturnPagedRows() throws Exception {
        FolwerAppletSkuVo skuVo = new FolwerAppletSkuVo();
        skuVo.setSkuId(31L);
        skuVo.setSkuName("rose-red");
        when(skuService.queryPageList(any(FolwerAppletSkuBo.class), any())).thenReturn(TableDataInfo.build(List.of(skuVo)));

        mockMvc.perform(get("/flowerapplet/sku/list").param("pageNum", "1").param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].skuId").value(31))
            .andExpect(jsonPath("$.rows[0].skuName").value("rose-red"));
    }

    @Test
    void skuDetailShouldReturnSku() throws Exception {
        FolwerAppletSkuVo skuVo = new FolwerAppletSkuVo();
        skuVo.setSkuId(32L);
        skuVo.setSkuName("rose-white");
        when(skuService.queryById(32L)).thenReturn(skuVo);

        mockMvc.perform(get("/flowerapplet/sku/32"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.skuId").value(32))
            .andExpect(jsonPath("$.data.skuName").value("rose-white"));
    }

    @Test
    void productDetailListShouldReturnPagedRows() throws Exception {
        FolwerAppletProductDetailVo detailVo = new FolwerAppletProductDetailVo();
        detailVo.setDetailId(41L);
        detailVo.setSkuId(31L);
        detailVo.setRemarks("detail-body");
        when(productDetailService.queryPageList(any(FolwerAppletProductDetailBo.class), any())).thenReturn(TableDataInfo.build(List.of(detailVo)));

        mockMvc.perform(get("/flowerapplet/productDetail/list").param("pageNum", "1").param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].detailId").value(41))
            .andExpect(jsonPath("$.rows[0].skuId").value(31));
    }

    @Test
    void productDetailBySkuIdShouldReturnDetail() throws Exception {
        FolwerAppletProductDetailVo detailVo = new FolwerAppletProductDetailVo();
        detailVo.setDetailId(42L);
        detailVo.setSkuId(32L);
        detailVo.setRemarks("detail-by-sku");
        when(productDetailService.queryByProductId(32L)).thenReturn(detailVo);

        mockMvc.perform(get("/flowerapplet/productDetail/getInfoBySkuId/32"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.detailId").value(42))
            .andExpect(jsonPath("$.data.skuId").value(32))
            .andExpect(jsonPath("$.data.remarks").value("detail-by-sku"));
    }
}
