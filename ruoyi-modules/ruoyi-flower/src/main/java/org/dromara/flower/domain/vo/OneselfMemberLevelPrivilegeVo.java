package org.dromara.flower.domain.vo;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.dromara.flower.domain.OneselfMemberLevelPrivilege;
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
 * 会员中心--个人会员权益详情记录视图对象 oneself_member_level_privilege
 *
 * @author mlhxj
 * @date 2025-01-13
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = OneselfMemberLevelPrivilege.class)
public class OneselfMemberLevelPrivilegeVo implements Serializable {

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
     * 会员权益名称
     */
    @ExcelProperty(value = "会员权益名称")
    private String name;

    /**
     * 总数量
     */
    @ExcelProperty(value = "总数量")
    private Long amount;

    /**
     * 会员购买记录ID
     */
    @ExcelProperty(value = "会员购买记录ID")
    private Long memberPurchaseRecordId;

    /**
     * 权益使用状态 0 否 1 是
     */
    @ExcelProperty(value = "权益使用状态 0 否 1 是")
    private Long status;

    /**
     * 个人权益到期时间
     */
    @ExcelProperty(value = "个人权益到期时间")
    private Date endTime;

    /**
     * 已使用数量
     */
    @ExcelProperty(value = "已使用数量")
    private Long usageQuantity;


}
