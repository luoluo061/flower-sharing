package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.CoursesManagerVideo;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 课程管理-视频管理-视频业务对象 courses_manager_video
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoursesManagerVideo.class, reverseConvertGenerate = false)
public class CoursesManagerVideoBo extends BaseEntity {

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
     * 视频名称
     */
    @NotBlank(message = "视频名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String videoName;

    /**
     * 视频序号
     */
    @NotNull(message = "视频序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long order;

    /**
     * 视频状态 0 下架 1 上架
     */
    @NotNull(message = "视频状态 0 下架 1 上架不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 视频存储地址
     */
    @NotNull(message = "视频存储地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long url;

    /**
     * 课程id
     */
    @NotNull(message = "课程id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long coursesManagerId;


}
