package org.dromara.flowerapplet.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuColorVo;
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
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 单品SKU
 *
 * @author mlhxj
 * @date 2025-01-16
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/sku")
public class FolwerAppletSkuController extends BaseController {

    private final IFolwerAppletSkuService folwerAppletSkuService;

    /**
     * 查询单品SKU列表
     */
    @SaCheckPermission("flower:sku:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletSkuVo> list(FolwerAppletSkuBo bo, PageQuery pageQuery) {
        return folwerAppletSkuService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询SKU颜色
     */
    @SaCheckPermission("flower:sku:queryColor")
    @GetMapping("/queryColor")
    @SaIgnore //忽略权限校验 小程序过审
    public R<List<FolwerAppletSkuColorVo>> queryColor(FolwerAppletSkuBo bo) {
        return R.ok(folwerAppletSkuService.queryByColor(bo));
    }

    /**
     * 查询SKU等级
     */
    @SaCheckPermission("flower:sku:queryLevel")
    @GetMapping("/queryLevel")
    @SaIgnore //忽略权限校验 小程序过审
    public R<List<FolwerAppletSkuColorVo>> queryLevel(FolwerAppletSkuBo bo) {
        return R.ok(folwerAppletSkuService.queryByLevel(bo));
    }

    /**
     * 导出单品SKU列表
     */
    @SaCheckPermission("flower:sku:export")
    @Log(title = "单品SKU", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletSkuBo bo, HttpServletResponse response) {
        List<FolwerAppletSkuVo> list = folwerAppletSkuService.queryList(bo);
        ExcelUtil.exportExcel(list, "单品SKU", FolwerAppletSkuVo.class, response);
    }

    /**
     * 获取单品SKU详细信息
     *
     * @param skuId 主键
     */
    @SaCheckPermission("flower:sku:query")
    @GetMapping("/{skuId}")
    public R<FolwerAppletSkuVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long skuId) {
        return R.ok(folwerAppletSkuService.queryById(skuId));
    }

    /**
     * 新增单品SKU
     */
    @SaCheckPermission("flower:sku:add")
    @Log(title = "单品SKU", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletSkuBo bo) {
        return toAjax(folwerAppletSkuService.insertByBo(bo));
    }

    /**
     * 修改单品SKU
     */
    @SaCheckPermission("flower:sku:edit")
    @Log(title = "单品SKU", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletSkuBo bo) {
        return toAjax(folwerAppletSkuService.updateByBo(bo));
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
        return toAjax(folwerAppletSkuService.deleteWithValidByIds(List.of(skuIds), true));
    }
}
