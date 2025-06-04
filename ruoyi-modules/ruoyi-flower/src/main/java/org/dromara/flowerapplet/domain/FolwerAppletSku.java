package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 单品SKU对象 folwer_sku
 *
 * @author mlhxj
 * @date 2025-01-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_sku")
public class FolwerAppletSku extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 单品ID
     */
    @TableId(value = "sku_id")
    private Long skuId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 规格图ID
     */
    private String skuPicid;

    /**
     * 商品颜色
     */
    private String colour;

    /**
     * 数量
     */
    private String number;

    /**
     * 商品重量
     */
    private Long weight;

    /**
     * 商品尺寸
     */
    private String size;

    /**
     * 最大价格
     */
    private Long price;

    /**
     * 最小价格
     */
    private BigDecimal minPrice;

    /**
     * 库存
     */
    private Long actualStocks;

    /**
     * 0 禁用 1 启用
     */
    private Long status;

    /**
     * 颜色
     */
    private String color;

    /**
     * 等级
     */
    private String level;

    /**
     * 颜色代码
     */
    private String colorCode;

    /**
     * 是否是基地 默认为0否，1:是
     */
    private Long isSource;

    /**
     * 基地名称
     */
    private String source;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
