package org.dromara.flower.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 优惠卷管理对象 marketing_coupon
 *
 * @author chy
 * @date 2025-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("marketing_coupon")
public class MarketingCoupon extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
     * 优惠卷名称
     */
    private String couponName;

    /**
     * 优惠卷描述
     */
    private String couponDescription;

    /**
     * 适用商品分类（0所有商品，1特定分类，2特定商品）
     */
    private Long applicableCategory;

    /**
     * 特定分类id（多个分类，逗号隔开）
     */
    private String classificationId;

    /**
     * 特定商品id(多个商品，逗号隔开)
     */
    private String goodsId;

    /**
     * 优惠劵类型（0满减优惠劵，1无限制优惠劵，2花劵）
     */
    private Long couponType;

    /**
     * 优惠券种类（0普通优惠卷，1定向优惠卷）
     */
    private Long couponKind;

    /**
     * 优惠券开始时间
     */
    private Date startTime;

    /**
     * 优惠券结束时间
     */
    private Date endTime;

    /**
     * 满减金额
     */
    private BigDecimal fullReductionAmount;

    /**
     * 优惠券金额
     */
    private BigDecimal couponSum;

    /**
     * 优惠券数量
     */
    private Long couponNumber;

    /**
     * 优惠券剩余数量
     */
    private Long surplusNumber;

    /**
     * 排序
     */
    private Long sorting;

    /**
     * 优惠券状态（0关闭，1开启）
     */
    private Long state;

    /**
     * 特定会员等级（定向优惠卷）
     */
    private String specificMembershipLevel;

    /**
     * 特定会员用户id（定向优惠卷）
     */
    private String specificUsersId;

    /**
     * 适用用户范围（1特定会员等级，2指定用户）
     */
    private Long applicableScope;


}
