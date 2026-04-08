package org.dromara.flower.service.support;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class ProductCategoryHierarchySupport {

    private ProductCategoryHierarchySupport() {
    }

    public static boolean shouldPopulateChildren(Long parentId) {
        return !Objects.equals(parentId, 0L);
    }

    public static <T> void populateChildrenIfNeeded(T node,
                                                    Function<T, Long> parentIdGetter,
                                                    Function<T, Long> idGetter,
                                                    Function<Long, List<T>> childLoader,
                                                    BiConsumer<T, List<T>> childSetter) {
        if (node == null || !shouldPopulateChildren(parentIdGetter.apply(node))) {
            return;
        }
        childSetter.accept(node, childLoader.apply(idGetter.apply(node)));
    }
}
