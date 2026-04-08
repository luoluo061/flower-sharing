package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.domain.bo.FolwerSkuBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.mapper.FolwerCategoryMapper;
import org.dromara.flower.mapper.FolwerProductMapper;
import org.dromara.flower.mapper.FolwerSkuMapper;
import org.dromara.flower.service.IFolwerCategoryService;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysOssService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerCatalogReadServiceTest {

    @Mock
    private FolwerCategoryMapper categoryMapper;
    @Mock
    private ISysOssService ossService;
    @Mock
    private IFolwerAppletProductService appletProductService;
    @Mock
    private FolwerProductMapper productMapper;
    @Mock
    private IFolwerCategoryService categoryService;
    @Mock
    private SysOssMapper sysOssMapper;
    @Mock
    private IFolwerSkuService skuService;
    @Mock
    private FolwerSkuMapper skuMapper;

    @Test
    void categoryQueryByIdShouldAttachChildrenForNonRootParent() {
        FolwerCategoryServiceImpl service = new FolwerCategoryServiceImpl(categoryMapper, ossService, appletProductService);

        FolwerCategoryVo parent = new FolwerCategoryVo();
        parent.setId(1L);
        parent.setParentId(9L);

        FolwerCategoryVo child = new FolwerCategoryVo();
        child.setId(2L);
        child.setParentId(0L);

        when(categoryMapper.selectVoById(1L)).thenReturn(parent);
        when(categoryMapper.selectVoList(any())).thenReturn(List.of(child));

        FolwerCategoryVo result = service.queryById(1L);

        assertEquals(1, result.getChildren().size());
        assertEquals(2L, result.getChildren().get(0).getId());
    }

    @Test
    void categoryQueryByIdShouldSkipChildrenForRootParent() {
        FolwerCategoryServiceImpl service = new FolwerCategoryServiceImpl(categoryMapper, ossService, appletProductService);

        FolwerCategoryVo parent = new FolwerCategoryVo();
        parent.setId(1L);
        parent.setParentId(0L);
        when(categoryMapper.selectVoById(1L)).thenReturn(parent);

        FolwerCategoryVo result = service.queryById(1L);

        assertNull(result.getChildren());
        verify(categoryMapper, never()).selectVoList(any());
    }

    @Test
    void productQueryByIdShouldComposeParentAndChildCategoryName() {
        FolwerProductServiceImpl service = new FolwerProductServiceImpl(productMapper, categoryService, ossService, sysOssMapper, skuService);

        FolwerProductVo product = new FolwerProductVo();
        product.setId(10L);
        product.setCategoryId(200L);

        FolwerCategoryVo child = new FolwerCategoryVo();
        child.setId(200L);
        child.setParentId(100L);
        child.setCategoryName("Child");

        FolwerCategoryVo parent = new FolwerCategoryVo();
        parent.setId(100L);
        parent.setParentId(0L);
        parent.setCategoryName("Parent");

        when(productMapper.selectVoById(10L)).thenReturn(product);
        when(categoryService.queryById(200L)).thenReturn(child);
        when(categoryService.queryById(100L)).thenReturn(parent);

        FolwerProductVo result = service.queryById(10L);

        assertEquals("Parent/Child", result.getCategoryName());
    }

    @Test
    void productQueryPageListShouldKeepCurrentShortParentRule() {
        FolwerProductServiceImpl service = new FolwerProductServiceImpl(productMapper, categoryService, ossService, sysOssMapper, skuService);

        FolwerProductVo product = new FolwerProductVo();
        product.setId(11L);
        product.setCategoryId(201L);

        FolwerCategoryVo child = new FolwerCategoryVo();
        child.setId(201L);
        child.setParentId(12L);
        child.setCategoryName("Child");

        Page<FolwerProductVo> page = new Page<>();
        page.setRecords(List.of(product));

        when(productMapper.selectVoPage(any(), any())).thenReturn(page);
        when(categoryService.queryById(201L)).thenReturn(child);

        TableDataInfo<FolwerProductVo> result = service.queryPageList(new FolwerProductBo(), pageQuery());

        assertEquals("Child", result.getRows().get(0).getCategoryName());
        verify(categoryService, never()).queryById(12L);
    }

    @Test
    void productQueryPageListShouldFailWhenCategoryMissing() {
        FolwerProductServiceImpl service = new FolwerProductServiceImpl(productMapper, categoryService, ossService, sysOssMapper, skuService);

        FolwerProductVo product = new FolwerProductVo();
        product.setId(12L);
        product.setCategoryId(202L);

        Page<FolwerProductVo> page = new Page<>();
        page.setRecords(List.of(product));

        when(productMapper.selectVoPage(any(), any())).thenReturn(page);
        when(categoryService.queryById(202L)).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.queryPageList(new FolwerProductBo(), pageQuery()));
        assertEquals("202 category does not exist", ex.getMessage());
    }

    @Test
    void skuQueryPageListShouldFillPictureUrlsFromSharedLookup() {
        FolwerSkuServiceImpl service = new FolwerSkuServiceImpl(skuMapper, ossService, appletProductService);

        FolwerSkuVo first = new FolwerSkuVo();
        first.setSkuId(21L);
        first.setSkuPicid("1001");
        FolwerSkuVo second = new FolwerSkuVo();
        second.setSkuId(22L);
        second.setSkuPicid("1002");

        Page<FolwerSkuVo> page = new Page<>();
        page.setRecords(List.of(first, second));

        when(skuMapper.selectVoPage(any(), any())).thenReturn(page);
        when(ossService.listUrlByIds(any())).thenReturn(Map.of("1001", "url-1", "1002", "url-2"));

        TableDataInfo<FolwerSkuVo> result = service.queryPageList(new FolwerSkuBo(), pageQuery());

        assertEquals("url-1", result.getRows().get(0).getSkuPicidURL());
        assertEquals("url-2", result.getRows().get(1).getSkuPicidURL());
    }

    @Test
    void skuQueryListByProdIdShouldSkipLookupWhenNoPictureIds() {
        FolwerSkuServiceImpl service = new FolwerSkuServiceImpl(skuMapper, ossService, appletProductService);

        FolwerSkuVo sku = new FolwerSkuVo();
        sku.setSkuId(23L);
        sku.setProdId(300L);
        sku.setSkuPicid("");

        when(skuMapper.selectVoList(any())).thenReturn(List.of(sku));

        List<FolwerSkuVo> result = service.queryListByProdId(300L);

        assertEquals(1, result.size());
        assertNull(result.get(0).getSkuPicidURL());
        verify(ossService, never()).listUrlByIds(any());
    }

    private PageQuery pageQuery() {
        PageQuery pageQuery = new PageQuery();
        pageQuery.setPageNum(1);
        pageQuery.setPageSize(10);
        return pageQuery;
    }
}
