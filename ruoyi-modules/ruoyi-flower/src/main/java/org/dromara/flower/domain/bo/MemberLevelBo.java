package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MemberLevel;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 会员等级业务对象 member_level
 *
 * @author chzl
 * @date 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MemberLevel.class, reverseConvertGenerate = false)
public class MemberLevelBo extends BaseEntity {

    /**
     * 主键
     */
    private Long id;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 等级
     */
    @NotNull(message = "等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String grade;

    /**
     * 等级中文名称
     */
    @NotBlank(message = "等级中文名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String gradeName;

    /**
     * 会员图标
     */
    private String gradeIcon;

    /**
     * 折扣比率数值(无百分号)
     */
    @NotNull(message = "折扣比率数值(无百分号)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long discountRatio;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long price;

    /**
     * 可以花券/张
     */
    @NotNull(message = "可用花券/张不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long coupon;

    /**
     * 是否显示 0 否 1 是
     */
    private Long display;

    /**
     * 会员权益标签
     */
    private String memberTag;

}
