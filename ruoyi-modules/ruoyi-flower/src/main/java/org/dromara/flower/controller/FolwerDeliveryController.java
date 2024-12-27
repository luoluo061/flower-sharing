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
import org.dromara.flower.domain.vo.FolwerDeliveryVo;
import org.dromara.flower.domain.bo.FolwerDeliveryBo;
import org.dromara.flower.service.IFolwerDeliveryService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 物流公司
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/delivery")
public class FolwerDeliveryController extends BaseController {

    private final IFolwerDeliveryService folwerDeliveryService;

    /**
     * 查询物流公司列表
     */
    @SaCheckPermission("flower:delivery:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerDeliveryVo> list(FolwerDeliveryBo bo, PageQuery pageQuery) {
        return folwerDeliveryService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出物流公司列表
     */
    @SaCheckPermission("flower:delivery:export")
    @Log(title = "物流公司", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerDeliveryBo bo, HttpServletResponse response) {
        List<FolwerDeliveryVo> list = folwerDeliveryService.queryList(bo);
        ExcelUtil.exportExcel(list, "物流公司", FolwerDeliveryVo.class, response);
    }

    /**
     * 获取物流公司详细信息
     *
     * @param dvyId 主键
     */
    @SaCheckPermission("flower:delivery:query")
    @GetMapping("/{dvyId}")
    public R<FolwerDeliveryVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long dvyId) {
        return R.ok(folwerDeliveryService.queryById(dvyId));
    }

    /**
     * 新增物流公司
     */
    @SaCheckPermission("flower:delivery:add")
    @Log(title = "物流公司", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerDeliveryBo bo) {
        return toAjax(folwerDeliveryService.insertByBo(bo));
    }

    /**
     * 修改物流公司
     */
    @SaCheckPermission("flower:delivery:edit")
    @Log(title = "物流公司", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerDeliveryBo bo) {
        return toAjax(folwerDeliveryService.updateByBo(bo));
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
        return toAjax(folwerDeliveryService.deleteWithValidByIds(List.of(dvyIds), true));
    }
}
