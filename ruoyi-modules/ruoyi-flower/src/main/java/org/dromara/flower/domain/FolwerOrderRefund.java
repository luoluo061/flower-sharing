package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 订单退款对象 folwer_order_refund
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_order_refund")
public class FolwerOrderRefund extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @TableId(value = "refund_id")
    private Long refundId;

    /**
     * 会员ID
     */
    private Long userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 会员类型
     */
    private Long memberLevelId;

    /**
     * 订单ID
     */
    private String orderId;

    /**
     * 实际金额
     */
    private Long actualTotal;

    /**
     * 退款状态 0:拒绝退款 1：已退款
     */
    private Long refundStatus;

    /**
     * 订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败
     */
    private Long status;

    /**
     * 退款类型:1,拒绝退款,2同意退款
     */
    private Long applyType;

    /**
     * 退款理由
     */
    private String refundMsg;

    /**
     * 退款金额
     */
    private Long refundAmount;

    /**
     * 退款时间
     */
    private String refundTime;

    /**
     * 拒绝退款原因
     */
    private String buyerMsg;

    /**
     * 售后备注
     */
    private String refundRemark;

    /**
     * 售后凭证
     */
    private String refundRemarkPic;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
