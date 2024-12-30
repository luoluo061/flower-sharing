package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerDelivery;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
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
     * 配送方式 1:商家配送, 默认商家配送 2:物流快递
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
     * 建立时间
     */
    private Date recTime;

    /**
     * 修改时间
     */
    private Date modifyTime;

    /**
     * 物流查询接口
     */
    private String queryUrl;

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
