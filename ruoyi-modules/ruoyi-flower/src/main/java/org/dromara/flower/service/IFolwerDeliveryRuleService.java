package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerDeliveryRuleVo;
import org.dromara.flower.domain.bo.FolwerDeliveryRuleBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 运费规则Service接口
 *
 * @author mlhxj
 * @date 2025-03-31
 */
public interface IFolwerDeliveryRuleService {

    /**
     * 查询运费规则
     *
     * @param deliveryRuleId 主键
     * @return 运费规则
     */
    FolwerDeliveryRuleVo queryById(Long deliveryRuleId);

    /**
     * 分页查询运费规则列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 运费规则分页列表
     */
    TableDataInfo<FolwerDeliveryRuleVo> queryPageList(FolwerDeliveryRuleBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的运费规则列表
     *
     * @param bo 查询条件
     * @return 运费规则列表
     */
    List<FolwerDeliveryRuleVo> queryList(FolwerDeliveryRuleBo bo);

    /**
     * 新增运费规则
     *
     * @param bo 运费规则
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerDeliveryRuleBo bo);

    /**
     * 修改运费规则
     *
     * @param bo 运费规则
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerDeliveryRuleBo bo);

    /**
     * 校验并批量删除运费规则信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
