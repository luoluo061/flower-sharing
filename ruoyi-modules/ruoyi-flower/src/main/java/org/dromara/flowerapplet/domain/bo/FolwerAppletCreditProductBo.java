package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletCreditProduct;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 积分商品管理业务对象 folwer_credit_product
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletCreditProduct.class, reverseConvertGenerate = false)
public class FolwerAppletCreditProductBo extends BaseEntity implements Serializable {

    /**
     * 主键
     */
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
     * 兑换积分
     */
    private Long redeemPrice;

    /**
     * 规格类型 默认是0，表示单规格，1表示多规格
     */
    private Long normsType;

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
     * 商品详情
     */
    private String remarks;


}
