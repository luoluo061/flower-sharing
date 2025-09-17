package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 物流公司对象 folwer_delivery
 *
 * @author mlhxj
 * @date 2025-09-02
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
     * 配送方式  1:普通 2:冷链,  3:空运 
     */
    private Long dvyType;

    /**
     * 付款类型(1:到付, 2:预付)
     */
    private Long isCod;

    /**
     * 备注
     */
    private String reamrk;

    /**
     * 排序
     */
    private Long seq;

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
