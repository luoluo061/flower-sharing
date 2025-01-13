package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 会员中心--个人会员权益详情记录对象 oneself_member_level_privilege
 *
 * @author mlhxj
 * @date 2025-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("oneself_member_level_privilege")
public class OneselfMemberLevelPrivilege extends TenantEntity {

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
     * 会员权益名称
     */
    private String name;

    /**
     * 总数量
     */
    private Long amount;

    /**
     * 会员购买记录ID
     */
    private Long memberPurchaseRecordId;

    /**
     * 权益使用状态 0 否 1 是
     */
    private Long status;

    /**
     * 个人权益到期时间
     */
    private Date endTime;

    /**
     * 已使用数量
     */
    private Long usageQuantity;


}
