package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.CoursesPurchaseRecords;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 课程管理-课程购买记录业务对象 courses_purchase_records
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoursesPurchaseRecords.class, reverseConvertGenerate = false)
public class CoursesPurchaseRecordsBo extends BaseEntity {

    /**
     * 主键
     */
//    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
//    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 订单号
     */
//    @NotBlank(message = "订单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;

    /**
     * 课程名称
     */
    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String coursesName;

    /**
     * 会员ID
     */
    @NotBlank(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberId;

    /**
     * 小程序用户信息ID
     */
    @NotNull(message = "小程序用户信息ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long appletUserInformationId;

    /**
     * 会员名称
     */
//    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 价格
     */
    @NotNull(message = "价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long price;

    /**
     * 订单状态 0 待支付 1 已支付
     */
//    @NotNull(message = "订单状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 课程ID
     */
    @NotNull(message = "课程ID", groups = { AddGroup.class, EditGroup.class })
    private Long coursesManagerId;
}
