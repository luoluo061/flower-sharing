package org.dromara.flower.domain.vo;

import org.dromara.common.oss.factory.OssFactory;
import org.dromara.common.translation.annotation.Translation;
import org.dromara.common.translation.constant.TransConstant;
import org.dromara.flower.domain.FolwerCategory;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.excel.annotation.ExcelDictFormat;
import org.dromara.common.excel.convert.ExcelDictConvert;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;


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
     * 类目图标
     */
    @ExcelProperty(value = "类目图标URL")
    @Translation(type = TransConstant.OSS_ID_TO_URL, mapper = "icon")
    private String iconUrl;

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
     * 是否显示花艺课程
     */
    @ExcelProperty(value = "默认是0，表示不显示,1为花艺课程")
    private Long isShowFeature;

    /**
     * 二级分类
     */
    @ExcelProperty(value = "二级分类")
    private List<FolwerCategoryVo> children;


}
