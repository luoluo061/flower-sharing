package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 订单物流对象 folwer_order_dvy
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_order_dvy")
public class FolwerAppletOrderDvy extends TenantEntity {

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
    private Double dvyWeight;

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
     * 续重价格
     */
    private BigDecimal additionalWeightPrice;

    /**
     * 订单运费
     */
    private BigDecimal freightAmount;

    /**
     * 保温棉数量
     */
    private Long insulationNum;

    /**
     * 保温棉费用
     */
    private BigDecimal insulationAmount;

    /**
     * 冰瓶数量
     */
    private Long iceNum;

    /**
     * 冰瓶费用
     */
    private BigDecimal iceAmount;

    /**
     * 人工费
     */
    private BigDecimal laborPrice;

    /**
     * 包装费
     */
    private BigDecimal boxPrice;

    /**
     * 物料费
     */
    private BigDecimal materialPrace;

    /**
     * 小计
     */
    private BigDecimal packingAmount;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
