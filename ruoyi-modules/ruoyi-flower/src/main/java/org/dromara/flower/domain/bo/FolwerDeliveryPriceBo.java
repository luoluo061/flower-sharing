package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerDeliveryPrice;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 物流计费业务对象 folwer_delivery_price
 *
 * @author mlhxj
 * @date 2025-09-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDeliveryPrice.class, reverseConvertGenerate = false)
public class FolwerDeliveryPriceBo extends BaseEntity {

    /**
     * 物流计费ID
     */
    @NotNull(message = "物流计费ID不能为空", groups = { EditGroup.class })
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


}
