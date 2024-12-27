package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerCreditOrderDetail;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 积分订单详细视图对象 folwer_credit_order_detail
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerCreditOrderDetail.class)
public class FolwerCreditOrderDetailVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private Long id;

    /**
     * 订单流水号
     */
    @ExcelProperty(value = "订单流水号")
    private String orderId;

    /**
     * 商品名称
     */
    @ExcelProperty(value = "商品名称")
    private String productName;

    /**
     * 商品列表图
     */
    @ExcelProperty(value = "商品列表图")
    private String productListPictureUrl;

    /**
     * 积分
     */
    @ExcelProperty(value = "积分")
    private Long orderPrice;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private Long number;

    /**
     * 小计/积分
     */
    @ExcelProperty(value = "小计/积分")
    private Long subtotal;


}
