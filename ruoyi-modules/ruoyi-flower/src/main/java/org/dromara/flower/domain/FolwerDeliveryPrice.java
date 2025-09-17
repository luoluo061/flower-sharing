package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 物流计费对象 folwer_delivery_price
 *
 * @author mlhxj
 * @date 2025-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_delivery_price")
public class FolwerDeliveryPrice extends TenantEntity {

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
    private Long firstWeight;

    /**
     * 续重重量
     */
    private Long additionalWeight;

    /**
     * 首重价格
     */
    private Long firstWeightPrice;

    /**
     * 续重1价格
     */
    private Long additionalWeightPrice;

    /**
     * 续重2价格
     */
    private Long additionalWeightPricel;

    /**
     * 是否使用，1:使用，0:不使用
     */
    private Long status;

    /**
     * 价格调整规则
     */
    private Long priceRule;

    /**
     * 区域调整规则
     */
    private Long areaRule;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
