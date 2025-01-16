package org.dromara.flowerapplet.controller;

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
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditProductVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditProductBo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditProductService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分商品管理
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/creditProduct")
public class FolwerAppletCreditProductController extends BaseController {

    private final IFolwerAppletCreditProductService folwerAppletCreditProductService;

    /**
     * 查询积分商品管理列表
     */
    @SaCheckPermission("flower:creditProduct:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletCreditProductVo> list(FolwerAppletCreditProductBo bo, PageQuery pageQuery) {
        return folwerAppletCreditProductService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分商品管理列表
     */
    @SaCheckPermission("flower:creditProduct:export")
    @Log(title = "积分商品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletCreditProductBo bo, HttpServletResponse response) {
        List<FolwerAppletCreditProductVo> list = folwerAppletCreditProductService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分商品管理", FolwerAppletCreditProductVo.class, response);
    }

    /**
     * 获取积分商品管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:creditProduct:query")
    @GetMapping("/{id}")
    public R<FolwerAppletCreditProductVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerAppletCreditProductService.queryById(id));
    }

    /**
     * 新增积分商品管理
     */
    @SaCheckPermission("flower:creditProduct:add")
    @Log(title = "积分商品管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletCreditProductBo bo) {
        return toAjax(folwerAppletCreditProductService.insertByBo(bo));
    }

    /**
     * 修改积分商品管理
     */
    @SaCheckPermission("flower:creditProduct:edit")
    @Log(title = "积分商品管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletCreditProductBo bo) {
        return toAjax(folwerAppletCreditProductService.updateByBo(bo));
    }

    /**
     * 删除积分商品管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:creditProduct:remove")
    @Log(title = "积分商品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerAppletCreditProductService.deleteWithValidByIds(List.of(ids), true));
    }
}
