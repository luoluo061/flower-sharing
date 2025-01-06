package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerBasket;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import org.dromara.flowerapplet.domain.FolwerShopCartItem;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.Date;
import java.util.List;

/**
 * 小程序购物车业务对象 folwer_basket
 *
 * @author mlhxj
 * @date 2025-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerBasket.class, reverseConvertGenerate = false)
public class FolwerBasketBo extends BaseEntity {

    /**
     * 主键
     */
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



}
