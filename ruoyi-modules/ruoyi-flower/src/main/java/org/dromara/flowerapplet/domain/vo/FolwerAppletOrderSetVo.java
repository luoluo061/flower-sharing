package org.dromara.flowerapplet.domain.vo;

import org.dromara.flowerapplet.domain.FolwerAppletOrderSet;
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
 * 订单设置视图对象 folwer_order_set
 *
 * @author mlhxj
 * @date 2025-02-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletOrderSet.class)
public class FolwerAppletOrderSetVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单ID
     */
    @ExcelProperty(value = "订单ID")
    private Long id;

    /**
     * 满额包邮
     */
    @ExcelProperty(value = "满额包邮")
    private Long freeShippingPrice;

    /**
     * 起步价
     */
    @ExcelProperty(value = "起步价")
    private Long startPrice;

    /**
     * 退货收货人姓名
     */
    @ExcelProperty(value = "退货收货人姓名")
    private String refundName;

    /**
     * 退货收货人电话
     */
    @ExcelProperty(value = "退货收货人电话")
    private String refundPhone;

    /**
     * 退货收货人地址
     */
    @ExcelProperty(value = "退货收货人地址")
    private String refundAddr;

    /**
     * 退货原因
     */
    @ExcelProperty(value = "退货原因")
    private String refundMsg;

    /**
     * 优惠劵退还  0，不退还 1，表示退还,
     */
    @ExcelProperty(value = "优惠劵退还  0，不退还 1，表示退还,")
    private Long couponRefund;

    /**
     * 售后期限
     */
    @ExcelProperty(value = "售后期限")
    private String term;

    /**
     * 订单取消时间
     */
    @ExcelProperty(value = "订单取消时间")
    private String orderCancel;

    /**
     * 自动收货时间
     */
    @ExcelProperty(value = "自动收货时间")
    private String autoDvy;


}
