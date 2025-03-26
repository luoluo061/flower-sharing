package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flower.domain.FolwerOrderDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

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
//    @NotNull(message = "订单ID不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 订单流水号
     */
//    @NotBlank(message = "订单流水号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String orderId;

    /**
     * 商品名称
     */
//    @NotBlank(message = "商品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

    /**
     * 商品列表图
     */
//    @NotBlank(message = "商品列表图不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productListPictureUrl;

    /**
     * 单价
     */
//    @NotNull(message = "单价不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long orderPrice;

    /**
     * 数量
     */
//    @NotNull(message = "数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long number;

    /**
     * 小计
     */
//    @NotNull(message = "小计不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long subtotal;


}
