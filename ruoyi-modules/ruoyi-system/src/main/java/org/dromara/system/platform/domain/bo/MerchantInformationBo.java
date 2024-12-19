package org.dromara.system.platform.domain.bo;

import org.dromara.system.platform.domain.MerchantInformation;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.util.Date;

/**
 * 商户信息业务对象 merchant_information
 *
 * @author Lion Li
 * @date 2024-12-05
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MerchantInformation.class, reverseConvertGenerate = false)
public class MerchantInformationBo extends BaseEntity {

    /**
     * 主键id
     */

    private Long id;

    /**
     * 商铺图片
     */
    @NotBlank(message = "商铺图片不能为空", groups = { AddGroup.class, EditGroup.class })
    private String merchantImage;

    /**
     * 商户名称
     */
    @NotBlank(message = "商户名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String merchantName;

    /**
     * 商铺名称
     */
    @NotBlank(message = "商铺名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String storeName;

    /**
     * 合作类型 0 内部商户 ,1  外部商户
     */
    @NotNull(message = "合作类型 0 内部商户 ,1  外部商户不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long cooperationType;

    /**
     * 商户类型  0 企业，1 个人
     */
    @NotNull(message = "商户类型  0 企业，1 个人不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long merchantType;

    /**
     * 统一社会信用代码
     */
    @NotBlank(message = "统一社会信用代码不能为空", groups = { AddGroup.class, EditGroup.class })
    private String creditCode;

    /**
     * 门牌号
     */
    @NotBlank(message = "门牌号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String houseNumber;

    /**
     * 商铺地址
     */
    @NotBlank(message = "商铺地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String merchantAddress;

    /**
     * 营业类型
     */
    @NotNull(message = "营业类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long businessType;

    /**
     * 身份证正面
     */
    @NotBlank(message = "身份证正面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String idCardFront;

    /**
     * 身份证反面
     */
    @NotBlank(message = "身份证反面不能为空", groups = { AddGroup.class, EditGroup.class })
    private String idCardBack;

    /**
     * 紧急联系人
     */
    @NotBlank(message = "紧急联系人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String emergencyContact;

    /**
     * 紧急联系人关系
     */
    @NotBlank(message = "紧急联系人关系不能为空", groups = { AddGroup.class, EditGroup.class })
    private String emergencyContactRelationship;

    /**
     * 紧急联系人电话
     */
    @NotBlank(message = "紧急联系人电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String emergencyContactPhone;

    /**
     * 详细地址
     */
    @NotBlank(message = "详细地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String detailedAddress;

    /**
     * 合作开始时间
     */
    @NotNull(message = "合作开始时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date startTime;

    /**
     * 合作结束时间
     */
    @NotNull(message = "合作结束时间不能为空", groups = { AddGroup.class, EditGroup.class })
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
    @NotBlank(message = "负责人不能为空", groups = { AddGroup.class, EditGroup.class })
    private String manager;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 身份证号
     */
    @NotBlank(message = "身份证号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String idNumber;


}
