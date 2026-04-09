package org.dromara.flower.service.domain;

import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import org.dromara.common.core.domain.R;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("dev")
class PaymentTransactionDomainServiceTest {

    private final PaymentTransactionDomainService service = new PaymentTransactionDomainService();

    @Test
    void preparePaidMutationShouldLockCurrentPaymentShape() {
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(1L);
        Date payTime = new Date();

        FolwerAppletOrderBo result = service.preparePaidMutation(orderVo, 1L, "txn-1", payTime, "callback");

        assertEquals(1L, result.getStatus());
        assertEquals("txn-1", result.getOrderNumber());
        assertEquals(payTime, result.getPayTime());
        assertEquals("callback", result.getPayCallback());
    }

    @Test
    void prepareRefundResponseShouldKeepCurrentStatusMapping() {
        Refund success = new Refund();
        success.setStatus(Status.SUCCESS);
        assertEquals(R.SUCCESS, service.prepareRefundResponse(success).getCode());

        Refund processing = new Refund();
        processing.setStatus(Status.PROCESSING);
        assertEquals(R.SUCCESS, service.prepareRefundResponse(processing).getCode());

        Refund abnormal = new Refund();
        abnormal.setStatus(Status.ABNORMAL);
        assertEquals(R.FAIL, service.prepareRefundResponse(abnormal).getCode());
    }

    @Test
    void transactionStateHelpersShouldLockCurrentSemantics() {
        Transaction success = new Transaction();
        success.setTradeState(Transaction.TradeStateEnum.SUCCESS);

        assertTrue(service.isPaid(success));
        assertFalse(service.isPaid(null));
        assertTrue(service.isRefundTerminal(Status.CLOSED));
        assertFalse(service.isRefundTerminal(Status.PROCESSING));
    }
}
