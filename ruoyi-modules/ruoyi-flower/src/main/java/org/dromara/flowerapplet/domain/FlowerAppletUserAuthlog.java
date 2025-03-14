package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 小程序用户信息认证记录对象 applet_user_authlog
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("applet_user_authlog")
public class FlowerAppletUserAuthlog extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "authlog_id")
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

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
