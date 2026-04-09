package org.dromara.flower.service.domain;

import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flower.service.support.ProductWriteDefaultsSupport;
import org.springframework.stereotype.Service;

@Service
public class ProductCoreDomainService {

    public FolwerProduct prepareProductForCreate(FolwerProduct product, String deliveryPrice) {
        product.setDeliveryPrice(ProductWriteDefaultsSupport.normalizeDeliveryPrice(deliveryPrice));
        return product;
    }

    public FolwerProduct prepareProductForUpdate(FolwerProduct product) {
        return product;
    }

    public FolwerProduct prepareStatusMutation(FolwerProduct product) {
        product.setStatus(0L);
        return product;
    }
}
