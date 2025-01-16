package org.dromara.flowerapplet.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flowerapplet.domain.FolwerShopCartItem;
import org.dromara.flowerapplet.domain.bo.FolwerAppletBasketBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;
import org.dromara.flowerapplet.service.IFolwerAppletBasketService;
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
import org.dromara.common.mybatis.core.page.TableDataInfo;


/**
 * 小程序购物车
 *
 * @author mlhxj
 * @date 2025-01-02
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerapplet/basket")
public class FolwerAppletBasketController extends BaseController {

    private final IFolwerAppletBasketService folwerBasketService;

    /**
     * 查询小程序购物车列表
     */
    @SaCheckPermission("flower:basket:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerAppletBasketVo> list(FolwerAppletBasketBo bo, PageQuery pageQuery) {
        return folwerBasketService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出小程序购物车列表
     */
    @SaCheckPermission("flower:basket:export")
    @Log(title = "小程序购物车", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletBasketBo bo, HttpServletResponse response) {
        List<FolwerAppletBasketVo> list = folwerBasketService.queryList(bo);
        ExcelUtil.exportExcel(list, "小程序购物车", FolwerAppletBasketVo.class, response);
    }

    /**
     * 获取小程序购物车详细信息
     *
     * @param basketId 主键
     */
    @SaCheckPermission("flower:basket:query")
    @GetMapping("/{basketId}")
    public R<FolwerAppletBasketVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long basketId) {
        return R.ok(folwerBasketService.queryById(basketId));
    }

    /**
     * 新增小程序购物车
     */
    @SaCheckPermission("flower:basket:add")
    @Log(title = "小程序购物车", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletBasketBo bo) {
        return toAjax(folwerBasketService.insertByBo(bo));
    }

    /**
     * 修改小程序购物车
     */
    @SaCheckPermission("flower:basket:edit")
    @Log(title = "小程序购物车", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletBasketBo bo) {
        return toAjax(folwerBasketService.updateByBo(bo));
    }

    /**
     * 删除小程序购物车
     *
     * @param basketIds 主键串
     */
    @SaCheckPermission("flower:basket:remove")
    @Log(title = "小程序购物车", businessType = BusinessType.DELETE)
    @DeleteMapping("/{basketIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] basketIds) {
        return toAjax(folwerBasketService.deleteWithValidByIds(List.of(basketIds), true));
    }

    /**
     * 获取小程序购物车列表
     *
     * @param userId 主键
     */
    @SaCheckPermission("flower:basket:query")
    @GetMapping("/CartItem/{userId}")
    public R<FolwerShopCartItem> getFolwerShopCartItem(@NotNull(message = "主键不能为空")
                                           @PathVariable Long userId) {
        return R.ok(folwerBasketService.getShopCartItems(userId));
    }
}
