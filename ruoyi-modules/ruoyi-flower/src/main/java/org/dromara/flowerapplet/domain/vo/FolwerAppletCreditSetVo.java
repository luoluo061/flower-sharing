package org.dromara.flowerapplet.domain.vo;

import org.dromara.flowerapplet.domain.FolwerAppletCreditSet;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 积分配置视图对象 folwer_credit_set
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletCreditSet.class)
public class FolwerAppletCreditSetVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long id;

    /**
     * 购买商品
     */
    @ExcelProperty(value = "购买商品")
    private Long goodsPurchase;

    /**
     * 商品积分
     */
    @ExcelProperty(value = "商品积分")
    private Long goodsCredit;

    /**
     * 充值会员
     */
    @ExcelProperty(value = "充值会员")
    private Long memberPurchase;

    /**
     * 会员积分
     */
    @ExcelProperty(value = "会员积分")
    private Long memberCredit;

    /**
     * 登录积分
     */
    @ExcelProperty(value = "登录积分")
    private Long loginCredit;

    /**
     * 邀请积分
     */
    @ExcelProperty(value = "邀请积分")
    private Long inviteCredit;

    /**
     * 金币
     */
    @ExcelProperty(value = "金币")
    private Long goldcoin;

    /**
     * 金币积分
     */
    @ExcelProperty(value = "金币积分")
    private Long goldcoinCredit;

    /**
     * 金币兑现
     */
    @ExcelProperty(value = "金币兑现")
    private Long goldcoinCash;

    /**
     * 兑现金额
     */
    @ExcelProperty(value = "兑现金额")
    private Long cashIn;

    /**
     * 状态 0:不使用 1：使用
     */
    @ExcelProperty(value = "状态 0:不使用 1：使用")
    private Long status;


}
