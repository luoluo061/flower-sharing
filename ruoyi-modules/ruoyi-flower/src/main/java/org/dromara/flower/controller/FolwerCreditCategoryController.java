package org.dromara.flower.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
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
import org.dromara.flower.domain.vo.FolwerCreditCategoryVo;
import org.dromara.flower.domain.bo.FolwerCreditCategoryBo;
import org.dromara.flower.service.IFolwerCreditCategoryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分商城产品类目
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/creditCategory")
public class FolwerCreditCategoryController extends BaseController {

    private final IFolwerCreditCategoryService folwerCreditCategoryService;

    /**
     * 查询积分商城产品类目列表
     */
    @SaCheckPermission("flower:creditCategory:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerCreditCategoryVo> list(FolwerCreditCategoryBo bo, PageQuery pageQuery) {
        bo.setParentId(0L);
        return folwerCreditCategoryService.queryPageList(bo, pageQuery);
    }

    /**
     * 查询产品所有类目列表
     */
    @SaCheckPermission("flower:creditCategory:alllist")
    @GetMapping("/allList")
    public R<List<FolwerCreditCategoryVo>> list() {
        FolwerCreditCategoryBo bo = new FolwerCreditCategoryBo();
        bo.setParentId(0L);
        return R.ok(folwerCreditCategoryService.queryList(bo));
    }

    /**
     * 导出积分商城产品类目列表
     */
    @SaCheckPermission("flower:creditCategory:export")
    @Log(title = "积分商城产品类目", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerCreditCategoryBo bo, HttpServletResponse response) {
        List<FolwerCreditCategoryVo> list = folwerCreditCategoryService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分商城产品类目", FolwerCreditCategoryVo.class, response);
    }

    /**
     * 获取积分商城产品类目详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:creditCategory:query")
    @GetMapping("/{id}")
    public R<FolwerCreditCategoryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerCreditCategoryService.queryById(id));
    }

    /**
     * 新增积分商城产品类目
     */
    @SaCheckPermission("flower:creditCategory:add")
    @Log(title = "积分商城产品类目", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCreditCategoryBo bo) {
        return toAjax(folwerCreditCategoryService.insertByBo(bo));
    }

    /**
     * 修改积分商城产品类目
     */
    @SaCheckPermission("flower:creditCategory:edit")
    @Log(title = "积分商城产品类目", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCreditCategoryBo bo) {
        return toAjax(folwerCreditCategoryService.updateByBo(bo));
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
        return toAjax(folwerCreditCategoryService.deleteWithValidByIds(List.of(ids), true));
    }
}
