package org.dromara.flowerapplet.domain.vo;

import org.dromara.flowerapplet.domain.FolwerAppletOrderDvy;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * 订单物流视图对象 folwer_order_dvy
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletOrderDvy.class)
public class FolwerAppletOrderDvyVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 订单物流ID
     */
    @ExcelProperty(value = "订单物流ID")
    private Long orderDevId;

    /**
     * 订单号
     */
    @ExcelProperty(value = "订单号")
    private Long orderId;

    /**
     * 物流公司ID
     */
    @ExcelProperty(value = "物流公司ID")
    private Long dvyId;

    /**
     * 物流公司
     */
    @ExcelProperty(value = "物流公司")
    private String dvyName;

    /**
     * 物流总重量
     */
    @ExcelProperty(value = "物流总重量")
    private Double dvyWeight;

    /**
     * 鲜花数量
     */
    @ExcelProperty(value = "鲜花数量")
    private Long flowerNum;

    /**
     * 物流数量
     */
    @ExcelProperty(value = "物流数量")
    private Long dvyNum;

    /**
     * 计费首重
     */
    @ExcelProperty(value = "计费首重")
    private Double firstWeight;

    /**
     * 续重重量
     */
    @ExcelProperty(value = "续重重量")
    private Double additionalWeight;

    /**
     * 首重价格
     */
    @ExcelProperty(value = "首重价格")
    private BigDecimal firstWeightPrice;

    /**
     * 续重价格
     */
    @ExcelProperty(value = "续重价格")
    private BigDecimal additionalWeightPrice;

    /**
     * 订单运费
     */
    @ExcelProperty(value = "订单运费")
    private BigDecimal freightAmount;

    /**
     * 保温棉数量
     */
    @ExcelProperty(value = "保温棉数量")
    private Long insulationNum;

    /**
     * 保温棉费用
     */
    @ExcelProperty(value = "保温棉费用")
    private BigDecimal insulationAmount;

    /**
     * 冰瓶数量
     */
    @ExcelProperty(value = "冰瓶数量")
    private Long iceNum;

    /**
     * 冰瓶费用
     */
    @ExcelProperty(value = "冰瓶费用")
    private BigDecimal iceAmount;

    /**
     * 人工费
     */
    @ExcelProperty(value = "人工费")
    private BigDecimal laborPrice;

    /**
     * 包装费
     */
    @ExcelProperty(value = "包装箱费")
    private BigDecimal boxPrice;

    /**
     * 物料费
     */
    @ExcelProperty(value = "物料费")
    private BigDecimal materialPrace;

    /**
     * 小计
     */
    @ExcelProperty(value = "小计")
    private BigDecimal packingAmount;


}
