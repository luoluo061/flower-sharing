package org.dromara.flower.service.impl;

import org.dromara.flower.domain.FolwerProductDetail;
import org.dromara.flower.domain.bo.FolwerProductDetailBo;
import org.dromara.flower.mapper.FolwerProductDetailMapper;
import org.dromara.flower.service.domain.ProductDetailDomainService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerProductDetailWriteServiceTest {

    @Mock
    private FolwerProductDetailMapper detailMapper;
    @Test
    void insertByBoShouldPersistCurrentDetailShape() {
        FolwerProductDetailBo bo = new FolwerProductDetailBo();
        bo.setSkuId(100L);
        bo.setRemarks("detail-remarks");

        TestableFolwerProductDetailServiceImpl service = new TestableFolwerProductDetailServiceImpl(detailMapper, new ProductDetailDomainService());
        service.nextEntity.setSkuId(100L);
        service.nextEntity.setRemarks("detail-remarks");
        service.nextEntity.setDetailId(88L);

        when(detailMapper.insert(any(FolwerProductDetail.class))).thenReturn(1);
        boolean result = service.insertByBo(bo);

        assertTrue(result);
        assertEquals(88L, bo.getDetailId());
        verify(detailMapper).insert(service.nextEntity);
    }

    @Test
    void updateByBoShouldPersistCurrentDetailShape() {
        FolwerProductDetailBo bo = new FolwerProductDetailBo();
        bo.setDetailId(22L);
        bo.setSkuId(100L);
        bo.setRemarks("updated-remarks");

        TestableFolwerProductDetailServiceImpl service = new TestableFolwerProductDetailServiceImpl(detailMapper, new ProductDetailDomainService());
        service.nextEntity.setDetailId(22L);
        service.nextEntity.setSkuId(100L);
        service.nextEntity.setRemarks("updated-remarks");

        when(detailMapper.updateById(any(FolwerProductDetail.class))).thenReturn(1);
        boolean result = service.updateByBo(bo);

        assertTrue(result);
        verify(detailMapper).updateById(service.nextEntity);
    }

    @Test
    void deleteWithValidByIdsShouldDeleteDirectly() {
        TestableFolwerProductDetailServiceImpl service = new TestableFolwerProductDetailServiceImpl(detailMapper, new ProductDetailDomainService());

        when(detailMapper.deleteByIds(List.of(33L))).thenReturn(1);

        boolean result = service.deleteWithValidByIds(List.of(33L), true);

        assertTrue(result);
        verify(detailMapper).deleteByIds(List.of(33L));
    }

    private static final class TestableFolwerProductDetailServiceImpl extends FolwerProductDetailServiceImpl {

        private final FolwerProductDetail nextEntity = new FolwerProductDetail();

        private TestableFolwerProductDetailServiceImpl(FolwerProductDetailMapper baseMapper,
                                                       ProductDetailDomainService productDetailDomainService) {
            super(baseMapper, productDetailDomainService);
        }

        @Override
        protected FolwerProductDetail toEntity(FolwerProductDetailBo bo) {
            return nextEntity;
        }
    }
}
