package org.dromara.flowerapplet.domain.vo;

import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flowerapplet.domain.FolwerAppletCategory;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;


/**
 * 小程序端产品类目视图对象 folwer_category
 *
 * @author Lion Li
 * @date 2025-01-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerAppletCategory.class)
public class FolwerAppletCategoryVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ExcelProperty(value = "主键")
    private Long id;

    /**
     * 父节点
     */
    @ExcelProperty(value = "父节点")
    private Long parentId;

    /**
     * 产品类目名称
     */
    @ExcelProperty(value = "产品类目名称")
    private String categoryName;

    /**
     * 类目图标
     */
    @ExcelProperty(value = "类目图标")
    private String icon;

    /**
     * 类目图标Url
     */
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "icon")
    private String iconUrl;
    /**
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Long seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    @ExcelProperty(value = "默认是1，表示正常状态,0为下线状态")
    private Long status;

    /**
     * 部门id
     */
    @ExcelProperty(value = "部门id")
    private Long deptId;


}
