package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import org.dromara.common.core.domain.R;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.common.mypay.server.SharingService;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.domain.OrderDetailDomainService;
import org.dromara.flower.service.domain.OrderLifecycleDomainService;
import org.dromara.flower.service.IMarketingCouponService;
import org.dromara.flower.service.IMarketingMemberPromotionPecordService;
import org.dromara.flower.service.IOneselfMemberLevelPrivilegeService;
import org.dromara.flower.service.IFolwerCreditSetService;
import org.dromara.flower.service.IFolwerDeliveryService;
import org.dromara.flower.service.IFolwerOrderSetService;
import org.dromara.flower.service.IFolwerPickAddrService;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderMapper;
import org.dromara.flowerapplet.service.IFolwerAppletBasketService;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDetailService;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDvyService;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class FolwerAppletOrderServiceImplTest {

    @Mock
    private FolwerAppletOrderMapper baseMapper;
    @Mock
    private IFolwerAppletOrderDetailService folwerAppletOrderDetailService;
    @Mock
    private IFolwerAppletProductService productService;
    @Mock
    private IFolwerAppletBasketService basketService;
    @Mock
    private IFolwerPickAddrService folwerPickAddrService;
    @Mock
    private IAppletUserInformationService appletUserInformationService;
    @Mock
    private IFolwerDeliveryService deliveryService;
    @Mock
    private IMarketingCouponService marketingCouponService;
    @Mock
    private IOneselfMemberLevelPrivilegeService oneselfMemberLevelPrivilegeService;
    @Mock
    private IMarketingMemberPromotionPecordService marketingMemberPromotionPecordService;
    @Mock
    private IFolwerAppletSkuService folwerAppletSkuService;
    @Mock
    private IFolwerOrderSetService folwerOrderSetService;
    @Mock
    private IFolwerCreditSetService folwerCreditSetService;
    @Mock
    private IFolwerAppletProductService folwerAppletProductService;
    @Mock
    private IFolwerAppletOrderDvyService folwerAppletOrderDvyService;
    @Mock
    private IPayService payService;
    @Mock
    private SharingService sharingService;
    @Mock
    private Snowflake snowflake;

    private FolwerAppletOrderServiceImpl service;

    @BeforeEach
    void setUp() {
        service = spy(new FolwerAppletOrderServiceImpl(
            baseMapper,
            folwerAppletOrderDetailService,
            productService,
            basketService,
            folwerPickAddrService,
            appletUserInformationService,
            deliveryService,
            marketingCouponService,
            oneselfMemberLevelPrivilegeService,
            marketingMemberPromotionPecordService,
            folwerAppletSkuService,
            folwerOrderSetService,
            folwerCreditSetService,
            folwerAppletProductService,
            folwerAppletOrderDvyService,
            new OrderDetailDomainService(),
            new OrderLifecycleDomainService(),
            payService,
            sharingService
        ));
    }

    @Test
    void refundOrderShouldMapRefundStatuses() throws Exception {
        Refund successRefund = new Refund();
        successRefund.setStatus(Status.SUCCESS);
        when(payService.refundOrder(any(WxRefundRequest.class))).thenReturn(successRefund);

        R<String> result = service.refundOrder(new WxRefundRequest());

        assertEquals(R.SUCCESS, result.getCode());
    }

    @Test
    void refundOrderShouldHandleProcessingStatus() throws Exception {
        Refund refund = new Refund();
        refund.setStatus(Status.PROCESSING);
        when(payService.refundOrder(any(WxRefundRequest.class))).thenReturn(refund);

        R<String> result = service.refundOrder(new WxRefundRequest());

        assertEquals(R.SUCCESS, result.getCode());
    }

    @Test
    void refundOrderShouldHandleAbnormalStatus() throws Exception {
        Refund refund = new Refund();
        refund.setStatus(Status.ABNORMAL);
        when(payService.refundOrder(any(WxRefundRequest.class))).thenReturn(refund);

        R<String> result = service.refundOrder(new WxRefundRequest());

        assertEquals(R.FAIL, result.getCode());
    }

    @Test
    void refundOrderShouldHandleClosedOrMissingStatus() throws Exception {
        Refund closedRefund = new Refund();
        closedRefund.setStatus(Status.CLOSED);
        when(payService.refundOrder(any(WxRefundRequest.class))).thenReturn(closedRefund);

        R<String> closedResult = service.refundOrder(new WxRefundRequest());
        assertEquals(R.FAIL, closedResult.getCode());

        Refund unknownRefund = new Refund();
        when(payService.refundOrder(any(WxRefundRequest.class))).thenReturn(unknownRefund);

        R<String> unknownResult = service.refundOrder(new WxRefundRequest());
        assertEquals(R.FAIL, unknownResult.getCode());
    }

    @Test
    void queryOrderShouldReturnNullWhenOrderIdBlank() throws Exception {
        assertNull(service.queryOrder(""));
        verify(payService, never()).transactionsOrder(any());
    }

    @Test
    void queryOrderShouldReturnNullWhenTradeStateIsNotSuccess() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setTradeState(Transaction.TradeStateEnum.NOTPAY);
        when(payService.transactionsOrder("1001")).thenReturn(transaction);

        assertNull(service.queryOrder("1001"));
    }

    @Test
    void queryOrderShouldReturnNullWhenTransactionMissing() throws Exception {
        when(payService.transactionsOrder("1001")).thenReturn(null);

        assertNull(service.queryOrder("1001"));
    }

    @Test
    void queryOrderShouldUpdateOrderToWaitingDeliveryWhenPaymentConfirmed() throws Exception {
        FolwerAppletOrderVo orderVo = buildPendingOrder();
        AppletUserInformationVo userVo = buildUser();
        FolwerAppletProductVo productVo = buildProduct();
        Transaction transaction = buildSuccessfulTransaction("1001", "tx-query-1");

        when(payService.transactionsOrder("1001")).thenReturn(transaction);
        doReturn(orderVo).when(service).queryById(1001L);
        doReturn(true).when(service).updateByBo(any(FolwerAppletOrderBo.class));
        when(appletUserInformationService.queryById(10L)).thenReturn(userVo);
        when(folwerAppletProductService.queryById(100L)).thenReturn(productVo);

        FolwerAppletOrderVo result = service.queryOrder("1001");

        assertEquals(orderVo, result);
        ArgumentCaptor<FolwerAppletOrderBo> orderCaptor = ArgumentCaptor.forClass(FolwerAppletOrderBo.class);
        verify(service).updateByBo(orderCaptor.capture());
        assertEquals(5L, orderCaptor.getValue().getStatus());
        assertEquals("tx-query-1", orderCaptor.getValue().getOrderNumber());
        assertNotNull(orderCaptor.getValue().getPayTime());
        assertNotNull(orderCaptor.getValue().getPayCallback());
    }

    @Test
    void payCallbackOrderShouldUpdateOrderAndPostPaymentSideEffects() throws Exception {
        FolwerAppletOrderVo orderVo = buildPendingOrder();
        AppletUserInformationVo userVo = buildUser();
        FolwerAppletProductVo productVo = buildProduct();
        Transaction transaction = buildSuccessfulTransaction("1001", "tx-pay-1");

        doReturn(orderVo).when(service).queryById(1001L);
        doReturn(true).when(service).updateByBo(any(FolwerAppletOrderBo.class));
        when(appletUserInformationService.queryById(10L)).thenReturn(userVo);
        when(folwerAppletProductService.queryById(100L)).thenReturn(productVo);

        FolwerAppletOrderVo result = service.payCallbackOrder(transaction);

        assertEquals(orderVo, result);

        ArgumentCaptor<FolwerAppletOrderBo> orderCaptor = ArgumentCaptor.forClass(FolwerAppletOrderBo.class);
        verify(service).updateByBo(orderCaptor.capture());
        assertEquals(1L, orderCaptor.getValue().getStatus());
        assertEquals("tx-pay-1", orderCaptor.getValue().getOrderNumber());
        assertNotNull(orderCaptor.getValue().getPayTime());

        ArgumentCaptor<AppletUserInformationBo> userCaptor = ArgumentCaptor.forClass(AppletUserInformationBo.class);
        verify(appletUserInformationService).updateByBo(userCaptor.capture());
        assertEquals(13L, userCaptor.getValue().getPoints());

        ArgumentCaptor<FolwerAppletProductBo> productCaptor = ArgumentCaptor.forClass(FolwerAppletProductBo.class);
        verify(folwerAppletProductService).updateByBo(productCaptor.capture());
        assertEquals(20L, productCaptor.getValue().getTotalStocks());
        assertEquals(5L, productCaptor.getValue().getSoldNum());
    }

    @Test
    void payCallbackOrderShouldBeIdempotentForPaidOrder() throws Exception {
        FolwerAppletOrderVo orderVo = buildPendingOrder();
        orderVo.setStatus(1L);
        Transaction transaction = buildSuccessfulTransaction("1001", "tx-pay-2");

        doReturn(orderVo).when(service).queryById(1001L);

        FolwerAppletOrderVo result = service.payCallbackOrder(transaction);

        assertEquals(orderVo, result);
        verify(service, never()).updateByBo(any(FolwerAppletOrderBo.class));
        verify(appletUserInformationService, never()).updateByBo(any(AppletUserInformationBo.class));
        verify(folwerAppletProductService, never()).updateByBo(any(FolwerAppletProductBo.class));
    }

    @Test
    void payCallbackOrderShouldReturnNullWhenTransactionMissing() throws Exception {
        assertNull(service.payCallbackOrder(null));
        verify(service, never()).updateByBo(any(FolwerAppletOrderBo.class));
    }

    private static FolwerAppletOrderVo buildPendingOrder() {
        FolwerAppletOrderDetailVo detailVo = new FolwerAppletOrderDetailVo();
        detailVo.setProductId(100L);
        detailVo.setNumber(2L);

        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(1001L);
        orderVo.setUserId(10L);
        orderVo.setStatus(0L);
        orderVo.setRebate(8L);
        orderVo.setActualTotal(100L);
        orderVo.setIsProfitSharing(0L);
        orderVo.setOrderDetails(List.of(detailVo));
        return orderVo;
    }

    private static AppletUserInformationVo buildUser() {
        AppletUserInformationVo userVo = new AppletUserInformationVo();
        userVo.setUserId(10L);
        userVo.setPoints(5L);
        userVo.setParentId(0L);
        return userVo;
    }

    private static FolwerAppletProductVo buildProduct() {
        FolwerAppletProductVo productVo = new FolwerAppletProductVo();
        productVo.setId(100L);
        productVo.setTotalStocks(10L);
        productVo.setSoldNum(3L);
        productVo.setDeliveryPrice(BigDecimal.ZERO);
        return productVo;
    }

    private static Transaction buildSuccessfulTransaction(String outTradeNo, String transactionId) {
        Transaction transaction = new Transaction();
        transaction.setOutTradeNo(outTradeNo);
        transaction.setTransactionId(transactionId);
        transaction.setSuccessTime("2026-04-07T12:34:56");
        transaction.setTradeState(Transaction.TradeStateEnum.SUCCESS);
        return transaction;
    }
}
