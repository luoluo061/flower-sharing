package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.MarketingAdvertisement;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 广告管理业务对象 marketing_advertisement
 *
 * @author chy
 * @date 2024-12-31
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = MarketingAdvertisement.class, reverseConvertGenerate = false)
public class MarketingAdvertisementBo extends BaseEntity {

    /**
     * 主键
     */
    @NotNull(message = "主键不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long id;

    /**
     * 部门id
     */
    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deptId;

    /**
     * 序号
     */
    @NotNull(message = "序号不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long sortId;

    /**
     * 类型
     */
    @NotBlank(message = "类型不能为空", groups = { AddGroup.class, EditGroup.class })
    private String type;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String name;

    /**
     * 缩影图url
     */
    @NotBlank(message = "缩影图url不能为空", groups = { AddGroup.class, EditGroup.class })
    private String thumbnail;

    /**
     * 链接地址
     */
    @NotBlank(message = "链接地址不能为空", groups = { AddGroup.class, EditGroup.class })
    private String link;

    /**
     * 状态 0 否 1 是
     */
    @NotNull(message = "状态 0 否 1 是不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;


}
