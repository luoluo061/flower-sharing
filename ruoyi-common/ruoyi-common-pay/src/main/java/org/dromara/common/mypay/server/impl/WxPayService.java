package org.dromara.common.mypay.server.impl;

import com.wechat.pay.java.core.exception.ValidationException;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.notification.RequestParam;
import  com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.payments.jsapi.model.*;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.profitsharing.ProfitsharingService;
import com.wechat.pay.java.service.profitsharing.model.CreateOrderRequest;
import com.wechat.pay.java.service.profitsharing.model.OrdersEntity;
import com.wechat.pay.java.service.refund.RefundService;
import com.wechat.pay.java.service.refund.model.*;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.mypay.config.MypayConfig;
import org.dromara.common.mypay.config.properties.PayProperties;
import org.dromara.common.mypay.domain.PayProfitsharingParam;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.common.mypay.domain.WxPayRequest;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.common.mypay.server.IPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Slf4j
@Service
public class WxPayService implements IPayService {
    @Autowired
    private MypayConfig mypayConfig;

    @Autowired
    private PayProperties properties;

    @Override
    public WxJsapiResponse JsapiOrder(WxPayRequest wxPayRequest) throws Exception {

        PrepayRequest request = this.getPayOrderRequest(wxPayRequest);
        JsapiService service = mypayConfig.getJsapiService();
        // 调用下单方法，得到应答
        PrepayResponse response = service.prepay(request);
        if (log.isInfoEnabled()) {
            log.info("账户:{},下单:{},金额:{},PrepayId:{}",
                properties.getMchId(), request.getOutTradeNo(), wxPayRequest.getAmount(), response.getPrepayId());
        }
        WxJsapiResponse build = WxJsapiResponse.build(response.getPrepayId(), properties.getAppId(),
            mypayConfig.getPrivateKey(), request.getOutTradeNo());
        return build;
    }

    @Override
    public Transaction transactionsOrder(String outTradeNo) throws Exception {

        QueryOrderByOutTradeNoRequest request = new QueryOrderByOutTradeNoRequest();
        request.setMchid(properties.getMchId());
        request.setOutTradeNo(outTradeNo);
        JsapiService service = mypayConfig.getJsapiService();
        // 调用下单方法，得到应答
        Transaction transaction = service.queryOrderByOutTradeNo(request);
        if (log.isInfoEnabled()) {
            log.info("账户:{},订单号:{}",
                properties.getMchId(), request.getOutTradeNo());
        }
        return transaction;
    }

    @Override
    public Object confirmOrder(HttpServletRequest request, HttpServletResponse response) {
        Transaction transaction = null;
        try {
            NotificationParser parser = mypayConfig.getParser();
            // 构造 RequestParam
            RequestParam requestParam = this.getRequestParam(request);
            transaction = parser.parse(requestParam, Transaction.class);
        } catch (ValidationException ex) {
            // 签名验证失败，返回 401 UNAUTHORIZED 状态码
            if (log.isErrorEnabled()) {
                log.error("签名验证失败，原因:{}", ex.getLocalizedMessage());
            }
            return "ERROR";
        } catch (Exception ex) {
            if (log.isErrorEnabled()) {
                log.error("订单确认失败，原因:{}", ex.getLocalizedMessage());
            }
            return "ERROR";
        }
        // 处理成功，返回 200 OK 状态码
        return transaction;
    }

    @Override
    public void closeOrder(String outTradeNo) throws IOException {
        CloseOrderRequest request = new CloseOrderRequest();
        request.setMchid(properties.getMchId());
        request.setOutTradeNo(outTradeNo);
        JsapiService service = mypayConfig.getJsapiService();
        service.closeOrder(request);
//        return true;
    }

    @Override
    public Refund refundOrder(WxRefundRequest wxRefundRequest) throws Exception {
        try{
            CreateRequest refundOrderRequest = this.getRefundOrderRequest(wxRefundRequest);
            RefundService refundConfig = mypayConfig.getRefundConfig();

            // 调用微信sdk退款接口
            Refund refund = refundConfig.create(refundOrderRequest);

            return refund;
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }

    @Override
    public RefundNotification refundNotify(HttpServletRequest request) {
        try {
            //读取请求体的信息
            ServletInputStream inputStream = request.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String s;
            //读取回调请求体
            while ((s = bufferedReader.readLine()) != null) {
                stringBuffer.append(s);
            }
            // 如果已经初始化了 RSAAutoCertificateConfig，可直接使用
            // 初始化 NotificationParser
            NotificationParser parser = mypayConfig.getParser();

            RequestParam requestParam = this.getRequestParam(request);
            RefundNotification parse = parser.parse(requestParam, RefundNotification.class);
            return parse;
//            System.out.println("parse = " + parse);
//            //parse.getRefundStatus().equals("SUCCESS");说明退款成功
//
//            //这里和上面退款返回差不多的处理，可以抽成一个公共的方法
//            if (Status.SUCCESS.equals(parse.getRefundStatus().SUCCESS)) {
//                //你的业务代码，根据请求返回状态修改对应订单状态
//                return Result.ok("退款成功");
//            }
//            if (Status.PROCESSING.equals(parse.getRefundStatus().PROCESSING)) {
//                //你的业务代码，根据请求返回状态修改对应订单状态
//                return Result.OK("退款中");
//            }
//            if (Status.ABNORMAL.equals(parse.getRefundStatus().ABNORMAL)) {
//                //你的业务代码，根据请求返回状态修改对应订单状态
//                return Result.error("退款异常");
//            }
//            if (Status.CLOSED.equals(parse.getRefundStatus().CLOSED)) {
//                //你的业务代码，根据请求返回状态修改对应订单状态
//                return Result.error("退款关闭");
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    @Override
//    public Object profitSharing(PayProfitsharingParam payProfitsharingParam) throws Exception {
//        try{
//            CreateOrderRequest request = this.getProfitsharingRequest(payProfitsharingParam);
//            ProfitsharingService profitConfig = mypayConfig.getProfitConfig();
//
//
//            // 调用微信sdk分账接口
//            OrdersEntity order = profitConfig.createOrder(request);
//
//            return refund;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }

    /**
     * 生成支付订单
     *
     * @param wxPayRequest
     * @return
     */
    private PrepayRequest getPayOrderRequest(WxPayRequest wxPayRequest) {
        PrepayRequest request = new PrepayRequest();
        request.setAppid(properties.getAppId());
        request.setMchid(properties.getMchId());
        request.setDescription(wxPayRequest.getDescription());
        request.setOutTradeNo(wxPayRequest.getOutTradeNo());
        request.setNotifyUrl(properties.getNotifyUrl());

        SceneInfo sceneInfo = new SceneInfo();
        sceneInfo.setPayerClientIp(wxPayRequest.getClientIp());
        request.setSceneInfo(sceneInfo);

        Amount amount = new Amount();
        amount.setTotal(Integer.valueOf(Math.toIntExact(wxPayRequest.getAmount())));
        request.setAmount(amount);

        Payer payer = new Payer();
        payer.setOpenid(wxPayRequest.getOpenId());
        request.setPayer(payer);
        SettleInfo settleInfo = new SettleInfo();
        settleInfo.setProfitSharing(wxPayRequest.getProfitSharing());
        request.setSettleInfo(settleInfo);

        return request;
    }

    /**
     * 生成退款订单
     *
     * @param wxRefundRequest
     * @return
     */
    private CreateRequest getRefundOrderRequest(WxRefundRequest wxRefundRequest) {
        //构建退款请求
        CreateRequest request = new CreateRequest();

//        request.setOutRefundNo(wxRefundRequest.getOutRefundNo());
//        request.setOutTradeNo(wxRefundRequest.getOutTradeNo());

        // request.setXxx(val)设置所需参数，具体参数可见Request定义
        //构建订单金额信息
        AmountReq amountReq = new AmountReq();
        //退款金额
        amountReq.setRefund(Long.valueOf((int) wxRefundRequest.getAmount().getRefund()));// (wxRefundRequest.getAmount().getRefund() * 100)));
        //原订单金额
        amountReq.setTotal(Long.valueOf((int)wxRefundRequest.getAmount().getTotal())); // (wxRefundRequest.getAmount().getTotal() * 100)));
        //货币类型(默认人民币)
        amountReq.setCurrency("CNY");
        request.setAmount(amountReq);
        request.setOutTradeNo(wxRefundRequest.getOutTradeNo());
        request.setReason("退款");
        //商户退款单号
        request.setOutRefundNo(String.valueOf(wxRefundRequest.getOutRefundNo()));

        return request;
    }

    /**
     * 生成分账信息
     *
     * @param payProfitsharingParam
     * @return
     */
//    private CreateOrderRequest getProfitsharingRequest(PayProfitsharingParam payProfitsharingParam) {
//        //构建分账请求
//        ProfitsharingRequest request = new ProfitsharingRequest();
//
//        CreateOrderRequest request = new CreateOrderRequest();
//        // request.setXxx(val)设置所需参数，具体参数可见Request定义
//        //构建分账金额信息
//        AmountReq amountReq = new AmountReq();
//        //分账金额
//        amountReq.setRefund(Long.valueOf((int) (payProfitsharingParam.getAmount().getRefund() * 100)));
//        //原订单金额
////        amountReq.setTotal(Long.valueOf((int) (payProfitsharingParam.getAmount().getTotal() * 100)));
//        //货币类型(默认人民币)
//        amountReq.setCurrency("CNY");
//        request.setAmount(amountReq);
//        request.setOutTradeNo(wxRefundRequest.getOutTradeNo());
//        request.setReason("退款");
//        //商户退款单号
//        request.setOutRefundNo(String.valueOf(wxRefundRequest.getOutRefundNo()));
//
//        return request;
//    }

    /**
     * 读取请求参数
     *
     * @param request
     * @return
     * @throws IOException
     */
    public RequestParam getRequestParam(HttpServletRequest request) throws IOException {
        String wechatPaySerial = request.getHeader("Wechatpay-Serial");
        String wechatpayNonce = request.getHeader("Wechatpay-Nonce");
        String wechatSignature = request.getHeader("Wechatpay-Signature");
        String wechatTimestamp = request.getHeader("Wechatpay-Timestamp");
        String requestBody = readRequestBody(request);
        if (log.isInfoEnabled()) {
            log.info("微信支付回调响应参数:Wechatpay-Serial={}", wechatPaySerial);
            log.info("微信支付回调响应参数:Wechatpay-Nonce={}", wechatpayNonce);
            log.info("微信支付回调响应参数:Wechatpay-Signature={}", wechatSignature);
            log.info("微信支付回调响应参数:Wechatpay-Timestamp={}", wechatTimestamp);
            log.info("微信支付回调响应参数:body={}", requestBody);
        }
        // 构造 RequestParam
        RequestParam requestParam = new RequestParam.Builder()
            .serialNumber(wechatPaySerial)
            .nonce(wechatpayNonce)
            .signature(wechatSignature)
            .timestamp(wechatTimestamp)
            .body(requestBody)
            .build();
        return requestParam;
    }

    /**
     * 读取body
     *
     * @param request
     * @return
     * @throws IOException
     */
    private String readRequestBody(HttpServletRequest request) throws IOException {
        BufferedReader reader = request.getReader();
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }
}

