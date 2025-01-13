package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MemberPointsExchangeGold;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 会员中心--积分兑换为金币业务对象 member_points_exchange_gold
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MemberPointsExchangeGold.class, reverseConvertGenerate = false)
public class MemberPointsExchangeGoldBo extends BaseEntity {

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
     * 兑换积分
     */
//    @NotNull(message = "兑换积分不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long points;

    /**
     * 兑现金币
     */
    @NotNull(message = "兑现金币不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long gold;

    /**
     * 剩余积分
     */
//    @NotNull(message = "剩余积分不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long balance;

    /**
     * 创建人中文名称
     */
//    @NotBlank(message = "创建人中文名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String createName;

    /**
     * 会员的UserId
     */
    private String memberId;

}
