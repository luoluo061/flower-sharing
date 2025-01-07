package org.dromara.flower.service;

import org.dromara.flower.domain.vo.MarketingLogisticsExpressVo;
import org.dromara.flower.domain.bo.MarketingLogisticsExpressBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 营销推广-物流快递Service接口
 *
 * @author chy
 * @date 2025-01-06
 */
public interface IMarketingLogisticsExpressService {

    /**
     * 查询营销推广-物流快递
     *
     * @param id 主键
     * @return 营销推广-物流快递
     */
    MarketingLogisticsExpressVo queryById(Long id);

    /**
     * 分页查询营销推广-物流快递列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 营销推广-物流快递分页列表
     */
    TableDataInfo<MarketingLogisticsExpressVo> queryPageList(MarketingLogisticsExpressBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的营销推广-物流快递列表
     *
     * @param bo 查询条件
     * @return 营销推广-物流快递列表
     */
    List<MarketingLogisticsExpressVo> queryList(MarketingLogisticsExpressBo bo);

    /**
     * 新增营销推广-物流快递
     *
     * @param bo 营销推广-物流快递
     * @return 是否新增成功
     */
    Boolean insertByBo(MarketingLogisticsExpressBo bo);

    /**
     * 修改营销推广-物流快递
     *
     * @param bo 营销推广-物流快递
     * @return 是否修改成功
     */
    Boolean updateByBo(MarketingLogisticsExpressBo bo);

    /**
     * 校验并批量删除营销推广-物流快递信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
