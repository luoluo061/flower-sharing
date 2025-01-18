package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;

import java.io.Serial;

/**
 * 积分获取记录对象 folwer_credit_getrecords
 *
 * @author mlhxj
 * @date 2025-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_credit_getrecords")
public class FolwerAppletCreditGetrecords extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 记录ID
     */
    @TableId(value = "record_id")
    private Long recordId;

    /**
     * 会员ID
     */
    private Long userId;

    /**
     * 会员名称
     */
    private String userName;

    /**
     * 会员类型
     */
    private Long memberLevelId;

    /**
     * 积分来源ID
     */
    private Long creditSourId;

    /**
     * 积分来源
     */
    private String creditSourName;

    /**
     * 交易积分
     */
    private String getTotal;

    /**
     * 交易时间
     */
    private Date getTime;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 状态 0:待兑换 1：已兑换 2:成功 3:失败
     */
    private Long status;

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
