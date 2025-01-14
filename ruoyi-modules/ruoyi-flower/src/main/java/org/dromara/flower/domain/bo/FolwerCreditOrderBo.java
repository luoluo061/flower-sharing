package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerCreditOrder;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 积分订单业务对象 folwer_credit_order
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerCreditOrder.class, reverseConvertGenerate = false)
public class FolwerCreditOrderBo extends BaseEntity {

    /**
     * 订单ID
     */
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
     * 兑换积分
     */
    private Long actualTotal;

    /**
     * 兑换时间
     */
    private Date payTime;

    /**
     * 订单备注
     */
    private String remarks;

    /***
     *商家备注
     */
    private String merchRemarks;

    /**
     * 订单状态 0:待兑换 1：已兑换 2:待发货 3:待收货 4:待评价 5:成功 6:失败
     */
    private Long status;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
    private Long deliveryMode;

    /**
     * 物流公司ID
     */
    private Long dvyId;

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
    private Long addrOrderId;

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

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
