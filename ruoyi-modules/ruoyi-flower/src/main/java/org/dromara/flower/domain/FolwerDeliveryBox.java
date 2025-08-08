package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

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
     * 成本价
     */
    private Long costPrice;

    /**
     * 每箱装载重量
     */
    private Double packagPrice;

    /**
     * 是否启用 1：启用 0：禁用
     */
    private Long status;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
