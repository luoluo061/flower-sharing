package org.dromara.flower.domain.bo;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;

@Data
public class AppCouponRecord {

    /**
     * 会员id
     */
    @NotNull(message = "会员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;


    /**
     * 优惠券状态（0默认已领取，1已使用，已过期）
     */
    @NotNull(message = "优惠券状态（0默认已领取，1已使用，已过期）不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long state;







}
