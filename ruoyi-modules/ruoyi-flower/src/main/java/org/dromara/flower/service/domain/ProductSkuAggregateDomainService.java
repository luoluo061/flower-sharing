package org.dromara.flower.service.domain;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.service.support.ProductSkuAggregateSupport;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductSkuAggregateDomainService {

    public Snapshot buildInsertSnapshot(List<FolwerSkuVo> skuVos) {
        return Snapshot.from(ProductSkuAggregateSupport.buildInsertSnapshot(skuVos));
    }

    public Snapshot buildUpdateSnapshot(List<FolwerSkuVo> skuVos) {
        return Snapshot.from(ProductSkuAggregateSupport.buildUpdateSnapshot(skuVos));
    }

    public FolwerAppletProductBo applySnapshotToProductBo(FolwerAppletProductVo productVo, Snapshot snapshot) {
        FolwerAppletProductBo productBo = BeanUtil.copyProperties(productVo, FolwerAppletProductBo.class);
        productBo.setOriPrice(snapshot.maxPrice);
        productBo.setDerlinePrice(snapshot.minPrice);
        productBo.setTotalStocks(snapshot.totalStocks);
        return productBo;
    }

    public record Snapshot(
        java.math.BigDecimal maxPrice,
        java.math.BigDecimal minPrice,
        Long totalStocks
    ) {
        private static Snapshot from(ProductSkuAggregateSupport.ProductAggregateSnapshot snapshot) {
            return new Snapshot(snapshot.maxPrice(), snapshot.minPrice(), snapshot.totalStocks());
        }
    }
}
