package org.dromara.common.core.domain.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 微信小程序用户登录对象
 *
 * @author Lion Li
 */
@Data
@NoArgsConstructor
public class WxLoginBody {

    /**
     * 客户端id
     */
    @NotBlank(message = "{auth.clientid.not.blank}")
    private String clientId;

    /**
     * 授权类型
     */
    @NotBlank(message = "{auth.grant.type.not.blank}")
    private String grantType;



    /**
     * 小程序xcxCode
     */
    private String xcxCode;

    /**
     * 包括敏感数据在内的完整用户信息的加密数据
     */
    private String encryptedData;
    /**
     *加密算法的初始向量
     */
    private String iv;

    /**
     * 小程序openid
     */
    private String openid;




}
