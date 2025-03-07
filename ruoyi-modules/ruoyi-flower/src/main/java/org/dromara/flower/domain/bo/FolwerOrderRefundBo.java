package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flower.domain.FolwerOrderRefund;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单退款业务对象 folwer_order_refund
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerOrderRefund.class, reverseConvertGenerate = false)
public class FolwerOrderRefundBo extends BaseEntity {

    /**
     * 退款订单ID
     */
//    @NotNull(message = "订单ID不能为空", groups = { EditGroup.class })
    private Long refundId;

    /**
     * 会员ID
     */
//    @NotNull(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long userId;

    /**
     * 会员名称
     */
//    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userName;

    /**
     * 会员类型
     */
//    @NotNull(message = "会员类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberLevelId;

    /**
     * 订单ID
     */
//    @NotBlank(message = "订单ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderId;

    /**
     * 实际金额
     */
//    @NotNull(message = "实际金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal actualTotal;

    /**
     * 退款状态 0:拒绝退款 1：已退款 2：退款中 3：退款异常 4：退款关闭
     */
//    @NotNull(message = "退款状态 0:拒绝退款 1：已退款 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long refundStatus;

    /**
     * 订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败
     */
//    @NotNull(message = "订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 退款类型:1,拒绝退款,2同意退款
     */
//    @NotNull(message = "退款类型:1,拒绝退款,2同意退款不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long applyType;

    /**
     * 退款理由
     */
//    @NotBlank(message = "退款理由不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundMsg;

    /**
     * 退款金额
     */
//    @NotNull(message = "退款金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal refundAmount;

    /**
     * 退款时间
     */
//    @NotNull(message = "退款时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundTime;

    /**
     * 拒绝退款原因
     */
//    @NotBlank(message = "拒绝退款原因不能为空", groups = { AddGroup.class, EditGroup.class })
    private String buyerMsg;

    /**
     * 售后备注
     */
//    @NotBlank(message = "售后备注不能为空", groups = { AddGroup.class, EditGroup.class })
    private String refundRemark;

    /**
     * 售后凭证
     */
    private String refundRemarkPic;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
