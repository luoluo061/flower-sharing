package org.dromara.system.platform.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.system.platform.domain.bo.AppletUserInformationBo;
import org.dromara.system.platform.domain.vo.AppletUserInformationVo;
import org.dromara.system.platform.service.IAppletUserInformationService;
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

import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 小程序用户信息
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/userInformation")
public class AppletUserInformationController extends BaseController {

    private final IAppletUserInformationService appletUserInformationService;

    /**
     * 查询小程序用户信息列表
     */
    @SaCheckPermission("system:userInformation:list")
    @GetMapping("/list")
    public TableDataInfo<AppletUserInformationVo> list(AppletUserInformationBo bo, PageQuery pageQuery) {
        return appletUserInformationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出小程序用户信息列表
     */
    @SaCheckPermission("system:userInformation:export")
    @Log(title = "小程序用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppletUserInformationBo bo, HttpServletResponse response) {
        List<AppletUserInformationVo> list = appletUserInformationService.queryList(bo);
        ExcelUtil.exportExcel(list, "小程序用户信息", AppletUserInformationVo.class, response);
    }

    /**
     * 获取小程序用户信息详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("system:userInformation:query")
    @GetMapping("/{userId}")
    public R<AppletUserInformationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(appletUserInformationService.queryById(userId));
    }

    /**
     * 新增小程序用户信息
     */
    @SaCheckPermission("system:userInformation:add")
    @Log(title = "小程序用户信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppletUserInformationBo bo) {
        return toAjax(appletUserInformationService.insertByBo(bo));
    }

    /**
     * 修改小程序用户信息
     */
    @SaCheckPermission("system:userInformation:edit")
    @Log(title = "小程序用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppletUserInformationBo bo) {
        return toAjax(appletUserInformationService.updateByBo(bo));
    }

    /**
     * 删除小程序用户信息
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("system:userInformation:remove")
    @Log(title = "小程序用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(appletUserInformationService.deleteWithValidByIds(List.of(userIds), true));
    }
}
