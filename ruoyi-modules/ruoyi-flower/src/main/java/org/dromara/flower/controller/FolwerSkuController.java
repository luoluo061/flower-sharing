package org.dromara.flower.controller;

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
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.domain.bo.FolwerSkuBo;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 单品SKU
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/sku")
public class FolwerSkuController extends BaseController {

    private final IFolwerSkuService folwerSkuService;

    /**
     * 查询单品SKU列表
     */
//    @SaCheckPermission("flower:sku:list")
//    @GetMapping("/list")
//    public TableDataInfo<FolwerSkuVo> list(FolwerSkuBo bo, PageQuery pageQuery) {
//        return folwerSkuService.queryPageList(bo, pageQuery);
//    }

    /**
     * 查询单品SKU列表
     */
    @SaCheckPermission("flower:sku:list")
    @GetMapping("/list")
    public R<List<FolwerSkuVo>> list(FolwerSkuBo bo) {
        return R.ok(folwerSkuService.queryList(bo));
    }

    /**
     * 导出单品SKU列表
     */
    @SaCheckPermission("flower:sku:export")
    @Log(title = "单品SKU", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerSkuBo bo, HttpServletResponse response) {
        List<FolwerSkuVo> list = folwerSkuService.queryList(bo);
        ExcelUtil.exportExcel(list, "单品SKU", FolwerSkuVo.class, response);
    }

    /**
     * 获取单品SKU详细信息
     *
     * @param skuId 主键
     */
    @SaCheckPermission("flower:sku:query")
    @GetMapping("/{skuId}")
    public R<FolwerSkuVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long skuId) {
        return R.ok(folwerSkuService.queryById(skuId));
    }

    /**
     * 新增单品SKU
     */
    @SaCheckPermission("flower:sku:add")
    @Log(title = "单品SKU", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerSkuBo bo) {
        return toAjax(folwerSkuService.insertByBo(bo));
    }

    /**
     * 批量新增单品SKU
     */
    @SaCheckPermission("flower:sku:batchadd")
    @Log(title = "批量单品SKU", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/batchAdd")
    public R<Void> batchAdd(@Validated(AddGroup.class) @RequestBody List<FolwerSkuBo> bo) {
        return toAjax(folwerSkuService.batchInsertByBo(bo));
    }

    /**
     * 修改单品SKU
     */
    @SaCheckPermission("flower:sku:edit")
    @Log(title = "单品SKU", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerSkuBo bo) {
        return toAjax(folwerSkuService.updateByBo(bo));
    }

    /**
     * 删除单品SKU
     *
     * @param skuIds 主键串
     */
    @SaCheckPermission("flower:sku:remove")
    @Log(title = "单品SKU", businessType = BusinessType.DELETE)
    @DeleteMapping("/{skuIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] skuIds) {
        return toAjax(folwerSkuService.deleteWithValidByIds(List.of(skuIds), true));
    }
}
