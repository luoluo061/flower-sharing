package org.dromara.flowerapplet.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flowerapplet.domain.FlowerAppletFriendsCommunity;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 花友圈视图对象 flower_friends_community
 *
 * @author mlhxj
 * @date 2025-10-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FlowerAppletFriendsCommunity.class)
public class FlowerAppletFriendsCommunityVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 弹窗ID 主键
     */
    @ExcelProperty(value = "弹窗ID 主键")
    private Long id;

    /**
     * 部门ID
     */
    @ExcelProperty(value = "部门ID")
    private Long deptId;

    /**
     * 弹窗标题
     */
    @ExcelProperty(value = "弹窗标题")
    private String title;

    /**
     * 弹窗文字内容
     */
    @ExcelProperty(value = "弹窗文字内容")
    private String textContent;

    /**
     * 弹窗图片ID
     */
    @ExcelProperty(value = "弹窗图片ID")
    private String popupImageId;

      /** 弹窗图片URL（自动翻译） */
    @ExcelProperty(value = "图片URL")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "popupImageId")
    private String popupImageUrl;

    /**
     * 是否启用 0=否 1=是
     */
    @ExcelProperty(value = "是否启用 0=否 1=是")
    private Long isUsed;

    /**
     * 发布类型 1=首页 2=商品页 3=活动页
     */
    @ExcelProperty(value = "发布类型 1=首页 2=商品页 3=活动页")
    private Long publishType;


}
