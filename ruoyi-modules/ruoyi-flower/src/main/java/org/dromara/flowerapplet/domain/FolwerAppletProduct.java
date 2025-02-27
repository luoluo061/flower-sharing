package org.dromara.flowerapplet.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 小程序端商品管理对象 folwer_product
 *
 * @author LL
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_product")
public class FolwerAppletProduct extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品单位
     */
    private String unit;

    /**
     * 商品列表图
     */
    private String productListPictureUrl;

    /**
     * 商品轮播图
     */
    private String productCarouselPictureUrl;

    /**
     * 商品分类
     */
    private Long categoryId;

    /**
     * 原价
     */
    private BigDecimal oriPrice;

    /**
     * 划线价
     */
    private BigDecimal derlinePrice;

    /**
     * 规格类型 默认是0，表示单规格，1表示多规格
     */
    private Long normsType;

    /**
     * 单品SKUID
     */
    private Long skuId;

    /**
     * 规格图片
     */
    private String normsPictureUrl;

    /**
     * 销量
     */
    private Long soldNum;

    /**
     * 总库存
     */
    private Long totalStocks;

    /**
     * 重量
     */
    private Long weight;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
    private Long deliveryMode;

    /**
     * 快递费
     */
    private Long deliveryPrice;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架
     */
    private Long status;

    /**
     * 是否使用花券 默认是1，表示使用, 0，不使用
     */
    private Long isCoupon;

    /**
     * 是否为你推荐 默认是0，表示不推荐, 1，推荐
     */
    private Long isRecommend;

    /**
     * 是否支持退款1 是 2  否
     */
    private Long ifRefund;

    /**
     * 是否免费配送 1 是 2  否
     */
    private Long ifFreeShipping;

    /**
     * 是否预警 1 是 2  否
     */
    private Long ifEarlyWarning;

    /**
     * 库存预警值
     */
    private Long inventoryEarlyWarningNum;

    /**
     * 库存预警比例
     */
    private Long inventoryEarlyWarningProportion;

    /**
     * 商品评论
     */
    private String remarks;

    /**
     * 删除标志 0 否 1 是
     */
    @TableLogic
    private Long delFlag;


}
