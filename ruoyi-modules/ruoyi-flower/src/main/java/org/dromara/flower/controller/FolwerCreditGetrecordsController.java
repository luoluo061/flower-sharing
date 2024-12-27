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
import org.dromara.flower.domain.vo.FolwerCreditGetrecordsVo;
import org.dromara.flower.domain.bo.FolwerCreditGetrecordsBo;
import org.dromara.flower.service.IFolwerCreditGetrecordsService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分获取记录
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/creditGetrecords")
public class FolwerCreditGetrecordsController extends BaseController {

    private final IFolwerCreditGetrecordsService folwerCreditGetrecordsService;

    /**
     * 查询积分获取记录列表
     */
    @SaCheckPermission("flower:creditGetrecords:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerCreditGetrecordsVo> list(FolwerCreditGetrecordsBo bo, PageQuery pageQuery) {
        return folwerCreditGetrecordsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分获取记录列表
     */
    @SaCheckPermission("flower:creditGetrecords:export")
    @Log(title = "积分获取记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerCreditGetrecordsBo bo, HttpServletResponse response) {
        List<FolwerCreditGetrecordsVo> list = folwerCreditGetrecordsService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分获取记录", FolwerCreditGetrecordsVo.class, response);
    }

    /**
     * 获取积分获取记录详细信息
     *
     * @param recordId 主键
     */
    @SaCheckPermission("flower:creditGetrecords:query")
    @GetMapping("/{recordId}")
    public R<FolwerCreditGetrecordsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long recordId) {
        return R.ok(folwerCreditGetrecordsService.queryById(recordId));
    }

    /**
     * 新增积分获取记录
     */
    @SaCheckPermission("flower:creditGetrecords:add")
    @Log(title = "积分获取记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCreditGetrecordsBo bo) {
        return toAjax(folwerCreditGetrecordsService.insertByBo(bo));
    }

    /**
     * 修改积分获取记录
     */
    @SaCheckPermission("flower:creditGetrecords:edit")
    @Log(title = "积分获取记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCreditGetrecordsBo bo) {
        return toAjax(folwerCreditGetrecordsService.updateByBo(bo));
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
        return toAjax(folwerCreditGetrecordsService.deleteWithValidByIds(List.of(recordIds), true));
    }
}
