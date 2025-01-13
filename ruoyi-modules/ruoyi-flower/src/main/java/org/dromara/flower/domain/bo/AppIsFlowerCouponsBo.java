package org.dromara.flower.domain.bo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;


@Data
public class AppIsFlowerCouponsBo {

    /**
     * 会员id
     */
    @NotNull(message = "会员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 商品分类id
     */
    @NotNull(message = "商品分类id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long categoryId;

    /**
     * 商品id数组
     */
    @NotNull(message = "商品id不能为空",groups = { AddGroup.class, EditGroup.class })
    private Long productId;




}
