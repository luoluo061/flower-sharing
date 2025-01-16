package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletSku;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 单品SKU业务对象 folwer_sku
 *
 * @author mlhxj
 * @date 2025-01-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletSku.class, reverseConvertGenerate = false)
public class FolwerAppletSkuBo extends BaseEntity {

    /**
     * 单品ID
     */
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


}
