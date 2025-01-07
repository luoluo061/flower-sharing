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
import org.dromara.flower.domain.vo.MarketingMemberPromotionPlanVo;
import org.dromara.flower.domain.bo.MarketingMemberPromotionPlanBo;
import org.dromara.flower.service.IMarketingMemberPromotionPlanService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 营销推广-会员推广计划
 *
 * @author chy
 * @date 2024-12-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/memberPromotionPlan")
public class MarketingMemberPromotionPlanController extends BaseController {

    private final IMarketingMemberPromotionPlanService marketingMemberPromotionPlanService;

    /**
     * 查询营销推广-会员推广计划列表
     */
    @SaCheckPermission("flower:memberPromotionPlan:list")
    @GetMapping("/list")
    public TableDataInfo<MarketingMemberPromotionPlanVo> list(MarketingMemberPromotionPlanBo bo, PageQuery pageQuery) {
        return marketingMemberPromotionPlanService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出营销推广-会员推广计划列表
     */
    @SaCheckPermission("flower:memberPromotionPlan:export")
    @Log(title = "营销推广-会员推广计划", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MarketingMemberPromotionPlanBo bo, HttpServletResponse response) {
        List<MarketingMemberPromotionPlanVo> list = marketingMemberPromotionPlanService.queryList(bo);
        ExcelUtil.exportExcel(list, "营销推广-会员推广计划", MarketingMemberPromotionPlanVo.class, response);
    }

    /**
     * 获取营销推广-会员推广计划详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:memberPromotionPlan:query")
    @GetMapping("/{id}")
    public R<MarketingMemberPromotionPlanVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(marketingMemberPromotionPlanService.queryById(id));
    }

    /**
     * 新增营销推广-会员推广计划
     */
    @SaCheckPermission("flower:memberPromotionPlan:add")
    @Log(title = "营销推广-会员推广计划", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MarketingMemberPromotionPlanBo bo) {
        return toAjax(marketingMemberPromotionPlanService.insertByBo(bo));
    }

    /**
     * 修改营销推广-会员推广计划
     */
    @SaCheckPermission("flower:memberPromotionPlan:edit")
    @Log(title = "营销推广-会员推广计划", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MarketingMemberPromotionPlanBo bo) {
        return toAjax(marketingMemberPromotionPlanService.updateByBo(bo));
    }

    /**
     * 删除营销推广-会员推广计划
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:memberPromotionPlan:remove")
    @Log(title = "营销推广-会员推广计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(marketingMemberPromotionPlanService.deleteWithValidByIds(List.of(ids), true));
    }


    @SaCheckPermission("flower:memberPromotionPlan:edit")
    @Log(title = "营销推广-会员推广计划",businessType = BusinessType.UPDATE)
    @PutMapping("/{id}")
    public R<Void> editStatus(@NotBlank(message = "id不能为空") @PathVariable Long id){
        return toAjax(marketingMemberPromotionPlanService.updateStatus(id));
    }





}
