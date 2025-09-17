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
import org.dromara.flower.domain.vo.FolwerProductDetailVo;
import org.dromara.flower.domain.bo.FolwerProductDetailBo;
import org.dromara.flower.service.IFolwerProductDetailService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 商品详情
 *
 * @author mlhxj
 * @date 2025-08-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/productDetail")
public class FolwerProductDetailController extends BaseController {

    private final IFolwerProductDetailService folwerProductDetailService;

    /**
     * 查询商品详情列表
     */
    @SaCheckPermission("flower:productDetail:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerProductDetailVo> list(FolwerProductDetailBo bo, PageQuery pageQuery) {
        return folwerProductDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品详情列表
     */
    @SaCheckPermission("flower:productDetail:export")
    @Log(title = "商品详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerProductDetailBo bo, HttpServletResponse response) {
        List<FolwerProductDetailVo> list = folwerProductDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品详情", FolwerProductDetailVo.class, response);
    }

    /**
     * 获取商品详情详细信息
     *
     * @param detailId 主键
     */
    @SaCheckPermission("flower:productDetail:query")
    @GetMapping("/{detailId}")
    public R<FolwerProductDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long detailId) {
        return R.ok(folwerProductDetailService.queryById(detailId));
    }

    /**
     * 新增商品详情
     */
    @SaCheckPermission("flower:productDetail:add")
    @Log(title = "商品详情", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerProductDetailBo bo) {
        return toAjax(folwerProductDetailService.insertByBo(bo));
    }

    /**
     * 修改商品详情
     */
    @SaCheckPermission("flower:productDetail:edit")
    @Log(title = "商品详情", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerProductDetailBo bo) {
        return toAjax(folwerProductDetailService.updateByBo(bo));
    }

    /**
     * 删除商品详情
     *
     * @param detailIds 主键串
     */
    @SaCheckPermission("flower:productDetail:remove")
    @Log(title = "商品详情", businessType = BusinessType.DELETE)
    @DeleteMapping("/{detailIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] detailIds) {
        return toAjax(folwerProductDetailService.deleteWithValidByIds(List.of(detailIds), true));
    }
}
