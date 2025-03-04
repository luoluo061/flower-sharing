package org.dromara.flowerapplet.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
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
import org.dromara.flowerapplet.domain.vo.FlowerAppletUserInformationVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserInformationBo;
import org.dromara.flowerapplet.service.IFlowerAppletUserInformationService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 小程序用户信息
 *
 * @author mlhxj
 * @date 2025-03-03
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/userInformation")
public class FlowerAppletUserInformationController extends BaseController {

    private final IFlowerAppletUserInformationService flowerAppletUserInformationService;

    private final IAppletUserInformationService appletUserInformationService;

    /**
     * 查询小程序用户信息列表
     */
    @SaCheckPermission("flower:userInformation:list")
    @GetMapping("/list")
    public TableDataInfo<FlowerAppletUserInformationVo> list(FlowerAppletUserInformationBo bo, PageQuery pageQuery) {
        return flowerAppletUserInformationService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出小程序用户信息列表
     */
    @SaCheckPermission("flower:userInformation:export")
    @Log(title = "小程序用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlowerAppletUserInformationBo bo, HttpServletResponse response) {
        List<FlowerAppletUserInformationVo> list = flowerAppletUserInformationService.queryList(bo);
        ExcelUtil.exportExcel(list, "小程序用户信息", FlowerAppletUserInformationVo.class, response);
    }

    /**
     * 获取小程序用户信息详细信息
     *
     * @param userId 主键
     */
    @SaCheckPermission("flower:userInformation:query")
    @GetMapping("/{userId}")
    public R<FlowerAppletUserInformationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long userId) {
        return R.ok(flowerAppletUserInformationService.queryById(userId));
    }

    /**
     * 获取小程序用户信息详细信息
     *
     */
    @SaCheckPermission("flower:userInformation:queryinfo")
    @GetMapping("")
    public R<AppletUserInformationVo> getUserInfo() {
        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryUserInfo();
        return R.ok(appletUserInformationVo);
    }

    /**
     * 新增小程序用户信息
     */
    @SaCheckPermission("flower:userInformation:add")
    @Log(title = "小程序用户信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerAppletUserInformationBo bo) {
        return toAjax(flowerAppletUserInformationService.insertByBo(bo));
    }

    /**
     * 修改小程序用户信息
     */
    @SaCheckPermission("flower:userInformation:edit")
    @Log(title = "小程序用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerAppletUserInformationBo bo) {
        return toAjax(flowerAppletUserInformationService.updateByBo(bo));
    }

    /**
     * 删除小程序用户信息
     *
     * @param userIds 主键串
     */
    @SaCheckPermission("flower:userInformation:remove")
    @Log(title = "小程序用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] userIds) {
        return toAjax(flowerAppletUserInformationService.deleteWithValidByIds(List.of(userIds), true));
    }
}
