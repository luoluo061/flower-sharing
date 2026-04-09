package org.dromara.flower.service.domain;

import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("dev")
class ProductSkuAggregateDomainServiceTest {

    private final ProductSkuAggregateDomainService service = new ProductSkuAggregateDomainService();

    @Test
    void buildInsertSnapshotShouldKeepCurrentSemantics() {
        ProductSkuAggregateDomainService.Snapshot snapshot = service.buildInsertSnapshot(List.of(
            skuVo(new BigDecimal("99.999"), new BigDecimal("1.111"), 99L, 0L),
            skuVo(new BigDecimal("10.123"), new BigDecimal("8.111"), 5L, 1L),
            skuVo(new BigDecimal("11.456"), new BigDecimal("7.777"), 3L, 1L)
        ));

        assertEquals(new BigDecimal("11.46"), snapshot.maxPrice());
        assertEquals(new BigDecimal("7.78"), snapshot.minPrice());
        assertEquals(3L, snapshot.totalStocks());
    }

    @Test
    void buildUpdateSnapshotShouldKeepCurrentSemantics() {
        ProductSkuAggregateDomainService.Snapshot snapshot = service.buildUpdateSnapshot(List.of(
            skuVo(new BigDecimal("10.123"), new BigDecimal("8.111"), 5L, 1L),
            skuVo(new BigDecimal("11.456"), new BigDecimal("7.777"), 3L, 0L)
        ));

        assertEquals(new BigDecimal("11.456"), snapshot.maxPrice());
        assertEquals(BigDecimal.ZERO, snapshot.minPrice());
        assertEquals(3L, snapshot.totalStocks());
    }

    @Test
    void applySnapshotToProductBoShouldCopyCurrentAggregateFields() {
        FolwerAppletProductVo productVo = new FolwerAppletProductVo();
        productVo.setId(100L);
        ProductSkuAggregateDomainService.Snapshot snapshot =
            new ProductSkuAggregateDomainService.Snapshot(new BigDecimal("12.34"), new BigDecimal("5.67"), 8L);

        FolwerAppletProductBo productBo = service.applySnapshotToProductBo(productVo, snapshot);

        assertEquals(100L, productBo.getId());
        assertEquals(new BigDecimal("12.34"), productBo.getOriPrice());
        assertEquals(new BigDecimal("5.67"), productBo.getDerlinePrice());
        assertEquals(8L, productBo.getTotalStocks());
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
