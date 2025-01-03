package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerCoupon;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 优惠券管理业务对象 folwer_coupon
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerCoupon.class, reverseConvertGenerate = false)
public class FolwerCouponBo extends BaseEntity {

    /**
     * 主键
     */
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
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
