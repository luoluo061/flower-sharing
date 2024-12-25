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
import org.dromara.flower.domain.vo.FolwerProductCommVo;
import org.dromara.flower.domain.bo.FolwerProductCommBo;
import org.dromara.flower.service.IFolwerProductCommService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 商品评价
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/productComm")
public class FolwerProductCommController extends BaseController {

    private final IFolwerProductCommService folwerProductCommService;

    /**
     * 查询商品评价列表
     */
    @SaCheckPermission("flower:productComm:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerProductCommVo> list(FolwerProductCommBo bo, PageQuery pageQuery) {
        return folwerProductCommService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出商品评价列表
     */
    @SaCheckPermission("flower:productComm:export")
    @Log(title = "商品评价", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerProductCommBo bo, HttpServletResponse response) {
        List<FolwerProductCommVo> list = folwerProductCommService.queryList(bo);
        ExcelUtil.exportExcel(list, "商品评价", FolwerProductCommVo.class, response);
    }

    /**
     * 获取商品评价详细信息
     *
     * @param prodCommId 主键
     */
    @SaCheckPermission("flower:productComm:query")
    @GetMapping("/{prodCommId}")
    public R<FolwerProductCommVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long prodCommId) {
        return R.ok(folwerProductCommService.queryById(prodCommId));
    }

    /**
     * 新增商品评价
     */
    @SaCheckPermission("flower:productComm:add")
    @Log(title = "商品评价", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerProductCommBo bo) {
        return toAjax(folwerProductCommService.insertByBo(bo));
    }

    /**
     * 修改商品评价
     */
    @SaCheckPermission("flower:productComm:edit")
    @Log(title = "商品评价", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerProductCommBo bo) {
        return toAjax(folwerProductCommService.updateByBo(bo));
    }

    /**
     * 删除商品评价
     *
     * @param prodCommIds 主键串
     */
    @SaCheckPermission("flower:productComm:remove")
    @Log(title = "商品评价", businessType = BusinessType.DELETE)
    @DeleteMapping("/{prodCommIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] prodCommIds) {
        return toAjax(folwerProductCommService.deleteWithValidByIds(List.of(prodCommIds), true));
    }
}
