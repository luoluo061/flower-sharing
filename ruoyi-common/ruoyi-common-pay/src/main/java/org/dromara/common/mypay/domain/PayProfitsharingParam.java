package org.dromara.common.mypay.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 支付参数
 *
 * @author mlhxj
 */
@Data
public class PayProfitsharingParam {

    /**
     * appid
     * 微信分配的服务商appid
     */
    @NotBlank(message="公众账号ID")
    private String appid;

	/**
	  * 微信支付订单号
	 */
	@NotBlank(message="微信订单号不能为空")
	private String transactionId;


    /**
     * 商户分账单号
     * 服务商系统内部的分账单号，在服务商系统内部唯一，同一分账单号多次请求等同一次。只能是数字、大小写字母_-|*@
     */
    @NotBlank(message="商户分账单号")
    private String outOrderNo;

	/**
	 * 分账接收方类型
     * 1、MERCHANT_ID：商户号
     * 2、PERSONAL_OPENID：个人openid（由父商户APPID转换得到）
     * 3、PERSONAL_SUB_OPENID: 个人sub_openid（由子商户APPID转换得到）
	 */
	@NotNull(message="分账接收方类型不能为空")
	private String type;

    /**
     * 分账接收方帐号
     * 1、类型是MERCHANT_ID时，是商户号
     * 2、类型是PERSONAL_OPENID时，是个人openid
     */
    @NotNull(message="分账接收方帐号不能为空")
    private String account;

    /**
     * 分账金额
     * 分账金额，单位为分，只能为整数，不能超过原订单支付金额及最大分账比例金额
     */
    @NotNull(message="分账金额不能为空")
    private Long amount;

    /**
     * 分账接收方帐号
     * 分账的原因描述，分账账单中需要体现
     */
    @NotNull(message="分账描述不能为空")
    private String description;
}
