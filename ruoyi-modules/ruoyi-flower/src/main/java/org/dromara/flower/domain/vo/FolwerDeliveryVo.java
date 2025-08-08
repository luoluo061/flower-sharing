package org.dromara.flower.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.poi.hpsf.Decimal;
import org.dromara.flower.domain.FolwerDelivery;
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
 * 物流公司视图对象 folwer_delivery
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerDelivery.class)
public class FolwerDeliveryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long dvyId;

    /**
     * 配送公司名称
     */
    @ExcelProperty(value = "配送公司名称")
    private String dvyName;

    /**
     * 配送方式 1:商家配送, 默认商家配送 2:物流快递
     */
    @ExcelProperty(value = "配送方式 1:冷链,  2:空运 ")
    private Long dvyType;

    /**
     * 快递代码
     */
    @ExcelProperty(value = "快递代码")
    private String dvyCode;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long seq;

    /**
     * 打包费
     */
    @ExcelProperty(value = "打包费")
    private BigDecimal packagePrice;

    /**
     * 物料费
     */
    @ExcelProperty(value = "物料费")
    private BigDecimal materialPrice;

    /**
     * 人工费
     */
    @ExcelProperty(value = "人工费")
    private BigDecimal laborPrice;

    /**
     * 发货地址
     */
    @ExcelProperty(value = "发货地址")
    private String dvyAddr;


}
