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
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.service.IFolwerCategoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 产品类目
 *
 * @author Lion Li
 * @date 2024-12-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/category")
public class FolwerCategoryController extends BaseController {

    private final IFolwerCategoryService folwerCategoryService;

    /**
     * 查询产品类目列表
     */
    @SaCheckPermission("flower:category:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerCategoryVo> list(FolwerCategoryBo bo, PageQuery pageQuery) {
        return folwerCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询产品所有类目列表
     */
    @SaCheckPermission("flower:category:alllist")
    @GetMapping("/allList")
    public R<List<FolwerCategoryVo>> list() {
        FolwerCategoryBo bo = new FolwerCategoryBo();
        return R.ok(folwerCategoryService.queryList(bo));
    }


    /**
     * 导出产品类目列表
     */
    @SaCheckPermission("flower:category:export")
    @Log(title = "产品类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerCategoryBo bo, HttpServletResponse response) {
        List<FolwerCategoryVo> list = folwerCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "产品类目", FolwerCategoryVo.class, response);
    }

    /**
     * 获取产品类目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:category:query")
    @GetMapping("/{id}")
    public R<FolwerCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerCategoryService.queryById(id));
    }

    /**
     * 新增产品类目
     */
    @SaCheckPermission("flower:category:add")
    @Log(title = "产品类目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCategoryBo bo) {
        return toAjax(folwerCategoryService.insertByBo(bo));
    }

    /**
     * 修改产品类目
     */
    @SaCheckPermission("flower:category:edit")
    @Log(title = "产品类目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCategoryBo bo) {
        return toAjax(folwerCategoryService.updateByBo(bo));
    }

    /**
     * 删除产品类目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:category:remove")
    @Log(title = "产品类目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerCategoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
