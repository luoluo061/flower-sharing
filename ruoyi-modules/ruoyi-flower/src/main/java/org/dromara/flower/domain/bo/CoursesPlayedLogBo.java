package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.CoursesPlayedLog;
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
 * 课程管理-视频播放记录业务对象 courses_played_log
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoursesPlayedLog.class, reverseConvertGenerate = false)
public class CoursesPlayedLogBo extends BaseEntity {

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
     * 视频ID
     */
    @NotNull(message = "视频ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long coursesManagerVideoId;

    /**
     * 课程ID
     */
    @NotNull(message = "课程ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long coursesManagerId;

    /**
     * 播放时长毫秒数
     */
    @NotNull(message = "播放时长毫秒数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long duration;

    /**
     * 观看日期
     */
    @NotNull(message = "观看日期不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date viewingTime;

    /**
     * 观看状态 0  未完成 1 完成
     */
    @NotNull(message = "观看状态 0  未完成 1 完成不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;


}
