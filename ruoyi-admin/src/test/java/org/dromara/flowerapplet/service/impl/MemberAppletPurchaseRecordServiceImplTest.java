package org.dromara.flowerapplet.service.impl;

import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import org.dromara.common.core.domain.R;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flower.mapper.MarketingMemberPromotionPlanMapper;
import org.dromara.flower.mapper.MemberLevelMapper;
import org.dromara.flower.mapper.MemberPurchaseRecordMapper;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.domain.MemberAssetDomainService;
import org.dromara.flower.service.domain.PaymentTransactionDomainService;
import org.dromara.common.mypay.server.IPayService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class MemberAppletPurchaseRecordServiceImplTest {

    @Mock
    private MemberPurchaseRecordMapper baseMapper;
    @Mock
    private AppletUserInformationMapper userInformationMapper;
    @Mock
    private MemberLevelMapper memberLevelMapper;
    @Mock
    private IAppletUserInformationService appletUserInformationService;
    @Mock
    private MarketingMemberPromotionPlanMapper marketingMemberPromotionPlanMapper;
    @Mock
    private MemberAssetDomainService memberAssetDomainService;
    @Mock
    private PaymentTransactionDomainService paymentTransactionDomainService;
    @Mock
    private IPayService payService;

    @InjectMocks
    private MemberAppletPurchaseRecordServiceImpl service;

    @Test
    void queryByIdShouldAttachMemberLevelThroughDomainService() {
        MemberPurchaseRecordVo recordVo = new MemberPurchaseRecordVo();
        recordVo.setMemberLevelId(3L);
        MemberLevelVo levelVo = new MemberLevelVo();
        when(baseMapper.selectVoById(1L)).thenReturn(recordVo);
        when(memberLevelMapper.selectMemberLevelId(3L)).thenReturn(levelVo);

        MemberPurchaseRecordVo result = service.queryById(1L);

        verify(memberAssetDomainService).attachMemberLevel(recordVo, levelVo);
        assertSame(recordVo, result);
    }

    @Test
    void refundOrderShouldDelegateToTransactionDomainService() throws Exception {
        Refund refund = new Refund();
        refund.setStatus(Status.PROCESSING);
        R<String> expected = R.ok("processing");
        when(payService.refundOrder(org.mockito.ArgumentMatchers.any())).thenReturn(refund);
        when(paymentTransactionDomainService.prepareRefundResponse(refund)).thenReturn(expected);

        R<String> result = service.refundOrder(new org.dromara.common.mypay.domain.WxRefundRequest());

        assertSame(expected, result);
        verify(paymentTransactionDomainService).prepareRefundResponse(refund);
    }
}
