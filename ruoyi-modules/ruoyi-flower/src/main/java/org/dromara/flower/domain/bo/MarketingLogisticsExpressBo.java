package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MarketingLogisticsExpress;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 营销推广-物流快递业务对象 marketing_logistics_express
 *
 * @author chy
 * @date 2025-01-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MarketingLogisticsExpress.class, reverseConvertGenerate = false)
public class MarketingLogisticsExpressBo extends BaseEntity {

    /**
     * 主键
     */
    //@NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
    //@NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 快递名称
     */
    @NotBlank(message = "快递名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 快递编码
     */
    @NotBlank(message = "快递代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String expressCode;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sort;


}
