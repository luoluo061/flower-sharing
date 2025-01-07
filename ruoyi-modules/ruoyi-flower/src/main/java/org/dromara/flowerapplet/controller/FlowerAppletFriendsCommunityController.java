package org.dromara.flowerapplet.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.excel.utils.ExcelUtil;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.flower.domain.bo.FlowerFriendsCommunityBo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityCommentVo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityVo;
import org.dromara.flowerapplet.service.IFlowerAppletFriendsCommunityService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序端 花友圈
 *
 * @author mlhxj
 * @date 2024-12-30
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerApplet/friendsCommunity")
public class FlowerAppletFriendsCommunityController extends BaseController {

    private final IFlowerAppletFriendsCommunityService flowerAppletFriendsCommunityService;

    /**
     * 查询花友圈列表
     */
    @SaCheckPermission("flower:friendsCommunity:list")
    @GetMapping("/list")
    public TableDataInfo<FlowerFriendsCommunityVo> list(FlowerFriendsCommunityBo bo, PageQuery pageQuery) {
        return flowerAppletFriendsCommunityService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出花友圈列表
     */
    @SaCheckPermission("flower:friendsCommunity:export")
    @Log(title = "花友圈", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlowerFriendsCommunityBo bo, HttpServletResponse response) {
        List<FlowerFriendsCommunityVo> list = flowerAppletFriendsCommunityService.queryList(bo);
        ExcelUtil.exportExcel(list, "花友圈", FlowerFriendsCommunityVo.class, response);
    }

    /**
     * 获取花友圈详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:friendsCommunity:query")
    @GetMapping("/{id}")
    public R<FlowerFriendsCommunityVo> getInfo(@NotNull(message = "主键不能为空")
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
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerFriendsCommunityBo bo) {
        return toAjax(flowerAppletFriendsCommunityService.insertByBo(bo));
    }

    /**
     * 修改花友圈
     */
    @SaCheckPermission("flower:friendsCommunity:edit")
    @Log(title = "花友圈", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerFriendsCommunityBo bo) {
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

    /**
     * 获取评论信息
     *
     * @param communityId 主键串
     */
    @Log(title = "花友圈", businessType = BusinessType.DELETE)
    @GetMapping("/comment/{communityId}")
    @SaIgnore
    public List<FlowerFriendsCommunityCommentVo> getComment(@NotNull(message = "主键不能为空")
                          @PathVariable Long communityId) {
        return flowerAppletFriendsCommunityService.getCommentById(communityId);
    }
}
