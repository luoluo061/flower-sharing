package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 课程管理-视频管理-视频对象 courses_manager_video
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("courses_manager_video")
public class CoursesManagerVideo extends TenantEntity {

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
     * 视频名称
     */
    private String videoName;

    /**
     * 视频序号
     */
    private Long sort;

    /**
     * 视频状态 0 下架 1 上架
     */
    private Long status;

    /**
     * 视频存储地址
     */
    private Long url;

    /**
     * 课程id
     */
    private Long coursesManagerId;


}
