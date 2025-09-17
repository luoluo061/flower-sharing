package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 商品详情对象 folwer_product_detail
 *
 * @author mlhxj
 * @date 2025-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_product_detail")
public class FolwerAppletProductDetail extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 详情ID
     */
    @TableId(value = "detail_id")
    private Long detailId;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 规格详情
     */
    private String remarks;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
