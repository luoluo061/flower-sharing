package org.dromara.flower.service.domain;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("dev")
class ProductCategoryDomainServiceTest {

    private final ProductCategoryDomainService service = new ProductCategoryDomainService();

    @Test
    void shouldPopulateChildrenShouldKeepCurrentRootRule() {
        assertFalse(service.shouldPopulateChildren(0L));
        assertTrue(service.shouldPopulateChildren(1L));
    }

    @Test
    void populateChildrenShouldSkipNullNode() {
        service.populateChildren(null, TestNode::getParentId, TestNode::getId, id -> List.of(), TestNode::setChildren);
    }

    @Test
    void populateChildrenShouldKeepCurrentHierarchyBehavior() {
        TestNode root = new TestNode(1L, 0L);
        TestNode child = new TestNode(2L, 1L);

        service.populateChildren(root, TestNode::getParentId, TestNode::getId, id -> List.of(child), TestNode::setChildren);
        assertNull(root.getChildren());

        TestNode nonRoot = new TestNode(3L, 9L);
        service.populateChildren(nonRoot, TestNode::getParentId, TestNode::getId, id -> List.of(child), TestNode::setChildren);
        assertEquals(1, nonRoot.getChildren().size());
        assertEquals(2L, nonRoot.getChildren().get(0).getId());
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
