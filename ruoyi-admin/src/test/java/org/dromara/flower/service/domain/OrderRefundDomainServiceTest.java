package org.dromara.flower.service.domain;

import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("dev")
class OrderRefundDomainServiceTest {

    private final OrderRefundDomainService service = new OrderRefundDomainService();

    @Test
    void prepareBackendRefundCreationShouldLockCurrentRefundSeedData() {
        FolwerOrderVo orderVo = new FolwerOrderVo();
        orderVo.setOrderId(7001L);
        orderVo.setUserId(10L);
        orderVo.setUserName("buyer");
        orderVo.setMemberLevelId(2L);
        orderVo.setActualTotal(188L);
        orderVo.setStatus(5L);

        FolwerOrderRefundBo result = service.prepareBackendRefundCreation(orderVo);

        assertEquals("7001", result.getOrderId());
        assertEquals(10L, result.getUserId());
        assertEquals("buyer", result.getUserName());
        assertEquals(2L, result.getMemberLevelId());
        assertEquals(new BigDecimal("188"), result.getRefundAmount());
        assertEquals(2L, result.getRefundStatus());
        assertEquals(2L, result.getApplyType());
        assertEquals(5L, result.getStatus());
    }

    @Test
    void prepareRefundStatusMutationShouldLockCurrentRefundMutationShape() {
        FolwerOrderRefundVo refundVo = new FolwerOrderRefundVo();
        refundVo.setRefundId(9001L);
        refundVo.setOrderId("9001");
        refundVo.setUserId(10L);
        refundVo.setUserName("buyer");
        refundVo.setMemberLevelId(2L);
        refundVo.setActualTotal(20L);
        refundVo.setRefundAmount(20L);
        refundVo.setRefundMsg("backend-refund");
        refundVo.setApplyType(2L);
        refundVo.setStatus(5L);

        FolwerOrderRefundBo result = service.prepareRefundStatusMutation(refundVo, 3L, "2026-04-09T10:00:00");

        assertEquals(9001L, result.getRefundId());
        assertEquals("9001", result.getOrderId());
        assertEquals(3L, result.getRefundStatus());
        assertEquals("2026-04-09T10:00:00", result.getRefundTime());
        assertEquals(5L, result.getStatus());
    }
}
