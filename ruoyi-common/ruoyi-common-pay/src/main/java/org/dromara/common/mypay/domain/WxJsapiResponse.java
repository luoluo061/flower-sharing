package org.dromara.common.mypay.domain;

import lombok.Data;
import org.dromara.common.mypay.utils.Md5Utils;
//import org.springframework.util.Base64Utils;
//import org.springframework.util.Base64Utils;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.Signature;
import java.util.Base64;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * JSAPI支付响应参数
 *
 * @author mlhxj
 */
@Data
public class WxJsapiResponse implements Serializable {
    /**
     * 应用Id
     */
    private String appId;

    /**
     * 时间戳
     */
    private Long timeStamp = System.currentTimeMillis() / 1000;

    /**
     * 随机字符串
     */
    private String nonceStr= UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32); //RandomStringUtils.randomNumeric(32);

    /**
     * 预支付ID
     */
    private String prepayId;

    /**
     * 支付签名
     */
    private String paySign;

    /**
     * 签名算法
     */
    private String signType="RAS";

    /**
     * 系统生成的商户订单号
     */
    private String outTradeNo;

    /**
     * 参数签名
     * @param prepayId
     * @param appId
     * @param key
     * @return
     * @throws Exception
     */
    public WxJsapiResponse sign(String prepayId,String appId,PrivateKey key) throws Exception {
        this.prepayId=String.format("prepay_id=%s",prepayId);
        this.appId=appId;
        String signatureStr = Stream.of(appId, String.valueOf(this.timeStamp), this.nonceStr, this.prepayId)
                .collect(Collectors.joining("\n", "", "\n"));
        if (this.signType.equalsIgnoreCase("MD5")){
            this.paySign= Md5Utils.hash(signatureStr);
        } else {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(key);
            sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
            // 被弃用的方法
//            this.paySign= Base64Utils.encodeToString(sign.sign());
            // 替换为新的方法
            this.paySign = Base64.getEncoder().encodeToString(sign.sign());
        }
        return this;
    }

    /**
     * 支付响应构建
     * @param prepayId
     * @param appId
     * @param key
     * @return
     * @throws Exception
     */
    public static WxJsapiResponse build(String prepayId,String appId,PrivateKey key, String outTradeNo) throws Exception {
        WxJsapiResponse rsp=new WxJsapiResponse();
        rsp.setOutTradeNo(outTradeNo);
        return rsp.sign(prepayId,appId,key);
    }
}
