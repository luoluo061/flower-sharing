package org.dromara.flower.domain.bo;

import org.dromara.flower.domain.FolwerDelivery;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 物流公司业务对象 folwer_delivery
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerDelivery.class, reverseConvertGenerate = false)
public class FolwerDeliveryBo extends BaseEntity {

    /**
     * ID
     */
    private Long dvyId;

    /**
     * 配送公司名称
     */
    private String dvyName;

    /**
     * 配送方式  1:普通 2:冷链,  3:空运
     */
    private Long dvyType;

    /**
     * 付款类型(1:到付, 2:预付)
     */
    private Long isCod;

    /**
     * 备注
     */
    private String reamrk;

    /**
     * 排序
     */
    private Long seq;

    /**
     * 发货地址
     */
    private String dvyAddr;


}
