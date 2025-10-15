package org.dromara.flowerapplet.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 物流计费对象 folwer_delivery_price
 *
 * @author mlhxj
 * @date 2025-07-16
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_delivery_price")
public class FolwerAppletDeliveryPrice extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 物流计费ID
     */
    @TableId(value = "logistic_id")
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

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
