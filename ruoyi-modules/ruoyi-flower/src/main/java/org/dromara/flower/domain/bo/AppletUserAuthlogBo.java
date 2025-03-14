package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.AppletUserAuthlog;
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
 * 小程序用户信息认证记录业务对象 applet_user_authlog
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppletUserAuthlog.class, reverseConvertGenerate = false)
public class AppletUserAuthlogBo extends BaseEntity {

    /**
     * 主键id
     */
    private Long authlogId;

    /**
     * 会员ID
     */
    private Long userId;

    /**
     * 认证ID
     */
    private Long authId;

    /**
     * 状态 0:待审核， 1:已审核
     */
    private Long status;

    /**
     * 是否通过 0:未通过， 1:已通过
     */
    private Long isPass;

    /**
     * 原因
     */
    private String remarks;

    /**
     * 认证时间
     */
    private Date authTime;


}
