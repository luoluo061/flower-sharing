package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FlowerFriendsCommunityLike;
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
 * 花友圈--点赞详情业务对象 flower_friends_community_like
 *
 * @author mlhxj
 * @date 2025-01-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowerFriendsCommunityLike.class, reverseConvertGenerate = false)
public class FlowerFriendsCommunityLikeBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 花友圈ID
     */
    @NotNull(message = "花友圈ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long flowerFriendsCommunityId;

    /**
     * 会员ID
     */
    @NotNull(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long memberId;

    /**
     * 会员名称
     */
    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberName;

    /**
     * 点赞时间
     */
    @NotNull(message = "点赞时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date likeTime;

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long appletUserInformationId;


}
