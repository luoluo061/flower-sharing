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
import org.dromara.flower.domain.bo.CoursesPurchaseRecordsBo;
import org.dromara.flower.domain.vo.CoursesPurchaseRecordsVo;
import org.dromara.flower.service.ICoursesPurchaseRecordsService;
import org.dromara.flowerapplet.service.ICoursesAppletPurchaseRecordsService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序课程管理-课程购买记录
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerApplet/purchaseRecords")
public class CoursesAppletPurchaseRecordsController extends BaseController {

    private final ICoursesAppletPurchaseRecordsService coursesPurchaseRecordsService;

    /**
     * 查询课程管理-课程购买记录列表
     */
    @SaCheckPermission("flower:purchaseRecords:list")
    @GetMapping("/list")
    public TableDataInfo<CoursesPurchaseRecordsVo> list(CoursesPurchaseRecordsBo bo, PageQuery pageQuery) {
        return coursesPurchaseRecordsService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出课程管理-课程购买记录列表
     */
    @SaCheckPermission("flower:purchaseRecords:export")
    @Log(title = "课程管理-课程购买记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(CoursesPurchaseRecordsBo bo, HttpServletResponse response) {
        List<CoursesPurchaseRecordsVo> list = coursesPurchaseRecordsService.queryList(bo);
        ExcelUtil.exportExcel(list, "课程管理-课程购买记录", CoursesPurchaseRecordsVo.class, response);
    }

    /**
     * 获取课程管理-课程购买记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:purchaseRecords:query")
    @GetMapping("/{id}")
    public R<CoursesPurchaseRecordsVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(coursesPurchaseRecordsService.queryById(id));
    }

    /**
     * 新增课程管理-课程购买记录
     */
    @SaCheckPermission("flower:purchaseRecords:add")
    @Log(title = "课程管理-课程购买记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody CoursesPurchaseRecordsBo bo) {
        return toAjax(coursesPurchaseRecordsService.insertByBo(bo));
    }

    /**
     * 修改课程管理-课程购买记录
     */
    @SaCheckPermission("flower:purchaseRecords:edit")
    @Log(title = "课程管理-课程购买记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody CoursesPurchaseRecordsBo bo) {
        return toAjax(coursesPurchaseRecordsService.updateByBo(bo));
    }

    /**
     * 删除课程管理-课程购买记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:purchaseRecords:remove")
    @Log(title = "课程管理-课程购买记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(coursesPurchaseRecordsService.deleteWithValidByIds(List.of(ids), true));
    }
}
