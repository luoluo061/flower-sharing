package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 花友圈对象 flower_friends_community
 *
 * @author mlhxj
 * @date 2025-10-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("flower_friends_community")
public class FlowerAppletFriendsCommunity extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 弹窗ID 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 删除标志 0=否 2=是
     */
    @TableLogic
    private Long delFlag;

    /**
     * 弹窗标题
     */
    private String title;

    /**
     * 弹窗文字内容
     */
    private String textContent;

    /**
     * 弹窗图片ID
     */
    private String popupImageId;

    /**
     * 是否启用 0=否 1=是
     */
    private Long isUsed;

    /**
     * 发布类型 1=首页 2=商品页 3=活动页
     */
    private Long publishType;


}
