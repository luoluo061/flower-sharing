package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerCreditOrderVo;
import org.dromara.flower.domain.bo.FolwerCreditOrderBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 积分订单Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface IFolwerCreditOrderService {

    /**
     * 查询积分订单
     *
     * @param orderId 主键
     * @return 积分订单
     */
    FolwerCreditOrderVo queryById(Long orderId);

    /**
     * 分页查询积分订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分订单分页列表
     */
    TableDataInfo<FolwerCreditOrderVo> queryPageList(FolwerCreditOrderBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的积分订单列表
     *
     * @param bo 查询条件
     * @return 积分订单列表
     */
    List<FolwerCreditOrderVo> queryList(FolwerCreditOrderBo bo);

    /**
     * 新增积分订单
     *
     * @param bo 积分订单
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerCreditOrderBo bo);

    /**
     * 修改积分订单
     *
     * @param bo 积分订单
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerCreditOrderBo bo);

    /**
     * 校验并批量删除积分订单信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
