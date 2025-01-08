package org.dromara.flower.domain.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.MarketingCoupon;
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
 * 优惠卷管理视图对象 marketing_coupon
 *
 * @author chy
 * @date 2025-01-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MarketingCoupon.class)
public class MarketingCouponVo implements Serializable {

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
     * 优惠卷名称
     */
    @ExcelProperty(value = "优惠卷名称")
    private String couponName;

    /**
     * 优惠卷描述
     */
    @ExcelProperty(value = "优惠卷描述")
    private String couponDescription;

    /**
     * 适用商品分类（0所有商品，1特定分类，2特定商品）
     */
    @ExcelProperty(value = "适用商品分类", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=所有商品，1特定分类，2特定商品")
    private Long applicableCategory;

    /**
     * 特定分类id（多个分类，逗号隔开）
     */
    @ExcelProperty(value = "特定分类id", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "多=个分类，逗号隔开")
    private String classificationId;

    /**
     * 特定商品id(多个商品，逗号隔开)
     */
    @ExcelProperty(value = "特定商品id(多个商品，逗号隔开)")
    private String goodsId;

    /**
     * 优惠劵类型（0满减优惠劵，1无限制优惠劵，2花劵）
     */
    @ExcelProperty(value = "优惠劵类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=满减优惠劵，1无限制优惠劵，2花劵")
    private Long couponType;

    /**
     * 优惠券种类（0普通优惠卷，1定向优惠卷）
     */
    @ExcelProperty(value = "优惠券种类", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=普通优惠卷，1定向优惠卷")
    private Long couponKind;

    /**
     * 优惠券开始时间
     */
    @ExcelProperty(value = "优惠券开始时间")
    private Date startTime;

    /**
     * 优惠券结束时间
     */
    @ExcelProperty(value = "优惠券结束时间")
    private Date endTime;

    /**
     * 满减金额
     */
    @ExcelProperty(value = "满减金额")
    private BigDecimal fullReductionAmount;

    /**
     * 优惠券金额
     */
    @ExcelProperty(value = "优惠券金额")
    private BigDecimal couponSum;

    /**
     * 优惠券数量
     */
    @ExcelProperty(value = "优惠券数量")
    private Long couponNumber;

    /**
     * 优惠券剩余数量
     */
    @ExcelProperty(value = "优惠券剩余数量")
    private Long surplusNumber;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long sorting;

    /**
     * 优惠券状态（0关闭，1开启）
     */
    @ExcelProperty(value = "优惠券状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=关闭，1开启")
    private Long state;

    /**
     * 特定会员等级（定向优惠卷）
     */
    @ExcelProperty(value = "特定会员等级", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "定向优惠卷")
    private String specificMembershipLevel;

    /**
     * 特定会员用户id（定向优惠卷）
     */
    @ExcelProperty(value = "特定会员用户id", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "定向优惠卷")
    private String specificUsersId;



    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;



    /**
     * 适用用户范围（1特定会员等级，2指定用户）
     */
    @ExcelProperty(value = "优惠券适用用户范围", converter = ExcelDictConvert.class)
    private Long applicableScope;


}
