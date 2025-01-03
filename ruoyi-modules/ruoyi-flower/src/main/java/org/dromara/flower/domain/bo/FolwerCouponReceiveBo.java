package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerCouponReceive;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 优惠券领取记录业务对象 folwer_coupon_receive
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerCouponReceive.class, reverseConvertGenerate = false)
public class FolwerCouponReceiveBo extends BaseEntity {

    /**
     * 主键
     */
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




}
