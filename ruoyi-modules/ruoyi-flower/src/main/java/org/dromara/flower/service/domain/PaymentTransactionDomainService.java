package org.dromara.flower.service.domain;

import cn.hutool.core.bean.BeanUtil;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import org.dromara.common.core.domain.R;
import org.dromara.common.mypay.domain.WxPayRequest;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

@Service
public class PaymentTransactionDomainService {

    public WxPayRequest preparePaymentRequest(String clientIp,
                                              String outTradeNo,
                                              Long amount,
                                              String openId,
                                              String description) {
        WxPayRequest request = new WxPayRequest();
        request.setClientIp(clientIp);
        request.setOutTradeNo(outTradeNo);
        request.setAmount(amount);
        request.setOpenId(openId);
        request.setDescription(description);
        return request;
    }

    public FolwerAppletOrderBo preparePaidMutation(FolwerAppletOrderVo orderVo,
                                                   Long targetStatus,
                                                   String transactionId,
                                                   Date payTime,
                                                   String payCallback) {
        FolwerAppletOrderBo orderBo = new FolwerAppletOrderBo();
        BeanUtil.copyProperties(orderVo, orderBo);
        orderBo.setStatus(targetStatus);
        orderBo.setOrderNumber(transactionId);
        orderBo.setPayTime(payTime);
        orderBo.setPayCallback(payCallback);
        return orderBo;
    }

    public R<String> prepareRefundResponse(Refund refund) {
        if (Objects.isNull(refund) || Objects.isNull(refund.getStatus())) {
            return R.fail("退款状态未知");
        }
        Status status = refund.getStatus();
        if (Status.SUCCESS.equals(status)) {
            return R.ok("退款成功");
        }
        if (Status.PROCESSING.equals(status)) {
            return R.ok("退款处理中");
        }
        if (Status.ABNORMAL.equals(status)) {
            return R.fail("退款异常");
        }
        if (Status.CLOSED.equals(status)) {
            return R.fail("退款关闭");
        }
        return R.fail("退款状态未知");
    }

    public boolean isPaid(Transaction transaction) {
        return Objects.nonNull(transaction)
            && Transaction.TradeStateEnum.SUCCESS.equals(transaction.getTradeState());
    }

    public boolean isRefundTerminal(Status status) {
        return Status.SUCCESS.equals(status)
            || Status.ABNORMAL.equals(status)
            || Status.CLOSED.equals(status);
    }
}
