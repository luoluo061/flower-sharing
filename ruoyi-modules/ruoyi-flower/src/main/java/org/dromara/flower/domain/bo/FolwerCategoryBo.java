package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerCategory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 产品类目业务对象 folwer_category
 *
 * @author Lion Li
 * @date 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerCategory.class, reverseConvertGenerate = false)
public class FolwerCategoryBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 父节点
     */
    @NotNull(message = "父节点不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long parentId;

    /**
     * 产品类目名称
     */
    @NotBlank(message = "产品类目名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String categoryName;

    /**
     * 类目图标
     */
//    @NotBlank(message = "类目图标不能为空", groups = { AddGroup.class, EditGroup.class })
    private String icon;

    /**
     * 排序
     */
    @NotNull(message = "排序不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    @NotNull(message = "默认是1，表示正常状态,0为下线状态不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 部门id
     */
//    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
//    private Long deptId;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
