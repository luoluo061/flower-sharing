package org.dromara.flower.service.domain;

import org.dromara.flower.domain.FolwerProductDetail;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductDetailDomainService {

    public FolwerProductDetail prepareDetailForCreate(FolwerProductDetail detail) {
        return detail;
    }

    public FolwerProductDetail prepareDetailForUpdate(FolwerProductDetail detail) {
        return detail;
    }

    public Collection<Long> prepareDetailDelete(Collection<Long> ids) {
        return ids;
    }
}
