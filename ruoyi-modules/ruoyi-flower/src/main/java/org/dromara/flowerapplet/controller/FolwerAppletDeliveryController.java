package org.dromara.flowerapplet.controller;

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
import org.dromara.flowerapplet.domain.vo.FolwerAppletDeliveryVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletDeliveryBo;
import org.dromara.flowerapplet.service.IFolwerAppletDeliveryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 物流公司
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/delivery")
public class FolwerAppletDeliveryController extends BaseController {

    private final IFolwerAppletDeliveryService folwerAppletDeliveryService;

    /**
     * 查询物流公司列表
     */
    @SaCheckPermission("flower:delivery:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletDeliveryVo> list(FolwerAppletDeliveryBo bo, PageQuery pageQuery) {
        return folwerAppletDeliveryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出物流公司列表
     */
    @SaCheckPermission("flower:delivery:export")
    @Log(title = "物流公司", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletDeliveryBo bo, HttpServletResponse response) {
        List<FolwerAppletDeliveryVo> list = folwerAppletDeliveryService.queryList(bo);
        ExcelUtil.exportExcel(list, "物流公司", FolwerAppletDeliveryVo.class, response);
    }

    /**
     * 获取物流公司详细信息
     *
     * @param dvyId 主键
     */
    @SaCheckPermission("flower:delivery:query")
    @GetMapping("/{dvyId}")
    public R<FolwerAppletDeliveryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long dvyId) {
        return R.ok(folwerAppletDeliveryService.queryById(dvyId));
    }

    /**
     * 新增物流公司
     */
    @SaCheckPermission("flower:delivery:add")
    @Log(title = "物流公司", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletDeliveryBo bo) {
        return toAjax(folwerAppletDeliveryService.insertByBo(bo));
    }

    /**
     * 修改物流公司
     */
    @SaCheckPermission("flower:delivery:edit")
    @Log(title = "物流公司", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletDeliveryBo bo) {
        return toAjax(folwerAppletDeliveryService.updateByBo(bo));
    }

    /**
     * 删除物流公司
     *
     * @param dvyIds 主键串
     */
    @SaCheckPermission("flower:delivery:remove")
    @Log(title = "物流公司", businessType = BusinessType.DELETE)
    @DeleteMapping("/{dvyIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] dvyIds) {
        return toAjax(folwerAppletDeliveryService.deleteWithValidByIds(List.of(dvyIds), true));
    }
}
