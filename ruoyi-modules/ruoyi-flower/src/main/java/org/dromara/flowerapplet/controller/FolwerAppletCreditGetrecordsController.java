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
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditGetrecordsVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditGetrecordsBo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditGetrecordsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分获取记录
 *
 * @author mlhxj
 * @date 2025-01-17
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/creditGetrecords")
public class FolwerAppletCreditGetrecordsController extends BaseController {

    private final IFolwerAppletCreditGetrecordsService folwerAppletCreditGetrecordsService;

    /**
     * 查询积分获取记录列表
     */
    @SaCheckPermission("flower:creditGetrecords:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletCreditGetrecordsVo> list(FolwerAppletCreditGetrecordsBo bo, PageQuery pageQuery) {
        return folwerAppletCreditGetrecordsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分获取记录列表
     */
    @SaCheckPermission("flower:creditGetrecords:export")
    @Log(title = "积分获取记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletCreditGetrecordsBo bo, HttpServletResponse response) {
        List<FolwerAppletCreditGetrecordsVo> list = folwerAppletCreditGetrecordsService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分获取记录", FolwerAppletCreditGetrecordsVo.class, response);
    }

    /**
     * 获取积分获取记录详细信息
     *
     * @param recordId 主键
     */
    @SaCheckPermission("flower:creditGetrecords:query")
    @GetMapping("/{recordId}")
    public R<FolwerAppletCreditGetrecordsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long recordId) {
        return R.ok(folwerAppletCreditGetrecordsService.queryById(recordId));
    }

    /**
     * 新增积分获取记录
     */
    @SaCheckPermission("flower:creditGetrecords:add")
    @Log(title = "积分获取记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletCreditGetrecordsBo bo) {
        return toAjax(folwerAppletCreditGetrecordsService.insertByBo(bo));
    }

    /**
     * 修改积分获取记录
     */
    @SaCheckPermission("flower:creditGetrecords:edit")
    @Log(title = "积分获取记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletCreditGetrecordsBo bo) {
        return toAjax(folwerAppletCreditGetrecordsService.updateByBo(bo));
    }

    /**
     * 删除积分获取记录
     *
     * @param recordIds 主键串
     */
    @SaCheckPermission("flower:creditGetrecords:remove")
    @Log(title = "积分获取记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{recordIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] recordIds) {
        return toAjax(folwerAppletCreditGetrecordsService.deleteWithValidByIds(List.of(recordIds), true));
    }
}
