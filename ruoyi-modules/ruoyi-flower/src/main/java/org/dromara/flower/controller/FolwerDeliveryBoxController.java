package org.dromara.flower.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
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
import org.dromara.flower.domain.vo.FolwerDeliveryBoxVo;
import org.dromara.flower.domain.bo.FolwerDeliveryBoxBo;
import org.dromara.flower.service.IFolwerDeliveryBoxService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 物流箱型
 *
 * @author mlhxj
 * @date 2025-03-29
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/deliveryBox")
public class FolwerDeliveryBoxController extends BaseController {

    private final IFolwerDeliveryBoxService folwerDeliveryBoxService;

    /**
     * 查询物流箱型列表
     */
    @SaCheckPermission("flower:deliveryBox:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerDeliveryBoxVo> list(FolwerDeliveryBoxBo bo, PageQuery pageQuery) {
        return folwerDeliveryBoxService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出物流箱型列表
     */
    @SaCheckPermission("flower:deliveryBox:export")
    @Log(title = "物流箱型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerDeliveryBoxBo bo, HttpServletResponse response) {
        List<FolwerDeliveryBoxVo> list = folwerDeliveryBoxService.queryList(bo);
        ExcelUtil.exportExcel(list, "物流箱型", FolwerDeliveryBoxVo.class, response);
    }

    /**
     * 获取物流箱型详细信息
     *
     * @param boxId 主键
     */
    @SaCheckPermission("flower:deliveryBox:query")
    @GetMapping("/{boxId}")
    public R<FolwerDeliveryBoxVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long boxId) {
        return R.ok(folwerDeliveryBoxService.queryById(boxId));
    }

    /**
     * 新增物流箱型
     */
    @SaCheckPermission("flower:deliveryBox:add")
    @Log(title = "物流箱型", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerDeliveryBoxBo bo) {
        return toAjax(folwerDeliveryBoxService.insertByBo(bo));
    }

    /**
     * 修改物流箱型
     */
    @SaCheckPermission("flower:deliveryBox:edit")
    @Log(title = "物流箱型", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerDeliveryBoxBo bo) {
        return toAjax(folwerDeliveryBoxService.updateByBo(bo));
    }

    /**
     * 删除物流箱型
     *
     * @param boxIds 主键串
     */
    @SaCheckPermission("flower:deliveryBox:remove")
    @Log(title = "物流箱型", businessType = BusinessType.DELETE)
    @DeleteMapping("/{boxIds}")
    public R<List<FolwerSkuVo>> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] boxIds) {
        return folwerDeliveryBoxService.deleteWithValidByIds(List.of(boxIds), true);
    }
}
