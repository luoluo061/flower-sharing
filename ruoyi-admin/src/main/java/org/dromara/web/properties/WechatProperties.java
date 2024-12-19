package org.dromara.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


/**
 * 微信小程序配置
 *
 */
@Component
@ConfigurationProperties(prefix = "wechat")
@Data
public class WechatProperties {


    /**
     *  小程序的appid
     */
    private String appid;
    /**
     *  小程序的秘钥
     */
    private String secret;



}
