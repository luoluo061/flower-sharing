package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.FolwerShopCartItem;
import org.dromara.flowerapplet.domain.bo.FolwerAppletBasketBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 小程序购物车Service接口
 *
 * @author mlhxj
 * @date 2025-01-02
 */
public interface IFolwerBasketService {

    /**
     * 获取购物车商品列表
     *
     * @param userId 用户id
     * @return 购物车商品列表
     */
    FolwerShopCartItem getShopCartItems(Long userId);

    /**
     * 查询小程序购物车
     *
     * @param basketId 主键
     * @return 小程序购物车
     */
    FolwerAppletBasketVo queryById(Long basketId);

    /**
     * 分页查询小程序购物车列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序购物车分页列表
     */
    TableDataInfo<FolwerAppletBasketVo> queryPageList(FolwerAppletBasketBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的小程序购物车列表
     *
     * @param bo 查询条件
     * @return 小程序购物车列表
     */
    List<FolwerAppletBasketVo> queryList(FolwerAppletBasketBo bo);

    /**
     * 新增小程序购物车
     *
     * @param bo 小程序购物车
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletBasketBo bo);

    /**
     * 修改小程序购物车
     *
     * @param bo 小程序购物车
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletBasketBo bo);

    /**
     * 校验并批量删除小程序购物车信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
