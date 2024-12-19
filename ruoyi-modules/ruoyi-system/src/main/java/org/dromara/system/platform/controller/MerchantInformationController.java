package org.dromara.system.platform.controller;

import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import lombok.RequiredArgsConstructor;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.system.platform.domain.MerchantInformation;
import org.dromara.system.platform.domain.query.MerchantInformationQuery;
import org.dromara.system.platform.domain.vo.MerchantDetailedInformationVo;
import org.dromara.system.platform.domain.vo.MerchantInformationVo;
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
import org.dromara.system.platform.domain.bo.MerchantInformationBo;
import org.dromara.system.platform.service.IMerchantInformationService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 商户信息
 *
 * @author Lion Li
 * @date 2024-12-05
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/merchant")
public class MerchantInformationController extends BaseController {

    private final IMerchantInformationService merchantInformationService;

    /**
     * 查询商户信息列表
     */
    @SaCheckPermission("system:merchant:list")
    @GetMapping("/list")
    public TableDataInfo<MerchantInformationVo> list(MerchantInformationQuery query, PageQuery pageQuery) {
        return merchantInformationService.queryPageList(query, pageQuery);
    }

    /**
     * 导出商户信息列表
     */
    /*@SaCheckPermission("system:merchant:export")
    @Log(title = "商户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MerchantInformationBo bo, HttpServletResponse response) {
        List<MerchantInformationVo> list = merchantInformationService.queryList(bo);
        ExcelUtil.exportExcel(list, "商户信息", MerchantInformationVo.class, response);
    }*/

    /**
     * 获取商户信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:merchant:query")
    @GetMapping("/{id}")
    public R<MerchantDetailedInformationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        MerchantInformation merchantInformation = merchantInformationService.queryById(id);
        MerchantDetailedInformationVo vo = BeanUtil.toBean(merchantInformation, MerchantDetailedInformationVo.class);
        return R.ok(vo);
    }

    /**
     * 新增商户信息
     */
    @SaCheckPermission("system:merchant:add")
    @Log(title = "商户信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MerchantInformationBo bo) {
        return toAjax(merchantInformationService.insertByBo(bo));
    }

    /**
     * 修改商户信息
     */
    @SaCheckPermission("system:merchant:edit")
    @Log(title = "商户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@RequestBody MerchantInformationBo bo) {
        if(bo.getId() == null){
            return R.ok("id不能为空！");
        }
        return toAjax(merchantInformationService.updateByBo(bo));
    }

    /**
     * 删除商户信息
     *
     * @param ids 主键串
     */
    @SaCheckPermission("system:merchant:remove")
    @Log(title = "商户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(merchantInformationService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 修改商户状态
     * @param status
     * @param id
     * @return
     */
    @SaCheckPermission("system:merchant:update")
    @Log(title = "商户信息", businessType = BusinessType.UPDATE)
    @DeleteMapping("/status/{status}")
    public R<Void> updateStatus(@PathVariable String status,Long id){
        return toAjax(merchantInformationService.updateStatus(status,id));
    }
}
