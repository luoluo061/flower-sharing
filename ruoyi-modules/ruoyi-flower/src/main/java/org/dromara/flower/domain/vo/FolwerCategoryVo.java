package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerCategory;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;



/**
 * 产品类目视图对象 folwer_category
 *
 * @author Lion Li
 * @date 2024-12-20
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerCategory.class)
public class FolwerCategoryVo implements Serializable {

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
     * 排序
     */
    @ExcelProperty(value = "排序")
    private Integer seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    @ExcelProperty(value = "默认是1，表示正常状态,0为下线状态")
    private Integer status;

    /**
     * 部门id
     */
//    @ExcelProperty(value = "部门id")
//    private Long deptId;


}
