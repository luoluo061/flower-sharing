package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerDeliveryTemperature;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 城市温度业务对象 folwer_delivery_temperature
 *
 * @author mlhxj
 * @date 2025-09-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDeliveryTemperature.class, reverseConvertGenerate = false)
public class FolwerDeliveryTemperatureBo extends BaseEntity {

    /**
     * ID
     */
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


}
