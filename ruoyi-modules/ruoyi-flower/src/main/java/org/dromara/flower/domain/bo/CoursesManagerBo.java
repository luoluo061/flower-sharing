package org.dromara.flower.domain.bo;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.CoursesManager;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;


import java.util.Date;


/**
 * 视频管理业务对象 courses_manager
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = CoursesManager.class, reverseConvertGenerate = false)
public class CoursesManagerBo extends BaseEntity {

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
     * 课程名称
     */
//    @NotBlank(message = "课程名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 副标题
     */
//    @NotBlank(message = "副标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String subtitle;

    /**
     * 课程编号
     */
//    @NotBlank(message = "课程编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;

    /**
     * 课程类型
     */
//    @NotNull(message = "课程类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long courseTypeId;

    /**
     * 发布时间
     */
//    @NotNull(message = "发布时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date publishDate;

    /**
     * 课程数
     */
//    @NotNull(message = "课程数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long number;

    /**
     * 查看权限会员等级标识,多个权益之间逗号分隔, all 表示全部
     */
//    @NotBlank(message = "查看权限会员等级标识,多个权益之间逗号分隔,(无限制为super)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String accessLevel;

    /**
     * 会员查看权限对应ID,多个权益之间逗号分隔 , all 表示全部
     */
    private String accessIds;

    /**
     * 价格
     */
//    @NotNull(message = "价格不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long price;

    /**
     * 状态 0 否(下架) 1 是(上架)
     */
//    @NotNull(message = "状态 0 否(下架) 1 是(上架)不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 描述
     */
//    @NotBlank(message = "描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String description;

    /**
     * 封面URL的id
     */
//    @NotNull(message = "封面URL的id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long coverUrlId;

    /**
     * 课程详情
     */
    private CoursesManagerDetailBo detailBo;

    /**
     * 查询条件开始时间
     */
    private String  beginDate;

    /**
     * 查询条件结束时间
     */
    private String endDate;

}
