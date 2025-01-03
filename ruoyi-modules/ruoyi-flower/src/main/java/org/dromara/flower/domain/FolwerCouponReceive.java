package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 优惠券领取记录对象 folwer_coupon_receive
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_coupon_receive")
public class FolwerCouponReceive extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 优惠券ID
     */
    private Long couponId;

    /**
     * 会员ID
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
     * 状态 默认是0，表示未使用,1为已使用
     */
    private Long status;

    /**
     * 删除标志 0 否 1 是
     */
    @TableLogic
    private Long delFlag;


}
