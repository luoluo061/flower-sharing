package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerCouponVo;
import org.dromara.flower.domain.bo.FolwerCouponBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 优惠券管理Service接口
 *
 * @author mlhxj
 * @date 2025-01-03
 */
public interface IFolwerCouponService {

    /**
     * 查询优惠券管理
     *
     * @param couponId 主键
     * @return 优惠券管理
     */
    FolwerCouponVo queryById(Long couponId);

    /**
     * 分页查询优惠券管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠券管理分页列表
     */
    TableDataInfo<FolwerCouponVo> queryPageList(FolwerCouponBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的优惠券管理列表
     *
     * @param bo 查询条件
     * @return 优惠券管理列表
     */
    List<FolwerCouponVo> queryList(FolwerCouponBo bo);

    /**
     * 新增优惠券管理
     *
     * @param bo 优惠券管理
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerCouponBo bo);

    /**
     * 修改优惠券管理
     *
     * @param bo 优惠券管理
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerCouponBo bo);

    /**
     * 校验并批量删除优惠券管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
