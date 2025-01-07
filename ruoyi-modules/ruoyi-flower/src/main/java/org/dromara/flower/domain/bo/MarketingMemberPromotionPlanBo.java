package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MarketingMemberPromotionPlan;
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
 * 营销推广-会员推广计划业务对象 marketing_member_promotion_plan
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MarketingMemberPromotionPlan.class, reverseConvertGenerate = false)
public class MarketingMemberPromotionPlanBo extends BaseEntity {

    /**
     * 主键
     */
    //@NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
    //@NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 推广计划编号
     */
    //@NotBlank(message = "推广计划编号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String code;

    /**
     * 推广计划名称
     */
    @NotBlank(message = "推广计划名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 活动开始时间
     */
    @NotNull(message = "活动开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityBegin;

    /**
     * 活动结束时间
     */
    @NotNull(message = "活动结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityEnd;

    /**
     * 活动状态 0 否 1 是
     */
    @NotNull(message = "活动状态 0 否 1 是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 活动数量
     */
    @NotNull(message = "活动数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long num;

    /**
     * 剩余数量
     */
    // @NotNull(message = "剩余数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long residue;

    /**
     * 奖励设置 0 现金 1 花券 2 销售比例 3 积分
     */
    // @NotNull(message = "奖励设置 0 现金 1 花券 2 销售比例 3 积分 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long award;

    /**
     * 奖励额度
     */
    @NotNull(message = "奖励额度不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long rewardAmount;

    /**
     * 奖励额度的单位
     */
    //@NotBlank(message = "奖励额度的单位不能为空", groups = { AddGroup.class, EditGroup.class })
    private String unit;

    /**
     * 最高奖励
     */
    @NotNull(message = "最高奖励不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long maxRewar;

    /**
     * 是否叠加 0 否 1 是
     */
    @NotNull(message = "是否叠加 0 否 1 是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long superposition;

    /**
     * 推广计划说明
     */
    //@NotBlank(message = "推广计划说明不能为空", groups = { AddGroup.class, EditGroup.class })
    private String declareText;


    /**
     * 推广类别
     */
    @NotBlank(message = "推广类别不能为空",groups = { AddGroup.class, EditGroup.class })
    private  String category;

    /**
     * 推广类别细项（会员规则id）
     */
    @NotBlank(message = "推广类别细项不能为空",groups = { AddGroup.class, EditGroup.class })
    private String categoryDetailsId;

    /**
     * 推广类别细项（会员规则名称）
     */
    @NotBlank(message = "推广类别细项名称不能为空",groups = { AddGroup.class, EditGroup.class })
    private String categoryDetailsName;


}
