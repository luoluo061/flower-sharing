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
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditOrderDetailVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditOrderDetailBo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditOrderDetailService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分订单详细
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/creditOrderDetail")
public class FolwerAppletCreditOrderDetailController extends BaseController {

    private final IFolwerAppletCreditOrderDetailService folwerAppletCreditOrderDetailService;

    /**
     * 查询积分订单详细列表
     */
    @SaCheckPermission("flower:creditOrderDetail:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletCreditOrderDetailVo> list(FolwerAppletCreditOrderDetailBo bo, PageQuery pageQuery) {
        return folwerAppletCreditOrderDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分订单详细列表
     */
    @SaCheckPermission("flower:creditOrderDetail:export")
    @Log(title = "积分订单详细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletCreditOrderDetailBo bo, HttpServletResponse response) {
        List<FolwerAppletCreditOrderDetailVo> list = folwerAppletCreditOrderDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分订单详细", FolwerAppletCreditOrderDetailVo.class, response);
    }

    /**
     * 获取积分订单详细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:creditOrderDetail:query")
    @GetMapping("/{id}")
    public R<FolwerAppletCreditOrderDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerAppletCreditOrderDetailService.queryById(id));
    }

    /**
     * 新增积分订单详细
     */
    @SaCheckPermission("flower:creditOrderDetail:add")
    @Log(title = "积分订单详细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletCreditOrderDetailBo bo) {
        return toAjax(folwerAppletCreditOrderDetailService.insertByBo(bo));
    }

    /**
     * 修改积分订单详细
     */
    @SaCheckPermission("flower:creditOrderDetail:edit")
    @Log(title = "积分订单详细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletCreditOrderDetailBo bo) {
        return toAjax(folwerAppletCreditOrderDetailService.updateByBo(bo));
    }

    /**
     * 删除积分订单详细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:creditOrderDetail:remove")
    @Log(title = "积分订单详细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerAppletCreditOrderDetailService.deleteWithValidByIds(List.of(ids), true));
    }
}
