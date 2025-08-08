package org.dromara.flowerapplet.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flowerapplet.domain.FolwerAppletBasket;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 小程序购物车视图对象 folwer_basket
 *
 * @author mlhxj
 * @date 2025-01-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletBasket.class)
public class FolwerAppletBasketVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long basketId;

    /**
     * 商品ID
     */
    @ExcelProperty(value = "商品ID")
    private Long prodId;

    /***
     * 商品名称
     */
    @ExcelProperty(value = "商品名称")
    private String prodName;

    /***
     * 商品图片
     */
    @ExcelProperty(value = "商品图片")
    private String prodPic;

    /***
     * 商品图片Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "prodPic")
    private String prodPicUrl;

    /***
     * 产品价格
     */
    @ExcelProperty(value = "产品价格")
    private BigDecimal price;

    /**
     * SkuID
     */
    @ExcelProperty(value = "SkuID")
    private Long skuId;

    /***
     * 规格名称
     */
    private String skuName;

    /***
     * 颜色
     */
    private String color;

    /***
     * 等级
     */
    private String level;

    /**
     * 基地名称
     */
    private String source;

    /***
     * 重量
     */
    private String weight;

    /***
     * 尺寸
     */
    private String size;

    /**
     * 优惠券ID
     */
//    @ExcelProperty(value = "优惠券ID")
//    private Long couponId;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long userId;

    /**
     * 购物车产品个数
     */
    @ExcelProperty(value = "购物车产品个数")
    private Long basketCount;

    /**
     * 购物时间
     */
    @ExcelProperty(value = "购物时间")
    private Date basketDate;

    /**
     * 默认是1，表示正常状态,0为下架状态
     */
    @ExcelProperty(value = "默认是1，表示正常状态,0为下架状态")
    private Long status;

    /***
     * 产品价格
     */
    @ExcelProperty(value = "产品价格")
    private BigDecimal totalAmount;

}
