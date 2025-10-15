package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 订单物流对象 folwer_order_dvy
 *
 * @author mlhxj
 * @date 2025-09-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_order_dvy")
public class FolwerOrderDvy extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单物流ID
     */
    @TableId(value = "order_dev_id")
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

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
