package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MarketingMemberPromotionPecord;
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
 * 会员推广记录业务对象 marketing_member_promotion_pecord
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MarketingMemberPromotionPecord.class, reverseConvertGenerate = false)
public class MarketingMemberPromotionPecordBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 推广编号
     */
     @NotNull(message = "推广编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long promotionId;

    /**
     * 会员ID
     */
    @NotBlank(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberId;

    /**
     * 会员名称
     */
    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberName;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long appletUserInformationId;

    /**
     * 被推销人ID
     */
    @NotNull(message = "被推销人ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long promotedPersonId;

    /**
     * 被推销人
     */
    @NotBlank(message = "被推销人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String promotedPersonName;

    /**
     * 被推销人会员状态 是否充值 0 否 1 是
     */
    @NotNull(message = "被推销人会员状态 是否充值 0 否 1 是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long promotedPersonStatus;

    /**
     * 被推广人等级
     */
    @NotBlank(message = "被推广人等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String promotedPersonLevel;

    /**
     * 被推广人购买会员金额
     */
    @NotNull(message = "被推广人购买会员金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long promoterAmount;

    /**
     * 被推广人消费金额
     */
    @NotNull(message = "被推广人消费金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long consumptionAmount;

    /**
     * 购物返点
     */
    @NotNull(message = "购物返点不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long shoppingRebate;

    /**
     * 奖励设置 0 否 1 是
     */
    @NotNull(message = "奖励设置 0 否 1 是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long rewardSetting;

    /**
     * 推广返现小计
     */
    @NotNull(message = "推广返现小计不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long promotionCashback;

    /**
     * 发布时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "发布时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date createdAt;


}
