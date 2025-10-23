package org.dromara.flower.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flower.domain.FlowerFriendsCommunity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 弹窗管理 VO（对应表：flower_friends_community）
 *
 * @author mlhx
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowerFriendsCommunity.class)
public class FlowerFriendsCommunityVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 弹窗ID */
    @ExcelProperty(value = "弹窗ID")
    private Long id;

    /** 弹窗标题 */
    @ExcelProperty(value = "标题")
    private String title;

    /** 弹窗文字内容 */
    @ExcelProperty(value = "文字内容")
    private String textContent;

    /** 弹窗图片ID（popup_image_id） */
    @ExcelProperty(value = "图片ID")
    private String popupImageId;

    /** 弹窗图片URL（自动翻译） */
    @ExcelProperty(value = "图片URL")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "popupImageId")
    private String popupImageUrl;

    /** 是否启用 0=否 1=是 */
    @ExcelProperty(value = "是否启用")
    private Integer isUsed;

    /** 发布类型 1=首页 2=商品页 3=活动页 */
    @ExcelProperty(value = "发布类型")
    private Integer publishType;

    /** 创建时间 */
    @ExcelProperty(value = "创建时间")
    private Date createTime;
}
