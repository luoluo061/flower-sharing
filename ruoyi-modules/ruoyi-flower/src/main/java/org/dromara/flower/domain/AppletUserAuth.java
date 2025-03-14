package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 小程序用户信息认证对象 applet_user_auth
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("applet_user_auth")
public class AppletUserAuth extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    @TableId(value = "auth_id")
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

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
