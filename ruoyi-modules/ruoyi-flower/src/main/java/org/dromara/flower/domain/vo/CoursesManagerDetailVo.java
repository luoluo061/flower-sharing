package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.CoursesManagerDetail;
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
 * 课程管理-视频管理-课程详情(富文本)视图对象 courses_manager_detail
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoursesManagerDetail.class)
public class CoursesManagerDetailVo implements Serializable {

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
     * 课程详情内容
     */
    @ExcelProperty(value = "课程详情内容")
    private String content;

    /**
     * 课程Id
     */
    @ExcelProperty(value = "课程Id")
    private Long coursesManagerId;


}
