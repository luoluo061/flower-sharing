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
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDvyVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDvyBo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDvyService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单物流
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/orderDvy")
public class FolwerAppletOrderDvyController extends BaseController {

    private final IFolwerAppletOrderDvyService folwerAppletOrderDvyService;

    /**
     * 查询订单物流列表
     */
    @SaCheckPermission("flower:orderDvy:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletOrderDvyVo> list(FolwerAppletOrderDvyBo bo, PageQuery pageQuery) {
        return folwerAppletOrderDvyService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单物流列表
     */
    @SaCheckPermission("flower:orderDvy:export")
    @Log(title = "订单物流", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletOrderDvyBo bo, HttpServletResponse response) {
        List<FolwerAppletOrderDvyVo> list = folwerAppletOrderDvyService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单物流", FolwerAppletOrderDvyVo.class, response);
    }

    /**
     * 获取订单物流详细信息
     *
     * @param orderDevId 主键
     */
    @SaCheckPermission("flower:orderDvy:query")
    @GetMapping("/{orderDevId}")
    public R<FolwerAppletOrderDvyVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long orderDevId) {
        return R.ok(folwerAppletOrderDvyService.queryById(orderDevId));
    }

    /**
     * 新增订单物流
     */
    @SaCheckPermission("flower:orderDvy:add")
    @Log(title = "订单物流", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<FolwerAppletOrderDvyVo> add(@Validated(AddGroup.class) @RequestBody FolwerAppletOrderDvyBo bo) throws Exception {
        return R.ok(folwerAppletOrderDvyService.insertByBo(bo));
    }

    /**
     * 修改订单物流
     */
    @SaCheckPermission("flower:orderDvy:edit")
    @Log(title = "订单物流", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<FolwerAppletOrderDvyVo> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletOrderDvyBo bo) throws Exception {
        return R.ok(folwerAppletOrderDvyService.updateByBo(bo));
    }

    /**
     * 删除订单物流
     *
     * @param orderDevIds 主键串
     */
    @SaCheckPermission("flower:orderDvy:remove")
    @Log(title = "订单物流", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderDevIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] orderDevIds) {
        return toAjax(folwerAppletOrderDvyService.deleteWithValidByIds(List.of(orderDevIds), true));
    }
}
