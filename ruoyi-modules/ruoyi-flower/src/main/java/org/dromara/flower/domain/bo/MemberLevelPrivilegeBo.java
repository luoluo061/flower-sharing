package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MemberLevelPrivilege;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 会员中心--会员等级--权益名称业务对象 member_level_privilege
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MemberLevelPrivilege.class, reverseConvertGenerate = false)
public class MemberLevelPrivilegeBo extends BaseEntity {

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
     * 会员权益名称
     */
    @NotBlank(message = "会员权益名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 总数量
     */
//    @NotNull(message = "总数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long amount;

    /**
     * 会员等级ID
     */
//    @NotNull(message = "会员等级ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberLevelId;


}
