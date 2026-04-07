package org.dromara.flowerapplet.controller;

import java.util.List;

import cn.dev33.satoken.annotation.SaIgnore;
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
import org.dromara.flowerapplet.domain.vo.FlowerAppletFriendsCommunityVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletFriendsCommunityBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.service.IFlowerAppletFriendsCommunityService;

/**
 * 弹窗管理
 *
 * @author mlhxj
 * @date 2025-10-20
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/applet/flower/friendsCommunity")
public class FlowerAppletFriendsCommunityController extends BaseController {

    private final IFlowerAppletFriendsCommunityService flowerAppletFriendsCommunityService;

    /**
     * 查询弹窗管理列表
     */
    @SaCheckPermission("flower:friendsCommunity:list")
    @GetMapping("/list")
    @SaIgnore //忽略权限校验 小程序过审
    public TableDataInfo<FlowerAppletFriendsCommunityVo> list(FlowerAppletFriendsCommunityBo bo, PageQuery pageQuery) {
        return flowerAppletFriendsCommunityService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出弹窗管理列表
     */
    @SaCheckPermission("flower:friendsCommunity:export")
    @Log(title = "管理列表", businessType = BusinessType.EXPORT)
    @SaIgnore //忽略权限校验 小程序过审
    @PostMapping("/export")
    public void export(FlowerAppletFriendsCommunityBo bo, HttpServletResponse response) {
        List<FlowerAppletFriendsCommunityVo> list = flowerAppletFriendsCommunityService.queryList(bo);
        ExcelUtil.exportExcel(list, "花友圈", FlowerAppletFriendsCommunityVo.class, response);
    }

    /**
     * 获取弹窗管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:friendsCommunity:query")
    @SaIgnore //忽略权限校验 小程序过审
    @GetMapping("/{id}")
    public R<FlowerAppletFriendsCommunityVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flowerAppletFriendsCommunityService.queryById(id));
    }

    /**
     * 新增弹窗管理
     */
    @SaCheckPermission("flower:friendsCommunity:add")
    @Log(title = "管理列表", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerAppletFriendsCommunityBo bo) {
        return toAjax(flowerAppletFriendsCommunityService.insertByBo(bo));
    }

    /**
     * 修改弹窗管理
     */
    @SaCheckPermission("flower:friendsCommunity:edit")
    @Log(title = "管理列表", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerAppletFriendsCommunityBo bo) {
        return toAjax(flowerAppletFriendsCommunityService.updateByBo(bo));
    }

    /**
     * 删除弹窗管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:friendsCommunity:remove")
    @Log(title = "管理列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flowerAppletFriendsCommunityService.deleteWithValidByIds(List.of(ids), true));
    }
}
