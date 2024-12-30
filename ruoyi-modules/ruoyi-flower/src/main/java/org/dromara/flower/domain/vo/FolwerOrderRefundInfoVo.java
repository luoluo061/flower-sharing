package org.dromara.flower.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.flower.domain.FolwerOrderRefund;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 订单退款视图对象 folwer_order_refund
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerOrderRefund.class)
public class FolwerOrderRefundInfoVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private Long orderId;

    /**
     * 订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败
     */
    @ExcelProperty(value = "订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "order_status")
    private Long status;

    /**
     * 订单状态
     */
    @ExcelProperty(value = "订单状态")
    private String statusStr;

    /**
     * 实际金额
     */
    @ExcelProperty(value = "实际金额")
    private Long actualTotal;

    /**
     * 订单运费
     */
    @ExcelProperty(value = "订单运费")
    private Long freightAmount;

    /**
     * 商品总价
     */
    @ExcelProperty(value = "商品总价")
    private Long total;

    /**
     * 退款金额
     */
    @ExcelProperty(value = "退款金额")
    private Long refundAmount;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */
    @ExcelProperty(value = "支付方式 0 手动代付 1 微信支付 2 支付宝", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "payment_method")
    private Long payType;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */
    @ExcelProperty(value = "支付方式 0 手动代付 1 微信支付 2 支付宝")
    private String payTypeStr;

    /**
     * 付款时间
     */
    @ExcelProperty(value = "付款时间")
    private Date payTime;

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
     * 退款类型
     */
    @ExcelProperty(value = "退款类型")
    private Long applyType;

    /**
     * 退款类型
     */
    @ExcelProperty(value = "退款类型")
    private String applyTypeStr;

    /**
     * 退款时间
     */
    @ExcelProperty(value = "退款时间")
    private String refundTime;

    /**
     * 退款说明
     */
    @ExcelProperty(value = "退款说明")
    private String refundMsg;

    /**
     * 退款凭证
     */
    @ExcelProperty(value = "退款凭证")
    private String refundPic;

    /**
     * 退款凭证图片
     */
    @ExcelProperty(value = "退款凭证图片")
    private List<String> refundMsgPic;

    /**
     * 退款备注
     */
    @ExcelProperty(value = "退款备注")
    private String refundRemark;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    private String orderRemark;

    /**
     * 买家备注
     */
    @ExcelProperty(value = "买家备注")
    private String remark;

}
