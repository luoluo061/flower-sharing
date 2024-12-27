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
import org.dromara.flower.domain.vo.CoursesManagerVideoVo;
import org.dromara.flower.domain.bo.CoursesManagerVideoBo;
import org.dromara.flower.service.ICoursesManagerVideoService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程管理-视频管理-视频
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/managerVideo")
public class CoursesManagerVideoController extends BaseController {

    private final ICoursesManagerVideoService coursesManagerVideoService;

    /**
     * 查询课程管理-视频管理-视频列表
     */
    @SaCheckPermission("flower:managerVideo:list")
    @GetMapping("/list")
    public TableDataInfo<CoursesManagerVideoVo> list(CoursesManagerVideoBo bo, PageQuery pageQuery) {
        return coursesManagerVideoService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程管理-视频管理-视频列表
     */
    @SaCheckPermission("flower:managerVideo:export")
    @Log(title = "课程管理-视频管理-视频", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CoursesManagerVideoBo bo, HttpServletResponse response) {
        List<CoursesManagerVideoVo> list = coursesManagerVideoService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程管理-视频管理-视频", CoursesManagerVideoVo.class, response);
    }

    /**
     * 获取课程管理-视频管理-视频详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:managerVideo:query")
    @GetMapping("/{id}")
    public R<CoursesManagerVideoVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(coursesManagerVideoService.queryById(id));
    }

    /**
     * 新增课程管理-视频管理-视频
     */
    @SaCheckPermission("flower:managerVideo:add")
    @Log(title = "课程管理-视频管理-视频", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CoursesManagerVideoBo bo) {
        return toAjax(coursesManagerVideoService.insertByBo(bo));
    }

    /**
     * 修改课程管理-视频管理-视频
     */
    @SaCheckPermission("flower:managerVideo:edit")
    @Log(title = "课程管理-视频管理-视频", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CoursesManagerVideoBo bo) {
        return toAjax(coursesManagerVideoService.updateByBo(bo));
    }

    /**
     * 删除课程管理-视频管理-视频
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:managerVideo:remove")
    @Log(title = "课程管理-视频管理-视频", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(coursesManagerVideoService.deleteWithValidByIds(List.of(ids), true));
    }
}
