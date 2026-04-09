package org.dromara.flower.service.impl;

import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.mapper.FolwerProductMapper;
import org.dromara.flower.service.IFolwerCategoryService;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flower.service.domain.ProductCoreDomainService;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysOssService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerProductWriteServiceTest {

    @Mock
    private FolwerProductMapper productMapper;
    @Mock
    private IFolwerCategoryService categoryService;
    @Mock
    private ISysOssService ossService;
    @Mock
    private SysOssMapper sysOssMapper;
    @Mock
    private IFolwerSkuService skuService;
    @Test
    void insertByBoShouldDefaultDeliveryPriceToZero() {
        TestableFolwerProductServiceImpl service = new TestableFolwerProductServiceImpl(productMapper, categoryService, ossService, sysOssMapper, skuService, new ProductCoreDomainService());
        FolwerProductBo bo = new FolwerProductBo();
        bo.setProductName("write-product");

        when(productMapper.insert(any(FolwerProduct.class))).thenAnswer(invocation -> {
            FolwerProduct product = invocation.getArgument(0);
            product.setId(31L);
            return 1;
        });

        Boolean result = service.insertByBo(bo);

        assertTrue(result);
        assertEquals(31L, bo.getId());
        assertEquals(BigDecimal.ZERO, service.getLastPersisted().getDeliveryPrice());
    }

    @Test
    void updateByBoShouldPersistConvertedCurrentShape() {
        TestableFolwerProductServiceImpl service = new TestableFolwerProductServiceImpl(productMapper, categoryService, ossService, sysOssMapper, skuService, new ProductCoreDomainService());
        FolwerProductBo bo = new FolwerProductBo();
        bo.setId(41L);
        bo.setProductName("update-product");

        when(productMapper.updateById(any(FolwerProduct.class))).thenReturn(1);

        Boolean result = service.updateByBo(bo);

        assertTrue(result);
        assertEquals(41L, service.getLastPersisted().getId());
        assertEquals("update-product", service.getLastPersisted().getProductName());
    }

    @Test
    void deleteWithValidByIdsShouldDeleteDirectly() {
        FolwerProductServiceImpl service = new FolwerProductServiceImpl(productMapper, categoryService, ossService, sysOssMapper, skuService, new ProductCoreDomainService());
        when(productMapper.deleteByIds(List.of(51L))).thenReturn(1);

        Boolean result = service.deleteWithValidByIds(List.of(51L), true);

        assertTrue(result);
        verify(productMapper).deleteByIds(List.of(51L));
    }

    private static final class TestableFolwerProductServiceImpl extends FolwerProductServiceImpl {

        private FolwerProduct lastPersisted;

        private TestableFolwerProductServiceImpl(FolwerProductMapper baseMapper,
                                                 IFolwerCategoryService folwerCategoryService,
                                                 ISysOssService sysOssService,
                                                 SysOssMapper sysOssMapper,
                                                 IFolwerSkuService folwerSkuService,
                                                 ProductCoreDomainService productCoreDomainService) {
            super(baseMapper, folwerCategoryService, sysOssService, sysOssMapper, folwerSkuService, productCoreDomainService);
        }

        @Override
        protected FolwerProduct toEntity(FolwerProductBo bo) {
            FolwerProduct product = new FolwerProduct();
            product.setId(bo.getId());
            product.setProductName(bo.getProductName());
            lastPersisted = product;
            return product;
        }

        @Override
        protected void applyCurrentWriteDefaults(FolwerProduct product, FolwerProductBo bo) {
            super.applyCurrentWriteDefaults(product, bo);
            lastPersisted = product;
        }

        private FolwerProduct getLastPersisted() {
            return lastPersisted;
        }
    }
}
