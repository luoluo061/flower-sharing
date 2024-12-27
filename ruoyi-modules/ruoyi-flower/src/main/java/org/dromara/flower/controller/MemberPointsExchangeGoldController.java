package org.dromara.flower.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
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
import org.dromara.flower.domain.vo.MemberPointsExchangeGoldVo;
import org.dromara.flower.domain.bo.MemberPointsExchangeGoldBo;
import org.dromara.flower.service.IMemberPointsExchangeGoldService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员中心--积分兑换为金币
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/pointsExchangeGold")
public class MemberPointsExchangeGoldController extends BaseController {

    private final IMemberPointsExchangeGoldService memberPointsExchangeGoldService;

    /**
     * 查询会员中心--积分兑换为金币列表
     */
    @SaCheckPermission("flower:pointsExchangeGold:list")
    @GetMapping("/list")
    public TableDataInfo<MemberPointsExchangeGoldVo> list(MemberPointsExchangeGoldBo bo, PageQuery pageQuery) {
        return memberPointsExchangeGoldService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员中心--积分兑换为金币列表
     */
    @SaCheckPermission("flower:pointsExchangeGold:export")
    @Log(title = "会员中心--积分兑换为金币", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MemberPointsExchangeGoldBo bo, HttpServletResponse response) {
        List<MemberPointsExchangeGoldVo> list = memberPointsExchangeGoldService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员中心--积分兑换为金币", MemberPointsExchangeGoldVo.class, response);
    }

    /**
     * 获取会员中心--积分兑换为金币详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:pointsExchangeGold:query")
    @GetMapping("/{id}")
    public R<MemberPointsExchangeGoldVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(memberPointsExchangeGoldService.queryById(id));
    }

    /**
     * 新增会员中心--积分兑换为金币
     */
    @SaCheckPermission("flower:pointsExchangeGold:add")
    @Log(title = "会员中心--积分兑换为金币", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MemberPointsExchangeGoldBo bo) {
        return toAjax(memberPointsExchangeGoldService.insertByBo(bo));
    }

    /**
     * 修改会员中心--积分兑换为金币
     */
    @SaCheckPermission("flower:pointsExchangeGold:edit")
    @Log(title = "会员中心--积分兑换为金币", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MemberPointsExchangeGoldBo bo) {
        return toAjax(memberPointsExchangeGoldService.updateByBo(bo));
    }

    /**
     * 删除会员中心--积分兑换为金币
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:pointsExchangeGold:remove")
    @Log(title = "会员中心--积分兑换为金币", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(memberPointsExchangeGoldService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 积分兑换金币
     */
    @Log(title = "小程序用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("exchangeGold")
    public R<Void> exchangeGold(@Validated(EditGroup.class) @RequestBody AppletUserInformationBo bo) {
        return toAjax(memberPointsExchangeGoldService.exchangeGoldByBo(bo));
    }
}
