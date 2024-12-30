package org.dromara.flower.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import org.dromara.flower.domain.FolwerCreditOrder;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;


/**
 * 积分订单视图对象 folwer_credit_order
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerCreditOrder.class)
public class FolwerCreditOrderInfoVo implements Serializable {

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
    private Long status;

    /**
     * 订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败
     */
    @ExcelProperty(value = "订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败", converter = ExcelDictConvert.class)
    private String statusStr;

    /**
     * 兑换积分
     */
    @ExcelProperty(value = "兑换积分")
    private Long actualTotal;

    /**
     * 订单运费
     */
    @ExcelProperty(value = "订单运费")
    private Long freightAmount;

    /**
     * 支付方式
     */
    @ExcelProperty(value = "支付方式")
    private String actualTotalStr;

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
     * 收货人
     */
    @ExcelProperty(value = "收货人")
    private String addrName;

    /**
     * 收货电话
     */
    @ExcelProperty(value = "收货电话")
    private String mobile;

    /**
     * 收货地址
     */
    @ExcelProperty(value = "收货地址")
    private String addr;

    /**
     * 物流公司
     */
    @ExcelProperty(value = "物流公司")
    private String dvyName;

    /**
     * 物流单号
     */
    @ExcelProperty(value = "物流单号")
    private String dvyFlowId;

    /**
     * 发货地址
     */
    @ExcelProperty(value = "发货地址")
    private String dvyAddr;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    private String remarks;



}
