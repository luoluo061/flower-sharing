package org.dromara.flower.domain.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.MarketingCoupon;
import org.dromara.flower.domain.MarketingCouponReceive;
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
 * 优惠卷领取记录视图对象 marketing_coupon_receive
 *
 * @author chy
 * @date 2025-01-08
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MarketingCouponReceive.class)
public class MarketingCouponReceiveVo implements Serializable {

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
     * 优惠卷id
     */
    @ExcelProperty(value = "优惠卷id")
    private Long couponId;

    /**
     * 会员id
     */
    @ExcelProperty(value = "会员id")
    private Long userId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String userName;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String icon;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String iconUrl;

    /**
     * 优惠券状态（0默认已领取，1已使用，已过期）
     */
    @ExcelProperty(value = "优惠券状态", converter = ExcelDictConvert.class)
    @ExcelDictFormat(readConverterExp = "0=默认已领取，1已使用，已过期")
    private Long state;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 优惠券详细信息
     *
     */
    private MarketingCouponVo marketingCoupon;


    /**
     * 优惠券数量
     */
    private Long num;








}
