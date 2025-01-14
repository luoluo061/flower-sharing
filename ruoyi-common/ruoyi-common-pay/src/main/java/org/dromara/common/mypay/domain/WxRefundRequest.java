package org.dromara.common.mypay.domain;

import lombok.Data;

import java.util.List;

@Data
public class WxRefundRequest {
    private String transactionId;

    /***
     * 商户订单号(选填，与微信支付订单号二选一)
     */
    private String outTradeNo;

    /***
     * 商户退款单号(必填)
     */
    private String outRefundNo;

    /***
     * 退款原因(选填)
     */
    private String reason;

    /***
     * 退款结果回调url
     */
    private String notifyUrl = "";

    /***
     * 退款资金来源(选填)
     */
    private String fundsAccount;

    /***
     * 金额信息(必填)
     */
    private RefundAmount amount;

//    @ApiModelProperty(name = "goodsDetail", value = "退款商品(选填)")
//    private List<Goods> goodsDetail;


}
