package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

import java.io.Serial;

/**
 * 小程序购物车对象 folwer_basket
 *
 * @author mlhxj
 * @date 2025-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_basket")
public class FolwerBasket extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "basket_id")
    private Long basketId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * SkuID
     */
    private Long skuId;

    /**
     * 优惠券ID
     */
//    private Long couponId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 购物车产品个数
     */
    private Long basketCount;

    /**
     * 购物时间
     */
    private Date basketDate;

    /**
     * 默认是1，表示正常状态,0为下架状态
     */
    private Long status;

    /**
     * 删除标志 0 否 1 是
     */
    @TableLogic
    private Long delFlag;


}
