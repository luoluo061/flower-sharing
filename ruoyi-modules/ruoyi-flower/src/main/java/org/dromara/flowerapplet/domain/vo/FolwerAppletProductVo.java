package org.dromara.flowerapplet.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flowerapplet.domain.FolwerAppletProduct;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 小程序端商品管理视图对象 folwer_product
 *
 * @author LL
 * @date 2024-12-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletProduct.class)
public class FolwerAppletProductVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 商品名称
     */
    @ExcelProperty(value = "商品名称")
    private String productName;

    /**
     * 商品单位
     */
    @ExcelProperty(value = "商品单位")
    private String unit;

    /**
     * 商品列表图
     */
    @ExcelProperty(value = "商品列表图")
    private String productListPictureUrl;

    /**
     * 商品列表图Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "productListPictureUrl")
    private String productListPictureUrlUrl;
    /**
     * 商品轮播图
     */
    @ExcelProperty(value = "商品轮播图")
    private String productCarouselPictureUrl;

    /**
     * 商品轮播图Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "productCarouselPictureUrl")
    private String productCarouselPictureUrlUrl;
    /**
     * 商品分类
     *
     */
    @ExcelProperty(value = "商品分类")
    private Long categoryId;

    /**
     * 原价
     */
    @ExcelProperty(value = "原价")
    private Long oriPrice;

    /**
     * 划线价
     */
    @ExcelProperty(value = "划线价")
    private Long derlinePrice;

    /**
     * 规格类型 默认是0，表示单规格，1表示多规格
     */
    @ExcelProperty(value = "规格类型 默认是0，表示单规格，1表示多规格")
    private Long normsType;

    /**
     * 单品SKUID
     */
    @ExcelProperty(value = "单品SKUID")
    private Long skuId;

    /**
     * 规格图片
     */
    @ExcelProperty(value = "规格图片")
    private String normsPictureUrl;

    /**
     * 销量
     */
    @ExcelProperty(value = "销量")
    private Long soldNum;

    /**
     * 总库存
     */
    @ExcelProperty(value = "总库存")
    private Long totalStocks;

    /**
     * 重量
     */
    @ExcelProperty(value = "重量")
    private Long weight;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
    @ExcelProperty(value = "配送方式 默认是1，表示物流配送, 0，商家配送")
    private Long deliveryMode;

    /**
     * 快递费
     */
    @ExcelProperty(value = "快递费")
    private Long deliveryPrice;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架
     */
    @ExcelProperty(value = "默认是1，表示正常状态, -1表示删除, 0下架", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "shop_status")
    private Long status;

    /**
     * 是否使用花券 默认是1，表示使用, 0，不使用
     */
    @ExcelProperty(value = "是否使用花券 默认是1，表示使用, 0，不使用")
    private Long isCoupon;

    /**
     * 是否为你推荐 默认是0，表示不推荐, 1，推荐
     */
    @ExcelProperty(value = "是否为你推荐 默认是0，表示不推荐, 1，推荐")
    private Long isRecommend;

    /**
     * 是否支持退款1 是 2  否
     */
    @ExcelProperty(value = "是否支持退款1 是 2  否 ", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "shop_yesno_status")
    private Long ifRefund;

    /**
     * 是否免费配送 1 是 2  否
     */
    @ExcelProperty(value = "是否免费配送 1 是 2  否 ", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "shop_yesno_status")
    private Long ifFreeShipping;

    /**
     * 是否预警 1 是 2  否
     */
    @ExcelProperty(value = "是否预警 1 是 2  否 ", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "shop_yesno_status")
    private Long ifEarlyWarning;

    /**
     * 库存预警值
     */
    @ExcelProperty(value = "库存预警值")
    private Long inventoryEarlyWarningNum;

    /**
     * 库存预警比例
     */
    @ExcelProperty(value = "库存预警比例")
    private Long inventoryEarlyWarningProportion;

    /**
     * 商品评论
     */
    @ExcelProperty(value = "商品评论")
    private String remarks;

    /**
     * 商品规格
     */
    @ExcelProperty(value = "商品规格")
    private List<FolwerAppletSkuVo> skuList;


}
