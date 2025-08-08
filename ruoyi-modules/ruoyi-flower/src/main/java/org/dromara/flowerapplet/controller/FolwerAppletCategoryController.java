package org.dromara.flowerapplet.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCategoryVo;
import org.dromara.flowerapplet.service.IFolwerAppletCategoryService;
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
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 小程序端产品类目
 *
 * @author Lion Li
 * @date 2025-01-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/category")
public class FolwerAppletCategoryController extends BaseController {

    private final IFolwerAppletCategoryService folwerCategoryService;

    /**
     * 查询小程序端产品类目列表
     */
    @SaCheckPermission("flower:category:list")
    @GetMapping("/list")
    @SaIgnore //忽略权限校验 小程序过审
    public TableDataInfo<FolwerAppletCategoryVo> list(FolwerAppletCategoryBo bo, PageQuery pageQuery) {
        return folwerCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询产品所有类目列表
     */
    @SaCheckPermission("flower:category:alllist")
    @GetMapping("/allList")
    @SaIgnore
    public R<List<FolwerAppletCategoryVo>> getAllList(FolwerAppletCategoryBo bo) {
//        FolwerAppletCategoryBo bo = new FolwerAppletCategoryBo();
        bo.setParentId(0L);
        return R.ok(folwerCategoryService.queryList(bo));
    }

    /**
     * 导出小程序端产品类目列表
     */
    @SaCheckPermission("flower:category:export")
    @Log(title = "小程序端产品类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletCategoryBo bo, HttpServletResponse response) {
        List<FolwerAppletCategoryVo> list = folwerCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "小程序端产品类目", FolwerAppletCategoryVo.class, response);
    }

    /**
     * 获取小程序端产品类目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:category:query")
    @GetMapping("/{id}")
    @SaIgnore
    public R<FolwerAppletCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerCategoryService.queryById(id));
    }

    /**
     * 新增小程序端产品类目
     */
    @SaCheckPermission("flower:category:add")
    @Log(title = "小程序端产品类目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletCategoryBo bo) {
        return toAjax(folwerCategoryService.insertByBo(bo));
    }

    /**
     * 修改小程序端产品类目
     */
    @SaCheckPermission("flower:category:edit")
    @Log(title = "小程序端产品类目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletCategoryBo bo) {
        return toAjax(folwerCategoryService.updateByBo(bo));
    }

    /**
     * 删除小程序端产品类目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:category:remove")
    @Log(title = "小程序端产品类目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerCategoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
