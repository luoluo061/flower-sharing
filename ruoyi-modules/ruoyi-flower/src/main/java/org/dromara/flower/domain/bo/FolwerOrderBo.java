package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerOrder;
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
 * 订单业务对象 folwer_order
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerOrder.class, reverseConvertGenerate = false)
public class FolwerOrderBo extends BaseEntity {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空", groups = { EditGroup.class })
    private Long orderId;

    /**
     * 会员ID
     */
    @NotNull(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 会员名称
     */
    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 会员类型
     */
    @NotNull(message = "会员类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberLevelId;

    /**
     * 订单流水号
     */
    @NotBlank(message = "订单流水号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderNumber;

    /**
     * 商品总价
     */
    @NotNull(message = "商品总价不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long total;

    /**
     * 实际金额
     */
    @NotNull(message = "实际金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long actualTotal;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */
    @NotNull(message = "支付方式 0 手动代付 1 微信支付 2 支付宝不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long payType;

    /**
     * 付款时间
     */
    @NotNull(message = "付款时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date payTime;

    /**
     * 订单备注
     */
    @NotBlank(message = "订单备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remarks;

    /**
     * 订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败
     */
    @NotNull(message = "订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 退款类型:1,拒绝退款,2同意退款
     */
    @NotNull(message = "退款类型:1,拒绝退款,2同意退款不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long applyType;

    /**
     * 退款ID
     */
    @NotNull(message = "退款ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long refundId;

    /**
     * 退款金额
     */
    @NotNull(message = "退款金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long refundAmount;

    /**
     * 退款时间
     */
    @NotNull(message = "退款时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date refundTime;

    /**
     * 退款原因
     */
    @NotBlank(message = "退款原因不能为空", groups = { AddGroup.class, EditGroup.class })
    private String buyerMsg;

    /**
     * 售后备注
     */
    @NotBlank(message = "售后备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundRemark;


}
