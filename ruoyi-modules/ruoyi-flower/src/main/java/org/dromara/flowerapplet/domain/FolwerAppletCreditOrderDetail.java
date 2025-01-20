package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 积分订单详细对象 folwer_credit_order_detail
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_credit_order_detail")
public class FolwerAppletCreditOrderDetail extends TenantEntity {

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
     * 商品ID
     */
    private Long productId;

    /**
     * SKU_ID
     */
    private Long skuId;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品列表图
     */
    private String productListPictureUrl;

    /**
     * 积分
     */
    private Long orderPrice;

    /**
     * 数量
     */
    private Long number;

    /**
     * 小计/积分
     */
    private Long subtotal;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
