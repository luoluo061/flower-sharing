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

    public ProductSkuAggregateSupport.ProductAggregateSnapshot buildInsertSnapshot(List<FolwerSkuVo> skuVos) {
        return ProductSkuAggregateSupport.buildInsertSnapshot(skuVos);
    }

    public ProductSkuAggregateSupport.ProductAggregateSnapshot buildUpdateSnapshot(List<FolwerSkuVo> skuVos) {
        return ProductSkuAggregateSupport.buildUpdateSnapshot(skuVos);
    }

    public FolwerAppletProductBo applySnapshotToProductBo(FolwerAppletProductVo productVo,
                                                          ProductSkuAggregateSupport.ProductAggregateSnapshot snapshot) {
        FolwerAppletProductBo productBo = BeanUtil.copyProperties(productVo, FolwerAppletProductBo.class);
        productBo.setOriPrice(snapshot.maxPrice());
        productBo.setDerlinePrice(snapshot.minPrice());
        productBo.setTotalStocks(snapshot.totalStocks());
        return productBo;
    }
}
