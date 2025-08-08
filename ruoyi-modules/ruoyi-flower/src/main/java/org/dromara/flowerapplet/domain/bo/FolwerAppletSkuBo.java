package org.dromara.flowerapplet.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flowerapplet.domain.FolwerAppletSku;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

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
     * 规格轮播图ID
     */
    private String skuPictureId;

    /**
     * 规格名称
     */
    private String colour;

    /**
     * 数量
     */
    private String number;

    /**
     * 商品重量
     */
    private Double weight;

    /**
     * 商品尺寸
     */
    private String size;

    /**
     * 最大价格
     */
    private BigDecimal price;

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
     * 颜色图
     */
    private String colorPic;

    /**
     * 是否是基地 默认为0否，1:是
     */
    private Long isSource;

    /**
     * 基地名称
     */
    private String source;

    /**
     * 排序
     */
    private Long seq;

    /**
     * 销量
     */
    private Long soldNum;

    /**
     * 规格详情
     */
    private String remarks;

}
