package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.MemberLevelPrivilege;
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
 * 会员中心--会员等级--权益名称视图对象 member_level_privilege
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MemberLevelPrivilege.class)
public class MemberLevelPrivilegeVo implements Serializable {

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
     * 会员等级ID
     */
    @ExcelProperty(value = "会员等级ID")
    private Long memberLevelId;


}
