package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 弹窗管理实体 对应表：flower_friends_community
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flower_friends_community")
public class FlowerFriendsCommunity extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /** 部门ID（保留即可） */
    private Long deptId;

    /** 逻辑删除 0=否 1=是（与你DDL一致的话用0/1） */
    @TableLogic
    private Integer delFlag;

    /** 弹窗标题 */
    private String title;

    /** 弹窗文字内容（text_content） */
    @TableField("text_content")
    private String textContent;

    /** 弹窗图片ID（popup_image_id） */
    @TableField("popup_image_id")
    private String popupImageId;

    /** 是否启用 0=否 1=是（is_used） */
    @TableField("is_used")
    private Integer isUsed;

    /** 发布类型 1=首页 2=商品页 3=活动页（publish_type） */
    @TableField("publish_type")
    private Integer publishType;
}
