package org.dromara.system.platform.domain.vo;

import java.time.LocalDateTime;

import org.dromara.system.platform.domain.MerchantInformation;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 商户信息视图对象 merchant_information
 *
 * @author Lion Li
 * @date 2024-12-05
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = MerchantInformation.class)
public class MerchantInformationVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @ExcelProperty(value = "主键id")
    private Long id;


    /**
     * 商户名称
     */
    @ExcelProperty(value = "商户名称")
    private String merchantName;


    /**
     * 合作类型 0 内部商户 ,1  外部商户
     */
    @ExcelProperty(value = "合作类型 0 内部商户 ,1  外部商户", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "merchant_cooperation_type")
    private Long cooperationType;

    /**
     * 商户类型  0 企业，1 个人
     */
    @ExcelProperty(value = "商户类型  0 企业，1 个人", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "merchant_type")
    private Long merchantType;

    /**
     * 统一社会信用代码
     */
    @ExcelProperty(value = "统一社会信用代码")
    private String creditCode;



    /**
     * 商铺地址
     */
    @ExcelProperty(value = "商铺地址")
    private String merchantAddress;

    /**
     * 营业类型
     */
    /*@ExcelProperty(value = "营业类型", converter = ExcelDictConvert.class)
    @ExcelDictFormat(dictType = "merchant_business_type")
    private Long businessType;*/


    /**
     * 详细地址
     */
    @ExcelProperty(value = "详细地址")
    private String detailedAddress;

    /**
     * 合作开始时间
     */
    /*@ExcelProperty(value = "合作开始时间")
    private Date startTime;*/

    /**
     * 合作结束时间
     */
    /*@ExcelProperty(value = "合作结束时间")
    private Date endTime;*/



    /**
     * 负责人
     */
    /*@ExcelProperty(value = "负责人")
    private String manager;*/

    /**
     * 联系电话
     */
    @ExcelProperty(value = "联系电话")
    private String phone;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号")
    private String idNumber;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 状态
     */
    private String Status;


}
