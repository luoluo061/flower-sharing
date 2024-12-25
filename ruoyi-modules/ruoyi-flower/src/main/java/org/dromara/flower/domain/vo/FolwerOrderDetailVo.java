package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.FolwerOrderDetail;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 订单详细视图对象 folwer_order_detail
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerOrderDetail.class)
public class FolwerOrderDetailVo implements Serializable {

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
     * 单价
     */
    @ExcelProperty(value = "单价")
    private Long orderPrice;

    /**
     * 数量
     */
    @ExcelProperty(value = "数量")
    private Long number;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
    @ExcelProperty(value = "配送方式 默认是1，表示物流配送, 0，商家配送")
    private Long deliveryMode;

    /**
     * 配送方式ID
     */
    @ExcelProperty(value = "配送方式ID")
    private Long dvyId;

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


}
