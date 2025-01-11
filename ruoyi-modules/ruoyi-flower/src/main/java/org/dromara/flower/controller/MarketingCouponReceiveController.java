package org.dromara.flower.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.domain.bo.AppCouponRecordBo;
import org.dromara.flower.domain.bo.AppIsFlowerCouponsBo;
import org.dromara.flower.domain.bo.AppOrderConsumeBo;
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
import org.dromara.flower.domain.vo.MarketingCouponReceiveVo;
import org.dromara.flower.domain.bo.MarketingCouponReceiveBo;
import org.dromara.flower.service.IMarketingCouponReceiveService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 优惠卷领取记录
 *
 * @author chy
 * @date 2025-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/couponReceive")
public class MarketingCouponReceiveController extends BaseController {

    private final IMarketingCouponReceiveService marketingCouponReceiveService;

    /**
     * 查询优惠卷领取记录列表
     */
    @SaCheckPermission("flower:couponReceive:list")
    @GetMapping("/list")
    public TableDataInfo<MarketingCouponReceiveVo> list(MarketingCouponReceiveBo bo, PageQuery pageQuery) {
        return marketingCouponReceiveService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出优惠卷领取记录列表
     */
    @SaCheckPermission("flower:couponReceive:export")
    @Log(title = "优惠卷领取记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MarketingCouponReceiveBo bo, HttpServletResponse response) {
        List<MarketingCouponReceiveVo> list = marketingCouponReceiveService.queryList(bo);
        ExcelUtil.exportExcel(list, "优惠卷领取记录", MarketingCouponReceiveVo.class, response);
    }

    /**
     * 获取优惠卷领取记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:couponReceive:query")
    @GetMapping("/{id}")
    public R<MarketingCouponReceiveVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(marketingCouponReceiveService.queryById(id));
    }

    /**
     * 新增优惠卷领取记录
     */
    @SaCheckPermission("flower:couponReceive:add")
    @Log(title = "优惠卷领取记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MarketingCouponReceiveBo bo) {
        return toAjax(marketingCouponReceiveService.insertByBo(bo));
    }

    /**
     * 修改优惠卷领取记录
     */
    @SaCheckPermission("flower:couponReceive:edit")
    @Log(title = "优惠卷领取记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MarketingCouponReceiveBo bo) {
        return toAjax(marketingCouponReceiveService.updateByBo(bo));
    }

    /**
     * 删除优惠卷领取记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:couponReceive:remove")
    @Log(title = "优惠卷领取记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(marketingCouponReceiveService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * pc端，查询领取优惠券领取记录
     * 查询优惠卷领取记录列表,根据优惠券id
     */
    @SaCheckPermission("flower:couponReceive:list")
    @GetMapping("/list/{id}")
    public TableDataInfo<MarketingCouponReceiveVo> listByCouponId(@NotNull(message = "优惠券ID不能为空")
                                                                      @PathVariable Long id, PageQuery pageQuery) {
        return marketingCouponReceiveService.queryPageListByCouponId(id, pageQuery);
    }

    /**
     * 小程序用户，查询自己领取、已使用、已过期的优惠券
     */

    @SaCheckPermission("flower:couponReceive:list")
    @GetMapping("/userStateList")
    public List<MarketingCouponReceiveVo> userStateList(AppCouponRecordBo appCouponRecordBo){
        return marketingCouponReceiveService.queryUserStateList(appCouponRecordBo);

    }


    /**
     *当前用户查询该商品可用优惠卷
     * --订单使用优惠卷
     *
     */
    @SaCheckPermission("flower:couponReceive:list")
    @GetMapping("/userConsumeList")
    public List<MarketingCouponReceiveVo> userConsumeList(AppOrderConsumeBo appOrderConsumeBo){
        return marketingCouponReceiveService.queryUserConsumeList(appOrderConsumeBo);
    }


    /**
     * 查询该商品用户是否拥有 花劵
     * @param appIsFlowerCouponsBo
     * @return
     */
    @PutMapping("/isFlowerCoupons")
    public R<Void> isFlowerCoupons(AppIsFlowerCouponsBo appIsFlowerCouponsBo){
        return toAjax(marketingCouponReceiveService.isFlowerCoupons(appIsFlowerCouponsBo));
    }



















}
