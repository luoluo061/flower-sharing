package org.dromara.flower.service.domain;

import org.dromara.flower.service.support.ProductCategoryHierarchySupport;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

@Service
public class ProductCategoryDomainService {

    public boolean shouldPopulateChildren(Long parentId) {
        return ProductCategoryHierarchySupport.shouldPopulateChildren(parentId);
    }

    public <T> void populateChildren(T node,
                                     Function<T, Long> parentIdGetter,
                                     Function<T, Long> idGetter,
                                     Function<Long, List<T>> childLoader,
                                     BiConsumer<T, List<T>> childSetter) {
        ProductCategoryHierarchySupport.populateChildrenIfNeeded(
            node,
            parentIdGetter,
            idGetter,
            childLoader,
            childSetter
        );
    }
}
