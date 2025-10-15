package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerProductDetail;
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
 * 商品详情视图对象 folwer_product_detail
 *
 * @author mlhxj
 * @date 2025-08-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerProductDetail.class)
public class FolwerProductDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 详情ID
     */
    @ExcelProperty(value = "详情ID")
    private Long detailId;

    /**
     * 规格ID
     */
    @ExcelProperty(value = "规格ID")
    private Long skuId;

    /**
     * 规格详情
     */
    @ExcelProperty(value = "规格详情")
    private String remarks;


}
