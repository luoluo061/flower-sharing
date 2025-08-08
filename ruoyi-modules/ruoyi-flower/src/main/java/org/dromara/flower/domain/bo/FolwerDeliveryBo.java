package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.apache.poi.hpsf.Decimal;
import org.dromara.flower.domain.FolwerDelivery;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 物流公司业务对象 folwer_delivery
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDelivery.class, reverseConvertGenerate = false)
public class FolwerDeliveryBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
    private Long dvyId;

    /**
     * 配送公司名称
     */
    private String dvyName;

    /**
     * 配送方式 配送方式 1:冷链,  2:空运
     */
    private Long dvyType;

    /**
     * 快递代码
     */
    private String dvyCode;

    /**
     * 排序
     */
    private Long seq;

    /**
     * 打包费
     */
    private BigDecimal packagePrice;

    /**
     * 物料费
     */
    private BigDecimal materialPrice;

    /**
     * 人工费
     */
    private BigDecimal laborPrice;

    /**
     * 发货地址
     */
    private String dvyAddr;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
