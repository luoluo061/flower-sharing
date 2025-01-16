package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderRefundVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderRefundBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单退款Service接口
 *
 * @author mlhxj
 * @date 2025-01-15
 */
public interface IFolwerAppletOrderRefundService {

    /**
     * 查询订单退款
     *
     * @param refundId 主键
     * @return 订单退款
     */
    FolwerAppletOrderRefundVo queryById(Long refundId);

    /**
     * 分页查询订单退款列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单退款分页列表
     */
    TableDataInfo<FolwerAppletOrderRefundVo> queryPageList(FolwerAppletOrderRefundBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单退款列表
     *
     * @param bo 查询条件
     * @return 订单退款列表
     */
    List<FolwerAppletOrderRefundVo> queryList(FolwerAppletOrderRefundBo bo);

    /**
     * 新增订单退款
     *
     * @param bo 订单退款
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletOrderRefundBo bo);

    /**
     * 修改订单退款
     *
     * @param bo 订单退款
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletOrderRefundBo bo);

    /**
     * 校验并批量删除订单退款信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
