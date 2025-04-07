package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerDeliveryTemplate;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 运费模板业务对象 folwer_delivery_template
 *
 * @author mlhxj
 * @date 2025-04-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDeliveryTemplate.class, reverseConvertGenerate = false)
public class FolwerDeliveryTemplateBo extends BaseEntity {

    /**
     * ID
     */
    private Long tempId;

    /**
     * 父节点
     */
    private Long parentId;

    /**
     * 模板名称
     */
    private String tempKey;

    /**
     * 模板值
     */
    private String tempValue;

    /**
     * 是否显示，1:使用，0:不使用
     */
    private Long status;


}
