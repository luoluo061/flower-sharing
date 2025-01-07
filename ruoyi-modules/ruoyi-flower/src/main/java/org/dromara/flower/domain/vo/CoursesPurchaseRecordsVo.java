package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.CoursesPurchaseRecords;
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
 * 课程管理-课程购买记录视图对象 courses_purchase_records
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = CoursesPurchaseRecords.class)
public class CoursesPurchaseRecordsVo implements Serializable {

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
     * 订单号
     */
    @ExcelProperty(value = "订单号")
    private String code;

    /**
     * 课程名称
     */
    @ExcelProperty(value = "课程名称")
    private String coursesName;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private String memberId;

    /**
     * 小程序用户信息ID
     */
    @ExcelProperty(value = "小程序用户信息ID")
    private Long appletUserInformationId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String name;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private Long price;

    /**
     * 订单状态 0 待支付 1 已支付
     */
    @ExcelProperty(value = "订单状态 0 待支付 1 已支付")
    private Long status;

    /**
     * 课程ID
     */
    @ExcelProperty(value = "课程ID")
    private Long coursesManagerId;
}
