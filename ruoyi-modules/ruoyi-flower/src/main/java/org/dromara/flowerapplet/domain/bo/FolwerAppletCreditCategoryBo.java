package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletCreditCategory;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 积分商城产品类目业务对象 folwer_credit_category
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletCreditCategory.class, reverseConvertGenerate = false)
public class FolwerAppletCreditCategoryBo extends BaseEntity {

    /**
     * 主键
     */
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


}
