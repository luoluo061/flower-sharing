package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 积分配置对象 folwer_credit_set
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_credit_set")
public class FolwerCreditSet extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 购买商品
     */
    private Long goodsPurchase;

    /**
     * 商品积分
     */
    private Long goodsCredit;

    /**
     * 充值会员
     */
    private Long memberPurchase;

    /**
     * 会员积分
     */
    private Long memberCredit;

    /**
     * 登录积分
     */
    private Long loginCredit;

    /**
     * 邀请积分
     */
    private Long inviteCredit;

    /**
     * 金币
     */
    private Long goldcoin;

    /**
     * 金币积分
     */
    private Long goldcoinCredit;

    /**
     * 金币兑现
     */
    private Long goldcoinCash;

    /**
     * 兑现金额
     */
    private Long cashIn;

    /**
     * 状态 0:不使用 1：使用
     */
    private Long status;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
