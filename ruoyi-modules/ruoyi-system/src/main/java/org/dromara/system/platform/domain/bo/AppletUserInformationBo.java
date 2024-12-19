package org.dromara.system.platform.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.system.platform.domain.AppletUserInformation;


/**
 * 小程序用户信息业务对象 applet_user_information
 *
 * @author LionLi
 * @date 2024-11-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = AppletUserInformation.class, reverseConvertGenerate = false)
public class AppletUserInformationBo extends BaseEntity {

    /**
     * 主键id
     */

    private Long userId;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 用户类型
     */
    @NotNull(message = "用户类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String userType;


    /**
     * 姓名
     */

    private String name;

    /**
     * 昵称
     */

    private String nickName;

    /**
     * 手机号
     */
    @NotBlank(message = "手机号不能为空", groups = { AddGroup.class, EditGroup.class })
    private String phone;

    /**
     * 小程序openid
     */

    private String openid;

    /**
     * 积分
     */
    private Long points;
    /**
     * 身份证号
     */
    private String idNumber;
    /**
     * 状态
     */
    private Long status;

    /**
     * 微信号
     */

    private String wechatNumber;



}
