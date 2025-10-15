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
import org.dromara.flower.domain.vo.FolwerDeliveryTemperatureVo;
import org.dromara.flower.domain.bo.FolwerDeliveryTemperatureBo;
import org.dromara.flower.service.IFolwerDeliveryTemperatureService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 城市温度
 *
 * @author mlhxj
 * @date 2025-09-04
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/deliveryTemperature")
public class FolwerDeliveryTemperatureController extends BaseController {

    private final IFolwerDeliveryTemperatureService folwerDeliveryTemperatureService;

    /**
     * 查询城市温度列表
     */
    @SaCheckPermission("flower:deliveryTemperature:list")
    @GetMapping("/list")
    public TableDataInfo<FolwerDeliveryTemperatureVo> list(FolwerDeliveryTemperatureBo bo, PageQuery pageQuery) {
        return folwerDeliveryTemperatureService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出城市温度列表
     */
    @SaCheckPermission("flower:deliveryTemperature:export")
    @Log(title = "城市温度", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(FolwerDeliveryTemperatureBo bo, HttpServletResponse response) {
        List<FolwerDeliveryTemperatureVo> list = folwerDeliveryTemperatureService.queryList(bo);
        ExcelUtil.exportExcel(list, "城市温度", FolwerDeliveryTemperatureVo.class, response);
    }

    /**
     * 获取城市温度详细信息
     *
     * @param temperatureId 主键
     */
    @SaCheckPermission("flower:deliveryTemperature:query")
    @GetMapping("/{temperatureId}")
    public R<FolwerDeliveryTemperatureVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long temperatureId) {
        return R.ok(folwerDeliveryTemperatureService.queryById(temperatureId));
    }

    /**
     * 新增城市温度
     */
    @SaCheckPermission("flower:deliveryTemperature:add")
    @Log(title = "城市温度", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody FolwerDeliveryTemperatureBo bo) {
        return toAjax(folwerDeliveryTemperatureService.insertByBo(bo));
    }

    /**
     * 修改城市温度
     */
    @SaCheckPermission("flower:deliveryTemperature:edit")
    @Log(title = "城市温度", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody FolwerDeliveryTemperatureBo bo) {
        folwerDeliveryTemperatureService.updateByBo(bo);
        return toAjax(true);
    }

    /**
     * 删除城市温度
     *
     * @param temperatureIds 主键串
     */
    @SaCheckPermission("flower:deliveryTemperature:remove")
    @Log(title = "城市温度", businessType = BusinessType.DELETE)
    @DeleteMapping("/{temperatureIds}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] temperatureIds) {
        return toAjax(folwerDeliveryTemperatureService.deleteWithValidByIds(List.of(temperatureIds), true));
    }

    /**
     * 获取城市温度
     */
    @SaCheckPermission("flower:deliveryTemperature:getTemperature")
    @GetMapping("/getWeatherByTemperature")
    @Log(title = "城市温度", businessType = BusinessType.OTHER)
    public R<Double> getWeatherByTemperature(String cityCode) {
        return R.ok(folwerDeliveryTemperatureService.getWeatherByTemperature(cityCode));
    }
}
