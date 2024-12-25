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
import org.dromara.flower.domain.vo.FolwerOrderSetVo;
import org.dromara.flower.domain.bo.FolwerOrderSetBo;
import org.dromara.flower.service.IFolwerOrderSetService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单设置
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/orderSet")
public class FolwerOrderSetController extends BaseController {

    private final IFolwerOrderSetService folwerOrderSetService;

    /**
     * 查询订单设置列表
     */
    @SaCheckPermission("flower:orderSet:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerOrderSetVo> list(FolwerOrderSetBo bo, PageQuery pageQuery) {
        return folwerOrderSetService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单设置列表
     */
    @SaCheckPermission("flower:orderSet:export")
    @Log(title = "订单设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerOrderSetBo bo, HttpServletResponse response) {
        List<FolwerOrderSetVo> list = folwerOrderSetService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单设置", FolwerOrderSetVo.class, response);
    }

    /**
     * 获取订单设置详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:orderSet:query")
    @GetMapping("/{id}")
    public R<FolwerOrderSetVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerOrderSetService.queryById(id));
    }

    /**
     * 新增订单设置
     */
    @SaCheckPermission("flower:orderSet:add")
    @Log(title = "订单设置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerOrderSetBo bo) {
        return toAjax(folwerOrderSetService.insertByBo(bo));
    }

    /**
     * 修改订单设置
     */
    @SaCheckPermission("flower:orderSet:edit")
    @Log(title = "订单设置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerOrderSetBo bo) {
        return toAjax(folwerOrderSetService.updateByBo(bo));
    }

    /**
     * 删除订单设置
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:orderSet:remove")
    @Log(title = "订单设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerOrderSetService.deleteWithValidByIds(List.of(ids), true));
    }
}
