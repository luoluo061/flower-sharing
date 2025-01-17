package org.dromara.flowerapplet.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.common.web.core.BaseController;
import org.dromara.flower.domain.bo.MemberPurchaseRecordBo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flower.service.IMemberPurchaseRecordService;
import org.dromara.flowerapplet.domain.PayParam;
import org.dromara.flowerapplet.service.IMemberAppletPurchaseRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序会员购买记录
 *
 * @author chzl
 * @date 2024-12-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerApplet/purchaseRecord")
public class MemberAppletPurchaseRecordController extends BaseController {

    private final IMemberAppletPurchaseRecordService memberPurchaseRecordService;

    /**
     * 查询会员购买记录列表
     */
    @SaCheckPermission("flower:purchaseRecord:list")
    @GetMapping("/list")
    public TableDataInfo<MemberPurchaseRecordVo> list(MemberPurchaseRecordBo bo, PageQuery pageQuery) {
        return memberPurchaseRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员购买记录列表
     */
    @SaCheckPermission("flower:purchaseRecord:export")
    @Log(title = "会员购买记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MemberPurchaseRecordBo bo, HttpServletResponse response) {
        List<MemberPurchaseRecordVo> list = memberPurchaseRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员购买记录", MemberPurchaseRecordVo.class, response);
    }

    /**
     * 获取会员购买记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:purchaseRecord:query")
    @GetMapping("/{id}")
    public R<MemberPurchaseRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(memberPurchaseRecordService.queryById(id));
    }

    /**
     * 新增会员购买记录
     */
    @SaCheckPermission("flower:purchaseRecord:add")
    @Log(title = "会员购买记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<MemberPurchaseRecordVo> add(@Validated(AddGroup.class) @RequestBody MemberPurchaseRecordBo bo) {
        return R.ok(memberPurchaseRecordService.insertByBo(bo));
    }

    /**
     * 修改会员购买记录
     */
    @SaCheckPermission("flower:purchaseRecord:edit")
    @Log(title = "会员购买记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MemberPurchaseRecordBo bo) {
        return toAjax(memberPurchaseRecordService.updateByBo(bo));
    }

    /**
     * 删除会员购买记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:purchaseRecord:remove")
    @Log(title = "会员购买记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(memberPurchaseRecordService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 支付成功后修改会员购买记录
     */
    @SaCheckPermission("flower:purchaseRecord:edit")
    @Log(title = "会员购买记录编辑(支付后)", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/payLater")
    public R<Void> payLaterEdit(@Validated(EditGroup.class) @RequestBody MemberPurchaseRecordBo bo) {
        return toAjax(memberPurchaseRecordService.payLaterUpdateByBo(bo));
    }

    /**
     * 购买会员支付
     */
    @SaCheckPermission("flower:purchaseRecord:submitOrders")
    @Log(title = "提交订单", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping("/submitOrder")
    public R<WxJsapiResponse> submitOrders(@RequestBody PayParam payParam) throws Exception {
        R<WxJsapiResponse> wxJsapiResponseR = memberPurchaseRecordService.submitOrders(payParam);
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
        return memberPurchaseRecordService.refundOrder(wxRefundRequest);
    }


}
