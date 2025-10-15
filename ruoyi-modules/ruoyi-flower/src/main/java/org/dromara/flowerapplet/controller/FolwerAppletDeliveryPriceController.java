package org.dromara.flowerapplet.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.annotation.Validated;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.web.core.BaseController;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.flowerapplet.domain.vo.FolwerAppletDeliveryPriceVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletDeliveryPriceBo;
import org.dromara.flowerapplet.service.IFolwerAppletDeliveryPriceService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 物流计费
 *
 * @author mlhxj
 * @date 2025-07-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/deliveryPrice")
public class FolwerAppletDeliveryPriceController extends BaseController {

    private final IFolwerAppletDeliveryPriceService folwerAppletDeliveryPriceService;

    /**
     * 查询物流计费列表
     */
    @SaCheckPermission(value = "flower:deliveryPrice:list", orRole = "appletuser")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletDeliveryPriceVo> list(FolwerAppletDeliveryPriceBo bo, PageQuery pageQuery) {
        return folwerAppletDeliveryPriceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出物流计费列表
     */
    @SaCheckPermission(value = "flower:deliveryPrice:export", orRole = "appletuser")
    @Log(title = "物流计费", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletDeliveryPriceBo bo, HttpServletResponse response) {
        List<FolwerAppletDeliveryPriceVo> list = folwerAppletDeliveryPriceService.queryList(bo);
        ExcelUtil.exportExcel(list, "物流计费", FolwerAppletDeliveryPriceVo.class, response);
    }

    /**
     * 获取物流计费详细信息
     *
     * @param logisticId 主键
     */
    @SaCheckPermission(value = "flower:deliveryPrice:query", orRole = "appletuser")
    @GetMapping("/{logisticId}")
    public R<FolwerAppletDeliveryPriceVo> getInfo(@NotNull(message = "主键不能为空")
                                                  @PathVariable Long logisticId) {
        return R.ok(folwerAppletDeliveryPriceService.queryById(logisticId));
    }

    /**
     * 新增物流计费
     */
    @SaCheckPermission(value = "flower:deliveryPrice:add", orRole = "appletuser")
    @Log(title = "物流计费", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletDeliveryPriceBo bo) {
        return toAjax(folwerAppletDeliveryPriceService.insertByBo(bo));
    }

    /**
     * 修改物流计费
     */
    @SaCheckPermission(value = "flower:deliveryPrice:edit", orRole = "appletuser")
    @Log(title = "物流计费", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletDeliveryPriceBo bo) {
        return toAjax(folwerAppletDeliveryPriceService.updateByBo(bo));
    }

    /**
     * 删除物流计费
     *
     * @param logisticIds 主键串
     */
    @SaCheckPermission(value = "flower:deliveryPrice:remove", orRole = "appletuser")
    @Log(title = "物流计费", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logisticIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] logisticIds) {
        return toAjax(folwerAppletDeliveryPriceService.deleteWithValidByIds(List.of(logisticIds), true));
    }

    /**
     * 物流计费
     *
     * @param deliveryId
     */
    @SaCheckPermission(value = "flower:deliveryPrice:getDeliveryPrice", orRole = "appletuser")
    @GetMapping("/getDeliveryPrice/{deliveryId}/{userId}/{skuId}/{skuByNum}")
    public R<BigDecimal> getDeliveryPrice(@PathVariable Long deliveryId, @PathVariable Long userId,
                                          @PathVariable Long skuId, @PathVariable Integer skuByNum) throws Exception {
        List<String> basketIds = new ArrayList<>();
        return R.ok(folwerAppletDeliveryPriceService.calculateFreight(deliveryId, userId, basketIds, skuId, skuByNum, 0));
    }
}
