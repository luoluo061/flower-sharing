package org.dromara.flower.service.impl;

import org.dromara.common.core.exception.ServiceException;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.mapper.FolwerCategoryMapper;
import org.dromara.flower.mapper.FolwerProductMapper;
import org.dromara.flower.service.IFolwerCategoryService;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysOssService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerCatalogWriteServiceTest {

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

    @Test
    void categoryDeleteShouldFailWhenProductsExist() {
        FolwerCategoryServiceImpl service = new FolwerCategoryServiceImpl(categoryMapper, ossService, appletProductService);

        FolwerAppletProductVo productVo = new FolwerAppletProductVo();
        FolwerCategoryVo categoryVo = new FolwerCategoryVo();
        categoryVo.setId(5L);
        categoryVo.setParentId(0L);
        categoryVo.setCategoryName("keep-category");

        when(appletProductService.queryList(any(FolwerAppletProductBo.class))).thenReturn(List.of(productVo));
        when(categoryMapper.selectVoById(5L)).thenReturn(categoryVo);

        ServiceException ex = assertThrows(ServiceException.class, () -> service.deleteWithValidByIds(List.of(5L), true));

        assertEquals("Please remove products under category keep-category first", ex.getMessage());
    }

    @Test
    void categoryDeleteShouldDeleteWhenNoProductsExist() {
        FolwerCategoryServiceImpl service = new FolwerCategoryServiceImpl(categoryMapper, ossService, appletProductService);

        when(appletProductService.queryList(any(FolwerAppletProductBo.class))).thenReturn(List.of());
        when(categoryMapper.deleteByIds(List.of(6L))).thenReturn(1);

        Boolean result = service.deleteWithValidByIds(List.of(6L), true);

        assertEquals(true, result);
        verify(categoryMapper).deleteByIds(List.of(6L));
    }

    @Test
    void productUpdateStatusShouldReturnFalseWhenIdsEmpty() {
        FolwerProductServiceImpl service = new FolwerProductServiceImpl(productMapper, categoryService, ossService, sysOssMapper, skuService);

        Boolean result = service.updateStatusByIds(List.of(), true);

        assertEquals(false, result);
    }

    @Test
    void productUpdateStatusShouldPersistZeroStatusForEverySelectedProduct() {
        FolwerProductServiceImpl service = new FolwerProductServiceImpl(productMapper, categoryService, ossService, sysOssMapper, skuService);

        FolwerProduct first = new FolwerProduct();
        first.setId(11L);
        first.setStatus(1L);
        FolwerProduct second = new FolwerProduct();
        second.setId(12L);
        second.setStatus(0L);

        when(productMapper.selectById(11L)).thenReturn(first);
        when(productMapper.selectById(12L)).thenReturn(second);

        Boolean result = service.updateStatusByIds(List.of(11L, 12L), true);

        assertEquals(true, result);
        assertEquals(0L, first.getStatus());
        assertEquals(0L, second.getStatus());
        verify(productMapper).updateById(first);
        verify(productMapper).updateById(second);
    }
}
