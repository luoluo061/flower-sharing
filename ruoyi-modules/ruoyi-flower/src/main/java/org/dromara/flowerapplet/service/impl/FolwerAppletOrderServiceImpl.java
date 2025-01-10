package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import jakarta.annotation.Resource;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.vo.FolwerDeliveryVo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.IFolwerDeliveryService;
import org.dromara.flower.service.IFolwerPickAddrService;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flowerapplet.domain.bo.FolwerAppletBasketBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDetailBo;
import org.dromara.flowerapplet.domain.bo.OrderParamBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.service.IFolwerAppletBasketService;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDetailService;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.flowerapplet.util.Arith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.domain.FolwerAppletOrder;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderMapper;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 订单Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletOrderServiceImpl implements IFolwerAppletOrderService {

    @Resource
    private final FolwerAppletOrderMapper baseMapper;

    private final IFolwerAppletOrderDetailService folwerAppletOrderDetailService;

    private final IFolwerAppletProductService productService;

    private final IFolwerAppletBasketService basketService;

    private final IFolwerPickAddrService folwerPickAddrService;

    private final IAppletUserInformationService appletUserInformationService;

    private final IFolwerSkuService folwerSkuService;

    private final IFolwerDeliveryService deliveryService;

    @Autowired
    private Snowflake snowflake;

    /**
     * 查询订单
     *
     * @param orderId 主键
     * @return 订单
     */
    @Override
    public FolwerAppletOrderVo queryById(Long orderId){
        return baseMapper.selectVoById(orderId);
    }

    /**
     * 分页查询订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletOrderVo> queryPageList(FolwerAppletOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletOrder> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单列表
     *
     * @param bo 查询条件
     * @return 订单列表
     */
    @Override
    public List<FolwerAppletOrderVo> queryList(FolwerAppletOrderBo bo) {
        LambdaQueryWrapper<FolwerAppletOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletOrder> buildQueryWrapper(FolwerAppletOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerAppletOrder::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerAppletOrder::getUserName, bo.getUserName());
        lqw.eq(bo.getMemberLevelId() != null, FolwerAppletOrder::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNumber()), FolwerAppletOrder::getOrderNumber, bo.getOrderNumber());
        lqw.eq(bo.getTotal() != null, FolwerAppletOrder::getTotal, bo.getTotal());
        lqw.eq(bo.getActualTotal() != null, FolwerAppletOrder::getActualTotal, bo.getActualTotal());
        lqw.eq(bo.getPayType() != null, FolwerAppletOrder::getPayType, bo.getPayType());
        lqw.eq(bo.getPayTime() != null, FolwerAppletOrder::getPayTime, bo.getPayTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerAppletOrder::getRemarks, bo.getRemarks());
        lqw.eq(bo.getStatus() != null, FolwerAppletOrder::getStatus, bo.getStatus());
        lqw.eq(bo.getDeliveryMode() != null, FolwerAppletOrder::getDeliveryMode, bo.getDeliveryMode());
        lqw.eq(bo.getDvyId() != null, FolwerAppletOrder::getDvyId, bo.getDvyId());
        lqw.like(StringUtils.isNotBlank(bo.getDvyName()), FolwerAppletOrder::getDvyName, bo.getDvyName());
        lqw.eq(StringUtils.isNotBlank(bo.getDvyFlowId()), FolwerAppletOrder::getDvyFlowId, bo.getDvyFlowId());
        lqw.eq(bo.getFreightAmount() != null, FolwerAppletOrder::getFreightAmount, bo.getFreightAmount());
        lqw.eq(bo.getAddrOrderId() != null, FolwerAppletOrder::getAddrOrderId, bo.getAddrOrderId());
        lqw.eq(bo.getDvyTime() != null, FolwerAppletOrder::getDvyTime, bo.getDvyTime());
        lqw.eq(bo.getFinallyTime() != null, FolwerAppletOrder::getFinallyTime, bo.getFinallyTime());
        lqw.eq(bo.getCancelTime() != null, FolwerAppletOrder::getCancelTime, bo.getCancelTime());
        lqw.eq(StringUtils.isNotBlank(bo.getCancelMsg()), FolwerAppletOrder::getCancelMsg, bo.getCancelMsg());
        return lqw;
    }

    /**
     * 新增订单
     *
     * @param bo 订单
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletOrderBo bo) {
        FolwerAppletOrder add = MapstructUtils.convert(bo, FolwerAppletOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOrderId(add.getOrderId());
        }
        return flag;
    }

    /**
     * 修改订单
     *
     * @param bo 订单
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletOrderBo bo) {
        FolwerAppletOrder update = MapstructUtils.convert(bo, FolwerAppletOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FolwerAppletOrderVo createOrder(OrderParamBo orderParam, Long userId) throws Exception {
        FolwerAppletOrderBo bo = new FolwerAppletOrderBo();
        //订单ID
        bo.setOrderId(snowflake.nextId());
        LoginUser user = LoginHelper.getLoginUser();
        bo.setUserId(user.getUserId());
        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(user.getUserId());
        bo.setUserName(appletUserInformationVo.getName());
        bo.setMemberLevelId(appletUserInformationVo.getMemberLevelId());
        // 组装获取用户提交的购物车商品项
        List<FolwerAppletProductVo> shopCartItems = this.getShopCartItemsByOrderItems(orderParam.getBasketIds(),orderParam.getProductItem(),user.getUserId());
        if (CollectionUtil.isEmpty(shopCartItems)) {
            throw new Exception("请选择您需要的商品");
        }
        //总价
        double total = 0.0;
        //折扣价
        double derlinePrice = 0.0;
        //运费
        double transfee = 0.0;
        //购物车商品总数
        int totalCount = 0;
        List<Double> productPriceList = new ArrayList<Double>();
        List<FolwerAppletOrderDetailVo> orderDetailVoList = new ArrayList<>();
        for (FolwerAppletProductVo shopCartItem : shopCartItems){
            // 获取sku信息
            FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(shopCartItem.getSkuId());
            if (shopCartItem == null || folwerSkuVo == null) {
                throw new Exception("购物车包含无法识别的商品");
            }
            if (shopCartItem.getStatus() != 1 || folwerSkuVo.getStatus() != 1) {
                throw new Exception("商品[" + shopCartItem.getProductName() + "]已下架");
            }
            //订单详情
            FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();
            folwerAppletOrderDetailBo.setOrderId(bo.getOrderId().toString());
            folwerAppletOrderDetailBo.setProductName(shopCartItem.getProductName());
            folwerAppletOrderDetailBo.setProductListPictureUrl(shopCartItem.getProductListPictureUrl());
            folwerAppletOrderDetailBo.setOrderPrice(shopCartItem.getOriPrice());
            double price = 0.0;
            if (!CollectionUtil.isEmpty(orderParam.getBasketIds()) && orderParam.getProductItem() == null) {
                FolwerAppletBasketBo folwerAppletBasketBo = new FolwerAppletBasketBo();
                folwerAppletBasketBo.setProdId(shopCartItem.getId());
                int count = Math.toIntExact(basketService.queryList(folwerAppletBasketBo).get(0).getBasketCount());
                price = Arith.mul(shopCartItem.getOriPrice(), count);
                totalCount = (int) Arith.add(count, totalCount);
                folwerAppletOrderDetailBo.setNumber((long) count);
            }else {
                price = Arith.mul(shopCartItem.getOriPrice(), orderParam.getProdCount());
                totalCount = orderParam.getProdCount();
                folwerAppletOrderDetailBo.setNumber(Long.valueOf(orderParam.getProdCount()));
            }
            folwerAppletOrderDetailBo.setSubtotal((long) price);

            // 优惠 （未完）
            if(price==0){
                total = Arith.sub(shopCartItem.getOriPrice(), total);
            }
            //运费相加
            total = Arith.add(price, shopCartItem.getDeliveryPrice());

            //运费
            transfee = Arith.add(shopCartItem.getDeliveryPrice(), transfee);

            //商品详情插入
            Boolean b = folwerAppletOrderDetailService.insertByBo(folwerAppletOrderDetailBo);
            if (b){
                FolwerAppletOrderDetailVo orderDetailVo = MapstructUtils.convert(folwerAppletOrderDetailBo, FolwerAppletOrderDetailVo.class);
                orderDetailVoList.add(orderDetailVo);
            }
        }
        bo.setTotal((long) total);
//        bo.setActualTotal((long) derlinePrice);
        bo.setRemarks(orderParam.getRemarks());
        bo.setStatus(0L);
        FolwerDeliveryVo folwerDeliveryVo = deliveryService.queryById(orderParam.getDvyId());
        bo.setDeliveryMode(folwerDeliveryVo.getDvyType());
        bo.setDvyId(orderParam.getDvyId());
        bo.setDvyName(folwerDeliveryVo.getDvyName());
        //物流单号
//        bo.setDvyFlowId();
        bo.setFreightAmount((long) transfee);
        FolwerPickAddrVo folwerPickAddrVo = folwerPickAddrService.queryById(orderParam.getAddrId());
        bo.setAddrOrderId(orderParam.getAddrId());

        FolwerAppletOrder add = MapstructUtils.convert(bo, FolwerAppletOrder.class);
        validEntityBeforeSave(add);

        boolean flag = baseMapper.insert(add) > 0;
        FolwerAppletOrderVo folwerAppletOrderVo = new FolwerAppletOrderVo();
        if (flag) {
            bo.setOrderId(add.getOrderId());
            folwerAppletOrderVo.setOrderDetails(orderDetailVoList);
            //放入缓存
            this.putConfirmOrderCache(userId.toString(), folwerAppletOrderVo);
        }
        return folwerAppletOrderVo;
    }

    @Override
    public List<FolwerAppletProductVo> getShopCartItemsByOrderItems(List<Long> basketIds, Long productItemItem, Long userId) {
        if (productItemItem == null && CollectionUtil.isEmpty(basketIds)) {
            return Collections.emptyList();
        }
        List<FolwerAppletProductVo> shopCartItems = new ArrayList<>();
        // 当立即购买时，没有提交的订单是没有购物车信息的
        if (!CollectionUtil.isEmpty(basketIds) && productItemItem == null) {
            for (Long basketId : basketIds){
                FolwerAppletBasketVo folwerAppletBasketVo = basketService.queryById(basketId);
                shopCartItems.add(productService.queryById(folwerAppletBasketVo.getProdId()));
            }
        }else if (productItemItem != null) {
            shopCartItems.add(productService.queryById(productItemItem));
        }

        return shopCartItems;
    }

    @Override
    @CachePut(cacheNames = "ConfirmOrderCache", key = "#userId")
    public FolwerAppletOrderVo putConfirmOrderCache(String userId, FolwerAppletOrderVo folwerAppletProductVo) {
        return folwerAppletProductVo;
    }

    @Override
    @Cacheable(cacheNames = "ConfirmOrderCache", key = "#userId")
    public FolwerAppletProductVo getConfirmOrderCache(String userId) {
        return null;
    }

    @Override
    @CacheEvict(cacheNames = "ConfirmOrderCache", key = "#userId")
    public void removeConfirmOrderCache(String userId) {
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除订单信息
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
