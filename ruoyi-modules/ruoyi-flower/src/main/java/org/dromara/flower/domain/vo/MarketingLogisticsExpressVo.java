package org.dromara.flower.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.flower.domain.MarketingLogisticsExpress;
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
 * 营销推广-物流快递视图对象 marketing_logistics_express
 *
 * @author chy
 * @date 2025-01-06
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MarketingLogisticsExpress.class)
public class MarketingLogisticsExpressVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;

    /**
     * 快递名称
     */
    @ExcelProperty(value = "快递名称")
    private String name;

    /**
     * 快递编码
     */
    @ExcelProperty(value = "快递编码")
    private String expressCode;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long sort;


    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;


}
