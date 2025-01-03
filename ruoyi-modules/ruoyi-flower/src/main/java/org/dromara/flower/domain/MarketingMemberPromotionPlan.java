package org.dromara.flower.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 营销推广-会员推广计划对象 marketing_member_promotion_plan
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("marketing_member_promotion_plan")
public class MarketingMemberPromotionPlan extends TenantEntity {

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
     * 推广计划编号
     */
    private String code;

    /**
     * 推广计划名称
     */
    private String name;

    /**
     * 活动开始时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityBegin;

    /**
     * 活动结束时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityEnd;

    /**
     * 活动状态 0 否 1 是
     */
    private Long status;

    /**
     * 活动数量
     */
    private Long num;

    /**
     * 剩余数量
     */
    private Long residue;

    /**
     * 奖励设置 0 现金 1 花券 2 销售比例 3 积分
     */
    private Long award;

    /**
     * 奖励额度
     */
    private Long rewardAmount;

    /**
     * 奖励额度的单位
     */
    private String unit;

    /**
     * 最高奖励
     */
    private Long maxRewar;

    /**
     * 是否叠加 0 否 1 是
     */
    private Long superposition;

    /**
     * 推广计划说明
     */
    private String declare;


    /**
     * 剩余奖励额度
     */
    @ExcelProperty(value = "剩余奖励额度")
    private Long surplusRewar;



}
