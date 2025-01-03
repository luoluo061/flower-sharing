package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerCoupon;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 优惠券管理视图对象 folwer_coupon
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerCoupon.class)
public class FolwerCouponVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long couponId;

    /**
     * 优惠券名称
     */
    @ExcelProperty(value = "优惠券名称")
    private String couponName;

    /**
     * 商品分类ID
     */
    @ExcelProperty(value = "商品分类ID")
    private Long categoryId;

    /**
     * 商品分类名称
     */
    @ExcelProperty(value = "商品分类名称")
    private String categoryName;

    /**
     * 优惠券类型
     */
    @ExcelProperty(value = "优惠券类型")
    private String couponType;

    /**
     * 领取后有效期
     */
    @ExcelProperty(value = "领取后有效期")
    private String couponDate;

    /**
     * 优惠券种类，默认是0，表示普通优惠券,1为定向优惠券
     */
    @ExcelProperty(value = "优惠券种类，默认是0，表示普通优惠券,1为定向优惠券")
    private Long type;

    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long seq;

    /**
     * 总库存 -1为不限量
     */
    @ExcelProperty(value = "总库存 -1为不限量")
    private Long totalStocks;

    /**
     * 已发放量
     */
    @ExcelProperty(value = "已发放量")
    private Long total;

    /**
     * 状态 默认是1，表示正常领取,0为关闭
     */
    @ExcelProperty(value = "状态 默认是1，表示正常领取,0为关闭")
    private Long status;

    /**
     * 展示 默认是1，表示展示,0为不展示
     */
    @ExcelProperty(value = "展示 默认是1，表示展示,0为不展示")
    private Long isShow;

    /**
     * 优惠劵描述
     */
    @ExcelProperty(value = "优惠劵描述")
    private String remarks;


}
