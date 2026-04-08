package org.dromara.flower.controller;

import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.domain.bo.FolwerProductDetailBo;
import org.dromara.flower.domain.bo.FolwerSkuBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.domain.vo.FolwerProductDetailVo;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerCatalogReadControllerTest {

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
    void categoryListShouldReturnPagedRows() throws Exception {
        FolwerCategoryVo categoryVo = new FolwerCategoryVo();
        categoryVo.setId(101L);
        categoryVo.setCategoryName("root-category");
        when(categoryService.queryPageList(any(FolwerCategoryBo.class), any())).thenReturn(TableDataInfo.build(List.of(categoryVo)));

        mockMvc.perform(get("/flower/category/list").param("pageNum", "1").param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].id").value(101))
            .andExpect(jsonPath("$.rows[0].categoryName").value("root-category"));
    }

    @Test
    void categoryAllListShouldReturnData() throws Exception {
        FolwerCategoryVo categoryVo = new FolwerCategoryVo();
        categoryVo.setId(102L);
        categoryVo.setCategoryName("all-category");
        when(categoryService.queryList(any(FolwerCategoryBo.class))).thenReturn(List.of(categoryVo));

        mockMvc.perform(get("/flower/category/allList"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data[0].id").value(102))
            .andExpect(jsonPath("$.data[0].categoryName").value("all-category"));
    }

    @Test
    void categoryDetailShouldReturnCategory() throws Exception {
        FolwerCategoryVo categoryVo = new FolwerCategoryVo();
        categoryVo.setId(103L);
        categoryVo.setCategoryName("detail-category");
        when(categoryService.queryById(103L)).thenReturn(categoryVo);

        mockMvc.perform(get("/flower/category/103"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").value(103))
            .andExpect(jsonPath("$.data.categoryName").value("detail-category"));
    }

    @Test
    void productListShouldReturnPagedRows() throws Exception {
        FolwerProductVo productVo = new FolwerProductVo();
        productVo.setId(201L);
        productVo.setProductName("admin-product");
        when(productService.queryPageList(any(FolwerProductBo.class), any())).thenReturn(TableDataInfo.build(List.of(productVo)));

        mockMvc.perform(get("/flower/product/list").param("pageNum", "1").param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].id").value(201))
            .andExpect(jsonPath("$.rows[0].productName").value("admin-product"));
    }

    @Test
    void productAllListShouldReturnData() throws Exception {
        FolwerProductVo productVo = new FolwerProductVo();
        productVo.setId(202L);
        productVo.setProductName("admin-all-product");
        when(productService.queryList(any(FolwerProductBo.class))).thenReturn(List.of(productVo));

        mockMvc.perform(get("/flower/product/allList"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data[0].id").value(202))
            .andExpect(jsonPath("$.data[0].productName").value("admin-all-product"));
    }

    @Test
    void productDetailShouldReturnProduct() throws Exception {
        FolwerProductVo productVo = new FolwerProductVo();
        productVo.setId(203L);
        productVo.setProductName("detail-product");
        when(productService.queryById(203L)).thenReturn(productVo);

        mockMvc.perform(get("/flower/product/203"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.id").value(203))
            .andExpect(jsonPath("$.data.productName").value("detail-product"));
    }

    @Test
    void skuListShouldReturnRows() throws Exception {
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setSkuId(301L);
        skuVo.setColour("red");
        when(skuService.queryList(any(FolwerSkuBo.class))).thenReturn(List.of(skuVo));

        mockMvc.perform(get("/flower/sku/list"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data[0].skuId").value(301))
            .andExpect(jsonPath("$.data[0].colour").value("red"));
    }

    @Test
    void skuDetailShouldReturnSku() throws Exception {
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setSkuId(302L);
        skuVo.setColour("white");
        when(skuService.queryById(302L)).thenReturn(skuVo);

        mockMvc.perform(get("/flower/sku/302"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.skuId").value(302))
            .andExpect(jsonPath("$.data.colour").value("white"));
    }

    @Test
    void productDetailListShouldReturnPagedRows() throws Exception {
        FolwerProductDetailVo detailVo = new FolwerProductDetailVo();
        detailVo.setDetailId(401L);
        detailVo.setSkuId(302L);
        when(productDetailService.queryPageList(any(FolwerProductDetailBo.class), any())).thenReturn(TableDataInfo.build(List.of(detailVo)));

        mockMvc.perform(get("/flower/productDetail/list").param("pageNum", "1").param("pageSize", "10"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.rows[0].detailId").value(401))
            .andExpect(jsonPath("$.rows[0].skuId").value(302));
    }

    @Test
    void productDetailShouldReturnDetail() throws Exception {
        FolwerProductDetailVo detailVo = new FolwerProductDetailVo();
        detailVo.setDetailId(402L);
        detailVo.setSkuId(303L);
        detailVo.setRemarks("detail-body");
        when(productDetailService.queryById(402L)).thenReturn(detailVo);

        mockMvc.perform(get("/flower/productDetail/402"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.code").value(200))
            .andExpect(jsonPath("$.data.detailId").value(402))
            .andExpect(jsonPath("$.data.skuId").value(303))
            .andExpect(jsonPath("$.data.remarks").value("detail-body"));
    }
}
