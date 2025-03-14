package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FlowerAppletUserAuth;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 小程序用户信息认证业务对象 applet_user_auth
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowerAppletUserAuth.class, reverseConvertGenerate = false)
public class FlowerAppletUserAuthBo extends BaseEntity {

    /**
     * 主键id
     */
    private Long authId;

    /**
     * 会员ID
     */
    private Long userId;

    /**
     * 商店名称
     */
    private String merchantName;

    /**
     * 营业编号
     */
    private String merchantType;

    /**
     * 经营许可
     */
    private String merchantAuthPic;

    /**
     * 商店门头照片
     */
    private String merchantFacePic;

    /**
     * 商店内照片
     */
    private String merchantPic;

    /**
     * 店铺联系人
     */
    private String contactName;

    /**
     * 身份证正面
     */
    private String cardFrontPic;

    /**
     * 身份证反面
     */
    private String cardBackPic;

    /**
     * 身份证ID
     */
    private String contactCard;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 行政区域(如云南省昆明市盘龙区拓东街道)
     */
    private String district;

    /**
     * 地址详细位置
     */
    private String addDetail;

    /**
     * 状态 0:待审核，1:已审核
     */
    private Long status;

    /**
     * 是否通过 0:未通过， 1:已通过
     */
    private Long isPass;


}
