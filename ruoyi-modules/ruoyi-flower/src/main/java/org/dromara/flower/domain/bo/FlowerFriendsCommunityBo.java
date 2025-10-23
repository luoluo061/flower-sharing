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
 * 弹窗管理 BO（对应表：flower_friends_community）
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FlowerFriendsCommunity.class, reverseConvertGenerate = false)
public class FlowerFriendsCommunityBo extends BaseEntity {

    /** 弹窗ID */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /** 弹窗标题 */
    @Size(max = 255, message = "标题长度不能超过255")
    private String title;

    /** 弹窗文字内容 */
    @Size(max = 255, message = "文字长度不能超过255")
    private String textContent;     // ← 对应列 text_content

    /** 弹窗图片ID（或URL） */
    @Size(max = 64, message = "图片ID长度不能超过64")
    private String popupImageId;    // ← 对应列 popup_image_id

    /** 是否启用 0=否 1=是 */
    @NotNull(message = "是否启用不能为空", groups = { AddGroup.class, EditGroup.class })
    @Min(0) @Max(1)
    private Integer isUsed;

    /** 发布类型 1=首页 2=商品页 3=活动页 */
    @NotNull(message = "发布类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private Integer publishType;
}
