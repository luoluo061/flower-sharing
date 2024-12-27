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
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.flower.service.IFolwerOrderRefundService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单退款
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/orderRefund")
public class FolwerOrderRefundController extends BaseController {

    private final IFolwerOrderRefundService folwerOrderRefundService;

    /**
     * 查询订单退款列表
     */
    @SaCheckPermission("flower:orderRefund:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerOrderRefundVo> list(FolwerOrderRefundBo bo, PageQuery pageQuery) {
        return folwerOrderRefundService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单退款列表
     */
    @SaCheckPermission("flower:orderRefund:export")
    @Log(title = "订单退款", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerOrderRefundBo bo, HttpServletResponse response) {
        List<FolwerOrderRefundVo> list = folwerOrderRefundService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单退款", FolwerOrderRefundVo.class, response);
    }

    /**
     * 获取订单退款详细信息
     *
     * @param refundId 主键
     */
    @SaCheckPermission("flower:orderRefund:query")
    @GetMapping("/{refundId}")
    public R<FolwerOrderRefundVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long refundId) {
        return R.ok(folwerOrderRefundService.queryById(refundId));
    }

    /**
     * 新增订单退款
     */
    @SaCheckPermission("flower:orderRefund:add")
    @Log(title = "订单退款", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerOrderRefundBo bo) {
        return toAjax(folwerOrderRefundService.insertByBo(bo));
    }

    /**
     * 修改订单退款
     */
    @SaCheckPermission("flower:orderRefund:edit")
    @Log(title = "订单退款", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerOrderRefundBo bo) {
        return toAjax(folwerOrderRefundService.updateByBo(bo));
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
        return toAjax(folwerOrderRefundService.deleteWithValidByIds(List.of(refundIds), true));
    }
}
