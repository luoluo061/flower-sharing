package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 优惠卷领取记录对象 marketing_coupon_receive
 *
 * @author chy
 * @date 2025-01-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("marketing_coupon_receive")
public class MarketingCouponReceive extends TenantEntity {

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
     * 优惠卷id
     */
    private Long couponId;

    /**
     * 会员id
     */
    private Long userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String icon;

    /**
     * 优惠券状态（0默认已领取，1已使用，已过期）
     */
    private Long state;

    /**
     * 优惠卷开始时间
     */
    private Date startTime;

    /**
     * 优惠卷结束时间
     */
    private Date endTime;


}
