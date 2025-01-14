package org.dromara.common.mypay.server;


import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.RefundNotification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.dromara.common.mypay.domain.PayProfitsharingParam;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.common.mypay.domain.WxPayRequest;
import org.dromara.common.mypay.domain.WxRefundRequest;

import java.io.IOException;

public interface IPayService {


    /**
     * 微信预支付
     * @param
     * @return
     * @throws Exception
     */
    WxJsapiResponse JsapiOrder(WxPayRequest request) throws Exception;

    /**
     * 支付回调确认
     * @param request
     * @return
     */
    Object confirmOrder(HttpServletRequest request, HttpServletResponse response);

    /**
     * 关闭订单
     * @param outTradeNo
     * @return
     */
    void closeOrder(String outTradeNo) throws IOException;

    /***
     * 退款
     * @param wxRefundRequest
     * @return
     * @throws Exception
     */
    Refund refundOrder(WxRefundRequest wxRefundRequest) throws Exception;

    /**
     * 退款回调确认
     * @param request
     * @return
     */
    RefundNotification refundNotify(HttpServletRequest request);


    //分账
//    Object profitSharing(PayProfitsharingParam payProfitsharingParam) throws Exception;

}
