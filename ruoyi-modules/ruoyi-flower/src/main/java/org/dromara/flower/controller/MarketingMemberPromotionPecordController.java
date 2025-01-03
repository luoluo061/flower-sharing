package org.dromara.flower.controller;

import java.util.List;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.*;
import cn.dev33.satoken.annotation.SaCheckPermission;
import org.apache.poi.ss.formula.functions.T;
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
import org.dromara.flower.domain.vo.MarketingMemberPromotionPecordVo;
import org.dromara.flower.domain.bo.MarketingMemberPromotionPecordBo;
import org.dromara.flower.service.IMarketingMemberPromotionPecordService;
import org.dromara.common.mybatis.core.page.TableDataInfo;

/**
 * 会员推广记录
 *
 * @author chy
 * @date 2024-12-31
 */
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/flower/memberPromotionPecord")
public class MarketingMemberPromotionPecordController extends BaseController {

    private final IMarketingMemberPromotionPecordService marketingMemberPromotionPecordService;

    /**
     * 查询会员推广记录列表
     */
    @SaCheckPermission("flower:memberPromotionPecord:list")
    @GetMapping("/list")
    public TableDataInfo<MarketingMemberPromotionPecordVo> list(MarketingMemberPromotionPecordBo bo, PageQuery pageQuery) {
        return marketingMemberPromotionPecordService.queryPageList(bo, pageQuery);
    }

    /**
     * 导出会员推广记录列表
     */
    @SaCheckPermission("flower:memberPromotionPecord:export")
    @Log(title = "会员推广记录", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(MarketingMemberPromotionPecordBo bo, HttpServletResponse response) {
        List<MarketingMemberPromotionPecordVo> list = marketingMemberPromotionPecordService.queryList(bo);
        ExcelUtil.exportExcel(list, "会员推广记录", MarketingMemberPromotionPecordVo.class, response);
    }

    /**
     * 获取会员推广记录详细信息
     *
     * @param id 主键
     */
    @SaCheckPermission("flower:memberPromotionPecord:query")
    @GetMapping("/{id}")
    public R<MarketingMemberPromotionPecordVo> getInfo(@NotNull(message = "主键不能为空")
                                     @PathVariable Long id) {
        return R.ok(marketingMemberPromotionPecordService.queryById(id));
    }

    /**
     * 新增会员推广记录
     */
    @SaCheckPermission("flower:memberPromotionPecord:add")
    @Log(title = "会员推广记录", businessType = BusinessType.INSERT)
    @RepeatSubmit()
    @PostMapping()
    public R<Void> add(@Validated(AddGroup.class) @RequestBody MarketingMemberPromotionPecordBo bo) {
        return toAjax(marketingMemberPromotionPecordService.insertByBo(bo));
    }

    /**
     * 修改会员推广记录
     */
    @SaCheckPermission("flower:memberPromotionPecord:edit")
    @Log(title = "会员推广记录", businessType = BusinessType.UPDATE)
    @RepeatSubmit()
    @PutMapping()
    public R<Void> edit(@Validated(EditGroup.class) @RequestBody MarketingMemberPromotionPecordBo bo) {
        return toAjax(marketingMemberPromotionPecordService.updateByBo(bo));
    }

    /**
     * 删除会员推广记录
     *
     * @param ids 主键串
     */
    @SaCheckPermission("flower:memberPromotionPecord:remove")
    @Log(title = "会员推广记录", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public R<Void> remove(@NotEmpty(message = "主键不能为空")
                          @PathVariable Long[] ids) {
        return toAjax(marketingMemberPromotionPecordService.deleteWithValidByIds(List.of(ids), true));
    }


    /**
     * 判断该 “推广人” 是否，已经是 “被推广人了”
     *
     */

    @SaCheckPermission("flower:memberPromotionPecord:isPromoted")
    @GetMapping("/promoted/{id}")
    public R<MarketingMemberPromotionPecordVo> isPromoted(@NotNull(message = "主键不能为空") @PathVariable Long id){

        MarketingMemberPromotionPecordVo promoted = marketingMemberPromotionPecordService.getPromoted(id);
        Integer code= ObjectUtils.isEmpty(promoted)?200:500;
        //200 可以成为推荐人,500 已经是 “被推荐人了”

        if (code.intValue()==500){
            return R.fail(promoted);
        }
        return  R.ok(null);
    }






}
