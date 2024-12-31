package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serial;

/**
 * 花友圈--评论详情对象 flower_friends_community_comment
 *
 * @author mlhxj
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flower_friends_community_comment")
public class FlowerFriendsCommunityComment extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 删除标志 0 否 2 是
     */
//    @TableLogic
    private Long delFlag;

    /**
     * 花友圈ID
     */
    private Long flowerFriendsCommunityId;

    /**
     * 跑路时间
     */
    private Date commentTime;

    /**
     * 评论内容
     */
    private String commentContent;

    /**
     * 评论Id，0标识顶级评论，不为 0 表示沟通回复
     */
    private Long parentId;

    /**
     * 用户ID
     */
    private Long appletUserInformationId;


}
