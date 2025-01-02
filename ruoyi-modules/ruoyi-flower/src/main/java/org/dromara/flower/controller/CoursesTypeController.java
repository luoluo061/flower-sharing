package org.dromara.flower.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
import cn.hutool.core.lang.tree.Tree;
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
import org.dromara.flower.domain.vo.CoursesTypeVo;
import org.dromara.flower.domain.bo.CoursesTypeBo;
import org.dromara.flower.service.ICoursesTypeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 课程分类
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/type")
public class CoursesTypeController extends BaseController {

    private final ICoursesTypeService coursesTypeService;

    /**
     * 查询课程分类列表
     */
    @SaCheckPermission("flower:type:list")
    @GetMapping("/list")
    public TableDataInfo<CoursesTypeVo> list(CoursesTypeBo bo, PageQuery pageQuery) {
        return coursesTypeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程分类列表
     */
    @SaCheckPermission("flower:type:export")
    @Log(title = "课程分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CoursesTypeBo bo, HttpServletResponse response) {
        List<CoursesTypeVo> list = coursesTypeService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程分类", CoursesTypeVo.class, response);
    }

    /**
     * 获取课程分类详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:type:query")
    @GetMapping("/{id}")
    public R<CoursesTypeVo> getInfo(@NotNull(message = "主键不能为空")
                                    @PathVariable Long id) {
        return R.ok(coursesTypeService.queryById(id));
    }

    /**
     * 新增课程分类
     */
    @SaCheckPermission("flower:type:add")
    @Log(title = "课程分类", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CoursesTypeBo bo) {
        return toAjax(coursesTypeService.insertByBo(bo));
    }

    /**
     * 修改课程分类
     */
    @SaCheckPermission("flower:type:edit")
    @Log(title = "课程分类", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CoursesTypeBo bo) {
        return toAjax(coursesTypeService.updateByBo(bo));
    }

    /**
     * 删除课程分类
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:type:remove")
    @Log(title = "课程分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(coursesTypeService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 下拉类型树结构列表
     */
    @Log(title = "课程分类", businessType = BusinessType.DELETE)
    @GetMapping("/tree")
    public R<List<Tree<Long>>> getCoursesTypeTree() {
        return coursesTypeService.getCoursesTypeTree();
    }


}
