package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerProductDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

/**
 * 商品详情业务对象 folwer_product_detail
 *
 * @author mlhxj
 * @date 2025-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerProductDetail.class, reverseConvertGenerate = false)
public class FolwerProductDetailBo extends BaseEntity {

    /**
     * 详情ID
     */
    private Long detailId;

    /**
     * 商品ID
     */
    private Long skuId;

    /**
     * 规格详情
     */
    private String remarks;


}
