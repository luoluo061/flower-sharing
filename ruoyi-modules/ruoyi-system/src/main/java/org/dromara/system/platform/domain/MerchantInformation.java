package org.dromara.system.platform.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

import java.io.Serial;

/**
 * 商户信息对象 merchant_information
 *
 * @author Lion Li
 * @date 2024-12-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("merchant_information")
public class MerchantInformation extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 商铺图片
     */
    private String merchantImage;

    /**
     * 商户名称
     */
    private String merchantName;

    /**
     * 商铺名称
     */
    private String storeName;

    /**
     * 合作类型 0 内部商户 ,1  外部商户
     */
    private Long cooperationType;

    /**
     * 商户类型  0 企业，1 个人
     */
    private Long merchantType;

    /**
     * 统一社会信用代码
     */
    private String creditCode;

    /**
     * 门牌号
     */
    private String houseNumber;

    /**
     * 商铺地址
     */
    private String merchantAddress;

    /**
     * 营业类型
     */
    private Long businessType;

    /**
     * 身份证正面
     */
    private String idCardFront;

    /**
     * 身份证反面
     */
    private String idCardBack;

    /**
     * 紧急联系人
     */
    private String emergencyContact;

    /**
     * 紧急联系人关系
     */
    private String emergencyContactRelationship;

    /**
     * 紧急联系人电话
     */
    private String emergencyContactPhone;

    /**
     * 详细地址
     */
    private String detailedAddress;

    /**
     * 合作开始时间
     */
    private Date startTime;

    /**
     * 合作结束时间
     */
    private Date endTime;

    /**
     * 营业执照(PDF)
     */
    private String businessLicense;

    /**
     * 经营许可(PDF)
     */
    private String operatingLicense;

    /**
     * 身份证复印件(PDF)
     */
    private String idCardFile;

    /**
     * 负责人
     */
    private String manager;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 小程序openid
     */
    private String openId;

    /**
     * 状态 Y 启用 N 禁用
     */
    private String status;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
