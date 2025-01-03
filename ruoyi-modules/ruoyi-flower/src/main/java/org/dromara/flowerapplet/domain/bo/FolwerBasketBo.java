package org.dromara.flowerapplet.domain.bo;

import org.dromara.flowerapplet.domain.FolwerBasket;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 小程序购物车业务对象 folwer_basket
 *
 * @author mlhxj
 * @date 2025-01-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerBasket.class, reverseConvertGenerate = false)
public class FolwerBasketBo extends BaseEntity {

    /**
     * 主键
     */
    private Long basketId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * SkuID
     */
    private Long skuId;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 购物车产品个数
     */
    private Long basketCount;

    /**
     * 购物时间
     */
    private Date basketDate;


}
