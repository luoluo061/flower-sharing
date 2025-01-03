package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 小程序购物车对象 folwer_basket
 *
 * @author mlhxj
 * @date 2025-01-02
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
     * 删除标志 0 否 1 是
     */
    @TableLogic
    private Long delFlag;


}
