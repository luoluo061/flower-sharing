package org.dromara.flower.domain;

import org.apache.poi.hpsf.Decimal;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 物流公司对象 folwer_delivery
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_delivery")
public class FolwerDelivery extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "dvy_id")
    private Long dvyId;

    /**
     * 配送公司名称
     */
    private String dvyName;

    /**
     * 配送方式 1:冷链,  2:空运
     */
    private Long dvyType;

    /**
     * 快递代码
     */
    private String dvyCode;

    /**
     * 排序
     */
    private Long seq;

    /**
     * 打包费
     */
    private BigDecimal packagePrice;

    /**
     * 物料费
     */
    private BigDecimal materialPrice;

    /**
     * 人工费
     */
    private BigDecimal laborPrice;

    /**
     * 发货地址
     */
    private String dvyAddr;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
