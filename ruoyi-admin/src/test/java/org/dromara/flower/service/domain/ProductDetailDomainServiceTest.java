package org.dromara.flower.service.domain;

import org.dromara.flower.domain.FolwerProductDetail;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertSame;

@Tag("dev")
class ProductDetailDomainServiceTest {

    private final ProductDetailDomainService service = new ProductDetailDomainService();

    @Test
    void prepareDetailForCreateShouldKeepCurrentPassThroughSemantics() {
        FolwerProductDetail detail = new FolwerProductDetail();
        assertSame(detail, service.prepareDetailForCreate(detail));
    }

    @Test
    void prepareDetailForUpdateShouldKeepCurrentPassThroughSemantics() {
        FolwerProductDetail detail = new FolwerProductDetail();
        assertSame(detail, service.prepareDetailForUpdate(detail));
    }

    @Test
    void prepareDetailDeleteShouldKeepCurrentPassThroughSemantics() {
        List<Long> ids = List.of(1L, 2L);
        assertSame(ids, service.prepareDetailDelete(ids));
    }
}
