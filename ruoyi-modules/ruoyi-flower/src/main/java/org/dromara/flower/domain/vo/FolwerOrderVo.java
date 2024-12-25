package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.FolwerOrder;
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
 * 订单视图对象 folwer_order
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerOrder.class)
public class FolwerOrderVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private Long orderId;

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
     * 会员类型
     */
    @ExcelProperty(value = "会员类型")
    private Long memberLevelId;

    /**
     * 订单流水号
     */
    @ExcelProperty(value = "订单流水号")
    private String orderNumber;

    /**
     * 商品总价
     */
    @ExcelProperty(value = "商品总价")
    private Long total;

    /**
     * 实际金额
     */
    @ExcelProperty(value = "实际金额")
    private Long actualTotal;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */
    @ExcelProperty(value = "支付方式 0 手动代付 1 微信支付 2 支付宝")
    private Long payType;

    /**
     * 付款时间
     */
    @ExcelProperty(value = "付款时间")
    private Date payTime;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    private String remarks;

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
     * 退款ID
     */
    @ExcelProperty(value = "退款ID")
    private Long refundId;

    /**
     * 退款金额
     */
    @ExcelProperty(value = "退款金额")
    private Long refundAmount;

    /**
     * 退款时间
     */
    @ExcelProperty(value = "退款时间")
    private Date refundTime;

    /**
     * 退款原因
     */
    @ExcelProperty(value = "退款原因")
    private String buyerMsg;

    /**
     * 售后备注
     */
    @ExcelProperty(value = "售后备注")
    private String refundRemark;


}
