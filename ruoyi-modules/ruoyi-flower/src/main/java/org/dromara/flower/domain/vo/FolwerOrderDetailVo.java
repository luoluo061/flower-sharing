package org.dromara.flower.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
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
import java.util.List;


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
     * 规格ID
     */
    @ExcelProperty(value = "规格ID")
    private Long skuId;

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
     * 商品列表图
     */
    @ExcelProperty(value = "商品列表图")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "productListPictureUrl")
    private String productListPictureUrlUrl;

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
     * 小计
     */
    @ExcelProperty(value = "小计")
    private Long subtotal;


    /**
     * 商品规格
     */
    @ExcelProperty(value = "商品规格")
    private FolwerSkuVo folwerSkuVo;

}
