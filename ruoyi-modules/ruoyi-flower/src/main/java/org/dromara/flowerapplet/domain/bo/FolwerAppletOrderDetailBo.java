package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletOrderDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 订单详细业务对象 folwer_order_detail
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletOrderDetail.class, reverseConvertGenerate = false)
public class FolwerAppletOrderDetailBo extends BaseEntity {

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
     * 商品名称
     */
    private String productName;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 商品列表图
     */
    private String productListPictureUrl;

    /**
     * 单价
     */
    private BigDecimal orderPrice;

    /**
     * 数量
     */
    private Long number;

    /**
     * 小计
     */
    private BigDecimal subtotal;


}
