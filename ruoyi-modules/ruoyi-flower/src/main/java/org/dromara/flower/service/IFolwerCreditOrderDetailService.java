package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerCreditOrderDetailVo;
import org.dromara.flower.domain.bo.FolwerCreditOrderDetailBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 积分订单详细Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface IFolwerCreditOrderDetailService {

    /**
     * 查询积分订单详细
     *
     * @param id 主键
     * @return 积分订单详细
     */
    FolwerCreditOrderDetailVo queryById(Long id);

    /**
     * 分页查询积分订单详细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分订单详细分页列表
     */
    TableDataInfo<FolwerCreditOrderDetailVo> queryPageList(FolwerCreditOrderDetailBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的积分订单详细列表
     *
     * @param bo 查询条件
     * @return 积分订单详细列表
     */
    List<FolwerCreditOrderDetailVo> queryList(FolwerCreditOrderDetailBo bo);

    /**
     * 新增积分订单详细
     *
     * @param bo 积分订单详细
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerCreditOrderDetailBo bo);

    /**
     * 修改积分订单详细
     *
     * @param bo 积分订单详细
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerCreditOrderDetailBo bo);

    /**
     * 校验并批量删除积分订单详细信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
