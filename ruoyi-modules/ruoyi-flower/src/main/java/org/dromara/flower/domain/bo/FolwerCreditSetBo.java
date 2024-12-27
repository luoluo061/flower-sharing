package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerCreditSet;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 积分配置业务对象 folwer_credit_set
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerCreditSet.class, reverseConvertGenerate = false)
public class FolwerCreditSetBo extends BaseEntity {

    /**
     * ID
     */
    @NotNull(message = "ID不能为空", groups = { EditGroup.class })
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


}
