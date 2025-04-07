package org.dromara.flower.domain.vo;

import org.dromara.flower.domain.FolwerDeliveryTemplate;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


/**
 * 运费模板视图对象 folwer_delivery_template
 *
 * @author mlhxj
 * @date 2025-04-02
 */
@Data
@ExcelIgnoreUnannotated
@AutoMapper(target = FolwerDeliveryTemplate.class)
public class FolwerDeliveryTemplateVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @ExcelProperty(value = "ID")
    private Long tempId;

    /**
     * 父节点
     */
    @ExcelProperty(value = "父节点")
    private Long parentId;

    /**
     * 模板名称
     */
    @ExcelProperty(value = "模板名称")
    private String tempKey;

    /**
     * 模板值
     */
    @ExcelProperty(value = "模板值")
    private String tempValue;

    /**
     * 是否显示，1:使用，0:不使用
     */
    @ExcelProperty(value = "是否显示，1:使用，0:不使用")
    private Long status;

    /**
     * 子节点
     */
    @ExcelProperty(value = "子节点")
    private List<FolwerDeliveryTemplateVo> children;


}
