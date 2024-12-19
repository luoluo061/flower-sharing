package org.dromara.web.domain.vo;

import lombok.Data;

/**
 * 微信登录信息
 */
@Data
public class WxLoginVo {
    //微信用户唯一标识
    private String openid;
    //会话密钥
    private String sessionKey;


}
