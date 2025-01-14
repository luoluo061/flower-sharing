package org.dromara.common.mypay.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 微信支付信息
 *
 * @author mlhxj
 */
@Data
public class WxPayRequest implements Serializable {

    /**
     * openId
     */
    private String openId;

    /**
     * 商户ID
     */
    private String outMchId;

    /**
     * 支付金额
     */
    private long amount;

    /**
     * 支付终端IP
     */
    private String clientIp;

    /**
     * 订单描述
     */
    private String description;

    /**
     * 订单ID
     */
    private List<String> orderIds;

    /**
     * 系统生成ID
     */
    private String outTradeNo;


    /**
     * 获取金额长整型
     * @return
     */
//    public Long getAmountLong(){
//        if(null!=this.amount){
//            Float t=this.amount*100;
//            return t.longValue();
//        }
//        return 0l;
//    }

}
