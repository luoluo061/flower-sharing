package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletCreditGetrecords;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 积分获取记录业务对象 folwer_credit_getrecords
 *
 * @author mlhxj
 * @date 2025-01-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletCreditGetrecords.class, reverseConvertGenerate = false)
public class FolwerAppletCreditGetrecordsBo extends BaseEntity {

    /**
     * 记录ID
     */
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


}
