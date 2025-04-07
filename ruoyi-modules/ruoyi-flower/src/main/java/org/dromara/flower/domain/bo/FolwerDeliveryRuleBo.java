package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerDeliveryRule;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 运费规则业务对象 folwer_delivery_rule
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDeliveryRule.class, reverseConvertGenerate = false)
public class FolwerDeliveryRuleBo extends BaseEntity {

    /**
     * 主键id
     */
    private Long deliveryRuleId;

    /**
     * 省份
     */
    private String province;

    /**
     * 物流企业
     */
    private String deliveryName;

    /**
     * 首重KG
     */
    private Long firstWeight;

    /**
     * 首重价格
     */
    private Long firstPrice;

    /**
     * 续重1价格
     */
    private Long additional1Price;

    /**
     * 续重1价格
     */
    private Long additional2Price;

    /**
     * 票均增值收入
     */
    private Long valueAddedPrice;

    /**
     * 票均计费重量
     */
    private Long valueAddedWeight;

    /**
     * 子母件单件最大重量
     */
    private Long parentChildPackageWeight;

    /**
     * 子母件单件最大体积
     */
    private Long parentChildPackageVolume;

    /**
     * 票均件数
     */
    private Long number;

    /**
     * 线路票数
     */
    private Long packagPrice;


}
