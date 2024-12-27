package org.dromara.flower.domain.vo;

import jakarta.validation.constraints.NotNull;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.flower.domain.FolwerProduct;
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
 * 商品管理视图对象 folwer_product
 *
 * @author Lion Li
 * @date 2024-12-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerProduct.class)
public class FolwerProductVo implements Serializable {

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
     * 商品列表图
     */
    @ExcelProperty(value = "商品列表图URL")
    private String productListPicture;

    /**
     * 商品列表图
     */
    @ExcelProperty(value = "商品轮播图")
    private String productCarouselPictureUrl;

    /**
     * 商品分类
     */
    @ExcelProperty(value = "商品分类")
    private Long categoryId;

    /**
     * 产品类目名称
     */
    @ExcelProperty(value = "产品类目名称")
    private String categoryName;

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
    @ExcelProperty(value = "默认是1，表示正常状态, -1表示删除, 0下架")
    private Long status;

    /**
     * 是否使用花券 默认是1，表示使用, 0，不使用
     */
    @ExcelProperty(value = "是否使用花券 默认是1，表示使用, 0，不使用")
    private Long isCoupon;

    /**
     * 是否支持退款1 是 2  否
     */
    @ExcelProperty(value = "是否支持退款1 是 2  否 ")
    private Long ifRefund;

    /**
     * 是否免费配送 1 是 2  否
     */
    @ExcelProperty(value = "是否免费配送 1 是 2  否 ")
    private Long ifFreeShipping;

    /**
     * 是否预警 1 是 2  否
     */
    @ExcelProperty(value = "是否预警 1 是 2  否 ")
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
     * 商品多规格
     */
    @ExcelProperty(value = "商品多规格")
    private List<FolwerSkuVo> prodSKU;


}
