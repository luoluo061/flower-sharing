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
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditSetVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditSetBo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditSetService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分配置
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/creditSet")
public class FolwerAppletCreditSetController extends BaseController {

    private final IFolwerAppletCreditSetService folwerAppletCreditSetService;

    /**
     * 查询积分配置列表
     */
    @SaCheckPermission("flower:creditSet:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletCreditSetVo> list(FolwerAppletCreditSetBo bo, PageQuery pageQuery) {
        return folwerAppletCreditSetService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分配置列表
     */
    @SaCheckPermission("flower:creditSet:export")
    @Log(title = "积分配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletCreditSetBo bo, HttpServletResponse response) {
        List<FolwerAppletCreditSetVo> list = folwerAppletCreditSetService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分配置", FolwerAppletCreditSetVo.class, response);
    }

    /**
     * 获取积分配置详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:creditSet:query")
    @GetMapping("/{id}")
    public R<FolwerAppletCreditSetVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerAppletCreditSetService.queryById(id));
    }

    /**
     * 新增积分配置
     */
    @SaCheckPermission("flower:creditSet:add")
    @Log(title = "积分配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletCreditSetBo bo) {
        return toAjax(folwerAppletCreditSetService.insertByBo(bo));
    }

    /**
     * 修改积分配置
     */
    @SaCheckPermission("flower:creditSet:edit")
    @Log(title = "积分配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletCreditSetBo bo) {
        return toAjax(folwerAppletCreditSetService.updateByBo(bo));
    }

    /**
     * 删除积分配置
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:creditSet:remove")
    @Log(title = "积分配置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerAppletCreditSetService.deleteWithValidByIds(List.of(ids), true));
    }
}
