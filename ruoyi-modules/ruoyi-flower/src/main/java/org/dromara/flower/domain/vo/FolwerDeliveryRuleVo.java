package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerDeliveryRule;
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
 * 运费规则视图对象 folwer_delivery_rule
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerDeliveryRule.class)
public class FolwerDeliveryRuleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long deliveryRuleId;

    /**
     * 省份
     */
    @ExcelProperty(value = "省份")
    private String province;

    /**
     * 物流企业
     */
    @ExcelProperty(value = "物流企业")
    private String deliveryName;

    /**
     * 首重KG
     */
    @ExcelProperty(value = "首重KG")
    private Long firstWeight;

    /**
     * 首重价格
     */
    @ExcelProperty(value = "首重价格")
    private Long firstPrice;

    /**
     * 续重1价格
     */
    @ExcelProperty(value = "续重1价格")
    private Long additional1Price;

    /**
     * 续重1价格
     */
    @ExcelProperty(value = "续重1价格")
    private Long additional2Price;

    /**
     * 票均增值收入
     */
    @ExcelProperty(value = "票均增值收入")
    private Long valueAddedPrice;

    /**
     * 票均计费重量
     */
    @ExcelProperty(value = "票均计费重量")
    private Long valueAddedWeight;

    /**
     * 子母件单件最大重量
     */
    @ExcelProperty(value = "子母件单件最大重量")
    private Long parentChildPackageWeight;

    /**
     * 子母件单件最大体积
     */
    @ExcelProperty(value = "子母件单件最大体积")
    private Long parentChildPackageVolume;

    /**
     * 票均件数
     */
    @ExcelProperty(value = "票均件数")
    private Long number;

    /**
     * 线路票数
     */
    @ExcelProperty(value = "线路票数")
    private Long packagPrice;


}
