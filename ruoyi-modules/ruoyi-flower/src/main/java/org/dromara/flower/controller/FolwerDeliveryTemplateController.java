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
import org.dromara.flower.domain.vo.FolwerDeliveryTemplateVo;
import org.dromara.flower.domain.bo.FolwerDeliveryTemplateBo;
import org.dromara.flower.service.IFolwerDeliveryTemplateService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 运费模板
 *
 * @author mlhxj
 * @date 2025-04-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/deliveryTemplate")
public class FolwerDeliveryTemplateController extends BaseController {

    private final IFolwerDeliveryTemplateService folwerDeliveryTemplateService;

    /**
     * 查询运费模板列表
     */
    @SaCheckPermission("flower:deliveryTemplate:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerDeliveryTemplateVo> list(FolwerDeliveryTemplateBo bo, PageQuery pageQuery) {
        return folwerDeliveryTemplateService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出运费模板列表
     */
    @SaCheckPermission("flower:deliveryTemplate:export")
    @Log(title = "运费模板", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerDeliveryTemplateBo bo, HttpServletResponse response) {
        List<FolwerDeliveryTemplateVo> list = folwerDeliveryTemplateService.queryList(bo);
        ExcelUtil.exportExcel(list, "运费模板", FolwerDeliveryTemplateVo.class, response);
    }

    /**
     * 获取运费模板详细信息
     *
     * @param tempId 主键
     */
    @SaCheckPermission("flower:deliveryTemplate:query")
    @GetMapping("/{tempId}")
    public R<FolwerDeliveryTemplateVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long tempId) {
        return R.ok(folwerDeliveryTemplateService.queryById(tempId));
    }

    /**
     * 新增运费模板
     */
    @SaCheckPermission("flower:deliveryTemplate:add")
    @Log(title = "运费模板", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody List<FolwerDeliveryTemplateBo> bos) {
        return toAjax(folwerDeliveryTemplateService.insertByBo(bos));
    }

    /**
     * 修改运费模板
     */
    @SaCheckPermission("flower:deliveryTemplate:edit")
    @Log(title = "运费模板", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody List<FolwerDeliveryTemplateBo> bos) {
        return toAjax(folwerDeliveryTemplateService.updateByBo(bos));
    }

    /**
     * 删除运费模板
     *
     * @param tempIds 主键串
     */
    @SaCheckPermission("flower:deliveryTemplate:remove")
    @Log(title = "运费模板", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tempIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] tempIds) {
        return toAjax(folwerDeliveryTemplateService.deleteWithValidByIds(List.of(tempIds), true));
    }
}
