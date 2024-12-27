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
import org.dromara.flower.domain.vo.FolwerCreditProductVo;
import org.dromara.flower.domain.bo.FolwerCreditProductBo;
import org.dromara.flower.service.IFolwerCreditProductService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 积分商品管理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/creditProduct")
public class FolwerCreditProductController extends BaseController {

    private final IFolwerCreditProductService folwerCreditProductService;

    /**
     * 查询积分商品管理列表
     */
    @SaCheckPermission("flower:creditProduct:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerCreditProductVo> list(FolwerCreditProductBo bo, PageQuery pageQuery) {
        return folwerCreditProductService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出积分商品管理列表
     */
    @SaCheckPermission("flower:creditProduct:export")
    @Log(title = "积分商品管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerCreditProductBo bo, HttpServletResponse response) {
        List<FolwerCreditProductVo> list = folwerCreditProductService.queryList(bo);
        ExcelUtil.exportExcel(list, "积分商品管理", FolwerCreditProductVo.class, response);
    }

    /**
     * 获取积分商品管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:creditProduct:query")
    @GetMapping("/{id}")
    public R<FolwerCreditProductVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(folwerCreditProductService.queryById(id));
    }

    /**
     * 新增积分商品管理
     */
    @SaCheckPermission("flower:creditProduct:add")
    @Log(title = "积分商品管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerCreditProductBo bo) {
        return toAjax(folwerCreditProductService.insertByBo(bo));
    }

    /**
     * 修改积分商品管理
     */
    @SaCheckPermission("flower:creditProduct:edit")
    @Log(title = "积分商品管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerCreditProductBo bo) {
        return toAjax(folwerCreditProductService.updateByBo(bo));
    }

    /**
     * 删除积分商品管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:creditProduct:remove")
    @Log(title = "积分商品管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(folwerCreditProductService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 批量修改商品状态
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:product:updatestatus")
    @Log(title = "商品管理", businessType = BusinessType.UPDATE)
    @PutMapping("batchUpdateStatus/{ids}")
    public R<Void> upDateStatus(@NotEmpty(message = "主键不能为空")
                                @PathVariable Long[] ids) {
        return toAjax(folwerCreditProductService.updateStatusByIds(List.of(ids), true));
    }
}
