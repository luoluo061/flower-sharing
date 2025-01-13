package org.dromara.flower.domain.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import org.dromara.flower.domain.MemberExchangeRecord;
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
 * 会员中心--兑换记录视图对象 member_exchange_record
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MemberExchangeRecord.class)
public class MemberExchangeRecordVo implements Serializable {

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
     * 兑现金额
     */
    @ExcelProperty(value = "兑现金额")
    private Long amount;

    /**
     * 兑现金币
     */
    @ExcelProperty(value = "兑现金币")
    private Long gold;

    /**
     * 剩余金币
     */
    @ExcelProperty(value = "剩余金币")
    private Long balance;

    /**
     * 创建人中文名称
     */
    @ExcelProperty(value = "创建人中文名称")
    private String createName;

    /**
     * 会员的UserId
     */
    @ExcelProperty(value = "会员的UserId")
    private String memberId;

    /**
     * 创建时间
     */
    @ExcelProperty(value = "创建时间")
    private Date createTime;
}
