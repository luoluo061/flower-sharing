package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerOrderInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.domain.bo.FolwerOrderBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单Service接口
 *
 * @author Lion Li
 * @date 2024-12-25
 */
public interface IFolwerOrderService {

    /**
     * 查询订单
     *
     * @param orderId 主键
     * @return 订单
     */
    FolwerOrderVo queryById(Long orderId);

    /**
     * 查询订单详情
     *
     * @param orderId 主键
     * @return 订单
     */
    FolwerOrderInfoVo queryInfoById(Long orderId);

    /**
     * 分页查询订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单分页列表
     */
    TableDataInfo<FolwerOrderVo> queryPageList(FolwerOrderBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单列表
     *
     * @param bo 查询条件
     * @return 订单列表
     */
    List<FolwerOrderVo> queryList(FolwerOrderBo bo);

    /**
     * 新增订单
     *
     * @param bo 订单
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerOrderBo bo);

    /**
     * 修改订单
     *
     * @param bo 订单
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerOrderBo bo);

    /**
     * 校验并批量删除订单信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
