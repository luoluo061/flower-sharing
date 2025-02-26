package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.flowerapplet.domain.FolwerAppletBasket;
import org.dromara.flowerapplet.domain.FolwerShopCartItem;
import org.dromara.flowerapplet.domain.bo.FolwerAppletBasketBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.mapper.FolwerAppletBasketMapper;
import org.dromara.flowerapplet.service.IFolwerAppletBasketService;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.dromara.flowerapplet.util.Arith;
import org.springframework.stereotype.Service;

import java.util.Date;
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
public class FolwerAppletBasketServiceImpl implements IFolwerAppletBasketService {

    private final FolwerAppletBasketMapper baseMapper;

    private final IFolwerAppletSkuService folwerAppletSkuService;

    @Override
    public FolwerShopCartItem getShopCartItems(Long userId) {
        // 在这个类里面要调用这里的缓存信息，并没有使用aop，所以不使用注解
//        String key = "shopcar:" + userId;
//        FolwerShopCartItem folwerShopCartItem = RedisUtils.getCacheObject(key);
//        if (folwerShopCartItem != null) {
//            return folwerShopCartItem;
//        }
        FolwerShopCartItem folwerShopCartItem = new FolwerShopCartItem();
        List<FolwerAppletBasketVo> folwerBasketVos = baseMapper.getShopCartItems(userId);
        if(folwerBasketVos != null){
            folwerBasketVos.forEach(folwerAppletBasketVo -> {
                if(folwerAppletBasketVo.getSkuId() != null){
                    FolwerAppletSkuVo folwerAppletSkuVo = folwerAppletSkuService.queryById(folwerAppletBasketVo.getSkuId());
                    if (folwerAppletSkuVo != null){
                        folwerAppletBasketVo.setPrice(folwerAppletSkuVo.getPrice());
                    }
                }
            });

            for (FolwerAppletBasketVo folwerBasketVo : folwerBasketVos) {
                folwerBasketVo.setTotalAmount((long) Arith.mul(folwerBasketVo.getBasketCount(), folwerBasketVo.getPrice()));
            }
            folwerShopCartItem.setFolwerBasketVos(folwerBasketVos);
            folwerShopCartItem.setProductTotalAmount(folwerBasketVos.stream().mapToDouble(FolwerAppletBasketVo::getTotalAmount).sum());
            folwerShopCartItem.setBasketCount(folwerBasketVos.stream().mapToLong(FolwerAppletBasketVo::getBasketCount).sum());
            //加入缓存 不过期
//            RedisUtils.setCacheObject(key, folwerShopCartItem);
        }

        return folwerShopCartItem;
    }

    /**
     * 查询小程序购物车
     *
     * @param basketId 主键
     * @return 小程序购物车
     */
    @Override
    public FolwerAppletBasketVo queryById(Long basketId){
        FolwerAppletBasketVo folwerAppletBasketVo = baseMapper.selectVoById(basketId);
        if (folwerAppletBasketVo == null){
            return null;
        }
        if(folwerAppletBasketVo.getSkuId() != null){
            folwerAppletBasketVo.setPrice(folwerAppletSkuService.queryById(folwerAppletBasketVo.getSkuId()).getPrice());
        }
        return folwerAppletBasketVo;
    }

    /**
     * 分页查询小程序购物车列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序购物车分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletBasketVo> queryPageList(FolwerAppletBasketBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletBasket> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletBasketVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        result.getRecords().forEach(item -> {
            if(item.getSkuId() != null){
                item.setPrice(folwerAppletSkuService.queryById(item.getSkuId()).getPrice());
            }
        });
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序购物车列表
     *
     * @param bo 查询条件
     * @return 小程序购物车列表
     */
    @Override
    public List<FolwerAppletBasketVo> queryList(FolwerAppletBasketBo bo) {
        LambdaQueryWrapper<FolwerAppletBasket> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletBasket> buildQueryWrapper(FolwerAppletBasketBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletBasket> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProdId() != null, FolwerAppletBasket::getProdId, bo.getProdId());
        lqw.eq(bo.getSkuId() != null, FolwerAppletBasket::getSkuId, bo.getSkuId());
        lqw.eq(bo.getSkuId() != null, FolwerAppletBasket::getUserId, bo.getUserId());
        lqw.eq(bo.getBasketCount() != null, FolwerAppletBasket::getBasketCount, bo.getBasketCount());
        lqw.eq(bo.getBasketDate() != null, FolwerAppletBasket::getBasketDate, bo.getBasketDate());
        return lqw;
    }

    /**
     * 新增小程序购物车
     *
     * @param bo 小程序购物车
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletBasketBo bo) {
        FolwerAppletBasketBo folwerAppletBasketBo = new FolwerAppletBasketBo();
        folwerAppletBasketBo.setUserId(bo.getUserId());
        folwerAppletBasketBo.setProdId(bo.getProdId());
        if (bo.getSkuId() != null){
            folwerAppletBasketBo.setSkuId(bo.getSkuId());
        }
        List<FolwerAppletBasketVo> folwerAppletBasketVos = this.queryList(folwerAppletBasketBo);
        if(folwerAppletBasketVos != null && folwerAppletBasketVos.size() > 0){
            for (FolwerAppletBasketVo folwerAppletBasketVo : folwerAppletBasketVos) {
                folwerAppletBasketVo.setBasketCount(folwerAppletBasketVo.getBasketCount() + bo.getBasketCount());
                BeanUtil.copyProperties(folwerAppletBasketVo, bo);
                bo.setBasketDate(new Date());
                Boolean b = this.updateByBo(bo);
                return b;
            }
        }

        FolwerAppletBasket add = MapstructUtils.convert(bo, FolwerAppletBasket.class);
        add.setBasketDate(new Date());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setBasketId(add.getBasketId());

//            String key = "shopcar:" + bo.getUserId();
//            FolwerShopCartItem folwerShopCartItem = RedisUtils.getCacheObject(key);
//            if (folwerShopCartItem != null) {
//                return folwerShopCartItem;
//            }
//            FolwerShopCartItem folwerShopCartItem = new FolwerShopCartItem();
//            List<FolwerAppletBasketVo> folwerBasketVos = baseMapper.getShopCartItems(bo.getUserId());
//            if(folwerBasketVos != null){
//                for (FolwerAppletBasketVo folwerBasketVo : folwerBasketVos) {
//                    folwerBasketVo.setTotalAmount((long) Arith.mul(folwerBasketVo.getBasketCount(), folwerBasketVo.getPrice()));
//                }
//                folwerShopCartItem.setFolwerBasketVos(folwerBasketVos);
//                folwerShopCartItem.setProductTotalAmount(folwerBasketVos.stream().mapToDouble(FolwerAppletBasketVo::getTotalAmount).sum());
//                folwerShopCartItem.setBasketCount(folwerBasketVos.stream().mapToLong(FolwerAppletBasketVo::getBasketCount).sum());
//                //不过期
//                RedisUtils.setCacheObject(key, folwerShopCartItem);
//            }
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
    public Boolean updateByBo(FolwerAppletBasketBo bo) {
        FolwerAppletBasket update = MapstructUtils.convert(bo, FolwerAppletBasket.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletBasket entity){
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
