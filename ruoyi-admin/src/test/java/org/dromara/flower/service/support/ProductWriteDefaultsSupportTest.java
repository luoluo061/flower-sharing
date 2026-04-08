package org.dromara.flower.service.support;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("dev")
class ProductWriteDefaultsSupportTest {

    @Test
    void normalizeDeliveryPriceShouldReturnZeroWhenNull() {
        assertEquals(BigDecimal.ZERO, ProductWriteDefaultsSupport.normalizeDeliveryPrice(null));
    }

    @Test
    void normalizeDeliveryPriceShouldReturnBigDecimalWhenProvided() {
        assertEquals(new BigDecimal("12.34"), ProductWriteDefaultsSupport.normalizeDeliveryPrice("12.34"));
    }
}
