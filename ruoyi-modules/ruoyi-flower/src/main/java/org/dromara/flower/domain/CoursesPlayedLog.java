package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 课程管理-视频播放记录对象 courses_played_log
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("courses_played_log")
public class CoursesPlayedLog extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;

    /**
     * 视频ID
     */
    private Long coursesManagerVideoId;

    /**
     * 课程ID
     */
    private Long coursesManagerId;

    /**
     * 播放时长毫秒数
     */
    private Long duration;

    /**
     * 观看日期
     */
    private Date viewingTime;

    /**
     * 观看状态 0  未完成 1 完成
     */
    private Long status;


}
