package org.dromara.flower.service;

import org.dromara.flower.domain.vo.MarketingMemberPromotionPlanVo;
import org.dromara.flower.domain.bo.MarketingMemberPromotionPlanBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 营销推广-会员推广计划Service接口
 *
 * @author chy
 * @date 2024-12-31
 */
public interface IMarketingMemberPromotionPlanService {

    /**
     * 查询营销推广-会员推广计划
     *
     * @param id 主键
     * @return 营销推广-会员推广计划
     */
    MarketingMemberPromotionPlanVo queryById(Long id);

    /**
     * 分页查询营销推广-会员推广计划列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 营销推广-会员推广计划分页列表
     */
    TableDataInfo<MarketingMemberPromotionPlanVo> queryPageList(MarketingMemberPromotionPlanBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的营销推广-会员推广计划列表
     *
     * @param bo 查询条件
     * @return 营销推广-会员推广计划列表
     */
    List<MarketingMemberPromotionPlanVo> queryList(MarketingMemberPromotionPlanBo bo);

    /**
     * 新增营销推广-会员推广计划
     *
     * @param bo 营销推广-会员推广计划
     * @return 是否新增成功
     */
    Boolean insertByBo(MarketingMemberPromotionPlanBo bo);

    /**
     * 修改营销推广-会员推广计划
     *
     * @param bo 营销推广-会员推广计划
     * @return 是否修改成功
     */
    Boolean updateByBo(MarketingMemberPromotionPlanBo bo);

    /**
     * 校验并批量删除营销推广-会员推广计划信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    boolean updateStatus(Long id);


    /**
     *
     * @param code
     * @return
     *
     * 推广计划 实行了一次
     */
    boolean updateNumSurplusRewar(String code);

}
