package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 运费模板对象 folwer_delivery_template
 *
 * @author mlhxj
 * @date 2025-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_delivery_template")
public class FolwerDeliveryTemplate extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "temp_id")
    private Long tempId;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 模板名称
     */
    private String tempKey;

    /**
     * 模板值
     */
    private String tempValue;

    /**
     * 是否显示，1:使用，0:不使用
     */
    private Long status;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
