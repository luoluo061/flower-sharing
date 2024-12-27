package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MemberExchangeRecord;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 会员中心--兑换记录业务对象 member_exchange_record
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MemberExchangeRecord.class, reverseConvertGenerate = false)
public class MemberExchangeRecordBo extends BaseEntity {

    /**
     * 主键
     */
//    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
//    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 兑现金额
     */
//    @NotNull(message = "兑现金额不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long amount;

    /**
     * 兑现金币
     */
//    @NotNull(message = "兑现金币不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long gold;

    /**
     * 剩余金币
     */
//    @NotNull(message = "剩余金币不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long balance;

    /**
     * 创建人中文名称
     */
//    @NotBlank(message = "创建人中文名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String createName;


}
