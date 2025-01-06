package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.flowerapplet.domain.FolwerShopCartItem;
import org.dromara.flowerapplet.util.Arith;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerBasketBo;
import org.dromara.flowerapplet.domain.vo.FolwerBasketVo;
import org.dromara.flowerapplet.domain.FolwerBasket;
import org.dromara.flowerapplet.mapper.FolwerBasketMapper;
import org.dromara.flowerapplet.service.IFolwerBasketService;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序购物车Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-02
 */
@RequiredArgsConstructor
@Service
public class FolwerBasketServiceImpl implements IFolwerBasketService {

    private final FolwerBasketMapper baseMapper;

    @Override
    public FolwerShopCartItem getShopCartItems(String userId) {
        // 在这个类里面要调用这里的缓存信息，并没有使用aop，所以不使用注解
//        List<FolwerShopCartItemBo> shopCartItemDtoList = cacheManagerUtil.getCache("ShopCartItems", userId);
        String key = "shopcar:" + userId;
        FolwerShopCartItem folwerShopCartItem = RedisUtils.getCacheObject(key);
        if (folwerShopCartItem != null) {
            return folwerShopCartItem;
        }
        List<FolwerBasketVo> folwerBasketVos = baseMapper.getShopCartItems(userId);
        for (FolwerBasketVo folwerBasketVo : folwerBasketVos) {
            folwerBasketVo.setTotalAmount((long) Arith.mul(folwerBasketVo.getBasketCount(), folwerBasketVo.getPrice()));
        }
        folwerShopCartItem.setFolwerBasketVos(folwerBasketVos);
        folwerShopCartItem.setProductTotalAmount(folwerBasketVos.stream().mapToDouble(FolwerBasketVo::getTotalAmount).sum());
        folwerShopCartItem.setBasketCount(folwerBasketVos.stream().mapToLong(FolwerBasketVo::getBasketCount).sum());
        //不过期
        RedisUtils.setCacheObject(key, folwerShopCartItem);
        return folwerShopCartItem;
    }

    /**
     * 查询小程序购物车
     *
     * @param basketId 主键
     * @return 小程序购物车
     */
    @Override
    public FolwerBasketVo queryById(Long basketId){
        return baseMapper.selectVoById(basketId);
    }

    /**
     * 分页查询小程序购物车列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序购物车分页列表
     */
    @Override
    public TableDataInfo<FolwerBasketVo> queryPageList(FolwerBasketBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerBasket> lqw = buildQueryWrapper(bo);
        Page<FolwerBasketVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序购物车列表
     *
     * @param bo 查询条件
     * @return 小程序购物车列表
     */
    @Override
    public List<FolwerBasketVo> queryList(FolwerBasketBo bo) {
        LambdaQueryWrapper<FolwerBasket> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerBasket> buildQueryWrapper(FolwerBasketBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerBasket> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProdId() != null, FolwerBasket::getProdId, bo.getProdId());
        lqw.eq(bo.getSkuId() != null, FolwerBasket::getSkuId, bo.getSkuId());
        lqw.eq(StringUtils.isNotBlank(bo.getUserId()), FolwerBasket::getUserId, bo.getUserId());
        lqw.eq(bo.getBasketCount() != null, FolwerBasket::getBasketCount, bo.getBasketCount());
        lqw.eq(bo.getBasketDate() != null, FolwerBasket::getBasketDate, bo.getBasketDate());
        return lqw;
    }

    /**
     * 新增小程序购物车
     *
     * @param bo 小程序购物车
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerBasketBo bo) {
        FolwerBasket add = MapstructUtils.convert(bo, FolwerBasket.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setBasketId(add.getBasketId());
        }
        return flag;
    }

    /**
     * 修改小程序购物车
     *
     * @param bo 小程序购物车
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerBasketBo bo) {
        FolwerBasket update = MapstructUtils.convert(bo, FolwerBasket.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerBasket entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除小程序购物车信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
