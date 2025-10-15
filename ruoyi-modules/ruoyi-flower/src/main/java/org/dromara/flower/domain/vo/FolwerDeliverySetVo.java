package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerDeliverySet;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 物流设置视图对象 folwer_delivery_set
 *
 * @author mlhxj
 * @date 2025-08-01
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerDeliverySet.class)
public class FolwerDeliverySetVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 物流设置ID
     */
    @ExcelProperty(value = "物流设置ID")
    private Long deliverySetId;

    /**
     * 商品ID
     */
    @ExcelProperty(value = "商品ID")
    private Long prodId;

    /**
     * 规格ID
     */
    @ExcelProperty(value = "规格ID")
    private Long skuId;

    /**
     * 是否使用 0否 1是
     */
    @ExcelProperty(value = "是否使用 0否 1是")
    private Long status;

    /**
     * 人工费
     */
    @ExcelProperty(value = "人工费")
    private BigDecimal laborPrice;

    /**
     * 二次人工费
     */
    @ExcelProperty(value = "二次人工费")
    private BigDecimal secondLaborPrice;

//    /**
//     * 保温棉费用
//     */
//    @ExcelProperty(value = "保温棉费用")
//    private BigDecimal insulationCotton;
//
//    /**
//     * 保温棉开始使用温度
//     */
//    @ExcelProperty(value = "保温棉开始使用温度")
//    private Long useInsulationStarttime;
//
//    /**
//     * 保温棉温度梯度
//     */
//    @ExcelProperty(value = "保温棉温度梯度")
//    private Long useInsulationEndtime;

//    /**
//     * 冰瓶费用
//     */
//    @ExcelProperty(value = "冰瓶费用")
//    private BigDecimal iceBottle;

    /**
     * 冰瓶数量/扎
     */
    @ExcelProperty(value = "冰瓶数量/箱")
    private Double iceBottleNum;

//    /**
//     * 冰瓶重量
//     */
//    @ExcelProperty(value = "冰瓶重量")
//    private Double iceBottleWeight;
//
//    /**
//     * 冰瓶开始使用月份
//     */
//    @ExcelProperty(value = "开始增加冰瓶的初始温度")
//    private Long useIceBottleStarttime;
//
//    /**
//     * 冰瓶使用结束月份
//     */
//    @ExcelProperty(value = "冰瓶使用温度梯度")
//    private Long useIceBottleEndtime;


}
