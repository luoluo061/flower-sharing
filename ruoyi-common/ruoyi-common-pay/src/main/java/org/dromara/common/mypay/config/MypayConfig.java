package org.dromara.common.mypay.config;


import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.core.notification.NotificationConfig;
import com.wechat.pay.java.core.notification.NotificationParser;
import com.wechat.pay.java.core.util.IOUtil;
import com.wechat.pay.java.core.util.PemUtil;
import com.wechat.pay.java.service.payments.jsapi.JsapiService;
import com.wechat.pay.java.service.profitsharing.ProfitsharingService;
import com.wechat.pay.java.service.refund.RefundService;
import org.dromara.common.mypay.config.properties.PayProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;
import java.security.cert.*;
import java.util.concurrent.TimeUnit;

/**
 * 支付配置
 *
 * @author Lion Li
 */
//@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(PayProperties.class)
public class MypayConfig {

    @Autowired
    private PayProperties payProperties;

    private static final String CLASS_PATH = "classpath:";

    /**
     * 获取支付配置
     * @param
     * @return
     */
    private Config getPayConfig() throws IOException {
        return new RSAAutoCertificateConfig.Builder()
            .merchantId(payProperties.getMchId())
            .privateKey(this.getPrivateKey())
            .merchantSerialNumber(payProperties.getSerialNumber())
            .apiV3Key(payProperties.getKey())
            .build();
    }

    /**
     * 获取JSAPI支付服务
     * @param
     * @return
     * @throws IOException
     */
    public JsapiService getJsapiService() throws IOException {
        Config config =this.getPayConfig();
        // 构建service
        JsapiService service = new JsapiService.Builder().config(config).build();
        return service;
    }

    /***
     * 退款配置
     */
    public RefundService getRefundConfig() throws IOException {
// 使用自动更新平台证书的RSA配置
        // 一个商户号只能初始化一个配置，否则会因为重复的下载任务报错
//        Config config =
//            new RSAAutoCertificateConfig.Builder()
//                .merchantId(payProperties.getMchId())
//                //使用 SDK 不需要计算请求签名和验证应答签名
//                // 使用 com.wechat.pay.java.core.util 中的函数从本地文件中加载商户私钥，商户私钥会用来生成请求的签名
//                .privateKey(this.getPrivateKey())
//                .merchantSerialNumber(payProperties.getSerialNumber())
//                .apiV3Key(payProperties.getKey())
//                .build();
        Config config =this.getPayConfig();
        // 构建退款service
        RefundService service = new RefundService.Builder().config(config).build();
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
     * 分账配置
     */
    public ProfitsharingService getProfitConfig() throws IOException {
        Config config =this.getPayConfig();
        // 构建分账service
//        Config profitConfig = new Config.Builder()
//            .config(config)
//            .httpReadTimeout(10, TimeUnit.SECONDS)
//            .build();
        ProfitsharingService profitService = new ProfitsharingService.Builder().config(config).build();

        return profitService;
    }

    /**
     * 获取通知转换器
     * @param
     * @return
     * @throws IOException
     */
    public NotificationParser getParser() throws IOException {
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
