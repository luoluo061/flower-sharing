package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.flower.domain.AppletUserAuthlog;
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
 * 小程序用户信息认证记录视图对象 applet_user_authlog
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = AppletUserAuthlog.class)
public class AppletUserAuthlogVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long authlogId;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private Long userId;

    /**
     * 认证ID
     */
    @ExcelProperty(value = "认证ID")
    private Long authId;

    /**
     * 状态 0:待审核， 1:已审核
     */
    @ExcelProperty(value = "状态 0:待审核， 1:已审核")
    private Long status;

    /**
     * 是否通过 0:未通过， 1:已通过
     */
    @ExcelProperty(value = "是否通过 0:未通过， 1:已通过")
    private Long isPass;

    /**
     * 原因
     */
    @ExcelProperty(value = "原因")
    private String remarks;

    /**
     * 认证时间
     */
    @ExcelProperty(value = "认证时间")
    private Date authTime;


}
