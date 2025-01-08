package org.dromara.flower.service;

import org.dromara.flower.domain.vo.MarketingCouponVo;
import org.dromara.flower.domain.bo.MarketingCouponBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 优惠卷管理Service接口
 *
 * @author chy
 * @date 2025-01-08
 */
public interface IMarketingCouponService {

    /**
     * 查询优惠卷管理
     *
     * @param id 主键
     * @return 优惠卷管理
     */
    MarketingCouponVo queryById(Long id);

    /**
     * 分页查询优惠卷管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠卷管理分页列表
     */
    TableDataInfo<MarketingCouponVo> queryPageList(MarketingCouponBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的优惠卷管理列表
     *
     * @param bo 查询条件
     * @return 优惠卷管理列表
     */
    List<MarketingCouponVo> queryList(MarketingCouponBo bo);

    /**
     * 新增优惠卷管理
     *
     * @param bo 优惠卷管理
     * @return 是否新增成功
     */
    Boolean insertByBo(MarketingCouponBo bo);

    /**
     * 修改优惠卷管理
     *
     * @param bo 优惠卷管理
     * @return 是否修改成功
     */
    Boolean updateByBo(MarketingCouponBo bo);

    /**
     * 校验并批量删除优惠卷管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    boolean deleteOneById(Long id);

    boolean updateState(Long id);

    List<MarketingCouponVo> queryPageUserList(Long id, PageQuery pageQuery);
}
