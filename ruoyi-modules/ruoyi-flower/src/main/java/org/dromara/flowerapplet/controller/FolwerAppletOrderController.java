package org.dromara.flowerapplet.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flowerapplet.domain.bo.OrderParamBo;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 订单
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/order")
public class FolwerAppletOrderController extends BaseController {

    private final IFolwerAppletOrderService folwerAppletOrderService;

    /**
     * 查询订单列表
     */
    @SaCheckPermission("flowerapplet:order:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletOrderVo> list(FolwerAppletOrderBo bo, PageQuery pageQuery) {
        return folwerAppletOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单列表
     */
    @SaCheckPermission("flowerapplet:order:export")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletOrderBo bo, HttpServletResponse response) {
        List<FolwerAppletOrderVo> list = folwerAppletOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "订单", FolwerAppletOrderVo.class, response);
    }

    /**
     * 获取订单详细信息
     *
     * @param orderId 主键
     */
    @SaCheckPermission("flowerapplet:order:query")
    @GetMapping("/{orderId}")
    public R<FolwerAppletOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long orderId) {
        return R.ok(folwerAppletOrderService.queryById(orderId));
    }

    /**
     * 新增订单
     */
    @SaCheckPermission("flowerapplet:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletOrderBo bo) {
        return toAjax(folwerAppletOrderService.insertByBo(bo));
    }

    /**
     * 修改订单
     */
    @SaCheckPermission("flowerapplet:order:edit")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletOrderBo bo) {
        return toAjax(folwerAppletOrderService.updateByBo(bo));
    }

    /**
     * 删除订单
     *
     * @param orderIds 主键串
     */
    @SaCheckPermission("flowerapplet:order:remove")
    @Log(title = "删除订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] orderIds) {
        return toAjax(folwerAppletOrderService.deleteWithValidByIds(List.of(orderIds), true));
    }

    /**
     * 新增订单
     */
    @SaCheckPermission("flowerapplet:order:createOrder")
    @Log(title = "创建订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/createOrder")
    public R<FolwerAppletOrderVo> createOrder(@Validated(AddGroup.class) @RequestBody OrderParamBo orderParam) throws Exception {
        FolwerAppletOrderVo order = folwerAppletOrderService.createOrder(orderParam);
        return R.ok(order);
    }

    /**
     * 提交订单
     */
    @SaCheckPermission("flowerapplet:order:createOrder")
    @Log(title = "提交订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/submit/{orderId}")
    public R<FolwerAppletOrderVo> submitOrders(@NotNull(message = "主键不能为空")
                                                   @PathVariable Long orderId) throws Exception {
        FolwerAppletOrderVo order = folwerAppletOrderService.submitOrders(orderId);
        return R.ok(order);
    }


}
