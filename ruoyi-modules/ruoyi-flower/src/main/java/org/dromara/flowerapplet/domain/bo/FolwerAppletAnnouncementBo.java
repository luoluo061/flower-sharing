package org.dromara.flowerapplet.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dromara.flowerapplet.domain.FolwerAppletAnnouncement;

/**
 * 公告业务对象 folwer_announcement
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletAnnouncement.class, reverseConvertGenerate = false)
public class FolwerAppletAnnouncementBo extends BaseEntity {

    /**
     * ID
     */
    private Long announcementId;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 访问量
     */
    private Long visits;

    /**
     * 封面图
     */
    private String coverImage;

    /**
     * 是否显示，1:为显示，0:待审核， -1：不通过审核，不显示
     */
    private Long status;

}
