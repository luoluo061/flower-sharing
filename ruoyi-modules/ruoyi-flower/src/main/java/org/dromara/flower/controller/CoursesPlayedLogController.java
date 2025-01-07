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
import org.dromara.flower.domain.vo.CoursesPlayedLogVo;
import org.dromara.flower.domain.bo.CoursesPlayedLogBo;
import org.dromara.flower.service.ICoursesPlayedLogService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程管理-视频播放XX
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/playedLog")
public class CoursesPlayedLogController extends BaseController {

    private final ICoursesPlayedLogService coursesPlayedLogService;

    /**
     * 查询课程管理-视频播放记录列表
     */
    @SaCheckPermission("flower:playedLog:list")
    @GetMapping("/list")
    public TableDataInfo<CoursesPlayedLogVo> list(CoursesPlayedLogBo bo, PageQuery pageQuery) {
        return coursesPlayedLogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程管理-视频播放记录列表
     */
    @SaCheckPermission("flower:playedLog:export")
    @Log(title = "课程管理-视频播放记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CoursesPlayedLogBo bo, HttpServletResponse response) {
        List<CoursesPlayedLogVo> list = coursesPlayedLogService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程管理-视频播放记录", CoursesPlayedLogVo.class, response);
    }

    /**
     * 获取课程管理-视频播放记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:playedLog:query")
    @GetMapping("/{id}")
    public R<CoursesPlayedLogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(coursesPlayedLogService.queryById(id));
    }

    /**
     * 新增课程管理-视频播放记录
     */
    @SaCheckPermission("flower:playedLog:add")
    @Log(title = "课程管理-视频播放记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CoursesPlayedLogBo bo) {
        return toAjax(coursesPlayedLogService.insertByBo(bo));
    }

    /**
     * 修改课程管理-视频播放记录
     */
    @SaCheckPermission("flower:playedLog:edit")
    @Log(title = "课程管理-视频播放记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CoursesPlayedLogBo bo) {
        return toAjax(coursesPlayedLogService.updateByBo(bo));
    }

    /**
     * 删除课程管理-视频播放记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:playedLog:remove")
    @Log(title = "课程管理-视频播放记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(coursesPlayedLogService.deleteWithValidByIds(List.of(ids), true));
    }
}
