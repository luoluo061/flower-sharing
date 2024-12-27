package org.dromara.flower.domain.vo;

import java.util.Date;

import org.dromara.flower.domain.FolwerCreditOrder;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 积分订单视图对象 folwer_credit_order
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerCreditOrder.class)
public class FolwerCreditOrderVo implements Serializable {

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
     * 兑换积分
     */
    @ExcelProperty(value = "兑换积分")
    private Long actualTotal;

    /**
     * 兑换时间
     */
    @ExcelProperty(value = "兑换时间")
    private Date payTime;

    /**
     * 订单备注
     */
    @ExcelProperty(value = "订单备注")
    private String remarks;

    /**
     * 订单状态 0:待兑换 1：已兑换 2:待发货 3:待收货 4:待评价 5:成功 6:失败
     */
    @ExcelProperty(value = "订单状态 0:待兑换 1：已兑换 2:待发货 3:待收货 4:待评价 5:成功 6:失败")
    private Long status;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
    @ExcelProperty(value = "配送方式 默认是1，表示物流配送, 0，商家配送")
    private Long deliveryMode;

    /**
     * 物流公司ID
     */
    @ExcelProperty(value = "物流公司ID")
    private Long dvyId;

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
     * 订单运费
     */
    @ExcelProperty(value = "订单运费")
    private Long freightAmount;

    /**
     * 用户订单地址Id
     */
    @ExcelProperty(value = "用户订单地址Id")
    private Long addrOrderId;

    /**
     * 发货时间
     */
    @ExcelProperty(value = "发货时间")
    private Date dvyTime;

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


}
