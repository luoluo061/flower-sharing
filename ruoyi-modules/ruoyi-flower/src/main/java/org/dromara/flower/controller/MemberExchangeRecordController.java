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
import org.dromara.flower.domain.vo.MemberExchangeRecordVo;
import org.dromara.flower.domain.bo.MemberExchangeRecordBo;
import org.dromara.flower.service.IMemberExchangeRecordService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员中心--金币兑现记录
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/exchangeRecord")
public class MemberExchangeRecordController extends BaseController {

    private final IMemberExchangeRecordService memberExchangeRecordService;

    /**
     * 查询会员中心--兑换记录列表
     */
    @SaCheckPermission("flower:exchangeRecord:list")
    @GetMapping("/list")
    public TableDataInfo<MemberExchangeRecordVo> list(MemberExchangeRecordBo bo, PageQuery pageQuery) {
        return memberExchangeRecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员中心--兑换记录列表
     */
    @SaCheckPermission("flower:exchangeRecord:export")
    @Log(title = "会员中心--兑换记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MemberExchangeRecordBo bo, HttpServletResponse response) {
        List<MemberExchangeRecordVo> list = memberExchangeRecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员中心--兑换记录", MemberExchangeRecordVo.class, response);
    }

    /**
     * 获取会员中心--兑换记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:exchangeRecord:query")
    @GetMapping("/{id}")
    public R<MemberExchangeRecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(memberExchangeRecordService.queryById(id));
    }

    /**
     * 新增会员中心--兑换记录
     */
    @SaCheckPermission("flower:exchangeRecord:add")
    @Log(title = "会员中心--兑换记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MemberExchangeRecordBo bo) {
        return toAjax(memberExchangeRecordService.insertByBo(bo));
    }

    /**
     * 修改会员中心--兑换记录
     */
    @SaCheckPermission("flower:exchangeRecord:edit")
    @Log(title = "会员中心--兑换记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MemberExchangeRecordBo bo) {
        return toAjax(memberExchangeRecordService.updateByBo(bo));
    }

    /**
     * 删除会员中心--兑换记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:exchangeRecord:remove")
    @Log(title = "会员中心--兑换记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(memberExchangeRecordService.deleteWithValidByIds(List.of(ids), true));
    }

    /**
     * 金币兑换现金
     */
    @Log(title = "小程序用户信息", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping("cash")
    public R<Void> exchangeCash(@Validated(EditGroup.class) @RequestBody AppletUserInformationBo bo) {
        return toAjax(memberExchangeRecordService.exchangeCash(bo));
    }
}
