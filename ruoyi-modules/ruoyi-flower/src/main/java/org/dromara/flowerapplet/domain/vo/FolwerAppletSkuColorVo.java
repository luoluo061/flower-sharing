package org.dromara.flowerapplet.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.flowerapplet.domain.FolwerAppletProduct;
import org.dromara.flowerapplet.domain.FolwerAppletSku;

import java.io.Serial;
import java.io.Serializable;


/**
 * 小程序端商品管理视图对象 folwer_product
 *
 * @author LL
 * @date 2024-12-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletSku.class)
public class FolwerAppletSkuColorVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 销量
     */
    @ExcelProperty(value = "销量")
    private Long soldNum;


    /**
     * 颜色
     */
    @ExcelProperty(value = "颜色")
    private String color;

    /**
     * 颜色代码
     */
    @ExcelProperty(value = "颜色代码")
    private String colorCode;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级")
    private String level;


}
