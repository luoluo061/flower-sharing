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
import org.dromara.flower.domain.vo.MarketingLogisticsExpressVo;
import org.dromara.flower.domain.bo.MarketingLogisticsExpressBo;
import org.dromara.flower.service.IMarketingLogisticsExpressService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 营销推广-物流快递
 *
 * @author chy
 * @date 2025-01-06
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/logisticsExpress")
public class MarketingLogisticsExpressController extends BaseController {

    private final IMarketingLogisticsExpressService marketingLogisticsExpressService;

    /**
     * 查询营销推广-物流快递列表
     */
    @SaCheckPermission("flower:logisticsExpress:list")
    @GetMapping("/list")
    public TableDataInfo<MarketingLogisticsExpressVo> list(MarketingLogisticsExpressBo bo, PageQuery pageQuery) {
        return marketingLogisticsExpressService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出营销推广-物流快递列表
     */
    @SaCheckPermission("flower:logisticsExpress:export")
    @Log(title = "营销推广-物流快递", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MarketingLogisticsExpressBo bo, HttpServletResponse response) {
        List<MarketingLogisticsExpressVo> list = marketingLogisticsExpressService.queryList(bo);
        ExcelUtil.exportExcel(list, "营销推广-物流快递", MarketingLogisticsExpressVo.class, response);
    }

    /**
     * 获取营销推广-物流快递详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:logisticsExpress:query")
    @GetMapping("/{id}")
    public R<MarketingLogisticsExpressVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(marketingLogisticsExpressService.queryById(id));
    }

    /**
     * 新增营销推广-物流快递
     */
    @SaCheckPermission("flower:logisticsExpress:add")
    @Log(title = "营销推广-物流快递", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MarketingLogisticsExpressBo bo) {
        return toAjax(marketingLogisticsExpressService.insertByBo(bo));
    }

    /**
     * 修改营销推广-物流快递
     */
    @SaCheckPermission("flower:logisticsExpress:edit")
    @Log(title = "营销推广-物流快递", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MarketingLogisticsExpressBo bo) {
        return toAjax(marketingLogisticsExpressService.updateByBo(bo));
    }

    /**
     * 删除营销推广-物流快递
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:logisticsExpress:remove")
    @Log(title = "营销推广-物流快递", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(marketingLogisticsExpressService.deleteWithValidByIds(List.of(ids), true));
    }
}
