package org.dromara.flower.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.service.IFlowerFriendsCommunityService;
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
import org.dromara.flower.domain.vo.FlowerFriendsCommunityVo;
import org.dromara.flower.domain.bo.FlowerFriendsCommunityBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 花友圈
 *
 * @author mlhxj
 * @date 2024-12-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/friendsCommunity")
public class FlowerFriendsCommunityController extends BaseController {

    private final IFlowerFriendsCommunityService flowerFriendsCommunityService;

    /** 列表 */
    @SaCheckPermission("flower:friendsCommunity:list")
    @GetMapping("/list")
    public TableDataInfo<FlowerFriendsCommunityVo> list(FlowerFriendsCommunityBo bo, PageQuery pageQuery) {
        return flowerFriendsCommunityService.queryPageList(bo, pageQuery);
    }

    /** 导出 */
    @SaCheckPermission("flower:friendsCommunity:export")
    @Log(title = "弹窗管理", businessType = BusinessType.EXPORT) // ← 改文案
    @PostMapping("/export")
    public void export(FlowerFriendsCommunityBo bo, HttpServletResponse response) {
        List<FlowerFriendsCommunityVo> list = flowerFriendsCommunityService.queryList(bo);
        ExcelUtil.exportExcel(list, "弹窗管理", FlowerFriendsCommunityVo.class, response); // ← 改文件名
    }

    /** 详情 */
    @SaCheckPermission("flower:friendsCommunity:query")
    @GetMapping("/{id}")
    public R<FlowerFriendsCommunityVo> getInfo(@NotNull @PathVariable Long id) {
        return R.ok(flowerFriendsCommunityService.queryById(id));
    }

    /** 新增 */
    @SaCheckPermission("flower:friendsCommunity:add")
    @Log(title = "弹窗管理", businessType = BusinessType.INSERT)
    @RepeatSubmit
    @PostMapping
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerFriendsCommunityBo bo) {
        return toAjax(flowerFriendsCommunityService.insertByBo(bo));
    }

    /** 修改 */
    @SaCheckPermission("flower:friendsCommunity:edit")
    @Log(title = "弹窗管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit
    @PutMapping
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerFriendsCommunityBo bo) {
        return toAjax(flowerFriendsCommunityService.updateByBo(bo));
    }

    /** 删除 */
    @SaCheckPermission("flower:friendsCommunity:remove")
    @Log(title = "弹窗管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty @PathVariable Long[] ids) {
        return toAjax(flowerFriendsCommunityService.deleteWithValidByIds(List.of(ids), true));
    }

}

