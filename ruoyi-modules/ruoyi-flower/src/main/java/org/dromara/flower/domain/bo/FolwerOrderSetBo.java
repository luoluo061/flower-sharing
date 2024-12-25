package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerOrderSet;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 订单设置业务对象 folwer_order_set
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerOrderSet.class, reverseConvertGenerate = false)
public class FolwerOrderSetBo extends BaseEntity {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 满额包邮
     */
//    @NotNull(message = "满额包邮不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long freeShippingPrice;

    /**
     * 退货收货人姓名
     */
//    @NotBlank(message = "退货收货人姓名不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundName;

    /**
     * 退货收货人电话
     */
//    @NotBlank(message = "退货收货人电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundPhone;

    /**
     * 退货收货人地址
     */
//    @NotBlank(message = "退货收货人地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundAddr;

    /**
     * 退货原因
     */
//    @NotBlank(message = "退货原因不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundMsg;

    /**
     * 优惠劵退还  0，不退还 1，表示退还,
     */
//    @NotNull(message = "优惠劵退还  0，不退还 1，表示退还,不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long couponRefund;

    /**
     * 售后期限
     */
//    @NotBlank(message = "售后期限不能为空", groups = { AddGroup.class, EditGroup.class })
    private String term;

    /**
     * 订单取消时间
     */
//    @NotBlank(message = "订单取消时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderCancel;

    /**
     * 自动收货时间
     */
//    @NotBlank(message = "自动收货时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String autoDvy;


}
