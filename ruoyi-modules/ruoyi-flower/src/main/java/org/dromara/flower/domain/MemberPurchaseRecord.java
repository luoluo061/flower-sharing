package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 会员购买记录对象 member_purchase_record
 *
 * @author chzl
 * @date 2024-12-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("member_purchase_record")
public class MemberPurchaseRecord extends TenantEntity {

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
     * 订单编号
     */
    private String orderCode;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 会员电话
     */
    private String phone;

    /**
     * 会员等级ID
     */
    private Long memberLevelId;

    /**
     * 等级
     */
    private String grade;

    /**
     * 等级中文名称
     */
    private String gradeName;

    /**
     * 价格
     */
    private Long price;


}
