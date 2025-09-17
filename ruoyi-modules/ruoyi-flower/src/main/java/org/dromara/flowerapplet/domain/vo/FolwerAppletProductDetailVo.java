package org.dromara.flowerapplet.domain.vo;

import org.dromara.flowerapplet.domain.FolwerAppletProductDetail;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 商品详情视图对象 folwer_product_detail
 *
 * @author mlhxj
 * @date 2025-08-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletProductDetail.class)
public class FolwerAppletProductDetailVo implements Serializable {

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
