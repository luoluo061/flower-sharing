package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.CoursesManagerDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 课程管理-视频管理-课程详情(富文本)业务对象 courses_manager_detail
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoursesManagerDetail.class, reverseConvertGenerate = false)
public class CoursesManagerDetailBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 课程详情内容
     */
    @NotBlank(message = "课程详情内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 课程Id
     */
    @NotNull(message = "课程Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long coursesManagerId;


}
