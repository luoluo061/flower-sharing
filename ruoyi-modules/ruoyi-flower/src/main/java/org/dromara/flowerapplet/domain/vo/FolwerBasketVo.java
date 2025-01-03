package org.dromara.flowerapplet.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
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
 * @date 2025-01-02
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

    /**
     * SkuID
     */
    @ExcelProperty(value = "SkuID")
    private Long skuId;

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


}
