/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package org.dromara.flowerapplet.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotNull;
import org.dromara.flowerapplet.domain.FolwerAppletProduct;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;

import java.util.List;
import java.util.Map;

/**
 * @author lanhai
 */
@Data
@Schema(description = "订单参数")
public class OrderParamBo {

    @Schema(description = "用户ID" )
    private Long UserId;

	@Schema(description = "购物车id 数组" )
	private List<Long> basketIds;

	@Schema(description = "立即购买时提交的商品项" )
	private Long productItem;

	@Schema(description = "地址ID，0为默认地址" ,required=true)
	@NotNull(message = "地址不能为空")
	private Long addrId;

    @Schema(description = "订单备注" ,required=true)
    private String remarks;

    @Schema(description = "产品个数" ,required=true)
    private Integer prodCount;

    @Schema(description = "物流公司ID" ,required=true)
    private Long dvyId;

	@Schema(description = "用户是否改变了优惠券的选择，如果用户改变了优惠券的选择，则完全根据传入参数进行优惠券的选择 -1:不参与优惠，0:满减，1：花券" )
	private Integer userChangeCoupon;

	@Schema(description = "优惠券id数组,商品ID+优惠券ID" )
	private Map<Long, Long> couponIds;

//	@Schema(description = "每次订单提交时的uuid" )
//	private String uuid;
//	@Schema(description = "订单入口 SHOP_CART购物车，BUY_NOW立即购买" )
//	private OrderEntry orderEntry;



}
