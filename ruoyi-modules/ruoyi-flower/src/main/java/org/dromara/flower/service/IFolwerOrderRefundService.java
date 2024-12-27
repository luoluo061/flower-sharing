package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单退款Service接口
 *
 * @author mlhxj
 * @date 2024-12-25
 */
public interface IFolwerOrderRefundService {

    /**
     * 查询订单退款
     *
     * @param refundId 主键
     * @return 订单退款
     */
    FolwerOrderRefundVo queryById(Long refundId);

    /**
     * 分页查询订单退款列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单退款分页列表
     */
    TableDataInfo<FolwerOrderRefundVo> queryPageList(FolwerOrderRefundBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单退款列表
     *
     * @param bo 查询条件
     * @return 订单退款列表
     */
    List<FolwerOrderRefundVo> queryList(FolwerOrderRefundBo bo);

    /**
     * 新增订单退款
     *
     * @param bo 订单退款
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerOrderRefundBo bo);

    /**
     * 修改订单退款
     *
     * @param bo 订单退款
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerOrderRefundBo bo);

    /**
     * 校验并批量删除订单退款信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
