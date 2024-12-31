package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.MarketingMemberPromotionPecord;
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
 * 会员推广记录视图对象 marketing_member_promotion_pecord
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MarketingMemberPromotionPecord.class)
public class MarketingMemberPromotionPecordVo implements Serializable {

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
     * 推广编号
     */
    @ExcelProperty(value = "推广编号")
    private Long promotionId;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private String memberId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String memberName;

    /**
     * 用户ID
     */
    @ExcelProperty(value = "用户ID")
    private Long appletUserInformationId;

    /**
     * 被推销人ID
     */
    @ExcelProperty(value = "被推销人ID")
    private Long promotedPersonId;

    /**
     * 被推销人
     */
    @ExcelProperty(value = "被推销人")
    private String promotedPersonName;

    /**
     * 被推销人会员状态 是否充值 0 否 1 是
     */
    @ExcelProperty(value = "被推销人会员状态 是否充值 0 否 1 是")
    private Long promotedPersonStatus;

    /**
     * 被推广人等级
     */
    @ExcelProperty(value = "被推广人等级")
    private String promotedPersonLevel;

    /**
     * 被推广人购买会员金额
     */
    @ExcelProperty(value = "被推广人购买会员金额")
    private Long promoterAmount;

    /**
     * 被推广人消费金额
     */
    @ExcelProperty(value = "被推广人消费金额")
    private Long consumptionAmount;

    /**
     * 购物返点
     */
    @ExcelProperty(value = "购物返点")
    private Long shoppingRebate;

    /**
     * 奖励设置 0 否 1 是
     */
    @ExcelProperty(value = "奖励设置 0 否 1 是")
    private Long rewardSetting;

    /**
     * 推广返现小计
     */
    @ExcelProperty(value = "推广返现小计")
    private Long promotionCashback;

    /**
     * 发布时间
     */
    @ExcelProperty(value = "发布时间")
    private Date createdAt;


}
