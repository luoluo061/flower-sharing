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
import org.dromara.flower.domain.vo.MarketingAdvertisementVo;
import org.dromara.flower.domain.bo.MarketingAdvertisementBo;
import org.dromara.flower.service.IMarketingAdvertisementService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 广告管理
 *
 * @author chy
 * @date 2024-12-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/advertisement")
public class MarketingAdvertisementController extends BaseController {

    private final IMarketingAdvertisementService marketingAdvertisementService;

    /**
     * 查询广告管理列表
     */
    @SaCheckPermission("flower:advertisement:list")
    @GetMapping("/list")
    public TableDataInfo<MarketingAdvertisementVo> list(MarketingAdvertisementBo bo, PageQuery pageQuery) {
        return marketingAdvertisementService.queryPageList(bo, pageQuery);
    }


    /**
     * 导出广告管理列表
     */
    @SaCheckPermission("flower:advertisement:export")
    @Log(title = "广告管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MarketingAdvertisementBo bo, HttpServletResponse response) {
        List<MarketingAdvertisementVo> list = marketingAdvertisementService.queryList(bo);
        ExcelUtil.exportExcel(list, "广告管理", MarketingAdvertisementVo.class, response);
    }

    /**
     * 获取广告管理详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:advertisement:query")
    @GetMapping("/{id}")
    public R<MarketingAdvertisementVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(marketingAdvertisementService.queryById(id));
    }

    /**
     * 新增广告管理
     */
    @SaCheckPermission("flower:advertisement:add")
    @Log(title = "广告管理", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MarketingAdvertisementBo bo) {
        return toAjax(marketingAdvertisementService.insertByBo(bo));
    }

    /**
     * 修改广告管理
     */
    @SaCheckPermission("flower:advertisement:edit")
    @Log(title = "广告管理", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MarketingAdvertisementBo bo) {
        return toAjax(marketingAdvertisementService.updateByBo(bo));
    }

    /**
     * 删除广告管理
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:advertisement:remove")
    @Log(title = "广告管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(marketingAdvertisementService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 切换状态
     */
    @SaCheckPermission("flower:advertisement:remove")
    @Log(title = "广告管理",businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    public R<Void> editState(@NotNull(message = "不能为空") @PathVariable Long id){
        return toAjax(marketingAdvertisementService.switchState(id));
    }

    /**
     * 小程序端根据类型查询banner信息
     * @param type
     * @return
     */
    //@SaCheckPermission("flower:advertisement:query")
    @GetMapping("/listByType")
    public R<List<MarketingAdvertisementVo>> getInfoByType(@NotBlank(message = "类型名不能为空") String type){
        return R.ok(marketingAdvertisementService.selectByType(type));

    }





}
