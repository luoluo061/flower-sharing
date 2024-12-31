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
import org.dromara.flower.domain.vo.FlowerFriendsCommunityCommentVo;
import org.dromara.flower.domain.bo.FlowerFriendsCommunityCommentBo;
import org.dromara.flower.service.IFlowerFriendsCommunityCommentService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 花友圈--评论详情
 *
 * @author mlhxj
 * @date 2024-12-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/friendsCommunityComment")
public class FlowerFriendsCommunityCommentController extends BaseController {

    private final IFlowerFriendsCommunityCommentService flowerFriendsCommunityCommentService;

    /**
     * 查询花友圈--评论详情列表
     */
    @SaCheckPermission("flower:friendsCommunityComment:list")
    @GetMapping("/list")
    public TableDataInfo<FlowerFriendsCommunityCommentVo> list(FlowerFriendsCommunityCommentBo bo, PageQuery pageQuery) {
        return flowerFriendsCommunityCommentService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出花友圈--评论详情列表
     */
    @SaCheckPermission("flower:friendsCommunityComment:export")
    @Log(title = "花友圈--评论详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlowerFriendsCommunityCommentBo bo, HttpServletResponse response) {
        List<FlowerFriendsCommunityCommentVo> list = flowerFriendsCommunityCommentService.queryList(bo);
        ExcelUtil.exportExcel(list, "花友圈--评论详情", FlowerFriendsCommunityCommentVo.class, response);
    }

    /**
     * 获取花友圈--评论详情详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:friendsCommunityComment:query")
    @GetMapping("/{id}")
    public R<FlowerFriendsCommunityCommentVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flowerFriendsCommunityCommentService.queryById(id));
    }

    /**
     * 新增花友圈--评论详情
     */
    @SaCheckPermission("flower:friendsCommunityComment:add")
    @Log(title = "花友圈--评论详情", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerFriendsCommunityCommentBo bo) {
        return toAjax(flowerFriendsCommunityCommentService.insertByBo(bo));
    }

    /**
     * 修改花友圈--评论详情
     */
    @SaCheckPermission("flower:friendsCommunityComment:edit")
    @Log(title = "花友圈--评论详情", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerFriendsCommunityCommentBo bo) {
        return toAjax(flowerFriendsCommunityCommentService.updateByBo(bo));
    }

    /**
     * 删除花友圈--评论详情
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:friendsCommunityComment:remove")
    @Log(title = "花友圈--评论详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flowerFriendsCommunityCommentService.deleteWithValidByIds(List.of(ids), true));
    }
}
