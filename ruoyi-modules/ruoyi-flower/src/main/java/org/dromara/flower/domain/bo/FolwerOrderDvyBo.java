package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerOrderDvy;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 订单物流业务对象 folwer_order_dvy
 *
 * @author mlhxj
 * @date 2025-09-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerOrderDvy.class, reverseConvertGenerate = false)
public class FolwerOrderDvyBo extends BaseEntity {

    /**
     * 订单物流ID
     */
    private Long orderDevId;

    /**
     * 订单号
     */
    private Long orderId;

    /**
     * 物流公司ID
     */
    private Long dvyId;

    /**
     * 物流公司
     */
    private String dvyName;

    /**
     * 物流总重量
     */
    private Long dvyWeight;

    /**
     * 鲜花数量
     */
    private Long flowerNum;

    /**
     * 物流数量
     */
    private Long dvyNum;

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
     * 续重价格
     */
    private Long additionalWeightPrice;

    /**
     * 订单运费
     */
    private Long freightAmount;

    /**
     * 保温棉数量
     */
    private Long insulationNum;

    /**
     * 保温棉费用
     */
    private Long insulationAmount;

    /**
     * 冰瓶数量
     */
    private Long iceNum;

    /**
     * 冰瓶费用
     */
    private Long iceAmount;

    /**
     * 人工费
     */
    private Long laborPrice;

    /**
     * 包装费
     */
    private Long boxPrice;

    /**
     * 小计
     */
    private Long packingAmount;


}
