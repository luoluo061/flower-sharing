package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FlowerFriendsCommunityComment;
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
 * 花友圈--评论详情业务对象 flower_friends_community_comment
 *
 * @author mlhxj
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowerFriendsCommunityComment.class, reverseConvertGenerate = false)
public class FlowerFriendsCommunityCommentBo extends BaseEntity {

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
     * 花友圈ID
     */
//    @NotNull(message = "花友圈ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long flowerFriendsCommunityId;

    /**
     * 跑路时间
     */
//    @NotNull(message = "跑路时间不能为空", groups = { AddGroup.class, EditGroup.class })
    private Date commentTime;

    /**
     * 评论内容
     */
//    @NotBlank(message = "评论内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String commentContent;

    /**
     * 评论Id，0标识顶级评论，不为 0 表示沟通回复
     */
//    @NotNull(message = "评论Id，0标识顶级评论，不为 0 表示回复不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 用户ID
     */
//    @NotNull(message = "用户ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long appletUserInformationId;


}
