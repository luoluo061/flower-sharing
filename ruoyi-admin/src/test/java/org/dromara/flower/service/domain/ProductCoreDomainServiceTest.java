package org.dromara.flower.service.domain;

import org.dromara.flower.domain.FolwerProduct;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@Tag("dev")
class ProductCoreDomainServiceTest {

    private final ProductCoreDomainService service = new ProductCoreDomainService();

    @Test
    void prepareProductForCreateShouldDefaultDeliveryPriceToZero() {
        FolwerProduct product = new FolwerProduct();

        FolwerProduct result = service.prepareProductForCreate(product, null);

        assertSame(product, result);
        assertEquals(BigDecimal.ZERO, result.getDeliveryPrice());
    }

    @Test
    void prepareProductForUpdateShouldKeepCurrentPassThroughSemantics() {
        FolwerProduct product = new FolwerProduct();
        product.setProductName("p");

        assertSame(product, service.prepareProductForUpdate(product));
    }

    @Test
    void prepareStatusMutationShouldKeepCurrentStatusLock() {
        FolwerProduct product = new FolwerProduct();
        product.setStatus(1L);

        FolwerProduct result = service.prepareStatusMutation(product);

        assertSame(product, result);
        assertEquals(0L, result.getStatus());
    }
}
