package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerCreditGetrecords;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 积分获取记录业务对象 folwer_credit_getrecords
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerCreditGetrecords.class, reverseConvertGenerate = false)
public class FolwerCreditGetrecordsBo extends BaseEntity {

    /**
     * 记录ID
     */
    @NotNull(message = "记录ID不能为空", groups = { EditGroup.class })
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
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
