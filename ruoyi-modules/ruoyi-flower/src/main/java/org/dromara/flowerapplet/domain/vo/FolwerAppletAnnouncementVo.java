package org.dromara.flowerapplet.domain.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flowerapplet.domain.FolwerAppletAnnouncement;

import java.io.Serial;
import java.io.Serializable;


/**
 * 公告视图对象 folwer_announcement
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletAnnouncement.class)
public class FolwerAppletAnnouncementVo extends BaseEntity implements Serializable  {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long announcementId;

    /**
     * 标题
     */
    @ExcelProperty(value = "标题")
    private String title;

    /**
     * 内容
     */
    @ExcelProperty(value = "内容")
    private String content;

    /**
     * 访问量
     */
    @ExcelProperty(value = "访问量")
    private Long visits;

    /**
     * 封面图
     */
    @ExcelProperty(value = "封面图")
    private String coverImage;

    /**
     * 封面图地址
     */
    @ExcelProperty(value = "封面图地址")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "coverImage")
    private String coverImageUrl;

    /**
     * 是否显示，1:为显示，0:待审核， -1：不通过审核，不显示
     */
    @ExcelProperty(value = "是否显示，1:为显示，0:待审核， -1：不通过审核，不显示")
    private Long status;


}
