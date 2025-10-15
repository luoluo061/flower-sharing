package org.dromara.flower.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.math.BigDecimal;

/**
 * 物流箱型对象 folwer_delivery_box
 *
 * @author mlhxj
 * @date 2025-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_delivery_box")
public class FolwerDeliveryBox extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "box_id")
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
     * 容积
     */
    private Long volume;

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
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;

    /**
     * 保温棉费用
     */
    private BigDecimal insulationCotton;

    /**
     * 保温棉开始使用月份
     */
    private Long useInsulationStarttime;

    /**
     * 保温棉使用结束月份
     */
    private Long useInsulationEndtime;


}
