package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerDelivery;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 物流公司视图对象 folwer_delivery
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerDelivery.class)
public class FolwerDeliveryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long dvyId;

    /**
     * 配送公司名称
     */
    @ExcelProperty(value = "配送公司名称")
    private String dvyName;

    /**
     * 配送方式  1:普通 2:冷链,  3:空运
     */
    @ExcelProperty(value = "配送方式  1:普通 2:冷链,  3:空运 ")
    private Long dvyType;

    /**
     * 付款类型(1:到付, 2:预付)
     */
    @ExcelProperty(value = "付款类型(1:到付, 2:预付)")
    private Long isCod;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String reamrk;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long seq;

    /**
     * 发货地址
     */
    @ExcelProperty(value = "发货地址")
    private String dvyAddr;


}
