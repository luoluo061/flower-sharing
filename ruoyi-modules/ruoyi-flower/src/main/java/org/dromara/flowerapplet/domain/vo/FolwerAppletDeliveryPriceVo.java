package org.dromara.flowerapplet.domain.vo;

import org.dromara.flowerapplet.domain.FolwerAppletDeliveryPrice;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 物流计费视图对象 folwer_delivery_price
 *
 * @author mlhxj
 * @date 2025-07-16
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletDeliveryPrice.class)
public class FolwerAppletDeliveryPriceVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 物流计费ID
     */
    @ExcelProperty(value = "物流计费ID")
    private Long logisticId;

    /**
     * 物流公司ID
     */
    @ExcelProperty(value = "物流公司ID")
    private Long dvyId;

    /**
     * 省份ID
     */
    @ExcelProperty(value = "省份ID")
    private Long provinceId;

    /**
     * 省份
     */
    @ExcelProperty(value = "省份")
    private String province;

    /**
     * 市id
     */
    @ExcelProperty(value = "市id")
    private Long cityId;

    /**
     * 市
     */
    @ExcelProperty(value = "市")
    private String city;

    /**
     * 县ID
     */
    @ExcelProperty(value = "县ID")
    private Long countyId;

    /**
     * 县
     */
    @ExcelProperty(value = "县")
    private String county;

    /**
     * 计费首重
     */
    @ExcelProperty(value = "计费首重")
    private Double firstWeight;

    /**
     * 续重重量
     */
    @ExcelProperty(value = "续重重量")
    private Double additionalWeight;

    /**
     * 首重价格
     */
    @ExcelProperty(value = "首重价格")
    private BigDecimal firstWeightPrice;

    /**
     * 续重1价格
     */
    @ExcelProperty(value = "续重1价格")
    private BigDecimal additionalWeightPrice;

    /**
     * 续重2价格
     */
    @ExcelProperty(value = "续重2价格")
    private BigDecimal additionalWeightPricel;

    /**
     * 是否使用，1:使用，0:不使用
     */
    @ExcelProperty(value = "是否使用，1:使用，0:不使用")
    private Long status;


}
