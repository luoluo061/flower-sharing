package org.dromara.flower.service.impl;

import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.mapper.FolwerSkuMapper;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.system.service.ISysOssService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerSkuWriteServiceTest {

    @Mock
    private FolwerSkuMapper skuMapper;
    @Mock
    private ISysOssService ossService;
    @Mock
    private IFolwerAppletProductService appletProductService;

    @Test
    void refreshProductAggregateAfterSkuInsertShouldKeepCurrentInsertSemantics() {
        FolwerSkuServiceImpl service = new FolwerSkuServiceImpl(skuMapper, ossService, appletProductService);

        FolwerSkuVo disabled = skuVo(100L, new BigDecimal("99.999"), new BigDecimal("1.111"), 99L, 0L);
        FolwerSkuVo activeFirst = skuVo(100L, new BigDecimal("10.123"), new BigDecimal("8.111"), 5L, 1L);
        FolwerSkuVo activeSecond = skuVo(100L, new BigDecimal("11.456"), new BigDecimal("7.777"), 3L, 1L);
        FolwerAppletProductVo productVo = new FolwerAppletProductVo();
        productVo.setId(100L);

        when(skuMapper.selectVoList(any())).thenReturn(List.of(disabled, activeFirst, activeSecond));
        when(appletProductService.queryById(100L)).thenReturn(productVo);
        when(appletProductService.updateByBo(any())).thenReturn(true);

        service.refreshProductAggregateAfterSkuInsert(100L);

        ArgumentCaptor<FolwerAppletProductBo> captor = ArgumentCaptor.forClass(FolwerAppletProductBo.class);
        verify(appletProductService).updateByBo(captor.capture());
        assertEquals(new BigDecimal("11.46"), captor.getValue().getOriPrice());
        assertEquals(new BigDecimal("7.78"), captor.getValue().getDerlinePrice());
        assertEquals(3L, captor.getValue().getTotalStocks());
    }

    @Test
    void refreshProductAggregateAfterSkuUpdateShouldKeepCurrentUpdateSemantics() {
        FolwerSkuServiceImpl service = new FolwerSkuServiceImpl(skuMapper, ossService, appletProductService);

        FolwerSkuVo first = skuVo(100L, new BigDecimal("10.123"), new BigDecimal("8.111"), 5L, 1L);
        FolwerSkuVo second = skuVo(100L, new BigDecimal("11.456"), new BigDecimal("7.777"), 3L, 0L);
        FolwerAppletProductVo productVo = new FolwerAppletProductVo();
        productVo.setId(100L);

        when(skuMapper.selectVoList(any())).thenReturn(List.of(first, second));
        when(appletProductService.queryById(100L)).thenReturn(productVo);
        when(appletProductService.updateByBo(any())).thenReturn(true);

        service.refreshProductAggregateAfterSkuUpdate(100L);

        ArgumentCaptor<FolwerAppletProductBo> captor = ArgumentCaptor.forClass(FolwerAppletProductBo.class);
        verify(appletProductService).updateByBo(captor.capture());
        assertEquals(new BigDecimal("11.456"), captor.getValue().getOriPrice());
        assertEquals(BigDecimal.ZERO, captor.getValue().getDerlinePrice());
        assertEquals(3L, captor.getValue().getTotalStocks());
    }

    private FolwerSkuVo skuVo(Long prodId, BigDecimal price, BigDecimal minPrice, Long stocks, Long status) {
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setProdId(prodId);
        skuVo.setPrice(price);
        skuVo.setMinPrice(minPrice);
        skuVo.setActualStocks(stocks);
        skuVo.setStatus(status);
        skuVo.setSkuPicid("");
        return skuVo;
    }
}
