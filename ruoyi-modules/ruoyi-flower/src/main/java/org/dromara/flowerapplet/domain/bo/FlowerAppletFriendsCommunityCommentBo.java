package org.dromara.flowerapplet.domain.bo;

import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.flowerapplet.domain.FlowerAppletFriendsCommunityComment;

import java.util.Date;

/**
 * 花友圈--评论详情业务对象 flower_friends_community_comment
 *
 * @author mlhxj
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowerAppletFriendsCommunityComment.class, reverseConvertGenerate = false)
public class FlowerAppletFriendsCommunityCommentBo extends BaseEntity {

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
     * 评论时间
     */
//    @NotNull(message = "评论时间不能为空", groups = { AddGroup.class, EditGroup.class })
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


}
