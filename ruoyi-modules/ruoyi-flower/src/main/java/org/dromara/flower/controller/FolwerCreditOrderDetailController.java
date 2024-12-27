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
import org.dromara.flower.domain.vo.FolwerCreditOrderDetailVo;
import org.dromara.flower.domain.bo.FolwerCreditOrderDetailBo;
import org.dromara.flower.service.IFolwerCreditOrderDetailService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分订单详细
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/creditOrderDetail")
public class FolwerCreditOrderDetailController extends BaseController {

    private final IFolwerCreditOrderDetailService folwerCreditOrderDetailService;

    /**
     * 查询积分订单详细列表
     */
    @SaCheckPermission("flower:creditOrderDetail:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerCreditOrderDetailVo> list(FolwerCreditOrderDetailBo bo, PageQuery pageQuery) {
        return folwerCreditOrderDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分订单详细列表
     */
    @SaCheckPermission("flower:creditOrderDetail:export")
    @Log(title = "积分订单详细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerCreditOrderDetailBo bo, HttpServletResponse response) {
        List<FolwerCreditOrderDetailVo> list = folwerCreditOrderDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分订单详细", FolwerCreditOrderDetailVo.class, response);
    }

    /**
     * 获取积分订单详细详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:creditOrderDetail:query")
    @GetMapping("/{id}")
    public R<FolwerCreditOrderDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerCreditOrderDetailService.queryById(id));
    }

    /**
     * 新增积分订单详细
     */
    @SaCheckPermission("flower:creditOrderDetail:add")
    @Log(title = "积分订单详细", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCreditOrderDetailBo bo) {
        return toAjax(folwerCreditOrderDetailService.insertByBo(bo));
    }

    /**
     * 修改积分订单详细
     */
    @SaCheckPermission("flower:creditOrderDetail:edit")
    @Log(title = "积分订单详细", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCreditOrderDetailBo bo) {
        return toAjax(folwerCreditOrderDetailService.updateByBo(bo));
    }

    /**
     * 删除积分订单详细
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:creditOrderDetail:remove")
    @Log(title = "积分订单详细", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerCreditOrderDetailService.deleteWithValidByIds(List.of(ids), true));
    }
}
