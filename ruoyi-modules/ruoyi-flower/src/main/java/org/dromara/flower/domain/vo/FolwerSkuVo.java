package org.dromara.flower.domain.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flower.domain.FolwerSku;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;



/**
 * 单品SKU视图对象 folwer_sku
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerSku.class)
public class FolwerSkuVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 单品ID
     */
    @ExcelProperty(value = "单品ID")
    private Long skuId;

    /**
     * 商品ID
     */
    @ExcelProperty(value = "商品ID")
    private Long prodId;

    /**
     * 规格图ID
     */
    @ExcelProperty(value = "规格图ID")
    private String skuPicid;

    /**
     * 规格图IDURL
     */
    @ExcelProperty(value = "规格图IDURL")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "skuPicid")
    private String skuPicidURL;

    /**
     * 商品颜色
     */
    @ExcelProperty(value = "商品颜色")
    private String colour;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private String number;

    /**
     * 商品重量
     */
    @ExcelProperty(value = "商品重量")
//    @JsonSerialize(using= ToStringSerializer.class)
    private Double weight;

    /**
     * 商品尺寸
     */
    @ExcelProperty(value = "商品尺寸")
    private String size;

    /**
     * 最大价格
     */
    @ExcelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 最小价格
     */
    @ExcelProperty(value = "最小价格")
    private BigDecimal minPrice;

    /**
     * 库存
     */
    @ExcelProperty(value = "库存")
    private Long actualStocks;

    /**
     * 0 禁用 1 启用
     */
    @ExcelProperty(value = "0 禁用 1 启用")
    private Long status;

    /**
     * 箱型ID
     */
    private Long boxId;

    /**
     * 颜色
     */
    @ExcelProperty(value = "颜色")
    private String color;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级")
    private String level;

    /**
     * 颜色代码
     */
    @ExcelProperty(value = "颜色代码")
    private String colorCode;

    /**
     * 是否是基地 默认为0否，1:是
     */
    @ExcelProperty(value = "是否是基地 默认为0否，1:是")
    private Long isSource;

    /**
     * 基地名称
     */
    @ExcelProperty(value = "基地名称")
    private String source;

    /**
     * 销量
     */
    @ExcelProperty(value = "销量")
    private Long soldNum;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long seq;

    /**
     * 规格详情
     */
    @ExcelProperty(value = "规格详情")
    private String details;


}
