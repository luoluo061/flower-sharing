package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerDeliveryBox;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 物流箱型视图对象 folwer_delivery_box
 *
 * @author mlhxj
 * @date 2025-03-29
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerDeliveryBox.class)
public class FolwerDeliveryBoxVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long boxId;

    /**
     * 箱名
     */
    @ExcelProperty(value = "箱名")
    private String boxName;

    /**
     * 长
     */
    @ExcelProperty(value = "长")
    private Long length;

    /**
     * 宽
     */
    @ExcelProperty(value = "宽")
    private Long width;

    /**
     * 高
     */
    @ExcelProperty(value = "高")
    private Long height;

    /**
     * 容积
     */
    @ExcelProperty(value = "容积")
    private Long volume;

    /**
     * 箱子重量
     */
    @ExcelProperty(value = "箱子重量")
    private Double boxWeight;

    /**
     * 成本价
     */
    @ExcelProperty(value = "成本价")
    private BigDecimal costPrice;

    /**
     * 每箱装载重量
     */
    @ExcelProperty(value = "每箱装载重量")
    private Double packagPrice;

    /**
     * 每箱最大扎数
     */
    @ExcelProperty(value = "每箱最大扎数")
    private Long bundle;

    /**
     * 冰瓶数量/扎
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
    @ExcelProperty(value = "是否启用 1：启用 0：禁用")
    private Long status;

    /**
     * 保温棉费用
     */
    @ExcelProperty(value = "保温棉费用")
    private BigDecimal insulationCotton;

    /**
     * 保温棉开始使用温度
     */
    @ExcelProperty(value = "保温棉开始使用温度")
    private Long useInsulationStarttime;

    /**
     * 保温棉温度梯度
     */
    @ExcelProperty(value = "保温棉温度梯度")
    private Long useInsulationEndtime;


}
