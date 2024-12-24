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
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.bo.MemberLevelBo;
import org.dromara.flower.service.IMemberLevelService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员等级
 *
 * @author chzl
 * @date 2024-12-24
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/level")
public class MemberLevelController extends BaseController {

    private final IMemberLevelService memberLevelService;

    /**
     * 查询会员等级列表
     */
    @SaCheckPermission("flower:level:list")
    @GetMapping("/list")
    public TableDataInfo<MemberLevelVo> list(MemberLevelBo bo, PageQuery pageQuery) {
        return memberLevelService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员等级列表
     */
    @SaCheckPermission("flower:level:export")
    @Log(title = "会员等级", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MemberLevelBo bo, HttpServletResponse response) {
        List<MemberLevelVo> list = memberLevelService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员等级", MemberLevelVo.class, response);
    }

    /**
     * 获取会员等级详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:level:query")
    @GetMapping("/{id}")
    public R<MemberLevelVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(memberLevelService.queryById(id));
    }

    /**
     * 新增会员等级
     */
    @SaCheckPermission("flower:level:add")
    @Log(title = "会员等级", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MemberLevelBo bo) {
        return toAjax(memberLevelService.insertByBo(bo));
    }

    /**
     * 修改会员等级
     */
    @SaCheckPermission("flower:level:edit")
    @Log(title = "会员等级", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MemberLevelBo bo) {
        return toAjax(memberLevelService.updateByBo(bo));
    }

    /**
     * 删除会员等级
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:level:remove")
    @Log(title = "会员等级", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(memberLevelService.deleteWithValidByIds(List.of(ids), true));
    }
}
