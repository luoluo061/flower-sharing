package org.dromara.common.mypay.config;


import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.util.IOUtil;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.mypay.config.properties.PayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.security.PrivateKey;
import java.util.concurrent.TimeUnit;

/**
 * 支付配置
 *
 * @author Lion Li
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(PayProperties.class)
public class MypayConfig {

    @Autowired
    private PayProperties payProperties;

    /**
     * 获取支付配置
     * @param isCharge
     * @return
     */
    private Config getPayConfig(Boolean isCharge) throws IOException {
        return new RSAAutoCertificateConfig.Builder()
            .merchantId(payProperties.getMchId())
            .privateKey(this.getPrivateKey())
            .merchantSerialNumber(payProperties.getSerialNumber())
            .apiV3Key(payProperties.getKey())
            .build();
    }

    /**
     * 获取JSAPI支付服务
     * @param isChange
     * @return
     * @throws IOException
     */
    public JsapiService getJsapiService(Boolean isChange) throws IOException {
        Config config =this.getPayConfig(isChange);
        // 构建service
        JsapiService service = new JsapiService.Builder().config(config).build();
        return service;
    }

    /**
     * 获取私钥
     * @return
     */
    public PrivateKey getPrivateKey() throws IOException {
        ClassPathResource resource = new ClassPathResource(payProperties.getPath());
        PrivateKey key= PemUtil.loadPrivateKeyFromString(IOUtil.toString(resource.getInputStream()));
        return key;
    }

    /**
     * 获取通知转换器
     * @param isChange
     * @return
     * @throws IOException
     */
    public NotificationParser getParser(Boolean isChange) throws IOException {
        NotificationConfig config = this.getNotifyConfig();
        // 初始化 NotificationParser
        NotificationParser parser = new NotificationParser(config);
        return parser;
    }

    /**
     * 获取通知配置
     * @param
     * @return
     */
    private NotificationConfig getNotifyConfig() throws IOException {
        return new RSAAutoCertificateConfig.Builder()
            .merchantId(payProperties.getMchId())
            .privateKey(this.getPrivateKey())
            .merchantSerialNumber(payProperties.getSerialNumber())
            .apiV3Key(payProperties.getKey())
            .build();
    }



}
