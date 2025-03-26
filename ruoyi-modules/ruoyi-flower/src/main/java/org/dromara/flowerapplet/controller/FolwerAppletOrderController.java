package org.dromara.flowerapplet.controller;

import java.util.List;

import cn.dev33.satoken.exception.NotPermissionException;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.common.mypay.domain.WxPayRequest;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.flowerapplet.domain.PayParam;
import org.dromara.flowerapplet.domain.bo.OrderParamBo;
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
@RestControllerAdvice
public class FolwerAppletOrderController extends BaseController {

    private final IFolwerAppletOrderService folwerAppletOrderService;

    /**
     * 查询订单列表
     */
    @SaCheckPermission("flower:order:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletOrderVo> list(FolwerAppletOrderBo bo, PageQuery pageQuery) {
        return folwerAppletOrderService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出订单列表
     */
    @SaCheckPermission("flower:order:export")
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
    @SaCheckPermission("flower:order:query")
    @GetMapping("/{orderId}")
    public R<FolwerAppletOrderVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long orderId) {
        return R.ok(folwerAppletOrderService.queryById(orderId));
    }

    /**
     * 新增订单
     */
    @SaCheckPermission("flower:order:add")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    @ExceptionHandler(NotPermissionException.class)
    public R<String> add(@Validated(AddGroup.class) @RequestBody OrderParamBo bo) throws Exception {
        return folwerAppletOrderService.insertByBo(bo);
    }

    /**
     * 修改订单
     */
    @SaCheckPermission("flower:order:edit")
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
    @SaCheckPermission("flower:order:remove")
    @Log(title = "删除订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] orderIds) {
        return toAjax(folwerAppletOrderService.deleteWithValidByIds(List.of(orderIds), true));
    }

    /**
     * 新增订单
     */
//    @SaCheckPermission("flower:order:createOrder")
//    @Log(title = "创建订单", businessType = BusinessType.INSERT)
//    @RepeatSubmit()
//    @PostMapping("/createOrder")
//    public R<FolwerAppletOrderVo> createOrder(@Validated(AddGroup.class) @RequestBody OrderParamBo orderParam) throws Exception {
//        FolwerAppletOrderVo order = folwerAppletOrderService.createOrder(orderParam);
//        return R.ok(order);
//    }

//    /**
//     * 提交订单
//     */
//    @SaCheckPermission("flowerapplet:order:submitOrders")
//    @Log(title = "提交订单", businessType = BusinessType.INSERT)
//    @RepeatSubmit()
//    @PostMapping("/submitOrder")
//    public R<FolwerAppletOrderVo> submitOrders(@RequestBody WxPayRequest wxPayRequest) throws Exception {
//        FolwerAppletOrderVo order = folwerAppletOrderService.submitOrders(wxPayRequest);
//        return R.ok(order);
//    }

    /**
     * 提交订单
     */
    @SaCheckPermission("flower:order:submitOrders")
    @Log(title = "提交订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/submitOrder")
    public R<WxJsapiResponse> submitOrders(@RequestBody PayParam payParam) throws Exception {
        R<WxJsapiResponse> wxJsapiResponseR = folwerAppletOrderService.submitOrders(payParam);
        return wxJsapiResponseR;
    }

    /**
     * 退款
     */
    @SaCheckPermission("flower:order:refundOrder")
    @Log(title = "退款", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/refundOrder")
    public R<String> refundOrderFlowerApplet(@RequestBody WxRefundRequest wxRefundRequest) throws Exception {
        return folwerAppletOrderService.refundOrder(wxRefundRequest);
    }


    /**
     * 查询订单
     */
    @SaCheckPermission("flower:order:queryOrder")
    @Log(title = "查询订单", businessType = BusinessType.EXPORT)
    @PostMapping("/queryOrder/{orderId}")
    public FolwerAppletOrderVo queryOrder(@NotNull(message = "主键不能为空")
                                              @PathVariable String orderId) throws Exception {
        return folwerAppletOrderService.queryOrder(orderId);
    }




//    @SaCheckPermission("flowerapplet:order:submitOrder")
//    @Log(title = "微信JSAPI预下单", businessType = BusinessType.INSERT)
//    @RepeatSubmit()
//    @PostMapping(value = "/submitOrder")
//    public R<WxJsapiResponse> createWxOrder(@RequestBody @Validated WxPayRequest pay){
//        pay.setClientIp(IpUtils.getIpAddr());
//        try {
////            WxJsapiResponse value =(WxJsapiResponse)this.payService.createOrder(pay);
////            return R.ok("订单生成成功",value);
//        }catch (Exception ex){
//            return R.fail(HttpStatus.ERROR, ex.getLocalizedMessage());
//        }
//        return R.fail(HttpStatus.ERROR,"订单生成失败");
//    }


}
