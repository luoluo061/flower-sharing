package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerCreditCategory;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 积分商城产品类目视图对象 folwer_credit_category
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerCreditCategory.class)
public class FolwerCreditCategoryVo implements Serializable {

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
     * 类目图标
     */
    @ExcelProperty(value = "类目图标URL")
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
     * 二级分类
     */
    @ExcelProperty(value = "二级分类")
    private List<FolwerCreditCategoryVo> children;


}
