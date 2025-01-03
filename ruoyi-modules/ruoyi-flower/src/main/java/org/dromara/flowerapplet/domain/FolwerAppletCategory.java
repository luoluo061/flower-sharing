package org.dromara.flowerapplet.domain;

import org.dromara.common.tenant.core.TenantEntity;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 小程序端产品类目对象 folwer_category
 *
 * @author Lion Li
 * @date 2025-01-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("folwer_category")
public class FolwerAppletCategory extends TenantEntity {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id")
    private Long id;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 产品类目名称
     */
    private String categoryName;

    /**
     * 类目图标
     */
    private String icon;

    /**
     * 排序
     */
    private Long seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    private Long status;

    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 删除标志 0 否 1 是
     */
    @TableLogic
    private Long delFlag;


}
