package org.dromara.flowerapplet.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.dromara.flowerapplet.domain.FolwerBasket;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 小程序购物车视图对象 folwer_basket
 *
 * @author mlhxj
 * @date 2025-01-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerBasket.class)
public class FolwerBasketVo implements Serializable {

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
    @ExcelProperty(value = "商品图片Url")
    private String prodPicUrl;

    /***
     * 产品价格
     */
    @ExcelProperty(value = "产品价格")
    private Long price;

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
    private String colour;

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
    private String userId;

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
    private Long totalAmount;

}
