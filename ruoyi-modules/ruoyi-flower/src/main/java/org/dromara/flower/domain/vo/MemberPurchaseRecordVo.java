package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.MemberPurchaseRecord;
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
 * 会员购买记录视图对象 member_purchase_record
 *
 * @author chzl
 * @date 2024-12-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MemberPurchaseRecord.class)
public class MemberPurchaseRecordVo implements Serializable {

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
     * 订单编号
     */
    @ExcelProperty(value = "订单编号")
    private String orderCode;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private String memberId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String memberName;

    /**
     * 会员电话
     */
    @ExcelProperty(value = "会员电话")
    private String phone;

    /**
     * 会员等级ID
     */
    @ExcelProperty(value = "会员等级ID")
    private Long memberLevelId;

    /**
     * 等级
     */
    @ExcelProperty(value = "等级")
    private String grade;

    /**
     * 等级中文名称
     */
    @ExcelProperty(value = "等级中文名称")
    private String gradeName;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private Long price;


}
