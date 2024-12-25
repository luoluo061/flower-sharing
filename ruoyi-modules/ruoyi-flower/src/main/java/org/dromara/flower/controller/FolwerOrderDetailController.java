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
import org.dromara.flower.domain.vo.FolwerOrderDetailVo;
import org.dromara.flower.domain.bo.FolwerOrderDetailBo;
import org.dromara.flower.service.IFolwerOrderDetailService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单详细
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/orderDetail")
public class FolwerOrderDetailController extends BaseController {

    private final IFolwerOrderDetailService folwerOrderDetailService;

    /**
     * 查询订单详细列表
     */
    @SaCheckPermission("flower:orderDetail:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerOrderDetailVo> list(FolwerOrderDetailBo bo, PageQuery pageQuery) {
        return folwerOrderDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单详细列表
     */
    @SaCheckPermission("flower:orderDetail:export")
    @Log(title = "订单详细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerOrderDetailBo bo, HttpServletResponse response) {
        List<FolwerOrderDetailVo> list = folwerOrderDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单详细", FolwerOrderDetailVo.class, response);
    }

    /**
     * 获取订单详细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:orderDetail:query")
    @GetMapping("/{id}")
    public R<FolwerOrderDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerOrderDetailService.queryById(id));
    }

    /**
     * 新增订单详细
     */
    @SaCheckPermission("flower:orderDetail:add")
    @Log(title = "订单详细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerOrderDetailBo bo) {
        return toAjax(folwerOrderDetailService.insertByBo(bo));
    }

    /**
     * 修改订单详细
     */
    @SaCheckPermission("flower:orderDetail:edit")
    @Log(title = "订单详细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerOrderDetailBo bo) {
        return toAjax(folwerOrderDetailService.updateByBo(bo));
    }

    /**
     * 删除订单详细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:orderDetail:remove")
    @Log(title = "订单详细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerOrderDetailService.deleteWithValidByIds(List.of(ids), true));
    }
}
