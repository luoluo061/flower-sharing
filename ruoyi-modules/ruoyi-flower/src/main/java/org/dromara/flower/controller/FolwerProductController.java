package org.dromara.flower.controller;

import java.util.List;

import cn.dev33.satoken.exception.NotPermissionException;
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
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.service.IFolwerProductService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 商品管理
 *
 * @author Lion Li
 * @date 2024-12-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/product")
@RestControllerAdvice
public class FolwerProductController extends BaseController {

    private final IFolwerProductService folwerProductService;

    /**
     * 查询商品管理列表
     */
    @SaCheckPermission("flower:product:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerProductVo> list(FolwerProductBo bo, PageQuery pageQuery) {
        return folwerProductService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询所有商品管理列表
     */
    @SaCheckPermission("flower:product:allList")
    @GetMapping("/allList")
    public R<List<FolwerProductVo>> allList() {
        FolwerProductBo bo = new FolwerProductBo();
        return R.ok(folwerProductService.queryList(bo));
    }

    /**
     * 导出商品管理列表
     */
    @SaCheckPermission("flower:product:export")
    @Log(title = "商品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerProductBo bo, HttpServletResponse response) {
        List<FolwerProductVo> list = folwerProductService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品管理", FolwerProductVo.class, response);
    }

    /**
     * 获取商品管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:product:query")
    @GetMapping("/{id}")
    public R<FolwerProductVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerProductService.queryById(id));
    }

    /**
     * 新增商品管理
     */
    @ExceptionHandler(NotPermissionException.class)
    @SaCheckPermission("flower:product:add")
    @Log(title = "商品管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerProductBo bo) {
        return toAjax(folwerProductService.insertByBo(bo));
    }

    /**
     * 修改商品管理
     */
    @SaCheckPermission("flower:product:edit")
    @Log(title = "商品管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerProductBo bo) {
        return toAjax(folwerProductService.updateByBo(bo));
    }

    /**
     * 删除商品管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:product:remove")
    @Log(title = "商品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerProductService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 批量修改商品状态
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:product:updatestatus")
    @Log(title = "商品管理", businessType = BusinessType.UPDATE)
    @PutMapping("batchUpdateStatus/{ids}")
    public R<Void> upDateStatus(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerProductService.updateStatusByIds(List.of(ids), true));
    }
}
