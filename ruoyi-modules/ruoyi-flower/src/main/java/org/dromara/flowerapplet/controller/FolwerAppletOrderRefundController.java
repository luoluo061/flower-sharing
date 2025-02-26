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
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderRefundVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderRefundBo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderRefundService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单退款
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/orderRefund")
public class FolwerAppletOrderRefundController extends BaseController {

    private final IFolwerAppletOrderRefundService folwerAppletOrderRefundService;

    /**
     * 查询订单退款列表
     */
    @SaCheckPermission("flower:orderRefund:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletOrderRefundVo> list(FolwerAppletOrderRefundBo bo, PageQuery pageQuery) {
        return folwerAppletOrderRefundService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单退款列表
     */
    @SaCheckPermission("flower:orderRefund:export")
    @Log(title = "订单退款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletOrderRefundBo bo, HttpServletResponse response) {
        List<FolwerAppletOrderRefundVo> list = folwerAppletOrderRefundService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单退款", FolwerAppletOrderRefundVo.class, response);
    }

    /**
     * 获取订单退款详细信息
     *
     * @param refundId 主键
     */
    @SaCheckPermission("flower:orderRefund:query")
    @GetMapping("/{refundId}")
    public R<FolwerAppletOrderRefundVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long refundId) {
        return R.ok(folwerAppletOrderRefundService.queryById(refundId));
    }

    /**
     * 新增订单退款
     */
    @SaCheckPermission("flower:orderRefund:add")
    @Log(title = "订单退款", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<String> add(@Validated(AddGroup.class) @RequestBody FolwerAppletOrderRefundBo bo) throws Exception {
        return R.ok(folwerAppletOrderRefundService.insertByBo(bo));
    }

    /**
     * 修改订单退款
     */
    @SaCheckPermission("flower:orderRefund:edit")
    @Log(title = "订单退款", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletOrderRefundBo bo) {
        return toAjax(folwerAppletOrderRefundService.updateByBo(bo));
    }

    /**
     * 删除订单退款
     *
     * @param refundIds 主键串
     */
    @SaCheckPermission("flower:orderRefund:remove")
    @Log(title = "订单退款", businessType = BusinessType.DELETE)
    @DeleteMapping("/{refundIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] refundIds) {
        return toAjax(folwerAppletOrderRefundService.deleteWithValidByIds(List.of(refundIds), true));
    }
}
