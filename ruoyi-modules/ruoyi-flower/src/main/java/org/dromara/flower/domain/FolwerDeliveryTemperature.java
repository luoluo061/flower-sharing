package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 城市温度对象 folwer_delivery_temperature
 *
 * @author mlhxj
 * @date 2025-09-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_delivery_temperature")
public class FolwerDeliveryTemperature extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "temperature_id")
    private Long temperatureId;

    /**
     * 城市code(查询温度)
     */
    private Long cityCode;

    /**
     * 城市
     */
    private String cityName;

    /**
     * 城市id
     */
    private Long areaCode;

    /**
     * 温度
     */
    private String temperature;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
