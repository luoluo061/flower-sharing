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
import org.dromara.flower.domain.vo.FolwerCouponVo;
import org.dromara.flower.domain.bo.FolwerCouponBo;
import org.dromara.flower.service.IFolwerCouponService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 优惠券管理
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@Validated
@RequiredArgsConstructor
@RestController
//@RequestMapping("/flower/coupon")
public class FolwerCouponController extends BaseController {

    private final IFolwerCouponService folwerCouponService;

    /**
     * 查询优惠券管理列表
     */
    @SaCheckPermission("flower:coupon:list")
    //@GetMapping("/list")
    public TableDataInfo<FolwerCouponVo> list(FolwerCouponBo bo, PageQuery pageQuery) {
        return folwerCouponService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出优惠券管理列表
     */
    @SaCheckPermission("flower:coupon:export")
    @Log(title = "优惠券管理", businessType = BusinessType.EXPORT)
    //@PostMapping("/export")
    public void export(FolwerCouponBo bo, HttpServletResponse response) {
        List<FolwerCouponVo> list = folwerCouponService.queryList(bo);
        ExcelUtil.exportExcel(list, "优惠券管理", FolwerCouponVo.class, response);
    }

    /**
     * 获取优惠券管理详细信息
     *
     * @param couponId 主键
     */
    @SaCheckPermission("flower:coupon:query")
    //@GetMapping("/{couponId}")
    public R<FolwerCouponVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long couponId) {
        return R.ok(folwerCouponService.queryById(couponId));
    }

    /**
     * 新增优惠券管理
     */
    @SaCheckPermission("flower:coupon:add")
    @Log(title = "优惠券管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    //@PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCouponBo bo) {
        return toAjax(folwerCouponService.insertByBo(bo));
    }

    /**
     * 修改优惠券管理
     */
    @SaCheckPermission("flower:coupon:edit")
    @Log(title = "优惠券管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    //@PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCouponBo bo) {
        return toAjax(folwerCouponService.updateByBo(bo));
    }

    /**
     * 删除优惠券管理
     *
     * @param couponIds 主键串
     */
    @SaCheckPermission("flower:coupon:remove")
    @Log(title = "优惠券管理", businessType = BusinessType.DELETE)
    //@DeleteMapping("/{couponIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] couponIds) {
        return toAjax(folwerCouponService.deleteWithValidByIds(List.of(couponIds), true));
    }
}
