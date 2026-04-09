package org.dromara.flower.service.impl;

import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.flower.domain.vo.FolwerOrderRefundInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.mapper.FolwerOrderRefundMapper;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.domain.OrderRefundDomainService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerOrderRefundServiceTest {

    @Mock
    private FolwerOrderRefundMapper baseMapper;
    @Mock
    private IPayService payService;
    @Mock
    private IAppletUserInformationService appletUserInformationService;

    @Test
    void submitRefundOrdersShouldApplySuccessfulRefundMutation() throws Exception {
        FolwerOrderRefundServiceImpl service = spy(
            new FolwerOrderRefundServiceImpl(baseMapper, payService, appletUserInformationService, new OrderRefundDomainService())
        );
        Refund refund = new Refund();
        refund.setStatus(Status.SUCCESS);
        refund.setSuccessTime("2026-04-09T10:00:00");

        doReturn(buildRefundInfo(501L)).when(service).queryInfoById(501L);
        doReturn(buildRefundVo(501L)).when(service).queryById(501L);
        doReturn(true).when(service).updateByBo(any());
        when(payService.refundOrder(any())).thenReturn(refund);

        FolwerOrderRefundVo result = service.submitRefundOrders(501L);

        assertEquals(1L, result.getRefundStatus());
        assertEquals("2026-04-09T10:00:00", result.getRefundTime());
    }

    @Test
    void submitRefundOrdersShouldApplyProcessingRefundMutation() throws Exception {
        FolwerOrderRefundServiceImpl service = spy(
            new FolwerOrderRefundServiceImpl(baseMapper, payService, appletUserInformationService, new OrderRefundDomainService())
        );
        Refund refund = new Refund();
        refund.setStatus(Status.PROCESSING);

        doReturn(buildRefundInfo(502L)).when(service).queryInfoById(502L);
        doReturn(buildRefundVo(502L)).when(service).queryById(502L);
        doReturn(true).when(service).updateByBo(any());
        when(payService.refundOrder(any())).thenReturn(refund);

        FolwerOrderRefundVo result = service.submitRefundOrders(502L);

        assertEquals(2L, result.getRefundStatus());
    }

    @Test
    void submitRefundOrdersShouldReturnNullWhenStatusUnknown() throws Exception {
        FolwerOrderRefundServiceImpl service = spy(
            new FolwerOrderRefundServiceImpl(baseMapper, payService, appletUserInformationService, new OrderRefundDomainService())
        );
        Refund refund = new Refund();

        doReturn(buildRefundInfo(503L)).when(service).queryInfoById(503L);
        when(payService.refundOrder(any())).thenReturn(refund);

        assertNull(service.submitRefundOrders(503L));
        verify(service, never()).updateByBo(any());
    }

    private static FolwerOrderRefundInfoVo buildRefundInfo(Long refundId) {
        FolwerOrderRefundInfoVo infoVo = new FolwerOrderRefundInfoVo();
        infoVo.setOrderId(refundId);
        infoVo.setRefundAmount(18L);
        infoVo.setActualTotal(18L);
        return infoVo;
    }

    private static FolwerOrderRefundVo buildRefundVo(Long refundId) {
        FolwerOrderRefundVo refundVo = new FolwerOrderRefundVo();
        refundVo.setRefundId(refundId);
        refundVo.setOrderId("O-" + refundId);
        refundVo.setUserId(10L);
        refundVo.setUserName("buyer");
        refundVo.setMemberLevelId(1L);
        refundVo.setActualTotal(18L);
        refundVo.setRefundAmount(18L);
        refundVo.setRefundMsg("backend-refund");
        refundVo.setApplyType(2L);
        refundVo.setStatus(5L);
        return refundVo;
    }
}
