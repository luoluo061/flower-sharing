package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.FolwerCreditGetrecords;
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
 * 积分获取记录视图对象 folwer_credit_getrecords
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerCreditGetrecords.class)
public class FolwerCreditGetrecordsVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @ExcelProperty(value = "记录ID")
    private Long recordId;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private Long userId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String userName;

    /**
     * 会员类型
     */
    @ExcelProperty(value = "会员类型")
    private Long memberLevelId;

    /**
     * 积分来源ID
     */
    @ExcelProperty(value = "积分来源ID")
    private Long creditSourId;

    /**
     * 积分来源
     */
    @ExcelProperty(value = "积分来源")
    private String creditSourName;

    /**
     * 交易积分
     */
    @ExcelProperty(value = "交易积分")
    private String getTotal;

    /**
     * 交易时间
     */
    @ExcelProperty(value = "交易时间")
    private Date getTime;

    /**
     * 备注
     */
    @ExcelProperty(value = "备注")
    private String remarks;

    /**
     * 状态 0:待兑换 1：已兑换 2:成功 3:失败
     */
    @ExcelProperty(value = "状态 0:待兑换 1：已兑换 2:成功 3:失败")
    private Long status;


}
