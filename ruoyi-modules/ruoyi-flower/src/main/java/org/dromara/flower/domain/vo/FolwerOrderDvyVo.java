package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerOrderDvy;
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
 * 订单物流视图对象 folwer_order_dvy
 *
 * @author mlhxj
 * @date 2025-09-28
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerOrderDvy.class)
public class FolwerOrderDvyVo implements Serializable {

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
    private Long dvyWeight;

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
    private Long firstWeight;

    /**
     * 续重重量
     */
    @ExcelProperty(value = "续重重量")
    private Long additionalWeight;

    /**
     * 首重价格
     */
    @ExcelProperty(value = "首重价格")
    private Long firstWeightPrice;

    /**
     * 续重价格
     */
    @ExcelProperty(value = "续重价格")
    private Long additionalWeightPrice;

    /**
     * 订单运费
     */
    @ExcelProperty(value = "订单运费")
    private Long freightAmount;

    /**
     * 保温棉数量
     */
    @ExcelProperty(value = "保温棉数量")
    private Long insulationNum;

    /**
     * 保温棉费用
     */
    @ExcelProperty(value = "保温棉费用")
    private Long insulationAmount;

    /**
     * 冰瓶数量
     */
    @ExcelProperty(value = "冰瓶数量")
    private Long iceNum;

    /**
     * 冰瓶费用
     */
    @ExcelProperty(value = "冰瓶费用")
    private Long iceAmount;

    /**
     * 人工费
     */
    @ExcelProperty(value = "人工费")
    private Long laborPrice;

    /**
     * 包装费
     */
    @ExcelProperty(value = "包装费")
    private Long boxPrice;

    /**
     * 小计
     */
    @ExcelProperty(value = "小计")
    private Long packingAmount;


}
