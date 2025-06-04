package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.flower.domain.FolwerSku;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * 单品SKU业务对象 folwer_sku
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerSku.class, reverseConvertGenerate = false)
public class FolwerSkuBo extends BaseEntity {

    /**
     * 单品ID
     */
    @NotNull(message = "单品ID不能为空", groups = { EditGroup.class })
    private Long skuId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 规格图ID
     */
    private String skuPicid;

    /**
     * 商品颜色
     */
    private String colour;

    /**
     * 数量
     */
    private String number;

    /**
     * 商品重量
     */
    private Double weight;

    /**
     * 商品尺寸
     */
    private String size;

    /**
     * 最大价格
     */
    private BigDecimal price;

    /**
     * 最小价格
     */
    private BigDecimal minPrice;

    /**
     * 库存
     */
    private Long actualStocks;

    /**
     * 0 禁用 1 启用
     */
    private Long status;

    /**
     * 箱型ID
     */
    private Long boxId;

    /**
     * 颜色
     */
    private String color;

    /**
     * 等级
     */
    private String level;


    /**
     * 颜色代码
     */
    private String colorCode;

    /**
     * 是否是基地 默认为0否，1:是
     */
    private Long isSource;

    /**
     * 基地名称
     */
    private String source;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
