package org.dromara.flower.service.support;

import org.dromara.flower.domain.vo.FolwerSkuVo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class ProductSkuAggregateSupport {

    private ProductSkuAggregateSupport() {
    }

    public static ProductAggregateSnapshot buildInsertSnapshot(List<FolwerSkuVo> skuVos) {
        BigDecimal maxPrice = new BigDecimal(-999999999);
        BigDecimal minPrice = new BigDecimal(999999999);
        Long totalStocks = 0L;
        if (skuVos.isEmpty()) {
            return new ProductAggregateSnapshot(maxPrice, minPrice, totalStocks);
        }
        for (FolwerSkuVo skuVo : skuVos) {
            if (Long.valueOf(0L).equals(skuVo.getStatus())) {
                continue;
            }
            if (skuVo.getPrice().compareTo(maxPrice) > 0) {
                maxPrice = skuVo.getPrice().setScale(2, RoundingMode.HALF_UP);
            }
            if (skuVo.getMinPrice().compareTo(minPrice) < 0) {
                minPrice = skuVo.getMinPrice().setScale(2, RoundingMode.HALF_UP);
            }
            totalStocks = +skuVo.getActualStocks();
        }
        return new ProductAggregateSnapshot(maxPrice, minPrice, totalStocks);
    }

    public static ProductAggregateSnapshot buildUpdateSnapshot(List<FolwerSkuVo> skuVos) {
        BigDecimal maxPrice = BigDecimal.ZERO;
        BigDecimal minPrice = BigDecimal.ZERO;
        Long totalStocks = 0L;
        if (skuVos.isEmpty()) {
            return new ProductAggregateSnapshot(maxPrice, minPrice, totalStocks);
        }
        for (FolwerSkuVo skuVo : skuVos) {
            if (skuVo.getPrice().compareTo(maxPrice) > 0) {
                maxPrice = skuVo.getPrice();
            }
            if (skuVo.getMinPrice().compareTo(minPrice) < 0) {
                minPrice = skuVo.getMinPrice();
            }
            totalStocks = +skuVo.getActualStocks();
        }
        return new ProductAggregateSnapshot(maxPrice, minPrice, totalStocks);
    }

    public record ProductAggregateSnapshot(BigDecimal maxPrice, BigDecimal minPrice, Long totalStocks) {
    }
}
