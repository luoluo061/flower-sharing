package org.dromara.common.mypay.config.properties;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redisson 配置属性
 *
 * @author Lion Li
 */
@Data
@ConfigurationProperties(prefix = "wx.pay")
public class PayProperties {
    /** 商户API私钥路径 */
    private   String path;
    /** 商户证书序列号 */
    private   String serialNumber;
    /** 商户APIV3密钥 */
    private   String key;
    /** 商户APPID**/
    private String appId;
    /**商户号**/
    private String mchId;
    /**通知回调**/
    private String notifyUrl;
    /**退款回调**/
    private String refunNotifyUrl;
    /** 平台证书地址 **/
    private String wechatpayCertificatePath;
}
