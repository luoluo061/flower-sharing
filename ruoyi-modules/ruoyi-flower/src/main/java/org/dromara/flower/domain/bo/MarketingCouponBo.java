package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.flower.domain.MarketingCoupon;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 优惠卷管理业务对象 marketing_coupon
 *
 * @author chy
 * @date 2025-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MarketingCoupon.class, reverseConvertGenerate = false)
public class MarketingCouponBo extends BaseEntity {

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
     * 优惠卷名称
     */
    @NotBlank(message = "优惠卷名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String couponName;

    /**
     * 优惠卷描述
     */
    //@NotBlank(message = "优惠卷描述不能为空", groups = { AddGroup.class, EditGroup.class })
    private String couponDescription;

    /**
     * 适用商品分类（0所有商品，1特定分类，2特定商品）
     */
    @NotNull(message = "适用商品分类（0所有商品，1特定分类，2特定商品）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long applicableCategory;

    /**
     * 特定分类id（多个分类，逗号隔开）
     */
    //@NotBlank(message = "特定分类id（多个分类，逗号隔开）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String classificationId;

    /**
     * 特定商品id(多个商品，逗号隔开)
     */
    //@NotBlank(message = "特定商品id(多个商品，逗号隔开)不能为空", groups = { AddGroup.class, EditGroup.class })
    private String goodsId;

    /**
     * 优惠劵类型（0满减优惠劵，1无限制优惠劵，2花劵）
     */
    @NotNull(message = "优惠劵类型（0满减优惠劵，1无限制优惠劵，2花劵）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long couponType;

    /**
     * 优惠券种类（0普通优惠卷，1定向优惠卷）（定向优惠券定向会员等级和制定用户）
     */
    @NotNull(message = "优惠券种类（0普通优惠卷，1定向优惠卷）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long couponKind;

    /**
     * 优惠券开始时间
     */
    @NotNull(message = "优惠券开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startTime;

    /**
     * 优惠券结束时间
     */
    @NotNull(message = "优惠券结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endTime;

    /**
     * 满减金额
     */
    //@NotNull(message = "满减金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal fullReductionAmount;

    /**
     * 优惠券金额
     */
    //@NotNull(message = "优惠券金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal couponSum;

    /**
     * 优惠券数量
     */
    @NotNull(message = "优惠券数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long couponNumber;

    /**
     * 优惠券剩余数量
     */
    //@NotNull(message = "优惠券剩余数量不能为空", groups = { AddGroup.class, EditGroup.class })
    //private Long surplusNumber;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sorting;

    /**
     * 优惠券状态（0关闭，1开启）
     */
    //@NotNull(message = "优惠券状态（0关闭，1开启）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long state;

    /**
     * 特定会员等级（定向优惠卷）
     */
    //@NotBlank(message = "特定会员等级（定向优惠卷）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String specificMembershipLevel;



    /**
     * 特定会员用户id（定向优惠卷）
     */
    //@NotBlank(message = "特定会员用户id（定向优惠卷）不能为空", groups = { AddGroup.class, EditGroup.class })
    private String specificUsersId;


    /**
     * 适用用户范围（1特定会员等级，2指定用户）
     */
    //@NotNull(message = "适用用户范围不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long applicableScope;


}
