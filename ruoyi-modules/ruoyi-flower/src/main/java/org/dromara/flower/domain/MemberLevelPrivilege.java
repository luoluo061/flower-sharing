package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 会员中心--会员等级--权益名称对象 member_level_privilege
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("member_level_privilege")
public class MemberLevelPrivilege extends TenantEntity {

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
     * 会员等级ID
     */
    private Long memberLevelId;


}
