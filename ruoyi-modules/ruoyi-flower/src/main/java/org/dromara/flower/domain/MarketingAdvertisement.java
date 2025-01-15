package org.dromara.flower.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.util.Date;

/**
 * 广告管理对象 marketing_advertisement
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("marketing_advertisement")
public class MarketingAdvertisement extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.ASSIGN_ID)
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
     * 序号
     */
    private Long sortId;

    /**
     * 类型
     */
    private String type;

    /**
     * 名称
     */
    private String name;

    /**
     * 缩影图url
     */
    private String thumbnail;

    /**
     * 链接地址
     */
    private String link;

    /**
     * 状态 0 否 1 是
     */
    private Long status;




}
