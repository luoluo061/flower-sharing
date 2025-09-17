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
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductDetailVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductDetailBo;
import org.dromara.flowerapplet.service.IFolwerAppletProductDetailService;
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
@RequestMapping("/flowerapplet/productDetail")
public class FolwerAppletProductDetailController extends BaseController {

    private final IFolwerAppletProductDetailService folwerAppletProductDetailService;

    /**
     * 查询商品详情列表
     */
    @SaCheckPermission("flower:productDetail:list")
    @GetMapping("/list")
    @SaIgnore
    public TableDataInfo<FolwerAppletProductDetailVo> list(FolwerAppletProductDetailBo bo, PageQuery pageQuery) {
        return folwerAppletProductDetailService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品详情列表
     */
    @SaCheckPermission("flower:productDetail:export")
    @Log(title = "商品详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerAppletProductDetailBo bo, HttpServletResponse response) {
        List<FolwerAppletProductDetailVo> list = folwerAppletProductDetailService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品详情", FolwerAppletProductDetailVo.class, response);
    }

    /**
     * 获取商品详情详细信息
     *
     * @param detailId 主键
     */
    @SaCheckPermission("flower:productDetail:query")
    @GetMapping("/{detailId}")
    @SaIgnore
    public R<FolwerAppletProductDetailVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long detailId) {
        return R.ok(folwerAppletProductDetailService.queryById(detailId));
    }

    /**
     * 商品ID获取商品详情详细信息
     *
     * @param skuId 主键
     */
    @SaCheckPermission("flower:productDetail:querybyproductid")
    @GetMapping("/getInfoBySkuId/{skuId}")
    @SaIgnore
    public R<FolwerAppletProductDetailVo> getInfoBySkuId(@NotNull(message = "主键不能为空")
                                                  @PathVariable Long skuId) {
        return R.ok(folwerAppletProductDetailService.queryByProductId(skuId));
    }

    /**
     * 新增商品详情
     */
    @SaCheckPermission("flower:productDetail:add")
    @Log(title = "商品详情", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerAppletProductDetailBo bo) {
        return toAjax(folwerAppletProductDetailService.insertByBo(bo));
    }

    /**
     * 修改商品详情
     */
    @SaCheckPermission("flower:productDetail:edit")
    @Log(title = "商品详情", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerAppletProductDetailBo bo) {
        return toAjax(folwerAppletProductDetailService.updateByBo(bo));
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
        return toAjax(folwerAppletProductDetailService.deleteWithValidByIds(List.of(detailIds), true));
    }
}
