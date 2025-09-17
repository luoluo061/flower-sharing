package org.dromara.flower.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 物流计费对象 folwer_delivery_price
 *
 * @author mlhxj
 * @date 2025-09-15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
//@EqualsAndHashCode(callSuper = true)
//@TableName("folwer_delivery_price")
public class FolwerDeliveryPriceAdd{

    @Serial
    private static final long serialVersionUID = 1L;


    /**
     * 物流公司ID
     */
    private Long dvyId;


    /**
     * 计费首重
     */
    private Long firstWeight;

    /**
     * 续重重量
     */
    private Long additionalWeight;

    /**
     * 首重价格
     */
    private Long firstWeightPrice;

    /**
     * 续重1价格
     */
    private Long additionalWeightPrice;

    /**
     * 续重2价格
     */
    private Long additionalWeightPricel;

    /**
     * 是否使用，1:使用，0:不使用
     */
    private Long status;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
