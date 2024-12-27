package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 会员中心--兑换记录对象 member_exchange_record
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("member_exchange_record")
public class MemberExchangeRecord extends TenantEntity {

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
     * 兑现金额
     */
    private Long amount;

    /**
     * 兑现金币
     */
    private Long gold;

    /**
     * 剩余金币
     */
    private Long balance;

    /**
     * 创建人中文名称
     */
    private String createName;


}
