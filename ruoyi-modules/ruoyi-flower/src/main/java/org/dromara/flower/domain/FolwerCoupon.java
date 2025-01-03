package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 优惠券管理对象 folwer_coupon
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_coupon")
public class FolwerCoupon extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "coupon_id")
    private Long couponId;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 商品分类ID
     */
    private Long categoryId;

    /**
     * 商品分类名称
     */
    private String categoryName;

    /**
     * 优惠券类型
     */
    private String couponType;

    /**
     * 领取后有效期
     */
    private String couponDate;

    /**
     * 优惠券种类，默认是0，表示普通优惠券,1为定向优惠券
     */
    private Long type;

    /**
     * 排序
     */
    private Long seq;

    /**
     * 总库存 -1为不限量
     */
    private Long totalStocks;

    /**
     * 已发放量
     */
    private Long total;

    /**
     * 状态 默认是1，表示正常领取,0为关闭
     */
    private Long status;

    /**
     * 展示 默认是1，表示展示,0为不展示
     */
    private Long isShow;

    /**
     * 优惠劵描述
     */
    private String remarks;

    /**
     * 删除标志 0 否 1 是
     */
    @TableLogic
    private Long delFlag;


}
