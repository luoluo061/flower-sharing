package org.dromara.flowerapplet.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flowerapplet.domain.FolwerAppletProduct;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


/**
 * 小程序端商品管理视图对象 folwer_product
 *
 * @author LL
 * @date 2024-12-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletProduct.class)
public class FolwerAppletProductColorVo implements Serializable {

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
     * 颜色图
     */
    @ExcelProperty(value = "颜色图")
    private String colorPic;

    /**
     * 颜色图IDURL
     */
    @ExcelProperty(value = "颜色图IDURL")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "colorPic")
    private String colorPicUrl;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级")
    private String level;


}
