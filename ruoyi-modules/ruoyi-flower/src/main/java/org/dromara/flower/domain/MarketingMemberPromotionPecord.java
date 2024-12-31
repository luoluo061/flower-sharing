package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 会员推广记录对象 marketing_member_promotion_pecord
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("marketing_member_promotion_pecord")
public class MarketingMemberPromotionPecord extends TenantEntity {

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
     * 推广编号
     */
    private Long promotionId;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 用户ID
     */
    private Long appletUserInformationId;

    /**
     * 被推销人ID
     */
    private Long promotedPersonId;

    /**
     * 被推销人
     */
    private String promotedPersonName;

    /**
     * 被推销人会员状态 是否充值 0 否 1 是
     */
    private Long promotedPersonStatus;

    /**
     * 被推广人等级
     */
    private String promotedPersonLevel;

    /**
     * 被推广人购买会员金额
     */
    private Long promoterAmount;

    /**
     * 被推广人消费金额
     */
    private Long consumptionAmount;

    /**
     * 购物返点
     */
    private Long shoppingRebate;

    /**
     * 奖励设置 0 否 1 是
     */
    private Long rewardSetting;

    /**
     * 推广返现小计
     */
    private Long promotionCashback;

    /**
     * 发布时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createdAt;


}
