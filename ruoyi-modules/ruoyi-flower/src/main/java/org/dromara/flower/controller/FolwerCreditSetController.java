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
import org.dromara.flower.domain.vo.FolwerCreditSetVo;
import org.dromara.flower.domain.bo.FolwerCreditSetBo;
import org.dromara.flower.service.IFolwerCreditSetService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分配置
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/creditSet")
public class FolwerCreditSetController extends BaseController {

    private final IFolwerCreditSetService folwerCreditSetService;

    /**
     * 查询积分配置列表
     */
    @SaCheckPermission("flower:creditSet:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerCreditSetVo> list(FolwerCreditSetBo bo, PageQuery pageQuery) {
        return folwerCreditSetService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分配置列表
     */
    @SaCheckPermission("flower:creditSet:export")
    @Log(title = "积分配置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerCreditSetBo bo, HttpServletResponse response) {
        List<FolwerCreditSetVo> list = folwerCreditSetService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分配置", FolwerCreditSetVo.class, response);
    }

    /**
     * 获取积分配置详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:creditSet:query")
    @GetMapping("/{id}")
    public R<FolwerCreditSetVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerCreditSetService.queryById(id));
    }

    /**
     * 新增积分配置
     */
    @SaCheckPermission("flower:creditSet:add")
    @Log(title = "积分配置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCreditSetBo bo) {
        return toAjax(folwerCreditSetService.insertByBo(bo));
    }

    /**
     * 修改积分配置
     */
    @SaCheckPermission("flower:creditSet:edit")
    @Log(title = "积分配置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCreditSetBo bo) {
        return toAjax(folwerCreditSetService.updateByBo(bo));
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
        return toAjax(folwerCreditSetService.deleteWithValidByIds(List.of(ids), true));
    }
}
