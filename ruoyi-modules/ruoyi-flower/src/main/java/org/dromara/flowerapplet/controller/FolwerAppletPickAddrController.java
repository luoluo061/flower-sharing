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
import org.dromara.flower.domain.bo.FolwerPickAddrBo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.dromara.flowerapplet.service.IFolwerAppletPickAddrService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序用户配送地址
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flowerApplet/pickAddr")
public class FolwerAppletPickAddrController extends BaseController {

    private final IFolwerAppletPickAddrService folwerAppletPickAddrService;

    /**
     * 查询用户配送地址列表
     */
    @SaCheckPermission("flower:pickAddr:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerPickAddrVo> list(FolwerPickAddrBo bo, PageQuery pageQuery) {
        return folwerAppletPickAddrService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出用户配送地址列表
     */
    @SaCheckPermission("flower:pickAddr:export")
    @Log(title = "用户配送地址", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerPickAddrBo bo, HttpServletResponse response) {
        List<FolwerPickAddrVo> list = folwerAppletPickAddrService.queryList(bo);
        ExcelUtil.exportExcel(list, "用户配送地址", FolwerPickAddrVo.class, response);
    }

    /**
     * 获取用户配送地址详细信息
     *
     * @param addrId 主键
     */
    @SaCheckPermission("flower:pickAddr:query")
    @GetMapping("/{addrId}")
    public R<FolwerPickAddrVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long addrId) {
        return R.ok(folwerAppletPickAddrService.queryById(addrId));
    }

    /**
     * 新增用户配送地址
     */
    @SaCheckPermission("flower:pickAddr:add")
    @Log(title = "用户配送地址", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerPickAddrBo bo) {
        return toAjax(folwerAppletPickAddrService.insertByBo(bo));
    }

    /**
     * 修改用户配送地址
     */
    @SaCheckPermission("flower:pickAddr:edit")
    @Log(title = "用户配送地址", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerPickAddrBo bo) {
        return toAjax(folwerAppletPickAddrService.updateByBo(bo));
    }

    /**
     * 删除用户配送地址
     *
     * @param addrIds 主键串
     */
    @SaCheckPermission("flower:pickAddr:remove")
    @Log(title = "用户配送地址", businessType = BusinessType.DELETE)
    @DeleteMapping("/{addrIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] addrIds) {
        return toAjax(folwerAppletPickAddrService.deleteWithValidByIds(List.of(addrIds), true));
    }
}
