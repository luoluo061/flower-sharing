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
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.domain.bo.MemberLevelPrivilegeBo;
import org.dromara.flower.service.IMemberLevelPrivilegeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员中心--会员等级--权益名称
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/levelPrivilege")
public class MemberLevelPrivilegeController extends BaseController {

    private final IMemberLevelPrivilegeService memberLevelPrivilegeService;

    /**
     * 查询会员中心--会员等级--权益名称列表
     */
    @SaCheckPermission("flower:levelPrivilege:list")
    @GetMapping("/list")
    public TableDataInfo<MemberLevelPrivilegeVo> list(MemberLevelPrivilegeBo bo, PageQuery pageQuery) {
        return memberLevelPrivilegeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员中心--会员等级--权益名称列表
     */
    @SaCheckPermission("flower:levelPrivilege:export")
    @Log(title = "会员中心--会员等级--权益名称", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MemberLevelPrivilegeBo bo, HttpServletResponse response) {
        List<MemberLevelPrivilegeVo> list = memberLevelPrivilegeService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员中心--会员等级--权益名称", MemberLevelPrivilegeVo.class, response);
    }

    /**
     * 获取会员中心--会员等级--权益名称详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:levelPrivilege:query")
    @GetMapping("/{id}")
    public R<MemberLevelPrivilegeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(memberLevelPrivilegeService.queryById(id));
    }

    /**
     * 新增会员中心--会员等级--权益名称
     */
    @SaCheckPermission("flower:levelPrivilege:add")
    @Log(title = "会员中心--会员等级--权益名称", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MemberLevelPrivilegeBo bo) {
        return toAjax(memberLevelPrivilegeService.insertByBo(bo));
    }

    /**
     * 修改会员中心--会员等级--权益名称
     */
    @SaCheckPermission("flower:levelPrivilege:edit")
    @Log(title = "会员中心--会员等级--权益名称", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MemberLevelPrivilegeBo bo) {
        return toAjax(memberLevelPrivilegeService.updateByBo(bo));
    }

    /**
     * 删除会员中心--会员等级--权益名称
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:levelPrivilege:remove")
    @Log(title = "会员中心--会员等级--权益名称", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(memberLevelPrivilegeService.deleteWithValidByIds(List.of(ids), true));
    }
}
