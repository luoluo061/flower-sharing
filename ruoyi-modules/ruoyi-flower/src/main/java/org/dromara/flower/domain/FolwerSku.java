package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 单品SKU对象 folwer_sku
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_sku")
public class FolwerSku extends TenantEntity {

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
    private String weight;

    /**
     * 商品尺寸
     */
    private String size;

    /**
     * 价格
     */
    private Long price;

    /**
     * 库存
     */
    private Long actualStocks;

    /**
     * 0 禁用 1 启用
     */
    private Long status;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
