package org.dromara.flower.service.domain;

import org.dromara.flower.domain.bo.FolwerOrderBo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("dev")
class OrderLifecycleDomainServiceTest {

    private final OrderLifecycleDomainService service = new OrderLifecycleDomainService();

    @Test
    void preparePaidOrderShouldLockCurrentPaymentMutationShape() {
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(1L);
        Date payTime = new Date();

        FolwerAppletOrderBo result = service.preparePaidOrder(orderVo, 5L, "txn-1", payTime, "callback");

        assertEquals(5L, result.getStatus());
        assertEquals("txn-1", result.getOrderNumber());
        assertEquals(payTime, result.getPayTime());
        assertEquals("callback", result.getPayCallback());
    }

    @Test
    void prepareRefundingOrderShouldLockBackendRefundTransition() {
        FolwerOrderVo orderVo = new FolwerOrderVo();
        orderVo.setOrderId(2L);
        orderVo.setStatus(5L);

        FolwerOrderBo result = service.prepareRefundingOrder(orderVo);

        assertEquals(2L, result.getStatus());
        assertEquals(2L, result.getOrderId());
    }

    @Test
    void statusHelpersShouldKeepCurrentLifecycleMapping() {
        assertTrue(service.isPendingPayment(0L));
        assertFalse(service.isPendingPayment(1L));
        assertTrue(service.isTerminalState(8L));
        assertFalse(service.isTerminalState(5L));
    }
}
