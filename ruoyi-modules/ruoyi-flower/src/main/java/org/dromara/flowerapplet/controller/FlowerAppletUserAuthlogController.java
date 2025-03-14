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
import org.dromara.flowerapplet.domain.vo.FlowerAppletUserAuthlogVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserAuthlogBo;
import org.dromara.flowerapplet.service.IFlowerAppletUserAuthlogService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 小程序用户信息认证记录
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/userAuthlog")
public class FlowerAppletUserAuthlogController extends BaseController {

    private final IFlowerAppletUserAuthlogService flowerAppletUserAuthlogService;

    /**
     * 查询小程序用户信息认证记录列表
     */
    @SaCheckPermission("flower:userAuthlog:list")
    @GetMapping("/list")
    public TableDataInfo<FlowerAppletUserAuthlogVo> list(FlowerAppletUserAuthlogBo bo, PageQuery pageQuery) {
        return flowerAppletUserAuthlogService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出小程序用户信息认证记录列表
     */
    @SaCheckPermission("flower:userAuthlog:export")
    @Log(title = "小程序用户信息认证记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlowerAppletUserAuthlogBo bo, HttpServletResponse response) {
        List<FlowerAppletUserAuthlogVo> list = flowerAppletUserAuthlogService.queryList(bo);
        ExcelUtil.exportExcel(list, "小程序用户信息认证记录", FlowerAppletUserAuthlogVo.class, response);
    }

    /**
     * 获取小程序用户信息认证记录详细信息
     *
     * @param authlogId 主键
     */
    @SaCheckPermission("flower:userAuthlog:query")
    @GetMapping("/{authlogId}")
    public R<FlowerAppletUserAuthlogVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long authlogId) {
        return R.ok(flowerAppletUserAuthlogService.queryById(authlogId));
    }

    /**
     * 新增小程序用户信息认证记录
     */
    @SaCheckPermission("flower:userAuthlog:add")
    @Log(title = "小程序用户信息认证记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerAppletUserAuthlogBo bo) {
        return toAjax(flowerAppletUserAuthlogService.insertByBo(bo));
    }

    /**
     * 修改小程序用户信息认证记录
     */
    @SaCheckPermission("flower:userAuthlog:edit")
    @Log(title = "小程序用户信息认证记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerAppletUserAuthlogBo bo) {
        return toAjax(flowerAppletUserAuthlogService.updateByBo(bo));
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
        return toAjax(flowerAppletUserAuthlogService.deleteWithValidByIds(List.of(authlogIds), true));
    }
}
