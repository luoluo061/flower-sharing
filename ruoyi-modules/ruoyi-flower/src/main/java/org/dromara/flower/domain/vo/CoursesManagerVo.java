package org.dromara.flower.domain.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.CoursesManager;
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
 * 视频管理视图对象 courses_manager
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoursesManager.class)
public class CoursesManagerVo implements Serializable {

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
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String name;

    /**
     * 副标题
     */
    @ExcelProperty(value = "副标题")
    private String subtitle;

    /**
     * 课程编号
     */
    @ExcelProperty(value = "课程编号")
    private String code;

    /**
     * 课程类型
     */
    @ExcelProperty(value = "课程类型")
    private Long courseTypeId;

    /**
     * 课程类型
     */
    @ExcelProperty(value = "课程类型递归父类名称")
    private String courseTypeName;

    /**
     * 发布时间
     */
    @ExcelProperty(value = "发布时间")
    private Date publishDate;

    /**
     * 课程数
     */
    @ExcelProperty(value = "课程数")
    private Long number;

    /**
     * 查看权限会员等级标识,多个权益之间逗号分隔,(无限制为super)
     */
    @ExcelProperty(value = "查看权限会员等级标识,多个权益之间逗号分隔,(无限制为super)")
    private String accessLevel;

    /**
     * 会员查看权限对应ID,多个权益之间逗号分隔
     */
    @ExcelProperty(value = "会员查看权限对应ID,多个权益之间逗号分隔")
    private String accessIds;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private Long price;

    /**
     * 状态 0 否(下架) 1 是(上架)
     */
    @ExcelProperty(value = "状态 0 否(下架) 1 是(上架)")
    private Long status;

    /**
     * 描述
     */
    @ExcelProperty(value = "描述")
    private String description;

    /**
     * 封面URL的id
     */
    @ExcelProperty(value = "封面URL的id")
    private Long coverUrlId;


}
