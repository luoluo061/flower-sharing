package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletCreditOrderDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分订单详细业务对象 folwer_credit_order_detail
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletCreditOrderDetail.class, reverseConvertGenerate = false)
public class FolwerAppletCreditOrderDetailBo extends BaseEntity {

    /**
     * 订单ID
     */
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


}
