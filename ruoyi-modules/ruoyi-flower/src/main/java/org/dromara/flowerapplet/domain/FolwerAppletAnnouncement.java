package org.dromara.flowerapplet.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 公告对象 folwer_announcement
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_announcement")
public class FolwerAppletAnnouncement extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "announcement_id")
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

    /**
     * 删除标志 0 否 2 是
     */
    @TableLogic
    private Long delFlag;


}
