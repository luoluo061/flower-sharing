package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletOrderSet;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 订单设置业务对象 folwer_order_set
 *
 * @author mlhxj
 * @date 2025-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletOrderSet.class, reverseConvertGenerate = false)
public class FolwerAppletOrderSetBo extends BaseEntity {

    /**
     * 订单ID
     */
    private Long id;

    /**
     * 满额包邮
     */
    private Long freeShippingPrice;

    /**
     * 起步价
     */
    private Long startPrice;

    /**
     * 退货收货人姓名
     */
    private String refundName;

    /**
     * 退货收货人电话
     */
    private String refundPhone;

    /**
     * 退货收货人地址
     */
    private String refundAddr;

    /**
     * 退货原因
     */
    private String refundMsg;

    /**
     * 优惠劵退还  0，不退还 1，表示退还,
     */
    private Long couponRefund;

    /**
     * 售后期限
     */
    private String term;

    /**
     * 订单取消时间
     */
    private String orderCancel;

    /**
     * 自动收货时间
     */
    private String autoDvy;


}
