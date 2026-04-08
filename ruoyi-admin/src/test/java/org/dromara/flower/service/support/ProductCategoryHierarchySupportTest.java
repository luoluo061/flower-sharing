package org.dromara.flower.service.support;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("dev")
class ProductCategoryHierarchySupportTest {

    @Test
    void shouldPopulateChildrenShouldReturnFalseForRootParentId() {
        assertFalse(ProductCategoryHierarchySupport.shouldPopulateChildren(0L));
        assertTrue(ProductCategoryHierarchySupport.shouldPopulateChildren(9L));
    }

    @Test
    void populateChildrenIfNeededShouldSkipNullNode() {
        ProductCategoryHierarchySupport.populateChildrenIfNeeded(
            null,
            TestNode::getParentId,
            TestNode::getId,
            id -> List.of(new TestNode(id + 1, id)),
            TestNode::setChildren
        );
    }

    @Test
    void populateChildrenIfNeededShouldSkipRootParentNode() {
        TestNode node = new TestNode(1L, 0L);

        ProductCategoryHierarchySupport.populateChildrenIfNeeded(
            node,
            TestNode::getParentId,
            TestNode::getId,
            id -> List.of(new TestNode(id + 1, id)),
            TestNode::setChildren
        );

        assertNull(node.getChildren());
    }

    @Test
    void populateChildrenIfNeededShouldSetChildrenForNonRootParent() {
        TestNode node = new TestNode(1L, 9L);

        ProductCategoryHierarchySupport.populateChildrenIfNeeded(
            node,
            TestNode::getParentId,
            TestNode::getId,
            id -> List.of(new TestNode(id + 1, id)),
            TestNode::setChildren
        );

        assertEquals(1, node.getChildren().size());
        assertEquals(2L, node.getChildren().get(0).getId());
    }

    private static final class TestNode {
        private final Long id;
        private final Long parentId;
        private List<TestNode> children;

        private TestNode(Long id, Long parentId) {
            this.id = id;
            this.parentId = parentId;
        }

        public Long getId() {
            return id;
        }

        public Long getParentId() {
            return parentId;
        }

        public List<TestNode> getChildren() {
            return children;
        }

        public void setChildren(List<TestNode> children) {
            this.children = children;
        }
    }
}
