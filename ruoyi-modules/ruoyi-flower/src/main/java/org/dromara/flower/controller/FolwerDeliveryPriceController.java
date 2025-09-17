package org.dromara.flower.controller;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.domain.FolwerDeliveryArea;
import org.dromara.flower.domain.FolwerDeliveryPriceAdd;
import org.dromara.flower.wrapper.DeliveryWrapper;
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
import org.dromara.flower.domain.vo.FolwerDeliveryPriceVo;
import org.dromara.flower.domain.bo.FolwerDeliveryPriceBo;
import org.dromara.flower.service.IFolwerDeliveryPriceService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 物流计费
 *
 * @author mlhxj
 * @date 2025-09-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/deliveryPrice")
public class FolwerDeliveryPriceController extends BaseController {

    private final IFolwerDeliveryPriceService folwerDeliveryPriceService;

    /**
     * 查询物流计费列表
     */
    @SaCheckPermission("flower:deliveryPrice:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerDeliveryPriceVo> list(FolwerDeliveryPriceBo bo, PageQuery pageQuery) {
        return folwerDeliveryPriceService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出物流计费列表
     */
    @SaCheckPermission("flower:deliveryPrice:export")
    @Log(title = "物流计费", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerDeliveryPriceBo bo, HttpServletResponse response) {
        List<FolwerDeliveryPriceVo> list = folwerDeliveryPriceService.queryList(bo);
        ExcelUtil.exportExcel(list, "物流计费", FolwerDeliveryPriceVo.class, response);
    }

    /**
     * 获取物流计费详细信息
     *
     * @param logisticId 主键
     */
    @SaCheckPermission("flower:deliveryPrice:query")
    @GetMapping("/{logisticId}")
    public R<FolwerDeliveryPriceVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long logisticId) {
        return R.ok(folwerDeliveryPriceService.queryById(logisticId));
    }

    /**
     * 新增物流计费
     */
    @SaCheckPermission("flower:deliveryPrice:add")
    @Log(title = "物流计费", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@RequestBody DeliveryWrapper wrapper) throws ExecutionException, InterruptedException {
        // 从包装类中获取两个对象
        FolwerDeliveryPriceAdd priceAdd = wrapper.getFolwerDeliveryPriceAdd();
        List<FolwerDeliveryArea> area = wrapper.getFolwerDeliveryArea();
        // 调用 CompletableFuture<Boolean> 类型的方法
        CompletableFuture<Boolean> completableResult = folwerDeliveryPriceService.insertByBo(priceAdd, area);
//        completableResult.thenAccept(result -> {
//            System.out.println("异步结果：" + result);
//        });
        return toAjax(completableResult.get());
    }

    /**
     * 修改物流计费
     */
    @SaCheckPermission("flower:deliveryPrice:edit")
    @Log(title = "物流计费", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody List<FolwerDeliveryPriceBo> bos) {
        return toAjax(folwerDeliveryPriceService.updateByBo(bos));
    }

    /**
     * 删除物流计费
     *
     * @param logisticIds 主键串
     */
    @SaCheckPermission("flower:deliveryPrice:remove")
    @Log(title = "物流计费", businessType = BusinessType.DELETE)
    @DeleteMapping("/{logisticIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] logisticIds) {
        return toAjax(folwerDeliveryPriceService.deleteWithValidByIds(List.of(logisticIds), true));
    }
}
