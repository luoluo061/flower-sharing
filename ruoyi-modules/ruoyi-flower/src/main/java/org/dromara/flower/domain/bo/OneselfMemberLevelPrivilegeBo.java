package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.OneselfMemberLevelPrivilege;
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
 * 会员中心--个人会员权益详情记录业务对象 oneself_member_level_privilege
 *
 * @author mlhxj
 * @date 2025-01-13
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = OneselfMemberLevelPrivilege.class, reverseConvertGenerate = false)
public class OneselfMemberLevelPrivilegeBo extends BaseEntity {

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
//    @NotBlank(message = "会员权益名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 总数量
     */
//    @NotNull(message = "总数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long amount;

    /**
     * 会员购买记录ID
     */
//    @NotNull(message = "会员购买记录ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberPurchaseRecordId;

    /**
     * 权益使用状态 0 否 1 是
     */
//    @NotNull(message = "权益使用状态 0 否 1 是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 个人权益到期时间
     */
//    @NotNull(message = "个人权益到期时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date endTime;

    /**
     * 已使用数量
     */
//    @NotNull(message = "已使用数量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long usageQuantity;


}
