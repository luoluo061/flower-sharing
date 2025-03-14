package org.dromara.flowerapplet.controller;

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
import org.dromara.flowerapplet.domain.vo.FlowerAppletUserAuthVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserAuthBo;
import org.dromara.flowerapplet.service.IFlowerAppletUserAuthService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 小程序用户信息认证
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/userAuth")
public class FlowerAppletUserAuthController extends BaseController {

    private final IFlowerAppletUserAuthService flowerAppletUserAuthService;

    /**
     * 查询小程序用户信息认证列表
     */
    @SaCheckPermission("flower:userAuth:list")
    @GetMapping("/list")
    public TableDataInfo<FlowerAppletUserAuthVo> list(FlowerAppletUserAuthBo bo, PageQuery pageQuery) {
        return flowerAppletUserAuthService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出小程序用户信息认证列表
     */
    @SaCheckPermission("flower:userAuth:export")
    @Log(title = "小程序用户信息认证", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlowerAppletUserAuthBo bo, HttpServletResponse response) {
        List<FlowerAppletUserAuthVo> list = flowerAppletUserAuthService.queryList(bo);
        ExcelUtil.exportExcel(list, "小程序用户信息认证", FlowerAppletUserAuthVo.class, response);
    }

    /**
     * 获取小程序用户信息认证详细信息
     *
     * @param authId 主键
     */
    @SaCheckPermission("flower:userAuth:query")
    @GetMapping("/{authId}")
    public R<FlowerAppletUserAuthVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long authId) {
        return R.ok(flowerAppletUserAuthService.queryById(authId));
    }

    /**
     * 新增小程序用户信息认证
     */
    @SaCheckPermission("flower:userAuth:add")
    @Log(title = "小程序用户信息认证", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerAppletUserAuthBo bo) {
        return toAjax(flowerAppletUserAuthService.insertByBo(bo));
    }

    /**
     * 修改小程序用户信息认证
     */
    @SaCheckPermission("flower:userAuth:edit")
    @Log(title = "小程序用户信息认证", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerAppletUserAuthBo bo) {
        return toAjax(flowerAppletUserAuthService.updateByBo(bo));
    }

    /**
     * 删除小程序用户信息认证
     *
     * @param authIds 主键串
     */
    @SaCheckPermission("flower:userAuth:remove")
    @Log(title = "小程序用户信息认证", businessType = BusinessType.DELETE)
    @DeleteMapping("/{authIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] authIds) {
        return toAjax(flowerAppletUserAuthService.deleteWithValidByIds(List.of(authIds), true));
    }
}
