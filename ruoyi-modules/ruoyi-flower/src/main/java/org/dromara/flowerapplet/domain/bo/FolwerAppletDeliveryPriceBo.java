package org.dromara.flowerapplet.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flowerapplet.domain.FolwerAppletDeliveryPrice;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 物流计费业务对象 folwer_delivery_price
 *
 * @author mlhxj
 * @date 2025-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletDeliveryPrice.class, reverseConvertGenerate = false)
public class FolwerAppletDeliveryPriceBo extends BaseEntity {

    /**
     * 物流计费ID
     */
    private Long logisticId;

    /**
     * 物流公司ID
     */
    private Long dvyId;

    /**
     * 省份ID
     */
    private Long provinceId;

    /**
     * 省份
     */
    private String province;

    /**
     * 市id
     */
    private Long cityId;

    /**
     * 市
     */
    private String city;

    /**
     * 县ID
     */
    private Long countyId;

    /**
     * 县
     */
    private String county;

    /**
     * 计费首重
     */
    private Double firstWeight;

    /**
     * 续重重量
     */
    private Double additionalWeight;

    /**
     * 首重价格
     */
    private BigDecimal firstWeightPrice;

    /**
     * 续重1价格
     */
    private BigDecimal additionalWeightPrice;

    /**
     * 续重2价格
     */
    private BigDecimal additionalWeightPricel;

    /**
     * 是否使用，1:使用，0:不使用
     */
    private Long status;


}
