package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerDeliveryBox;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物流箱型业务对象 folwer_delivery_box
 *
 * @author mlhxj
 * @date 2025-03-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDeliveryBox.class, reverseConvertGenerate = false)
public class FolwerDeliveryBoxBo extends BaseEntity {

    /**
     * 主键id
     */
    private Long boxId;

    /**
     * 箱名
     */
    private String boxName;

    /**
     * 长
     */
    private Long length;

    /**
     * 宽
     */
    private Long width;

    /**
     * 高
     */
    private Long height;

    /**
     * 容积
     */
    private Long volume;

    /**
     * 成本价
     */
    private Long costPrice;

    /**
     * 打包费
     */
    private Long packagPrice;

    /**
     * 是否启用 1：启用 0：禁用
     */
    private Long status;


}
