package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.MarketingMemberPromotionPlan;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 营销推广-会员推广计划视图对象 marketing_member_promotion_plan
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MarketingMemberPromotionPlan.class)
public class MarketingMemberPromotionPlanVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 推广计划编号
     */
    @ExcelProperty(value = "推广计划编号")
    private String code;

    /**
     * 推广计划名称
     */
    @ExcelProperty(value = "推广计划名称")
    private String name;

    /**
     * 活动开始时间
     */
    @ExcelProperty(value = "活动开始时间")
    private Date activityBegin;

    /**
     * 活动结束时间
     */
    @ExcelProperty(value = "活动结束时间")
    private Date activityEnd;




    /**
     * 活动状态 0 否 1 是
     */
    @ExcelProperty(value = "活动状态 0 否 1 是")
    private Long status;

    /**
     * 活动数量
     */
    @ExcelProperty(value = "活动数量")
    private Long num;

    /**
     * 剩余数量
     */
    @ExcelProperty(value = "剩余数量")
    private Long residue;

    /**
     * 奖励设置 0 现金 1 花券 2 销售比例 3 积分
     */
    @ExcelProperty(value = "奖励设置 0 现金 1 花券 2 销售比例 3 积分 ")
    private Long award;

    /**
     * 奖励额度
     */
    @ExcelProperty(value = "奖励额度")
    private Long rewardAmount;

    /**
     * 奖励额度的单位
     */
    @ExcelProperty(value = "奖励额度的单位")
    private String unit;

    /**
     * 最高奖励
     */
    @ExcelProperty(value = "最高奖励")
    private Long maxRewar;

    /**
     * 剩余奖励额度
     */
    @ExcelProperty(value = "剩余奖励额度")
    private Long surplusRewar;


    /**
     * 是否叠加 0 否 1 是
     */
    @ExcelProperty(value = "是否叠加 0 否 1 是")
    private Long superposition;

    /**
     * 推广计划说明
     */
    @ExcelProperty(value = "推广计划说明")
    private String declare;


    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;





}
