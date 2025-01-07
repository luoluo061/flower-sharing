package org.dromara.flowerapplet.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.flower.domain.bo.CoursesManagerBo;
import org.dromara.flower.domain.vo.CoursesManagerVo;
import org.dromara.flower.service.ICoursesManagerService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 视频管理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerApplet/manager")
public class CoursesAppletManagerController extends BaseController {

    private final ICoursesManagerService coursesManagerService;

    /**
     * 查询视频管理列表
     */
    @SaCheckPermission("flower:manager:list")
    @GetMapping("/list")
    public TableDataInfo<CoursesManagerVo> list(CoursesManagerBo bo, PageQuery pageQuery) {
        return coursesManagerService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出视频管理列表
     */
    @SaCheckPermission("flower:manager:export")
    @Log(title = "视频管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CoursesManagerBo bo, HttpServletResponse response) {
        List<CoursesManagerVo> list = coursesManagerService.queryList(bo);
        ExcelUtil.exportExcel(list, "视频管理", CoursesManagerVo.class, response);
    }

    /**
     * 获取视频管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:manager:query")
    @GetMapping("/{id}")
    public R<CoursesManagerVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(coursesManagerService.queryById(id));
    }

    /**
     * 新增视频管理
     */
    @SaCheckPermission("flower:manager:add")
    @Log(title = "视频管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CoursesManagerBo bo) {
        return toAjax(coursesManagerService.insertByBo(bo));
    }

    /**
     * 修改视频管理
     */
    @SaCheckPermission("flower:manager:edit")
    @Log(title = "视频管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CoursesManagerBo bo) {
        return toAjax(coursesManagerService.updateByBo(bo));
    }

    /**
     * 删除视频管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:manager:remove")
    @Log(title = "视频管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(coursesManagerService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 修改课程上架下架状态
     *
     * @param ids 主键串
     */
    @Log(title = "视频管理", businessType = BusinessType.DELETE)
    @GetMapping("/coursesType/{ids}")
    public R<Void> editCoursesStatus(@NotEmpty(message = "主键不能为空")
                                         @PathVariable Long[] ids) {
        return toAjax(coursesManagerService.editCoursesStatus(ids));
    }

}
