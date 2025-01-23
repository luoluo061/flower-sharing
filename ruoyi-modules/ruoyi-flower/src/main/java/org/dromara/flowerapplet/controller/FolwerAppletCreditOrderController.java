package org.dromara.flowerapplet.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.flowerapplet.domain.PayParam;
import org.dromara.flowerapplet.domain.bo.OrderParamBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
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
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditOrderVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditOrderBo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditOrderService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分订单
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/creditOrder")
public class FolwerAppletCreditOrderController extends BaseController {

    private final IFolwerAppletCreditOrderService folwerAppletCreditOrderService;

    /**
     * 查询积分订单列表
     */
    @SaCheckPermission("flower:creditOrder:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletCreditOrderVo> list(FolwerAppletCreditOrderBo bo, PageQuery pageQuery) {
        return folwerAppletCreditOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分订单列表
     */
    @SaCheckPermission("flower:creditOrder:export")
    @Log(title = "积分订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletCreditOrderBo bo, HttpServletResponse response) {
        List<FolwerAppletCreditOrderVo> list = folwerAppletCreditOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分订单", FolwerAppletCreditOrderVo.class, response);
    }

    /**
     * 获取积分订单详细信息
     *
     * @param orderId 主键
     */
    @SaCheckPermission("flower:creditOrder:query")
    @GetMapping("/{orderId}")
    public R<FolwerAppletCreditOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long orderId) {
        return R.ok(folwerAppletCreditOrderService.queryById(orderId));
    }

    /**
     * 新增积分订单
     */
    @SaCheckPermission("flower:creditOrder:add")
    @Log(title = "积分订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<FolwerAppletCreditOrderVo> add(@Validated(AddGroup.class) @RequestBody OrderParamBo bo) throws Exception {
        return R.ok(folwerAppletCreditOrderService.insertByBo(bo));
    }

    /**
     * 修改积分订单
     */
    @SaCheckPermission("flower:creditOrder:edit")
    @Log(title = "积分订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletCreditOrderBo bo) {
        return toAjax(folwerAppletCreditOrderService.updateByBo(bo));
    }

    /**
     * 删除积分订单
     *
     * @param orderIds 主键串
     */
    @SaCheckPermission("flower:creditOrder:remove")
    @Log(title = "积分订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] orderIds) {
        return toAjax(folwerAppletCreditOrderService.deleteWithValidByIds(List.of(orderIds), true));
    }

    /**
     * 提交积分订单
     */
    @SaCheckPermission("flower:creditOrder:submitCreditOrders")
    @Log(title = "提交订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/submitCreditOrder")
    public R<WxJsapiResponse> submitCreditOrders(@RequestBody PayParam payParam) throws Exception {
        return folwerAppletCreditOrderService.submitOrders(payParam);
    }

    /**
     * 积分订单微信查询
     * @param orderCreditId
     * @return
     * @throws Exception
     */
    @SaCheckPermission("flower:creditOrder:queryOrder")
    @Log(title = "查询订单", businessType = BusinessType.EXPORT)
    @PostMapping("/queryCreditOrder/{orderCreditId}")
    public FolwerAppletCreditOrderVo queryCreditOrder(@NotNull(message = "主键不能为空")
                                          @PathVariable String orderCreditId) throws Exception {
        return folwerAppletCreditOrderService.queryCreditOrder(orderCreditId);
    }

}
