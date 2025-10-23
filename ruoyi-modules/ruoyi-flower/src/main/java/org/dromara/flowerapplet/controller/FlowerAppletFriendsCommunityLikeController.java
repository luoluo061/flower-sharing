package org.dromara.flowerapplet.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
import org.dromara.flower.domain.bo.FlowerFriendsCommunityLikeBo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityLikeVo;
import org.dromara.flowerapplet.service.IFlowerAppletFriendsCommunityLikeService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 花友圈--点赞详情
 *
 * @author mlhxj
 * @date 2025-01-23
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerApplet/friendsCommunityLike")
public class FlowerAppletFriendsCommunityLikeController extends BaseController {

    private final IFlowerAppletFriendsCommunityLikeService flowerFriendsCommunityLikeService;

    /**
     * 查询花友圈--点赞详情列表
     */
    @SaCheckPermission("flower:friendsCommunityLike:list")
    @GetMapping("/list")
    public TableDataInfo<FlowerFriendsCommunityLikeVo> list(FlowerFriendsCommunityLikeBo bo, PageQuery pageQuery) {
        return flowerFriendsCommunityLikeService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出花友圈--点赞详情列表
     */
    @SaCheckPermission("flower:friendsCommunityLike:export")
    @Log(title = "花友圈--点赞详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FlowerFriendsCommunityLikeBo bo, HttpServletResponse response) {
        List<FlowerFriendsCommunityLikeVo> list = flowerFriendsCommunityLikeService.queryList(bo);
        ExcelUtil.exportExcel(list, "花友圈--点赞详情", FlowerFriendsCommunityLikeVo.class, response);
    }

    /**
     * 获取花友圈--点赞详情详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:friendsCommunityLike:query")
    @GetMapping("/{id}")
    public R<FlowerFriendsCommunityLikeVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(flowerFriendsCommunityLikeService.queryById(id));
    }

    /**
     * 新增花友圈--点赞详情
     */
    @SaCheckPermission("flower:friendsCommunityLike:add")
    @Log(title = "花友圈--点赞详情", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FlowerFriendsCommunityLikeBo bo) {
        return toAjax(flowerFriendsCommunityLikeService.insertByBo(bo));
    }

    /**
     * 修改花友圈--点赞详情
     */
    @SaCheckPermission("flower:friendsCommunityLike:edit")
    @Log(title = "花友圈--点赞详情", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FlowerFriendsCommunityLikeBo bo) {
        return toAjax(flowerFriendsCommunityLikeService.updateByBo(bo));
    }

    /**
     * 删除花友圈--点赞详情
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:friendsCommunityLike:remove")
    @Log(title = "花友圈--点赞详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(flowerFriendsCommunityLikeService.deleteWithValidByIds(List.of(ids), true));
    }
}
