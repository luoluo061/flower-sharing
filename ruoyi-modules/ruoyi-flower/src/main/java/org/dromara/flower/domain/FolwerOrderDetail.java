package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 订单详细对象 folwer_order_detail
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_order_detail")
public class FolwerOrderDetail extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 订单流水号
     */
    private String orderId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品列表图
     */
    private String productListPictureUrl;

    /**
     * 单价
     */
    private Long orderPrice;

    /**
     * 数量
     */
    private Long number;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
    private Long deliveryMode;

    /**
     * 配送方式ID
     */
    private Long dvyId;

    /**
     * 物流单号
     */
    private String dvyFlowId;

    /**
     * 订单运费
     */
    private Long freightAmount;

    /**
     * 用户订单地址Id
     */
    private Long addrOrderId;

    /**
     * 发货时间
     */
    private Date dvyTime;

    /**
     * 完成时间
     */
    private Date finallyTime;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
