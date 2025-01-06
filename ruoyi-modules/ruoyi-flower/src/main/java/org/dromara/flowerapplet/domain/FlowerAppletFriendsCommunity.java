package org.dromara.flowerapplet.domain;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.common.tenant.core.TenantEntity;

import java.io.Serial;

/**
 * 花友圈对象 flower_friends_community
 *
 * @author mlhxj
 * @date 2024-12-30
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flower_friends_community")
public class FlowerAppletFriendsCommunity extends TenantEntity {

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
    @TableLogic
    private Long delFlag;

    /**
     * 标题
     */
    private String title;

    /**
     * 发布类型 0 视频 1 图文 2 其它
     */
    private Long type;

    /**
     * 会员ID
     */
    private String memberId;

    /**
     * 会员名称
     */
    private String memberName;

    /**
     * 会员等级
     */
    private String grade;

    /**
     * 浏览量
     */
    private Long pageView;

    /**
     * 点赞数
     */
    private Long likes;

    /**
     * 发表文本内容
     */
    private String content;

    /**
     * 视频或图片ID，多个文件逗号(,)分隔
     */
    private String videoImagesIds;

    /**
     * 是否隐匿 0 否 1是
     */
    private Long status;


}
