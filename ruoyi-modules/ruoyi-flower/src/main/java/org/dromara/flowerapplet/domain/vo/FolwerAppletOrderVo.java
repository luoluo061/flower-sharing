package org.dromara.flowerapplet.domain.vo;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.flowerapplet.domain.FolwerAppletOrder;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDetailBo;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 订单视图对象 folwer_order
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletOrder.class)
public class FolwerAppletOrderVo extends BaseEntity implements Serializable {

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
    private BigDecimal total;

    /**
     * 商品总数量
     */
    @ExcelProperty(value = "商品总数量")
    private Long totalNum;

    /**
     * 积分
     */
    @ExcelProperty(value = "积分")
    private Long rebate;

    /**
     * 实际金额
     */
    @ExcelProperty(value = "实际金额")
    private Long actualTotal;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */
    @ExcelProperty(value = "支付方式 0 手动代付 1 微信支付 2 支付宝", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "payment_method")
    private Long payType;

    /**
     * 支付回调
     */
    @ExcelProperty(value = "支付回调")
    private String payCallback;

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
    @ExcelProperty(value = "订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "order_status")
    private Long status;

    /**
     *   是否退款 默认是0:未退款,1:已退款, 2:退款中, 3:取消退款 4:退款失败
     */
    @ExcelProperty(value = "是否退款 默认是0:未退款,1:已退款, 2:退款中, 3:取消退款 4:退款失败")
    private Long isRefund;

    /**
     * 是否分账 0：否，1：是
     */
    @ExcelProperty(value = "是否分账 0：否，1：是")
    private Long isProfitSharing;

    /**
     * 订单运费
     */
    @ExcelProperty(value = "订单运费")
    private BigDecimal freightAmount;

    /**
     * 用户订单地址Id
     */
    @ExcelProperty(value = "用户订单地址Id")
    private Long addrOrderId;

//    /**
//     * 发货时间
//     */
//    @ExcelProperty(value = "发货时间")
//    private Date dvyTime;

    /**
     * 完成时间
     */
    @ExcelProperty(value = "完成时间")
    private Date finallyTime;

    /**
     * 取消时间
     */
    @ExcelProperty(value = "取消时间")
    private Date cancelTime;

    /**
     * 取消原因
     */
    @ExcelProperty(value = "取消原因")
    private String cancelMsg;

    /**
     * 订单详情
     */
    @ExcelProperty(value = "订单详情")
    private List<FolwerAppletOrderDetailVo> orderDetails;


}
