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
import org.dromara.flower.domain.vo.FolwerDeliveryRuleVo;
import org.dromara.flower.domain.bo.FolwerDeliveryRuleBo;
import org.dromara.flower.service.IFolwerDeliveryRuleService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 运费规则
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/deliveryRule")
public class FolwerDeliveryRuleController extends BaseController {

    private final IFolwerDeliveryRuleService folwerDeliveryRuleService;

    /**
     * 查询运费规则列表
     */
    @SaCheckPermission("flower:deliveryRule:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerDeliveryRuleVo> list(FolwerDeliveryRuleBo bo, PageQuery pageQuery) {
        return folwerDeliveryRuleService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出运费规则列表
     */
    @SaCheckPermission("flower:deliveryRule:export")
    @Log(title = "运费规则", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerDeliveryRuleBo bo, HttpServletResponse response) {
        List<FolwerDeliveryRuleVo> list = folwerDeliveryRuleService.queryList(bo);
        ExcelUtil.exportExcel(list, "运费规则", FolwerDeliveryRuleVo.class, response);
    }

    /**
     * 获取运费规则详细信息
     *
     * @param deliveryRuleId 主键
     */
    @SaCheckPermission("flower:deliveryRule:query")
    @GetMapping("/{deliveryRuleId}")
    public R<FolwerDeliveryRuleVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long deliveryRuleId) {
        return R.ok(folwerDeliveryRuleService.queryById(deliveryRuleId));
    }

    /**
     * 新增运费规则
     */
    @SaCheckPermission("flower:deliveryRule:add")
    @Log(title = "运费规则", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerDeliveryRuleBo bo) {
        return toAjax(folwerDeliveryRuleService.insertByBo(bo));
    }

    /**
     * 修改运费规则
     */
    @SaCheckPermission("flower:deliveryRule:edit")
    @Log(title = "运费规则", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerDeliveryRuleBo bo) {
        return toAjax(folwerDeliveryRuleService.updateByBo(bo));
    }

    /**
     * 删除运费规则
     *
     * @param deliveryRuleIds 主键串
     */
    @SaCheckPermission("flower:deliveryRule:remove")
    @Log(title = "运费规则", businessType = BusinessType.DELETE)
    @DeleteMapping("/{deliveryRuleIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] deliveryRuleIds) {
        return toAjax(folwerDeliveryRuleService.deleteWithValidByIds(List.of(deliveryRuleIds), true));
    }
}
