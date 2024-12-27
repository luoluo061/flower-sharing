package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerSku;
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
     * 规格图ID
     */
    @ExcelProperty(value = "规格图URL")
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
    private String weight;

    /**
     * 商品尺寸
     */
    @ExcelProperty(value = "商品尺寸")
    private String size;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private Long price;

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


}
