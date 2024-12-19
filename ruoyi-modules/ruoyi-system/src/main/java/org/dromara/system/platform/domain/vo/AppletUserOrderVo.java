package org.dromara.system.platform.domain.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AppletUserOrderVo {
    //订单号
    private String orderCode;
    //收货人
    private String consigneeName;
    //订单数
    private Integer productNum;
    //实付金额
    private Long actualPayment;
    //支付时间
    private LocalDateTime paymentTime;
}
