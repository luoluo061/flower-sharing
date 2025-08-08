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
import org.dromara.flower.domain.vo.FolwerDeliverySetVo;
import org.dromara.flower.domain.bo.FolwerDeliverySetBo;
import org.dromara.flower.service.IFolwerDeliverySetService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 物流设置
 *
 * @author mlhxj
 * @date 2025-08-01
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/deliverySet")
public class FolwerDeliverySetController extends BaseController {

    private final IFolwerDeliverySetService folwerDeliverySetService;

    /**
     * 查询物流设置列表
     */
    @SaCheckPermission("flower:deliverySet:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerDeliverySetVo> list(FolwerDeliverySetBo bo, PageQuery pageQuery) {
        return folwerDeliverySetService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出物流设置列表
     */
    @SaCheckPermission("flower:deliverySet:export")
    @Log(title = "物流设置", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerDeliverySetBo bo, HttpServletResponse response) {
        List<FolwerDeliverySetVo> list = folwerDeliverySetService.queryList(bo);
        ExcelUtil.exportExcel(list, "物流设置", FolwerDeliverySetVo.class, response);
    }

    /**
     * 获取物流设置详细信息
     *
     * @param deliverySetId 主键
     */
    @SaCheckPermission("flower:deliverySet:query")
    @GetMapping("/{deliverySetId}")
    public R<FolwerDeliverySetVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long deliverySetId) {
        return R.ok(folwerDeliverySetService.queryById(deliverySetId));
    }

    /**
     * 新增物流设置
     */
    @SaCheckPermission("flower:deliverySet:add")
    @Log(title = "物流设置", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerDeliverySetBo bo) {
        return toAjax(folwerDeliverySetService.insertByBo(bo));
    }

    /**
     * 修改物流设置
     */
    @SaCheckPermission("flower:deliverySet:edit")
    @Log(title = "物流设置", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerDeliverySetBo bo) {
        return toAjax(folwerDeliverySetService.updateByBo(bo));
    }

    /**
     * 删除物流设置
     *
     * @param deliverySetIds 主键串
     */
    @SaCheckPermission("flower:deliverySet:remove")
    @Log(title = "物流设置", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deliverySetIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] deliverySetIds) {
        return toAjax(folwerDeliverySetService.deleteWithValidByIds(List.of(deliverySetIds), true));
    }
}
