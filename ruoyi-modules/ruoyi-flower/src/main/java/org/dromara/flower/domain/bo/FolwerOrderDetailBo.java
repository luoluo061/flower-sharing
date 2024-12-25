package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerOrderDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 订单详细业务对象 folwer_order_detail
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerOrderDetail.class, reverseConvertGenerate = false)
public class FolwerOrderDetailBo extends BaseEntity {

    /**
     * 订单ID
     */
    @NotNull(message = "订单ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 订单流水号
     */
    @NotBlank(message = "订单流水号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderId;

    /**
     * 商品名称
     */
    @NotBlank(message = "商品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

    /**
     * 商品列表图
     */
    @NotBlank(message = "商品列表图不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productListPictureUrl;

    /**
     * 单价
     */
    @NotNull(message = "单价不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderPrice;

    /**
     * 数量
     */
    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long number;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
    @NotNull(message = "配送方式 默认是1，表示物流配送, 0，商家配送不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deliveryMode;

    /**
     * 配送方式ID
     */
    @NotNull(message = "配送方式ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long dvyId;

    /**
     * 物流单号
     */
    @NotBlank(message = "物流单号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String dvyFlowId;

    /**
     * 订单运费
     */
    @NotNull(message = "订单运费不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long freightAmount;

    /**
     * 用户订单地址Id
     */
    @NotNull(message = "用户订单地址Id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long addrOrderId;

    /**
     * 发货时间
     */
    @NotNull(message = "发货时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date dvyTime;

    /**
     * 完成时间
     */
    @NotNull(message = "完成时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date finallyTime;

    /**
     * 取消时间
     */
    @NotNull(message = "取消时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date cancelTime;


}
