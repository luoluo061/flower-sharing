package org.dromara.flower.service.domain;

import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
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
}
