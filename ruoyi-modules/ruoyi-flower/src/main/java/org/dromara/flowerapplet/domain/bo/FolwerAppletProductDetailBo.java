package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerAppletProductDetail;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品详情业务对象 folwer_product_detail
 *
 * @author mlhxj
 * @date 2025-08-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerAppletProductDetail.class, reverseConvertGenerate = false)
public class FolwerAppletProductDetailBo extends BaseEntity {

    /**
     * 详情ID
     */
    private Long detailId;

    /**
     * 规格ID
     */
    private Long skuId;

    /**
     * 规格详情
     */
    private String remarks;


}
