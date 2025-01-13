package org.dromara.flower.domain.bo;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;


@Data

public class AppOrderConsumeBo {

    /**
     * 会员id
     */
    @NotNull(message = "会员id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;


    /**
     * 商品分类id
     */
    @NotEmpty(message = "商品分类id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long[] categoryId;

    /**
     * 商品id数组
     */
    @NotEmpty(message = "商品id不能为空",groups = { AddGroup.class, EditGroup.class })
    private Long[] productIds;


    /**
     * 优惠劵类型（0满减优惠劵，2花劵）
     */
    @NotNull(message = "优惠劵类型id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long couponType;







}
