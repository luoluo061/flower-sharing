package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MarketingCouponReceive;
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
 * 优惠卷领取记录业务对象 marketing_coupon_receive
 *
 * @author chy
 * @date 2025-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MarketingCouponReceive.class, reverseConvertGenerate = false)
public class MarketingCouponReceiveBo extends BaseEntity {

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
     * 优惠卷id
     */
    @NotNull(message = "优惠卷id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long couponId;

    /**
     * 会员id
     */
    @NotNull(message = "会员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 会员名称
     */
    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 头像
     */
    @NotBlank(message = "头像不能为空", groups = { AddGroup.class, EditGroup.class })
    private String icon;

    /**
     * 优惠券状态（0默认已领取，1已使用，2已过期）
     */
    @NotNull(message = "优惠券状态（0默认已领取，1已使用，2已过期）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long state;


    /**
     * 优惠券数量
     */
    @NotNull(message = "优惠卷数量", groups = { AddGroup.class, EditGroup.class })
    private Long num;



}
