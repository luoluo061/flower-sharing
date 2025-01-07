package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.CoursesPlayedLog;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 课程管理-视频播放记录视图对象 courses_played_log
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoursesPlayedLog.class)
public class CoursesPlayedLogVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 视频ID
     */
    @ExcelProperty(value = "视频ID")
    private Long coursesManagerVideoId;

    /**
     * 课程ID
     */
    @ExcelProperty(value = "课程ID")
    private Long coursesManagerId;

    /**
     * 播放时长毫秒数
     */
    @ExcelProperty(value = "播放时长毫秒数")
    private Long duration;

    /**
     * 观看日期
     */
    @ExcelProperty(value = "观看日期")
    private Date viewingTime;

    /**
     * 观看状态 0  未完成 1 完成
     */
    @ExcelProperty(value = "观看状态 0  未完成 1 完成")
    private Long status;

    /**
     * 课程播放记录
     */
    private CoursesPlayedLogVo playedLogVo;
}
