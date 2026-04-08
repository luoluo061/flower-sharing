package org.dromara.flower.service.support;

import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("dev")
class ProductSkuAggregateSupportTest {

    @Test
    void buildInsertSnapshotShouldKeepCurrentInsertSemantics() {
        FolwerSkuVo disabled = skuVo(new BigDecimal("99.999"), new BigDecimal("1.111"), 99L, 0L);
        FolwerSkuVo activeFirst = skuVo(new BigDecimal("10.123"), new BigDecimal("8.111"), 5L, 1L);
        FolwerSkuVo activeSecond = skuVo(new BigDecimal("11.456"), new BigDecimal("7.777"), 3L, 1L);

        ProductSkuAggregateSupport.ProductAggregateSnapshot snapshot =
            ProductSkuAggregateSupport.buildInsertSnapshot(List.of(disabled, activeFirst, activeSecond));

        assertEquals(new BigDecimal("11.46"), snapshot.maxPrice());
        assertEquals(new BigDecimal("7.78"), snapshot.minPrice());
        assertEquals(3L, snapshot.totalStocks());
    }

    @Test
    void buildUpdateSnapshotShouldKeepCurrentUpdateSemantics() {
        FolwerSkuVo first = skuVo(new BigDecimal("10.123"), new BigDecimal("8.111"), 5L, 1L);
        FolwerSkuVo second = skuVo(new BigDecimal("11.456"), new BigDecimal("7.777"), 3L, 0L);

        ProductSkuAggregateSupport.ProductAggregateSnapshot snapshot =
            ProductSkuAggregateSupport.buildUpdateSnapshot(List.of(first, second));

        assertEquals(new BigDecimal("11.456"), snapshot.maxPrice());
        assertEquals(BigDecimal.ZERO, snapshot.minPrice());
        assertEquals(3L, snapshot.totalStocks());
    }

    @Test
    void buildSnapshotShouldReturnCurrentDefaultsWhenSkuListEmpty() {
        ProductSkuAggregateSupport.ProductAggregateSnapshot insertSnapshot =
            ProductSkuAggregateSupport.buildInsertSnapshot(List.of());
        ProductSkuAggregateSupport.ProductAggregateSnapshot updateSnapshot =
            ProductSkuAggregateSupport.buildUpdateSnapshot(List.of());

        assertEquals(new BigDecimal("-999999999"), insertSnapshot.maxPrice());
        assertEquals(new BigDecimal("999999999"), insertSnapshot.minPrice());
        assertEquals(0L, insertSnapshot.totalStocks());

        assertEquals(BigDecimal.ZERO, updateSnapshot.maxPrice());
        assertEquals(BigDecimal.ZERO, updateSnapshot.minPrice());
        assertEquals(0L, updateSnapshot.totalStocks());
    }

    private FolwerSkuVo skuVo(BigDecimal price, BigDecimal minPrice, Long stocks, Long status) {
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setPrice(price);
        skuVo.setMinPrice(minPrice);
        skuVo.setActualStocks(stocks);
        skuVo.setStatus(status);
        return skuVo;
    }
}
