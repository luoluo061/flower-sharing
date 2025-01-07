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
import org.dromara.flower.domain.vo.MarketingCouponVo;
import org.dromara.flower.domain.bo.MarketingCouponBo;
import org.dromara.flower.service.IMarketingCouponService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 优惠卷管理
 *
 * @author chy
 * @date 2025-01-08
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/coupon")
public class MarketingCouponController extends BaseController {

    private final IMarketingCouponService marketingCouponService;

    /**
     * 查询优惠卷管理列表
     */
    @SaCheckPermission("flower:coupon:list")
    @GetMapping("/list")
    public TableDataInfo<MarketingCouponVo> list(MarketingCouponBo bo, PageQuery pageQuery) {
        return marketingCouponService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出优惠卷管理列表
     */
    @SaCheckPermission("flower:coupon:export")
    @Log(title = "优惠卷管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MarketingCouponBo bo, HttpServletResponse response) {
        List<MarketingCouponVo> list = marketingCouponService.queryList(bo);
        ExcelUtil.exportExcel(list, "优惠卷管理", MarketingCouponVo.class, response);
    }

    /**
     * 获取优惠卷管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:coupon:query")
    @GetMapping("/{id}")
    public R<MarketingCouponVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(marketingCouponService.queryById(id));
    }

    /**
     * 新增优惠卷管理
     */
    @SaCheckPermission("flower:coupon:add")
    @Log(title = "优惠卷管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MarketingCouponBo bo) {
        return toAjax(marketingCouponService.insertByBo(bo));
    }

    /**
     * 修改优惠卷管理
     */
    @SaCheckPermission("flower:coupon:edit")
    @Log(title = "优惠卷管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MarketingCouponBo bo) {
        return toAjax(marketingCouponService.updateByBo(bo));
    }

    /**
     * 删除优惠卷管理
     *
     * @param ids 主键串
     */
/*    @SaCheckPermission("flower:coupon:remove")
    @Log(title = "优惠卷管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(marketingCouponService.deleteWithValidByIds(List.of(ids), true));
    }*/


    /**
     * 删除单个优惠券管理
     * @param id
     * @return
     */
    @SaCheckPermission("flower:coupon:remove")
    @Log(title = "优惠卷管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{id}")
    public R<Void> removeOne(@NotNull(message = "主键不能为空") @PathVariable Long id){
        return  toAjax(marketingCouponService.deleteOneById(id));
    }


    /**
     * 修改优惠卷状态
     * @return
     */
    @SaCheckPermission("flower:coupon:edit")
    @Log(title = "优惠卷管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("/{id}")
    public R<Void> editState(@NotNull(message = "主键不能为空") @PathVariable Long id){

        return toAjax(marketingCouponService.updateState(id));
    }







}
