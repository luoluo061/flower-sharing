package org.dromara.flowerapplet.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.flowerapplet.domain.FolwerBasket;

import java.util.Date;

/**
 * 小程序购物车业务对象 folwer_basket
 *
 * @author mlhxj
 * @date 2025-01-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerBasket.class, reverseConvertGenerate = false)
public class FolwerShopCartItemBo extends BaseEntity {

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
     * 购物车产品总金额
     */
    private Double productTotalAmount;


}
