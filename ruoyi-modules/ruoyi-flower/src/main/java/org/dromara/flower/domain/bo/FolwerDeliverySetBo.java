package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flower.domain.FolwerDeliverySet;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 物流设置业务对象 folwer_delivery_set
 *
 * @author mlhxj
 * @date 2025-08-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDeliverySet.class, reverseConvertGenerate = false)
public class FolwerDeliverySetBo extends BaseEntity {

    /**
     * 物流设置ID
     */
    private Long deliverySetId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 是否使用 0否 1是
     */
    private Long status;

    /**
     * 人工费
     */
    private BigDecimal laborPrice;

    /**
     * 二次人工费
     */
    private BigDecimal secondLaborPrice;

//    /**
//     * 保温棉费用
//     */
//    private BigDecimal insulationCotton;
//
//    /**
//     * 保温棉开始使用温度
//     */
//    private Long useInsulationStarttime;
//
//    /**
//     * 保温棉温度梯度
//     */
//    private Long useInsulationEndtime;

    /**
     * 冰瓶费用
     */
//    private BigDecimal iceBottle;

    /**
     * 冰瓶数量/扎
     */
    private Double iceBottleNum;

    /**
     * 冰瓶重量
     */
//    private Double iceBottleWeight;

    /**
     * 开始增加冰瓶的初始温度
     */
//    private Long useIceBottleStarttime;

    /**
     * 冰瓶使用温度梯度
     */
//    private Long useIceBottleEndtime;


}
