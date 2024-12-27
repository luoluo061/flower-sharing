package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 视频管理对象 courses_manager
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("courses_manager")
public class CoursesManager extends TenantEntity {

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
     * 课程名称
     */
    private String name;

    /**
     * 副标题
     */
    private String subtitle;

    /**
     * 课程编号
     */
    private String code;

    /**
     * 课程类型
     */
    private Long courseTypeId;

    /**
     * 发布时间
     */
    private Date publishDate;

    /**
     * 课程数
     */
    private Long number;

    /**
     * 查看权限会员等级标识,多个权益之间逗号分隔,(无限制为super)
     */
    private String accessLevel;

    /**
     * 价格
     */
    private Long price;

    /**
     * 状态 0 否(下架) 1 是(上架)
     */
    private Long status;

    /**
     * 描述
     */
    private String description;

    /**
     * 封面URL的id
     */
    private Long coverUrlId;


}
