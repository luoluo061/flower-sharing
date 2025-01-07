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
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDetailBo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDetailService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单详细
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/orderDetail")
public class FolwerAppletOrderDetailController extends BaseController {

    private final IFolwerAppletOrderDetailService folwerAppletOrderDetailService;

    /**
     * 查询订单详细列表
     */
    @SaCheckPermission("flowerapplet:orderDetail:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletOrderDetailVo> list(FolwerAppletOrderDetailBo bo, PageQuery pageQuery) {
        return folwerAppletOrderDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单详细列表
     */
    @SaCheckPermission("flowerapplet:orderDetail:export")
    @Log(title = "订单详细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletOrderDetailBo bo, HttpServletResponse response) {
        List<FolwerAppletOrderDetailVo> list = folwerAppletOrderDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单详细", FolwerAppletOrderDetailVo.class, response);
    }

    /**
     * 获取订单详细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flowerapplet:orderDetail:query")
    @GetMapping("/{id}")
    public R<FolwerAppletOrderDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerAppletOrderDetailService.queryById(id));
    }

    /**
     * 新增订单详细
     */
    @SaCheckPermission("flowerapplet:orderDetail:add")
    @Log(title = "订单详细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletOrderDetailBo bo) {
        return toAjax(folwerAppletOrderDetailService.insertByBo(bo));
    }

    /**
     * 修改订单详细
     */
    @SaCheckPermission("flowerapplet:orderDetail:edit")
    @Log(title = "订单详细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletOrderDetailBo bo) {
        return toAjax(folwerAppletOrderDetailService.updateByBo(bo));
    }

    /**
     * 删除订单详细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flowerapplet:orderDetail:remove")
    @Log(title = "订单详细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerAppletOrderDetailService.deleteWithValidByIds(List.of(ids), true));
    }
}
