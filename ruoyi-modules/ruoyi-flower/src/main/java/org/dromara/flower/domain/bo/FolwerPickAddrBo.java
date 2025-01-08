package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flower.domain.FolwerPickAddr;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 用户配送地址业务对象 folwer_pick_addr
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerPickAddr.class, reverseConvertGenerate = false)
public class FolwerPickAddrBo extends BaseEntity {

    /**
     * ID
     */
    private Long addrId;

    /**
     * 自提点名称
     */
    private String addrName;

    /**
     * 地址
     */
    private String addr;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 省份ID
     */
    private Long provinceId;

    /**
     * 省份
     */
    private String province;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 城市
     */
    private String city;

    /**
     * 区/县ID
     */
    private Long areaId;

    /**
     * 区/县
     */
    private String area;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 默认地址状态 0 否 1 是
     */
    private Long status;
}
