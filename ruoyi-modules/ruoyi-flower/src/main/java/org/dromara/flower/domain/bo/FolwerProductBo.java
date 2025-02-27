package org.dromara.flower.domain.bo;

import com.alibaba.excel.annotation.ExcelProperty;
import org.dromara.common.json.handler.BigNumberSerializer;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.common.mybatis.core.domain.BaseEntity;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import io.github.linpeilie.annotations.AutoMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

/**
 * 商品管理业务对象 folwer_product
 *
 * @author Lion Li
 * @date 2024-12-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AutoMapper(target = FolwerProduct.class, reverseConvertGenerate = false)
public class FolwerProductBo extends BaseEntity {

    /**
     * 主键
     */
//    @NotNull(message = "主键不能为空", groups = { EditGroup.class })
    private Long id;

    /**
     * 商品名称
     */
//    @NotBlank(message = "商品名称不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productName;

    /**
     * 商品单位
     */
    private String unit;

    /**
     * 商品列表图
     */
//    @NotBlank(message = "商品列表图不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productListPictureUrl;

    /**
     * 商品轮播图
     */
//    @NotBlank(message = "商品列表图不能为空", groups = { AddGroup.class, EditGroup.class })
    private String productCarouselPictureUrl;

    /**
     * 商品分类
     */
//    @NotNull(message = "商品分类不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long categoryId;

    /**
     * 原价
     */
//    @NotNull(message = "原价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal oriPrice;

    /**
     * 划线价
     */
//    @NotNull(message = "划线价不能为空", groups = { AddGroup.class, EditGroup.class })
    private BigDecimal derlinePrice;

    /**
     * 规格类型 默认是0，表示单规格，1表示多规格
     */
//    @NotNull(message = "规格类型 默认是0，表示单规格，1表示多规格", groups = { AddGroup.class, EditGroup.class })
    private Long normsType;

    /**
     * 单品SKUID
     */
//    @NotNull(message = "单品SKUID", groups = { AddGroup.class, EditGroup.class })
    private Long skuId;



    /**
     * 规格图片
     */
//    @NotBlank(message = "商品列表图不能为空", groups = { AddGroup.class, EditGroup.class })
    private String normsPictureUrl;


    /**
     * 销量
     */
//    @NotNull(message = "销量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long soldNum;

    /**
     * 总库存
     */
//    @NotNull(message = "总库存不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long totalStocks;

    /**
     * 重量
     */
//    @NotNull(message = "重量", groups = { AddGroup.class, EditGroup.class })
    private String weight;

    /**
     * 配送方式 默认是1，表示物流配送, 0，商家配送
     */
//    @NotNull(message = "重量不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long deliveryMode;

    /**
     * 快递费
     */
//    @NotNull(message = "快递费", groups = { AddGroup.class, EditGroup.class })
    private String deliveryPrice;


    /**
     * 状态 默认是1，表示正常状态, -1表示删除, 0下架
     */
//    @NotNull(message = "状态 默认是1，表示正常状态, -1表示删除, 0下架不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long status;

    /**
     * 是否使用花券 默认是1，表示使用, 0，不使用
     */
//    @NotNull(message = "是否使用花券 默认是1，表示使用, 0，不使用", groups = { AddGroup.class, EditGroup.class })
    private Long isCoupon;

    /**
     * 是否为你推荐 默认是0，表示不推荐, 1，推荐
     */
    private Long isRecommend;


    /**
     * 是否支持退款1 是 2  否
     */
//    @NotNull(message = "是否支持退款1 是 2  否 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ifRefund;

    /**
     * 是否免费配送 1 是 2  否
     */
//    @NotNull(message = "是否免费配送 1 是 2  否 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ifFreeShipping;

    /**
     * 是否预警 1 是 2  否
     */
//    @NotNull(message = "是否预警 1 是 2  否 不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long ifEarlyWarning;

    /**
     * 库存预警值
     */
//    @NotNull(message = "库存预警值不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long inventoryEarlyWarningNum;

    /**
     * 库存预警比例
     */
//    @NotNull(message = "库存预警比例不能为空", groups = { AddGroup.class, EditGroup.class })
    private Long inventoryEarlyWarningProportion;

    /**
     * 商品评论
     */
//    @NotNull(message = "部门id不能为空", groups = { AddGroup.class, EditGroup.class })
    private String remarks;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;


}
