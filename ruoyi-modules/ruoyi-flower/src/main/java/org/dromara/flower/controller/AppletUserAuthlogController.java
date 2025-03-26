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
import org.dromara.flower.domain.vo.AppletUserAuthlogVo;
import org.dromara.flower.domain.bo.AppletUserAuthlogBo;
import org.dromara.flower.service.IAppletUserAuthlogService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 花店认证信息认证记录
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/userAuthlog")
public class AppletUserAuthlogController extends BaseController {

    private final IAppletUserAuthlogService appletUserAuthlogService;

    /**
     * 查询小程序用户信息认证记录列表
     */
    @SaCheckPermission("flower:userAuthlog:list")
    @GetMapping("/list")
    public TableDataInfo<AppletUserAuthlogVo> list(AppletUserAuthlogBo bo, PageQuery pageQuery) {
        return appletUserAuthlogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出小程序用户信息认证记录列表
     */
    @SaCheckPermission("flower:userAuthlog:export")
    @Log(title = "小程序用户信息认证记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppletUserAuthlogBo bo, HttpServletResponse response) {
        List<AppletUserAuthlogVo> list = appletUserAuthlogService.queryList(bo);
        ExcelUtil.exportExcel(list, "小程序用户信息认证记录", AppletUserAuthlogVo.class, response);
    }

    /**
     * 获取小程序用户信息认证记录详细信息
     *
     * @param authlogId 主键
     */
    @SaCheckPermission("flower:userAuthlog:query")
    @GetMapping("/{authlogId}")
    public R<AppletUserAuthlogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long authlogId) {
        return R.ok(appletUserAuthlogService.queryById(authlogId));
    }

    /**
     * 新增小程序用户信息认证记录
     */
    @SaCheckPermission("flower:userAuthlog:add")
    @Log(title = "小程序用户信息认证记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppletUserAuthlogBo bo) {
        return toAjax(appletUserAuthlogService.insertByBo(bo));
    }

    /**
     * 修改小程序用户信息认证记录
     */
    @SaCheckPermission("flower:userAuthlog:edit")
    @Log(title = "小程序用户信息认证记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody AppletUserAuthlogBo bo) {
        return toAjax(appletUserAuthlogService.updateByBo(bo));
    }

    /**
     * 删除小程序用户信息认证记录
     *
     * @param authlogIds 主键串
     */
    @SaCheckPermission("flower:userAuthlog:remove")
    @Log(title = "小程序用户信息认证记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{authlogIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] authlogIds) {
        return toAjax(appletUserAuthlogService.deleteWithValidByIds(List.of(authlogIds), true));
    }
}
