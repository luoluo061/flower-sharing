package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerPickAddr;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 用户配送地址视图对象 folwer_pick_addr
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerPickAddr.class)
public class FolwerPickAddrVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long addrId;

    /**
     * 收货人
     */
    @ExcelProperty(value = "收货人")
    private String addrName;

    /**
     * 地址
     */
    @ExcelProperty(value = "地址")
    private String addr;

    /**
     * 手机
     */
    @ExcelProperty(value = "手机")
    private String mobile;

    /**
     * 省份ID
     */
    @ExcelProperty(value = "省份ID")
    private Long provinceId;

    /**
     * 省份
     */
    @ExcelProperty(value = "省份")
    private String province;

    /**
     * 城市ID
     */
    @ExcelProperty(value = "城市ID")
    private Long cityId;

    /**
     * 城市
     */
    @ExcelProperty(value = "城市")
    private String city;

    /**
     * 区/县ID
     */
    @ExcelProperty(value = "区/县ID")
    private Long areaId;

    /**
     * 区/县
     */
    @ExcelProperty(value = "区/县")
    private String area;


}
