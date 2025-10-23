package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flower.domain.FolwerDeliveryBox;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 物流箱型业务对象 folwer_delivery_box
 *
 * @author mlhxj
 * @date 2025-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDeliveryBox.class, reverseConvertGenerate = false)
public class FolwerDeliveryBoxBo extends BaseEntity {

    /**
     * 主键id
     */
    private Long boxId;

    /**
     * 箱名
     */
    private String boxName;

    /**
     * 长
     */
    private Long length;

    /**
     * 宽
     */
    private Long width;

    /**
     * 高
     */
    private Long height;

    /**
     * 人工费
     */
    private BigDecimal volume;

    /**
     * 箱子重量
     */
    private Double boxWeight;

    /**
     * 成本价
     */
    private Long costPrice;

    /**
     * 每箱装载重量
     */
    private Double packagPrice;

    /**
     * 每箱最大扎数
     */
    private Long bundle;

    /**
     * 冰瓶/扎
     */
    private Double iceBunch;

    /**
     * 冰瓶费用
     */
    private BigDecimal iceBottleCost;

    /**
     * 冰瓶重量
     */
    private Double iceBottleWeight;

    /**
     * 是否启用 1：启用 0：禁用
     */
    private Long status;

    /**
     * 保温棉费用
     */
    @ExcelProperty(value = "保温棉费用")
    private BigDecimal insulationCotton;

    /**
     * 保温棉开始使用月份
     */
    @ExcelProperty(value = "保温棉开始使用温度")
    private Long useInsulationStarttime;

    /**
     * 保温棉使用结束月份
     */
    @ExcelProperty(value = "保温棉温度梯度")
    private Long useInsulationEndtime;


}
