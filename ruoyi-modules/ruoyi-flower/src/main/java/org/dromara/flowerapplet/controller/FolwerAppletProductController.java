package org.dromara.flowerapplet.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
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
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 小程序端商品管理
 *
 * @author LL
 * @date 2024-12-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/product")
public class FolwerAppletProductController extends BaseController {

    private final IFolwerAppletProductService folwerAppletProductService;

    /**
     * 查询小程序端商品管理列表
     */
    @SaCheckPermission("flower:product:list")
    @GetMapping("/list")
    @SaIgnore //忽略权限校验 小程序过审
    public TableDataInfo<FolwerAppletProductVo> list(FolwerAppletProductBo bo, PageQuery pageQuery) {
        return folwerAppletProductService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出小程序端商品管理列表
     */
    @SaCheckPermission("flower:product:export")
    @Log(title = "小程序端商品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletProductBo bo, HttpServletResponse response) {
        List<FolwerAppletProductVo> list = folwerAppletProductService.queryList(bo);
        ExcelUtil.exportExcel(list, "小程序端商品管理", FolwerAppletProductVo.class, response);
    }

    /**
     * 获取小程序端商品管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:product:query")
    @GetMapping("/{id}")
    public R<FolwerAppletProductVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerAppletProductService.queryById(id));
    }

    /**
     * 新增小程序端商品管理
     */
    @SaCheckPermission("flower:product:add")
    @Log(title = "小程序端商品管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletProductBo bo) {
        return toAjax(folwerAppletProductService.insertByBo(bo));
    }

    /**
     * 修改小程序端商品管理
     */
    @SaCheckPermission("flower:product:edit")
    @Log(title = "小程序端商品管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletProductBo bo) {
        return toAjax(folwerAppletProductService.updateByBo(bo));
    }

    /**
     * 删除小程序端商品管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:product:remove")
    @Log(title = "小程序端商品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerAppletProductService.deleteWithValidByIds(List.of(ids), true));
    }
}
