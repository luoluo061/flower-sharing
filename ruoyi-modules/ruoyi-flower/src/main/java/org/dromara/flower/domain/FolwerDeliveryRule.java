package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 运费规则对象 folwer_delivery_rule
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_delivery_rule")
public class FolwerDeliveryRule extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "delivery_rule_id")
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

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
