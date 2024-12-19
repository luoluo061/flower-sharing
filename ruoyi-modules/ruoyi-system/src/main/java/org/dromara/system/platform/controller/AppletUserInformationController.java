package org.dromara.system.platform.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.idempotent.annotation.RepeatSubmit;
import org.dromara.common.log.annotation.Log;
import org.dromara.common.log.enums.BusinessType;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.web.core.BaseController;
import org.dromara.system.platform.domain.bo.AppletUserInformationBo;
import org.dromara.system.platform.domain.query.AppletUserInformationQuery;
import org.dromara.system.platform.domain.vo.AppletUserInformationVo;
import org.dromara.system.platform.domain.vo.AppletUserOrderNumVo;
import org.dromara.system.platform.domain.vo.AppletUserOrderVo;
import org.dromara.system.platform.service.IAppletUserInformationService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小程序用户信息
 * 前端访问路由地址为:/system/appletUser
 *
 * @author LionLi
 * @date 2024-11-19
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/system/appletUser")
public class AppletUserInformationController extends BaseController {

    private final IAppletUserInformationService appletUserInformationService;

    /**
     * 查询小程序用户信息列表
     */
    @SaCheckPermission("system:appletUser:list")
    @GetMapping("/list")
    public TableDataInfo<AppletUserInformationVo> list(AppletUserInformationQuery query, PageQuery pageQuery) {
        return appletUserInformationService.queryPageList(query, pageQuery);
    }

    /**
     * 导出小程序用户信息列表
     */
    /*@SaCheckPermission("system:appletUser:export")
    @Log(title = "小程序用户信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(AppletUserInformationQuery query, HttpServletResponse response) {
        List<AppletUserInformationVo> list = appletUserInformationService.queryList(query);
        ExcelUtil.exportExcel(list, "小程序用户信息", AppletUserInformationVo.class, response);
    }
*/
    /**
     * 获取小程序用户信息详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("system:appletUser:query")
    @GetMapping("/{id}")
    public R<AppletUserInformationVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(appletUserInformationService.queryById(id));
    }

    /**
     * 新增小程序用户信息
     */
    @SaCheckPermission("system:appletUser:add")
    @Log(title = "小程序用户信息", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody AppletUserInformationBo bo) {
        return toAjax(appletUserInformationService.insertByBo(bo));
    }

    /**
     * 修改小程序用户信息
     */
    @SaCheckPermission("system:appletUser:edit")
    @Log(title = "小程序用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@RequestBody AppletUserInformationBo bo) {
        if(bo.getUserId() == null){
            return R.ok("userId不能为空！");
        }
        return toAjax(appletUserInformationService.updateByBo(bo));
    }

    /**
     * 删除小程序用户信息
     *
     * @param ids 主键串
     */
    /*@SaCheckPermission("system:appletUser:remove")
    @Log(title = "小程序用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(appletUserInformationService.deleteWithValidByIds(List.of(ids), true));
    }*/

    /**
     * 编辑用户状态
     * @param status
     * @param id
     * @return
     */
    @SaCheckPermission("system:appletUser:update")
    @Log(title = "小程序用户信息", businessType = BusinessType.UPDATE)
    @GetMapping("/status/{status}")
    public R<Void> updateStatus(@PathVariable String status,Long id) {
        return toAjax(appletUserInformationService.updateStatus(id,status));

    }

    /**
     * 获取小程序用户订单信息
     * @param id
     * @return
     */
    @SaCheckPermission("system:appletUser:order")
    @GetMapping("/order")
    public R<List<AppletUserOrderVo>> getAppletUserOrder(Long id) {
        return R.ok(appletUserInformationService.getAppletUserOrderById(id));
    }

    /**
     * 获取小程序用户订单数
     * @param id
     * @return
     */
    @SaCheckPermission("system:appletUser:orderNumber")
    @GetMapping("/orderNumber")
    public R<AppletUserOrderNumVo> getAppletUserOrderNum(Long id) {
        return R.ok(appletUserInformationService.getAppletUserOrderNumById(id));
    }


}
