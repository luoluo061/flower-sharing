package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FlowerAppletFriendsCommunity;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 花友圈业务对象 flower_friends_community
 *
 * @author mlhxj
 * @date 2025-10-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowerAppletFriendsCommunity.class, reverseConvertGenerate = false)
public class FlowerAppletFriendsCommunityBo extends BaseEntity {

    /**
     * 弹窗ID 主键
     */
    private Long id;

    /**
     * 部门ID
     */
    private Long deptId;

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
