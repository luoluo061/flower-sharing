package org.dromara.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 三方登录对象
 *
 * @author Lion Li
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class XcxLoginBody extends LoginBody {

    /**
     * 小程序id(多个小程序时使用)
     */
    private String appid;

    /**
     * 小程序code(避免与框架的验证码code重复而重命名)
     */
    @NotBlank(message = "{xcx.code.not.blank}")
    private String xcxCode;

    /**
     * 凭证-动态令牌
     */
    private String accessToken;

    /**
     * 推荐人ID 如果不是扫别人二维码登录的 传 0
     */
    private Long parentId;

    /**
     * 小程序openid
     */
    private String openid;

}
