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
import org.dromara.flower.domain.vo.OneselfMemberLevelPrivilegeVo;
import org.dromara.flower.domain.bo.OneselfMemberLevelPrivilegeBo;
import org.dromara.flower.service.IOneselfMemberLevelPrivilegeService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员中心--个人会员权益详情记录
 *
 * @author mlhxj
 * @date 2025-01-13
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/memberLevelPrivilege")
public class OneselfMemberLevelPrivilegeController extends BaseController {

    private final IOneselfMemberLevelPrivilegeService oneselfMemberLevelPrivilegeService;

    /**
     * 查询会员中心--个人会员权益详情记录列表
     */
    @SaCheckPermission("flower:memberLevelPrivilege:list")
    @GetMapping("/list")
    public TableDataInfo<OneselfMemberLevelPrivilegeVo> list(OneselfMemberLevelPrivilegeBo bo, PageQuery pageQuery) {
        return oneselfMemberLevelPrivilegeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员中心--个人会员权益详情记录列表
     */
    @SaCheckPermission("flower:memberLevelPrivilege:export")
    @Log(title = "会员中心--个人会员权益详情记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(OneselfMemberLevelPrivilegeBo bo, HttpServletResponse response) {
        List<OneselfMemberLevelPrivilegeVo> list = oneselfMemberLevelPrivilegeService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员中心--个人会员权益详情记录", OneselfMemberLevelPrivilegeVo.class, response);
    }

    /**
     * 获取会员中心--个人会员权益详情记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:memberLevelPrivilege:query")
    @GetMapping("/{id}")
    public R<OneselfMemberLevelPrivilegeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(oneselfMemberLevelPrivilegeService.queryById(id));
    }

    /**
     * 新增会员中心--个人会员权益详情记录
     */
    @SaCheckPermission("flower:memberLevelPrivilege:add")
    @Log(title = "会员中心--个人会员权益详情记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody OneselfMemberLevelPrivilegeBo bo) {
        return toAjax(oneselfMemberLevelPrivilegeService.insertByBo(bo));
    }

    /**
     * 修改会员中心--个人会员权益详情记录
     */
    @SaCheckPermission("flower:memberLevelPrivilege:edit")
    @Log(title = "会员中心--个人会员权益详情记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody OneselfMemberLevelPrivilegeBo bo) {
        return toAjax(oneselfMemberLevelPrivilegeService.updateByBo(bo));
    }

    /**
     * 删除会员中心--个人会员权益详情记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:memberLevelPrivilege:remove")
    @Log(title = "会员中心--个人会员权益详情记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(oneselfMemberLevelPrivilegeService.deleteWithValidByIds(List.of(ids), true));
    }
}
