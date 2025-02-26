package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletOrderRefund;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 订单退款业务对象 folwer_order_refund
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletOrderRefund.class, reverseConvertGenerate = false)
public class FolwerAppletOrderRefundBo extends BaseEntity {

    /**
     * 退款ID
     */
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
     * 退款状态 0:拒绝退款 1：已退款 2：退款中
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
    private Date refundTime;

    /**
     * 拒绝退款原因
     */
    private String buyerMsg;

    /**
     * 售后备注
     */
    private String refundRemark;

    /**
     * 退款凭证
     */
    private String refundRemarkPic;


}
