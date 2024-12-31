package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FlowerFriendsCommunity;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 花友圈业务对象 flower_friends_community
 *
 * @author mlhxj
 * @date 2024-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowerFriendsCommunity.class, reverseConvertGenerate = false)
public class FlowerFriendsCommunityBo extends BaseEntity {

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
     * 标题
     */
//    @NotBlank(message = "标题不能为空", groups = { AddGroup.class, EditGroup.class })
    private String title;

    /**
     * 发布类型 0 视频 1 图文 2 其它
     */
//    @NotNull(message = "发布类型 0 视频 1 图文 2 其它不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long type;

    /**
     * 会员ID
     */
//    @NotBlank(message = "会员ID不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberId;

    /**
     * 会员名称
     */
//    @NotBlank(message = "会员名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String memberName;

    /**
     * 会员等级
     */
//    @NotBlank(message = "会员等级不能为空", groups = { AddGroup.class, EditGroup.class })
    private String grade;

    /**
     * 浏览量
     */
//    @NotNull(message = "浏览量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long pageView;

    /**
     * 点赞数
     */
//    @NotNull(message = "点赞数不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long likes;

    /**
     * 发表文本内容
     */
//    @NotBlank(message = "发表文本内容不能为空", groups = { AddGroup.class, EditGroup.class })
    private String content;

    /**
     * 视频或图片ID，多个文件逗号(,)分隔
     */
//    @NotBlank(message = "视频或图片URL，多个文件逗号(,)分隔不能为空", groups = { AddGroup.class, EditGroup.class })
    private String videoImagesIds;

    /**
     * 是否隐匿 0 否 1是
     */
//    @NotNull(message = "是否隐匿 0 否 1是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;


}
