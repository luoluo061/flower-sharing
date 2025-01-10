package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.bo.OrderParamBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;
import java.util.List;

/**
 * 订单Service接口
 *
 * @author mlhxj
 * @date 2025-01-07
 */
public interface IFolwerAppletOrderService {

    /**
     * 查询订单
     *
     * @param orderId 主键
     * @return 订单
     */
    FolwerAppletOrderVo queryById(Long orderId);

    /**
     * 分页查询订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单分页列表
     */
    TableDataInfo<FolwerAppletOrderVo> queryPageList(FolwerAppletOrderBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单列表
     *
     * @param bo 查询条件
     * @return 订单列表
     */
    List<FolwerAppletOrderVo> queryList(FolwerAppletOrderBo bo);

    /**
     * 新增订单
     *
     * @param bo 订单
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletOrderBo bo);

    /**
     * 修改订单
     *
     * @param bo 订单
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletOrderBo bo);


    /**
     * 校验并批量删除订单信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /***
     * 创建订单
     * @param orderParam
     * @param userId
     * @return
     */
    FolwerAppletOrderVo createOrder(OrderParamBo orderParam, Long userId) throws Exception;

    /**
     * 获取购物车商品项
     *
     * @param basketIds 购物车id
     * @param productItemItem 订单项
     * @param userId    用户id
     * @return 购物车商品项
     */
    List<FolwerAppletProductVo> getShopCartItemsByOrderItems(List<Long> basketIds, Long productItemItem, Long userId);

    /**
     * 新增订单缓存
     * @param userId
     * @param folwerAppletProductVo
     * @return
     */
    FolwerAppletOrderVo putConfirmOrderCache(String userId ,FolwerAppletOrderVo folwerAppletProductVo);


    /**
     * 根据用户id获取订单缓存
     * @param userId
     * @return
     */
    FolwerAppletProductVo getConfirmOrderCache(String userId);

    /**
     * 根据用户id删除订单缓存
     * @param userId
     */
    void removeConfirmOrderCache(String userId);

}
