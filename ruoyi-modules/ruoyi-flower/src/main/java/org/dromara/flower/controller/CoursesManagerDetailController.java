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
import org.dromara.flower.domain.vo.CoursesManagerDetailVo;
import org.dromara.flower.domain.bo.CoursesManagerDetailBo;
import org.dromara.flower.service.ICoursesManagerDetailService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程管理-视频管理-课程详情(富文本)
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/managerDetail")
public class CoursesManagerDetailController extends BaseController {

    private final ICoursesManagerDetailService coursesManagerDetailService;

    /**
     * 查询课程管理-视频管理-课程详情(富文本)列表
     */
    @SaCheckPermission("flower:managerDetail:list")
    @GetMapping("/list")
    public TableDataInfo<CoursesManagerDetailVo> list(CoursesManagerDetailBo bo, PageQuery pageQuery) {
        return coursesManagerDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程管理-视频管理-课程详情(富文本)列表
     */
    @SaCheckPermission("flower:managerDetail:export")
    @Log(title = "课程管理-视频管理-课程详情(富文本)", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CoursesManagerDetailBo bo, HttpServletResponse response) {
        List<CoursesManagerDetailVo> list = coursesManagerDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程管理-视频管理-课程详情(富文本)", CoursesManagerDetailVo.class, response);
    }

    /**
     * 获取课程管理-视频管理-课程详情(富文本)详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:managerDetail:query")
    @GetMapping("/{id}")
    public R<CoursesManagerDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(coursesManagerDetailService.queryById(id));
    }

    /**
     * 新增课程管理-视频管理-课程详情(富文本)
     */
    @SaCheckPermission("flower:managerDetail:add")
    @Log(title = "课程管理-视频管理-课程详情(富文本)", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CoursesManagerDetailBo bo) {
        return toAjax(coursesManagerDetailService.insertByBo(bo));
    }

    /**
     * 修改课程管理-视频管理-课程详情(富文本)
     */
    @SaCheckPermission("flower:managerDetail:edit")
    @Log(title = "课程管理-视频管理-课程详情(富文本)", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CoursesManagerDetailBo bo) {
        return toAjax(coursesManagerDetailService.updateByBo(bo));
    }

    /**
     * 删除课程管理-视频管理-课程详情(富文本)
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:managerDetail:remove")
    @Log(title = "课程管理-视频管理-课程详情(富文本)", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(coursesManagerDetailService.deleteWithValidByIds(List.of(ids), true));
    }
}
