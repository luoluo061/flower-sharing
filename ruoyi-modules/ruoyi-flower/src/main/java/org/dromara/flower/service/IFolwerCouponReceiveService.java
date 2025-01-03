package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerCouponReceiveVo;
import org.dromara.flower.domain.bo.FolwerCouponReceiveBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 优惠券领取记录Service接口
 *
 * @author mlhxj
 * @date 2025-01-03
 */
public interface IFolwerCouponReceiveService {

    /**
     * 查询优惠券领取记录
     *
     * @param id 主键
     * @return 优惠券领取记录
     */
    FolwerCouponReceiveVo queryById(Long id);

    /**
     * 分页查询优惠券领取记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠券领取记录分页列表
     */
    TableDataInfo<FolwerCouponReceiveVo> queryPageList(FolwerCouponReceiveBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的优惠券领取记录列表
     *
     * @param bo 查询条件
     * @return 优惠券领取记录列表
     */
    List<FolwerCouponReceiveVo> queryList(FolwerCouponReceiveBo bo);

    /**
     * 新增优惠券领取记录
     *
     * @param bo 优惠券领取记录
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerCouponReceiveBo bo);

    /**
     * 修改优惠券领取记录
     *
     * @param bo 优惠券领取记录
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerCouponReceiveBo bo);

    /**
     * 校验并批量删除优惠券领取记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
