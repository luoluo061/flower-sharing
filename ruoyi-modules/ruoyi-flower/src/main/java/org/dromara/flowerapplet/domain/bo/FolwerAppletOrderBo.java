package org.dromara.flowerapplet.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flowerapplet.domain.FolwerAppletOrder;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单业务对象 folwer_order
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletOrder.class, reverseConvertGenerate = false)
public class FolwerAppletOrderBo extends BaseEntity {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空", groups = { EditGroup.class })
    private String orderId;

    /**
     * 会员ID
     */
    private String userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 会员类型
     */
    private String memberLevelId;

    /**
     * 订单流水号
     */
    private String orderNumber;

    /**
     * 商品总价
     */
    private BigDecimal total;

    /**
     * 积分
     */
    private Long rebate;

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
     * 订单状态 0:待付款 1：已支付 2:已取消 3：已退款 4：拒绝退款 5：待发货 6:待收货 7:待评价 8:成功 9:失败
     */
    private Long status;

    /**
     * 支付回调
     */
    private String payCallback;

    /**
     *   是否退款 默认是0:未退款,1:已退款, 2:退款中, 3:取消退款 4:退款失败
     */
    private Long isRefund;

    /**
     * 是否分账 默认是0,不分账, 1,分账
     */
    private Long isProfitSharing;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
    private Long deliveryMode;

    /**
     * 物流公司ID
     */
    private String dvyId;

    /**
     * 物流公司
     */
    private String dvyName;

    /**
     * 物流单号
     */
    private String dvyFlowId;

    /**
     * 订单运费
     */
    private Long freightAmount;

    /**
     * 用户订单地址Id
     */
    private String addrOrderId;

    /**
     * 发货时间
     */
    private Date dvyTime;

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
    private String cancelMsg;


}
