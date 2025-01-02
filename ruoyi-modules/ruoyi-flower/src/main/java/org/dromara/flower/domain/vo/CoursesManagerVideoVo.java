package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.CoursesManagerVideo;
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
 * 课程管理-视频管理-视频视图对象 courses_manager_video
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoursesManagerVideo.class)
public class CoursesManagerVideoVo implements Serializable {

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
     * 视频名称
     */
    @ExcelProperty(value = "视频名称")
    private String videoName;

    /**
     * 视频序号
     */
    @ExcelProperty(value = "视频序号")
    private Long sort;

    /**
     * 视频状态 0 下架 1 上架
     */
    @ExcelProperty(value = "视频状态 0 下架 1 上架")
    private Long status;

    /**
     * 视频存储地址
     */
    @ExcelProperty(value = "视频存储地址")
    private Long url;

    /**
     * 课程id
     */
    @ExcelProperty(value = "课程id")
    private Long coursesManagerId;


}
