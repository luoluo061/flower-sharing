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
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.domain.bo.FolwerOrderBo;
import org.dromara.flower.service.IFolwerOrderService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/order")
public class FolwerOrderController extends BaseController {

    private final IFolwerOrderService folwerOrderService;

    /**
     * 查询订单列表
     */
    @SaCheckPermission("flower:order:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerOrderVo> list(FolwerOrderBo bo, PageQuery pageQuery) {
        return folwerOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单列表
     */
    @SaCheckPermission("flower:order:export")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerOrderBo bo, HttpServletResponse response) {
        List<FolwerOrderVo> list = folwerOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单", FolwerOrderVo.class, response);
    }

    /**
     * 获取订单详细信息
     *
     * @param orderId 主键
     */
    @SaCheckPermission("flower:order:query")
    @GetMapping("/{orderId}")
    public R<FolwerOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long orderId) {
        return R.ok(folwerOrderService.queryById(orderId));
    }

    /**
     * 新增订单
     */
    @SaCheckPermission("flower:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerOrderBo bo) {
        return toAjax(folwerOrderService.insertByBo(bo));
    }

    /**
     * 修改订单
     */
    @SaCheckPermission("flower:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerOrderBo bo) {
        return toAjax(folwerOrderService.updateByBo(bo));
    }

    /**
     * 删除订单
     *
     * @param orderIds 主键串
     */
    @SaCheckPermission("flower:order:remove")
    @Log(title = "订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] orderIds) {
        return toAjax(folwerOrderService.deleteWithValidByIds(List.of(orderIds), true));
    }
}
