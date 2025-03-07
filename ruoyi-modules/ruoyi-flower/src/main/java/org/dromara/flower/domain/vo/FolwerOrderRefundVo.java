package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.flower.domain.FolwerOrderRefund;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 订单退款视图对象 folwer_order_refund
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerOrderRefund.class)
public class FolwerOrderRefundVo extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private Long refundId;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private Long userId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String userName;

    /**
     * 会员电话
     */
    @ExcelProperty(value = "会员电话")
    private String userPhone;

    /**
     * 会员类型
     */
    @ExcelProperty(value = "会员类型")
    private Long memberLevelId;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private String orderId;

    /**
     * 实际金额
     */
    @ExcelProperty(value = "实际金额")
    private Long actualTotal;

    /**
     * 退款状态 0:拒绝退款 1：已退款
     */
    @ExcelProperty(value = "退款状态 0:拒绝退款 1：已退款 ")
    private Long refundStatus;

    /**
     * 订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败
     */
    @ExcelProperty(value = "订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败")
    private Long status;

    /**
     * 退款类型:1,拒绝退款,2同意退款
     */
    @ExcelProperty(value = "退款类型:1,拒绝退款,2同意退款")
    private Long applyType;

    /**
     * 退款理由
     */
    @ExcelProperty(value = "退款理由")
    private String refundMsg;

    /**
     * 退款金额
     */
    @ExcelProperty(value = "退款金额")
    private Long refundAmount;

    /**
     * 退款时间
     */
    @ExcelProperty(value = "退款时间")
    private String refundTime;

    /**
     * 拒绝退款原因
     */
    @ExcelProperty(value = "拒绝退款原因")
    private String buyerMsg;

    /**
     * 售后备注
     */
    @ExcelProperty(value = "售后备注")
    private String refundRemark;

    /**
     * 售后凭证
     */
    @ExcelProperty(value = "售后凭证")
    private String refundRemarkPic;


}
