package org.dromara.flowerapplet.domain.bo;

import io.swagger.v3.oas.annotations.media.Schema;
import org.dromara.flowerapplet.domain.FolwerAppletOrderDvy;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单物流业务对象 folwer_order_dvy
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletOrderDvy.class, reverseConvertGenerate = false)
public class FolwerAppletOrderDvyBo extends BaseEntity {

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

//    /**
//     * 物流公司
//     */
//    private String dvyName;
//
//    /**
//     * 物流总重量
//     */
//    private Long dvyWeight;
//
//    /**
//     * 鲜花数量
//     */
//    private Long flowerNum;
//
//    /**
//     * 物流数量
//     */
//    private Long dvyNum;
//
//    /**
//     * 计费首重
//     */
//    private Double firstWeight;
//
//    /**
//     * 续重重量
//     */
//    private Double additionalWeight;
//
//    /**
//     * 首重价格
//     */
//    private BigDecimal firstWeightPrice;
//
//    /**
//     * 续重价格
//     */
//    private BigDecimal additionalWeightPrice;
//
//    /**
//     * 订单运费
//     */
//    private BigDecimal freightAmount;
//
    /**
     * 保温棉数量
     */
    private Long insulationNum;
//
//    /**
//     * 保温棉费用
//     */
//    private BigDecimal insulationAmount;
//
//    /**
//     * 冰瓶数量
//     */
//    private Long iceNum;
//
//    /**
//     * 冰瓶费用
//     */
//    private BigDecimal iceAmount;
//
//    /**
//     * 人工费
//     */
//    private BigDecimal laborPrice;
//
//    /**
//     * 包装费
//     */
//    private BigDecimal boxPrice;
//
//    /**
//     * 小计
//     */
//    private BigDecimal packingAmount;

    /**
     * 购物车id 列表
     */
    private List<String> basketIds;


    /**
     * 规格ID
     */
    private String skuId;

    /**
     * 产品个数
     */
    private Integer prodCount;

    /**
     * 用户ID
     */
    private String userId;


}
