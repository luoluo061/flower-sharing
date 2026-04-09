package org.dromara.flowerapplet.service.impl;

import org.dromara.flower.service.domain.OrderRefundDomainService;
import org.dromara.flowerapplet.domain.FolwerAppletOrderRefund;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderRefundBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderRefundMapper;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerAppletOrderRefundServiceTest {

    @Mock
    private FolwerAppletOrderRefundMapper baseMapper;
    @Mock
    private IFolwerAppletOrderService orderService;

    @Test
    void insertByBoShouldCreateRefundAndMarkOrderRefunding() throws Exception {
        FolwerAppletOrderRefundServiceImpl service =
            new FolwerAppletOrderRefundServiceImpl(baseMapper, orderService, new OrderRefundDomainService());
        FolwerAppletOrderRefundBo refundBo = new FolwerAppletOrderRefundBo();
        refundBo.setOrderId("1001");
        refundBo.setRefundMsg("broken");
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(1001L);
        orderVo.setUserId(10L);
        orderVo.setUserName("buyer");
        orderVo.setMemberLevelId(1L);
        orderVo.setTotal(new BigDecimal("20.00"));
        orderVo.setActualTotal(18L);
        orderVo.setStatus(1L);

        when(baseMapper.insert(any(FolwerAppletOrderRefund.class))).thenAnswer(invocation -> {
            Object entity = invocation.getArgument(0);
            entity.getClass().getMethod("setRefundId", Long.class).invoke(entity, 901L);
            return 1;
        });
        when(orderService.queryOrder("1001")).thenReturn(orderVo);
        when(orderService.updateByBo(any())).thenReturn(true);

        String result = service.insertByBo(refundBo);

        assertEquals("901", result);
        ArgumentCaptor<FolwerAppletOrderBo> captor = ArgumentCaptor.forClass(FolwerAppletOrderBo.class);
        verify(orderService).updateByBo(captor.capture());
        assertEquals(2L, captor.getValue().getIsRefund());
        assertEquals("1001", captor.getValue().getOrderId());
    }
}
