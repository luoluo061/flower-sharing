package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerCouponReceive;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 优惠券领取记录视图对象 folwer_coupon_receive
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerCouponReceive.class)
public class FolwerCouponReceiveVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 优惠券ID
     */
    @ExcelProperty(value = "优惠券ID")
    private Long couponId;

    /**
     * 会员ID
     */
    @ExcelProperty(value = "会员ID")
    private Long userId;

    /**
     * 会员名称
     */
    @ExcelProperty(value = "会员名称")
    private String userName;

    /**
     * 手机号
     */
    @ExcelProperty(value = "手机号")
    private String phone;

    /**
     * 头像
     */
    @ExcelProperty(value = "头像")
    private String icon;

    /**
     * 头像Url
     */
    @ExcelProperty(value = "头像Url")
    private String iconUrl;

    /**
     * 状态 默认是0，表示未使用,1为已使用
     */
    @ExcelProperty(value = "状态 默认是0，表示未使用,1为已使用")
    private Long status;


}
