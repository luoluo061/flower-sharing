package org.dromara.system.platform.controller;

import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.platform.domain.vo.MerchantRegionVo;
import org.dromara.system.platform.service.IMerchantRegionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 行政区划代码
 * 前端访问路由地址为:/user/region
 *
 * @author LionLi
 * @date 2024-11-11
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/region")
public class MerchantRegionController extends BaseController {

    private final IMerchantRegionService sysRegionService;

   /* *//**
     * 查询行政区划代码列表
     *//*
    @SaCheckPermission("system:region:list")
    @GetMapping("/list")
    public TableDataInfo<SysRegionVo> list(SysRegionBo bo, PageQuery pageQuery) {
        return sysRegionService.queryPageList(bo, pageQuery);
    }

    *//**
     * 导出行政区划代码列表
     *//*
    @SaCheckPermission("system:region:export")
    @Log(title = "行政区划代码", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(SysRegionBo bo, HttpServletResponse response) {
        List<SysRegionVo> list = sysRegionService.queryList(bo);
        ExcelUtil.exportExcel(list, "行政区划代码", SysRegionVo.class, response);
    }

    *//**
     * 获取行政区划代码详细信息
     *
     * @param id 主键
     *//*
    @SaCheckPermission("system:region:query")
    @GetMapping("/{id}")
    public R<SysRegionVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(sysRegionService.queryById(id));
    }

    *//**
     * 新增行政区划代码
     *//*
    @SaCheckPermission("system:region:add")
    @Log(title = "行政区划代码", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody SysRegionBo bo) {
        return toAjax(sysRegionService.insertByBo(bo));
    }

    *//**
     * 修改行政区划代码
     *//*
    @SaCheckPermission("system:region:edit")
    @Log(title = "行政区划代码", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody SysRegionBo bo) {
        return toAjax(sysRegionService.updateByBo(bo));
    }

    *//**
     * 删除行政区划代码
     *
     * @param ids 主键串
     *//*
    @SaCheckPermission("system:region:remove")
    @Log(title = "行政区划代码", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(sysRegionService.deleteWithValidByIds(List.of(ids), true));
    }*/

    /**
     *获取行政区划
     */
    @GetMapping("/{id}")
    public R<List<MerchantRegionVo>> getRegionList(@PathVariable Long id) {
        return R.ok(sysRegionService.getRegionList(id));

    }
}
