package org.dromara.flower.service.support;

import java.math.BigDecimal;

public final class ProductWriteDefaultsSupport {

    private ProductWriteDefaultsSupport() {
    }

    public static BigDecimal normalizeDeliveryPrice(String deliveryPrice) {
        if (deliveryPrice == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(deliveryPrice);
    }
}
