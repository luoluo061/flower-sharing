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
import org.dromara.flower.domain.vo.FolwerCouponReceiveVo;
import org.dromara.flower.domain.bo.FolwerCouponReceiveBo;
import org.dromara.flower.service.IFolwerCouponReceiveService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 优惠券领取记录
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/couponReceive")
public class FolwerCouponReceiveController extends BaseController {

    private final IFolwerCouponReceiveService folwerCouponReceiveService;

    /**
     * 查询优惠券领取记录列表
     */
    @SaCheckPermission("flower:couponReceive:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerCouponReceiveVo> list(FolwerCouponReceiveBo bo, PageQuery pageQuery) {
        return folwerCouponReceiveService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出优惠券领取记录列表
     */
    @SaCheckPermission("flower:couponReceive:export")
    @Log(title = "优惠券领取记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerCouponReceiveBo bo, HttpServletResponse response) {
        List<FolwerCouponReceiveVo> list = folwerCouponReceiveService.queryList(bo);
        ExcelUtil.exportExcel(list, "优惠券领取记录", FolwerCouponReceiveVo.class, response);
    }

    /**
     * 获取优惠券领取记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:couponReceive:query")
    @GetMapping("/{id}")
    public R<FolwerCouponReceiveVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerCouponReceiveService.queryById(id));
    }

    /**
     * 新增优惠券领取记录
     */
    @SaCheckPermission("flower:couponReceive:add")
    @Log(title = "优惠券领取记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCouponReceiveBo bo) {
        return toAjax(folwerCouponReceiveService.insertByBo(bo));
    }

    /**
     * 修改优惠券领取记录
     */
    @SaCheckPermission("flower:couponReceive:edit")
    @Log(title = "优惠券领取记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCouponReceiveBo bo) {
        return toAjax(folwerCouponReceiveService.updateByBo(bo));
    }

    /**
     * 删除优惠券领取记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:couponReceive:remove")
    @Log(title = "优惠券领取记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerCouponReceiveService.deleteWithValidByIds(List.of(ids), true));
    }
}
