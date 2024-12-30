package org.dromara.flower.service;

import org.dromara.flower.domain.vo.MarketingAdvertisementVo;
import org.dromara.flower.domain.bo.MarketingAdvertisementBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 广告管理Service接口
 *
 * @author chy
 * @date 2024-12-31
 */
public interface IMarketingAdvertisementService {

    /**
     * 查询广告管理
     *
     * @param id 主键
     * @return 广告管理
     */
    MarketingAdvertisementVo queryById(Long id);

    /**
     * 分页查询广告管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 广告管理分页列表
     */
    TableDataInfo<MarketingAdvertisementVo> queryPageList(MarketingAdvertisementBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的广告管理列表
     *
     * @param bo 查询条件
     * @return 广告管理列表
     */
    List<MarketingAdvertisementVo> queryList(MarketingAdvertisementBo bo);

    /**
     * 新增广告管理
     *
     * @param bo 广告管理
     * @return 是否新增成功
     */
    Boolean insertByBo(MarketingAdvertisementBo bo);

    /**
     * 修改广告管理
     *
     * @param bo 广告管理
     * @return 是否修改成功
     */
    Boolean updateByBo(MarketingAdvertisementBo bo);

    /**
     * 校验并批量删除广告管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
