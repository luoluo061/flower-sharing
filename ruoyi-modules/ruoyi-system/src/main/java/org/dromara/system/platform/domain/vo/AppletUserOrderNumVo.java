package org.dromara.system.platform.domain.vo;

import lombok.Data;

/**
 * 用户订单数
 */
@Data
public class AppletUserOrderNumVo {
    /**
     * 待支付
     */
    private Integer pendingPayment;
    /**
     * 待发货
     */
    private Integer pendingShipment;
    /**
     * 待收货
     */
    private Integer pendingPickup;
}
