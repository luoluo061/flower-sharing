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
import org.dromara.flowerapplet.domain.vo.FlowerAppletFriendsCommunityVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletFriendsCommunityBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.service.IFlowerAppletFriendsCommunityService;

/**
 * 花友圈
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
     * 查询花友圈列表
     */
    @SaCheckPermission("flower:friendsCommunity:list")
    @GetMapping("/list")
    public TableDataInfo<FlowerAppletFriendsCommunityVo> list(FlowerAppletFriendsCommunityBo bo, PageQuery pageQuery) {
        return flowerAppletFriendsCommunityService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出花友圈列表
     */
    @SaCheckPermission("flower:friendsCommunity:export")
    @Log(title = "花友圈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlowerAppletFriendsCommunityBo bo, HttpServletResponse response) {
        List<FlowerAppletFriendsCommunityVo> list = flowerAppletFriendsCommunityService.queryList(bo);
        ExcelUtil.exportExcel(list, "花友圈", FlowerAppletFriendsCommunityVo.class, response);
    }

    /**
     * 获取花友圈详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:friendsCommunity:query")
    @GetMapping("/{id}")
    public R<FlowerAppletFriendsCommunityVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flowerAppletFriendsCommunityService.queryById(id));
    }

    /**
     * 新增花友圈
     */
    @SaCheckPermission("flower:friendsCommunity:add")
    @Log(title = "花友圈", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerAppletFriendsCommunityBo bo) {
        return toAjax(flowerAppletFriendsCommunityService.insertByBo(bo));
    }

    /**
     * 修改花友圈
     */
    @SaCheckPermission("flower:friendsCommunity:edit")
    @Log(title = "花友圈", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerAppletFriendsCommunityBo bo) {
        return toAjax(flowerAppletFriendsCommunityService.updateByBo(bo));
    }

    /**
     * 删除花友圈
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:friendsCommunity:remove")
    @Log(title = "花友圈", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flowerAppletFriendsCommunityService.deleteWithValidByIds(List.of(ids), true));
    }
}
