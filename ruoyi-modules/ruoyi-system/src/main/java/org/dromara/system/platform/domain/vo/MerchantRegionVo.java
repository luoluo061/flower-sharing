package org.dromara.system.platform.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.system.platform.domain.MerchantRegion;


import java.io.Serial;
import java.io.Serializable;


/**
 * 行政区划代码视图对象 sys_region
 *
 * @author LionLi
 * @date 2024-11-11
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MerchantRegion.class)
public class MerchantRegionVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 创建人
     *//*
    @ExcelProperty(value = "创建人")
    private Long createdBy;

    *//**
     * 创建时间
     *//*
    @ExcelProperty(value = "创建时间")
    private Date createdTime;

    *//**
     * 更新人
     *//*
    @ExcelProperty(value = "更新人")
    private Long updatedBy;

    *//**
     * 更新时间
     *//*
    @ExcelProperty(value = "更新时间")
    private Date updatedTime;

    *//**
     * 主键
     *//*
    @ExcelProperty(value = "主键")
    private Long id;

    *//**
     * 省、直辖市、自治区代码
     *//*
    @ExcelProperty(value = "省、直辖市、自治区代码")
    private String provinceCode;

    *//**
     * 城市代码
     *//*
    @ExcelProperty(value = "城市代码")
    private String cityCode;

    *//**
     * 区县代码
     *//*
    @ExcelProperty(value = "区县代码")
    private String countyCode;

    *//**
     * 乡镇代码
     *//*
    @ExcelProperty(value = "乡镇代码")
    private String townCode;*/
    @ExcelProperty(value = "主键")
    private Long id;
    /**
     * 街道代码
     */
    @ExcelProperty(value = "街道代码")
    private String streetCode;

    /**
     * 街道名称
     */
    @ExcelProperty(value = "街道名称")
    private String streetName;

   /**
     * 数据级别：省市区县乡镇街道
     */
    @ExcelProperty(value = "数据级别：省市区县乡镇街道")
    private Long levelType;

   /**
     * 区划激活时间
     *//*
    @ExcelProperty(value = "区划激活时间")
    private Date enabledDate;

    *//**
     * 第一次出现的年份
     *//*
    @ExcelProperty(value = "第一次出现的年份")
    private Date createdDate;

    *//**
     * 某个区域被取消的时候会在这里标记年份
     *//*
    @ExcelProperty(value = "某个区域被取消的时候会在这里标记年份")
    private Date disabledDate;

    *//**
     * 取消的原因
     *//*
    @ExcelProperty(value = "取消的原因")
    private String disabledMark;

    *//**
     * 由于历史变更，有些乡变镇，县变区，县变市的城市会从历史中消失不见，此时将该记录给隐藏
     *//*
    @ExcelProperty(value = "由于历史变更，有些乡变镇，县变区，县变市的城市会从历史中消失不见，此时将该记录给隐藏")
    private Long isEnabled;

    *//**
     * 数据类型：1:GB2260、2:GB10114_88
     *//*
    @ExcelProperty(value = "数据类型：1:GB2260、2:GB10114_88")
    private Long regionType;

    *//**
     * 备注
     *//*
    @ExcelProperty(value = "备注")
    private String remark;*/


}
