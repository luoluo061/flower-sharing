package org.dromara.flowerapplet.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.dromara.flowerapplet.domain.vo.FolwerBasketVo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 小程序端购物车商品对象 folwer_product
 *
 * @author mlhxj
 */
@Data
public class FolwerShopCartItem implements Serializable {

    /**
     * 商品总金额
     */
    @ExcelProperty(value = "商品总金额")
    private Double productTotalAmount;

    /**
     * 购物车产品个数
     */
    @ExcelProperty(value = "购物车产品个数")
    private Long basketCount;

    /***
     * 购物车产品
     */
    @ExcelProperty(value = "购物车产品")
    private List<FolwerBasketVo> folwerBasketVos;

}
