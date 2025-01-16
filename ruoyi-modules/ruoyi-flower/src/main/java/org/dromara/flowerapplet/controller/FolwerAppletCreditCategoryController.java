package org.dromara.flowerapplet.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
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
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditCategoryVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditCategoryBo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditCategoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分商城产品类目
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/creditCategory")
public class FolwerAppletCreditCategoryController extends BaseController {

    private final IFolwerAppletCreditCategoryService folwerAppletCreditCategoryService;

    /**
     * 查询积分商城产品类目列表
     */
    @SaCheckPermission("flower:creditCategory:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletCreditCategoryVo> list(FolwerAppletCreditCategoryBo bo, PageQuery pageQuery) {
        return folwerAppletCreditCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询所有积分商城产品类目列表
     */
    @SaCheckPermission("flower:creditCategory:alllist")
    @GetMapping("/alllist")
    public List<FolwerAppletCreditCategoryVo> allList() {
        FolwerAppletCreditCategoryBo bo = new FolwerAppletCreditCategoryBo();
        bo.setParentId(0L);
        return folwerAppletCreditCategoryService.queryList(bo);
    }

    /**
     * 导出积分商城产品类目列表
     */
    @SaCheckPermission("flower:creditCategory:export")
    @Log(title = "积分商城产品类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletCreditCategoryBo bo, HttpServletResponse response) {
        List<FolwerAppletCreditCategoryVo> list = folwerAppletCreditCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分商城产品类目", FolwerAppletCreditCategoryVo.class, response);
    }

    /**
     * 获取积分商城产品类目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:creditCategory:query")
    @GetMapping("/{id}")
    public R<FolwerAppletCreditCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerAppletCreditCategoryService.queryById(id));
    }

    /**
     * 新增积分商城产品类目
     */
    @SaCheckPermission("flower:creditCategory:add")
    @Log(title = "积分商城产品类目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletCreditCategoryBo bo) {
        return toAjax(folwerAppletCreditCategoryService.insertByBo(bo));
    }

    /**
     * 修改积分商城产品类目
     */
    @SaCheckPermission("flower:creditCategory:edit")
    @Log(title = "积分商城产品类目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletCreditCategoryBo bo) {
        return toAjax(folwerAppletCreditCategoryService.updateByBo(bo));
    }

    /**
     * 删除积分商城产品类目
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:creditCategory:remove")
    @Log(title = "积分商城产品类目", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerAppletCreditCategoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
