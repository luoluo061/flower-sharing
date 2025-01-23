package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.MemberLevel;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * 会员等级视图对象 member_level
 *
 * @author chzl
 * @date 2024-12-24
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MemberLevel.class)
public class MemberLevelVo implements Serializable {

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
     * 会员图标
     */
    @ExcelProperty(value = "会员图标")
    private String gradeIcon;

    /**
     * 会员图标Url
     */
    @ExcelProperty(value = "会员图标Url")
    private String gradeIconUrl;

    /**
     * 折扣比率数值(无百分号)
     */
    @ExcelProperty(value = "折扣比率数值(无百分号)")
    private Long discountRatio;

    /**
     * 价格
     */
    @ExcelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 可以花券/张
     */
    @ExcelProperty(value = "可以花券/张")
    private Long coupon;

    /**
     * 是否显示 0 否 1 是
     */
    @ExcelProperty(value = "是否显示 0 否 1 是", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "radio_status")
    private Long display;


    /**
     * 会员权益说明
     */
    @ExcelProperty(value = "会员权益说明")
    private String privilege;

    /**
     * 会员权益集合
     */
    private List<MemberLevelPrivilegeVo> privilegeVos;
}
