package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flower.domain.FolwerOrder;
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
//    @NotNull(message = "订单ID不能为空", groups = { EditGroup.class })
    private Long orderId;

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
     * 订单流水号
     */
    private String orderNumber;

    /**
     * 商品总价
     */
    private Long total;

    /**
     * 返点
     */
    private Long rebate;

    /**
     * 是否退款 默认是0:未退款,1:已退款, 2:退款中, 3:取消退款 4:退款失败
     */
    private Long isRefund;

    /**
     * 实际金额
     */
    private BigDecimal actualTotal;

    /**
     * 支付方式 0 手动代付 1 微信支付 2 支付宝
     */
    private Long payType;

    /**
     * 付款时间
     */
    private Date payTime;

    /**
     * 订单备注
     */
    private String remarks;

    /**
     * 商家备注
     */
    private String merchRemarks;

    /**
     * 订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败
     */
    private Long status;

    /**
     * 订单运费
     */
    private BigDecimal freightAmount;

    /**
     * 用户订单地址Id
     */
    private Long addrOrderId;

//    /**
//     * 发货时间
//     */
//    private Date dvyTime;

    /**
     * 完成时间
     */
    private Date finallyTime;

    /**
     * 取消时间
     */
    private Date cancelTime;

    /**
     * 取消原因
     */
//    @NotBlank(message = "取消原因不能为空", groups = { AddGroup.class, EditGroup.class })
    private String cancelMsg;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
