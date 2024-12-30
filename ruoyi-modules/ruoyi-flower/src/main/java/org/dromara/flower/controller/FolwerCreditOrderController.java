package org.dromara.flower.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.domain.vo.FolwerCreditOrderInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderRefundInfoVo;
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
import org.dromara.flower.domain.vo.FolwerCreditOrderVo;
import org.dromara.flower.domain.bo.FolwerCreditOrderBo;
import org.dromara.flower.service.IFolwerCreditOrderService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分订单
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/creditOrder")
public class FolwerCreditOrderController extends BaseController {

    private final IFolwerCreditOrderService folwerCreditOrderService;

    /**
     * 查询积分订单列表
     */
    @SaCheckPermission("flower:creditOrder:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerCreditOrderVo> list(FolwerCreditOrderBo bo, PageQuery pageQuery) {
        return folwerCreditOrderService.queryPageList(bo, pageQuery);
    }


    /**
     * 获取页面积分订单详细信息
     *
     * @param orderId 主键
     */
    @SaCheckPermission("flower:order:queryinfo")
    @GetMapping("/info/{orderId}")
    public R<FolwerCreditOrderInfoVo> getInfoById(@NotNull(message = "主键不能为空")
                                                  @PathVariable Long orderId) {
        return R.ok(folwerCreditOrderService.queryInfoById(orderId));
    }

    /**
     * 导出积分订单列表
     */
    @SaCheckPermission("flower:creditOrder:export")
    @Log(title = "积分订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerCreditOrderBo bo, HttpServletResponse response) {
        List<FolwerCreditOrderVo> list = folwerCreditOrderService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分订单", FolwerCreditOrderVo.class, response);
    }

    /**
     * 获取积分订单详细信息
     *
     * @param orderId 主键
     */
    @SaCheckPermission("flower:creditOrder:query")
    @GetMapping("/{orderId}")
    public R<FolwerCreditOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long orderId) {
        return R.ok(folwerCreditOrderService.queryById(orderId));
    }

    /**
     * 新增积分订单
     */
    @SaCheckPermission("flower:creditOrder:add")
    @Log(title = "积分订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCreditOrderBo bo) {
        return toAjax(folwerCreditOrderService.insertByBo(bo));
    }

    /**
     * 修改积分订单
     */
    @SaCheckPermission("flower:creditOrder:edit")
    @Log(title = "积分订单", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCreditOrderBo bo) {
        return toAjax(folwerCreditOrderService.updateByBo(bo));
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
        return toAjax(folwerCreditOrderService.deleteWithValidByIds(List.of(orderIds), true));
    }
}
