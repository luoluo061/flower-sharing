package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerDeliveryTemperature;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 城市温度视图对象 folwer_delivery_temperature
 *
 * @author mlhxj
 * @date 2025-09-04
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerDeliveryTemperature.class)
public class FolwerDeliveryTemperatureVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long temperatureId;

    /**
     * 城市code(查询温度)
     */
    @ExcelProperty(value = "城市code(查询温度)")
    private Long cityCode;

    /**
     * 城市
     */
    @ExcelProperty(value = "城市")
    private String cityName;

    /**
     * 城市id
     */
    @ExcelProperty(value = "城市id")
    private Long areaCode;

    /**
     * 温度
     */
    @ExcelProperty(value = "温度")
    private String temperature;


}
