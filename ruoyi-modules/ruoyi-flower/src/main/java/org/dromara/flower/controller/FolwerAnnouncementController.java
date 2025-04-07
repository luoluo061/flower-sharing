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
import org.dromara.flower.domain.vo.FolwerAnnouncementVo;
import org.dromara.flower.domain.bo.FolwerAnnouncementBo;
import org.dromara.flower.service.IFolwerAnnouncementService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 公告
 *
 * @author mlhxj
 * @date 2025-03-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/announcement")
public class FolwerAnnouncementController extends BaseController {

    private final IFolwerAnnouncementService folwerAnnouncementService;

    /**
     * 查询公告列表
     */
    @SaCheckPermission("flower:announcement:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAnnouncementVo> list(FolwerAnnouncementBo bo, PageQuery pageQuery) {
        return folwerAnnouncementService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出公告列表
     */
    @SaCheckPermission("flower:announcement:export")
    @Log(title = "公告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAnnouncementBo bo, HttpServletResponse response) {
        List<FolwerAnnouncementVo> list = folwerAnnouncementService.queryList(bo);
        ExcelUtil.exportExcel(list, "公告", FolwerAnnouncementVo.class, response);
    }

    /**
     * 获取公告详细信息
     *
     * @param announcementId 主键
     */
    @SaCheckPermission("flower:announcement:query")
    @GetMapping("/{announcementId}")
    public R<FolwerAnnouncementVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long announcementId) {
        return R.ok(folwerAnnouncementService.queryById(announcementId));
    }

    /**
     * 新增公告
     */
    @SaCheckPermission("flower:announcement:add")
    @Log(title = "公告", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAnnouncementBo bo) {
        return toAjax(folwerAnnouncementService.insertByBo(bo));
    }

    /**
     * 修改公告
     */
    @SaCheckPermission("flower:announcement:edit")
    @Log(title = "公告", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAnnouncementBo bo) {
        return toAjax(folwerAnnouncementService.updateByBo(bo));
    }

    /**
     * 删除公告
     *
     * @param announcementIds 主键串
     */
    @SaCheckPermission("flower:announcement:remove")
    @Log(title = "公告", businessType = BusinessType.DELETE)
    @DeleteMapping("/{announcementIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] announcementIds) {
        return toAjax(folwerAnnouncementService.deleteWithValidByIds(List.of(announcementIds), true));
    }
}
