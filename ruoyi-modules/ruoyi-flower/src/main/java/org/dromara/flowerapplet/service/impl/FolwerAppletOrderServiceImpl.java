package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Snowflake;
import com.wechat.pay.java.service.payments.model.Transaction;
import com.wechat.pay.java.service.profitsharing.model.AddReceiverResponse;
import com.wechat.pay.java.service.profitsharing.model.OrdersEntity;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import jakarta.annotation.Resource;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.common.mypay.domain.WxPayRequest;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.common.mypay.server.SharingService;
import org.dromara.common.mypay.utils.IpUtils;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.flower.domain.bo.FolwerCreditSetBo;
import org.dromara.flower.domain.bo.FolwerOrderSetBo;
import org.dromara.flower.domain.bo.FolwerPickAddrBo;
import org.dromara.flower.domain.bo.MarketingMemberPromotionPecordBo;
import org.dromara.flower.domain.vo.*;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.*;
import org.dromara.flowerapplet.domain.PayParam;
import org.dromara.common.mypay.domain.PayProfitsharingParam;
import org.dromara.flowerapplet.domain.bo.*;
import org.dromara.flowerapplet.domain.vo.*;
import org.dromara.flowerapplet.service.*;
import org.dromara.flowerapplet.util.Arith;
import org.dromara.flowerapplet.util.SnowflakeIdGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.FolwerAppletOrder;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderMapper;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Duration;
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

    private static final String CONFIRM_ORDER_CACHE_KEY  = "order:";

    /**
     * 个人OpenID
     */
    public static final String PERSONAL_OPENID = "PERSONAL_OPENID";

    /**
     * 合作伙伴
     */
    public static final String PARTNER = "PARTNER";
    private static final Logger log = LoggerFactory.getLogger(FolwerAppletOrderServiceImpl.class);

    @Resource
    private final FolwerAppletOrderMapper baseMapper;

    private final IFolwerAppletOrderDetailService folwerAppletOrderDetailService;

    private final IFolwerAppletProductService productService;

    private final IFolwerAppletBasketService basketService;

    private final IFolwerPickAddrService folwerPickAddrService;

    private final IAppletUserInformationService appletUserInformationService;

    private final IFolwerDeliveryService deliveryService;

    private final IMarketingCouponService marketingCouponService;

    private final IOneselfMemberLevelPrivilegeService oneselfMemberLevelPrivilegeService;

    private final IMarketingMemberPromotionPecordService marketingMemberPromotionPecordService;

    private final IFolwerAppletSkuService folwerAppletSkuService;

    private final IFolwerOrderSetService folwerOrderSetService;

    private final IFolwerCreditSetService folwerCreditSetService;

    private final IFolwerAppletProductService folwerAppletProductService;

    private final IFolwerAppletOrderDvyService folwerAppletOrderDvyService;

//    private final IFolwerAppletDeliveryPriceService folwerAppletDeliveryPriceService;

    @Resource
    private Snowflake snowflake;

    @Resource
    private final IPayService payService;

    @Resource
    private final SharingService sharingService;

    /**
     * 查询订单
     *
     * @param orderId 主键
     * @return 订单
     */
    @Override
    public FolwerAppletOrderVo queryById(Long orderId){
        FolwerAppletOrderVo folwerAppletOrderVo = baseMapper.selectVoById(orderId);
        if (folwerAppletOrderVo != null){
            FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();
            folwerAppletOrderDetailBo.setOrderId(String.valueOf(folwerAppletOrderVo.getOrderId()));
            List<FolwerAppletOrderDetailVo> folwerAppletOrderDetailVos = folwerAppletOrderDetailService.queryList(folwerAppletOrderDetailBo);
            if (folwerAppletOrderDetailVos != null){
                folwerAppletOrderVo.setOrderDetails(folwerAppletOrderDetailVos);
                int totalNum = 0;
                for (FolwerAppletOrderDetailVo folwerAppletOrderDetailVo : folwerAppletOrderDetailVos){
                    totalNum += folwerAppletOrderDetailVo.getNumber();
                }
                folwerAppletOrderVo.setTotalNum((long) totalNum);
            }
        }
        return folwerAppletOrderVo;
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
        if (!result.getRecords().isEmpty()){
            result.getRecords().forEach(folwerAppletOrderVo -> {
                FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();
                folwerAppletOrderDetailBo.setOrderId(String.valueOf(folwerAppletOrderVo.getOrderId()));
                List<FolwerAppletOrderDetailVo> folwerAppletOrderDetailVos = folwerAppletOrderDetailService.queryList(folwerAppletOrderDetailBo);
                if (folwerAppletOrderDetailVos != null){
                    folwerAppletOrderVo.setOrderDetails(folwerAppletOrderDetailVos);
                    int totalNum = 0;
                    for (FolwerAppletOrderDetailVo folwerAppletOrderDetailVo : folwerAppletOrderDetailVos){
                        totalNum += folwerAppletOrderDetailVo.getNumber();
                    }
                    folwerAppletOrderVo.setTotalNum((long) totalNum);
                }
            });
        }

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
        lqw.eq(bo.getFreightAmount() != null, FolwerAppletOrder::getFreightAmount, bo.getFreightAmount());
        lqw.eq(bo.getAddrOrderId() != null, FolwerAppletOrder::getAddrOrderId, bo.getAddrOrderId());
//        lqw.eq(bo.getDvyTime() != null, FolwerAppletOrder::getDvyTime, bo.getDvyTime());
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
    @Transactional(rollbackFor = Exception.class)
    public R<String> insertByBo(OrderParamBo bo) throws Exception {
        if (bo.getUserId() == null){
            return R.fail("用户ID不能为空");
        }
        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(Long.valueOf(bo.getUserId()));
        if (appletUserInformationVo == null){
            return R.fail("用户不存在");
        }
        //购物车商品总数
        int totalCount = 0;
        //总价
        BigDecimal total = new BigDecimal(0);
        //折扣价
        BigDecimal derlinePrice  = new BigDecimal(0);
        //运费
        BigDecimal transfee  = new BigDecimal(0);

        List<FolwerAppletOrderDetailBo> folwerAppletOrderDetailBos = new ArrayList<>();
        //立即购买
        if(bo.getProductItem() != null){
            FolwerAppletProductVo folwerAppletProductVo = productService.queryById(Long.valueOf(bo.getProductItem()));
            if (folwerAppletProductVo == null){
                return R.fail("商品不存在");
            }
            if (bo.getProdCount() == null){
                return R.fail("商品数量不能为空");
            }
            if (bo.getSkuId() == null){
                return R.fail("规格ID不能为空");
            }
            //订单详情
            FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();

            FolwerAppletSkuVo folwerAppletSkuVo = folwerAppletSkuService.selsctById(Long.valueOf(bo.getSkuId()));
            total = folwerAppletSkuVo.getPrice().multiply(BigDecimal.valueOf(bo.getProdCount()));
            folwerAppletOrderDetailBo.setOrderPrice(folwerAppletSkuVo.getPrice());
            folwerAppletOrderDetailBo.setProductListPictureUrl(folwerAppletSkuVo.getSkuPicid());

            folwerAppletOrderDetailBo.setProductId(folwerAppletProductVo.getId());
            folwerAppletOrderDetailBo.setProductName(folwerAppletProductVo.getProductName());

            folwerAppletOrderDetailBo.setNumber(Long.valueOf(bo.getProdCount()));
            BigDecimal subtotal = total;//folwerAppletProductVo.getOriPrice().multiply(BigDecimal.valueOf(bo.getProdCount()));
            folwerAppletOrderDetailBo.setSubtotal(subtotal);
            if (bo.getSkuId() != null){
                folwerAppletOrderDetailBo.setSkuId(Long.valueOf(bo.getSkuId()));
            }
            folwerAppletOrderDetailBos.add(folwerAppletOrderDetailBo);
        }
        //购物车购买
        if(bo.getBasketIds() != null){
            for (String basketId : bo.getBasketIds()) {
                FolwerAppletBasketVo basketVo = basketService.queryById(Long.valueOf(basketId));
                if (basketVo == null){
                    return R.fail("购物车不存在");
                }
                if (!basketVo.getStatus().equals(1L)){
                    return R.fail("购物车状态为下架");
                }

                FolwerAppletProductVo productVo = productService.queryById(basketVo.getProdId());
                if (productVo == null){
                    return R.fail("商品不存在");
                }
                //订单详情
                FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();

                FolwerAppletSkuVo folwerAppletSkuVo = folwerAppletSkuService.selsctById(Long.valueOf(basketVo.getSkuId()));
                BigDecimal price = folwerAppletSkuVo.getPrice().multiply(BigDecimal.valueOf(basketVo.getBasketCount()));
                total = total.add(price);
                folwerAppletOrderDetailBo.setOrderPrice(folwerAppletSkuVo.getPrice());
                folwerAppletOrderDetailBo.setProductListPictureUrl(folwerAppletSkuVo.getSkuPicid());
                BigDecimal priceDel = productVo.getDeliveryPrice().multiply(BigDecimal.valueOf(basketVo.getBasketCount()));

                folwerAppletOrderDetailBo.setProductId(productVo.getId());
                folwerAppletOrderDetailBo.setProductName(productVo.getProductName());

                folwerAppletOrderDetailBo.setNumber(basketVo.getBasketCount());
                BigDecimal subtotal = price;//productVo.getOriPrice().multiply(BigDecimal.valueOf(basketVo.getBasketCount()));
                folwerAppletOrderDetailBo.setSubtotal(subtotal);
                if (basketVo.getSkuId() != null){
                    folwerAppletOrderDetailBo.setSkuId(basketVo.getSkuId());
                }

                folwerAppletOrderDetailBos.add(folwerAppletOrderDetailBo);
            }
//            if (bo.getDvyId() != null) {
//                transfee = folwerAppletDeliveryPriceService.calculateFreight(Long.valueOf(bo.getDvyId()), Long.valueOf(bo.getUserId()), bo.getBasketIds(), Long.valueOf(bo.getSkuId()), bo.getProdCount(), bo.getInsulationNum());   //从计算中获取运费
//            }


        }

        FolwerAppletOrderBo orderBo = new FolwerAppletOrderBo();
        orderBo.setUserId(String.valueOf(Long.valueOf(bo.getUserId())));
        orderBo.setUserName(appletUserInformationVo.getName());
        orderBo.setMemberLevelId(String.valueOf(appletUserInformationVo.getMemberLevelId()));

        orderBo.setTotal(total);
        FolwerCreditSetBo folwerCreditSetBo = new FolwerCreditSetBo();
        List<FolwerCreditSetVo> folwerCreditSetVos = folwerCreditSetService.queryList(folwerCreditSetBo);
        double points = Arith.div(folwerCreditSetVos.get(0).getGoodsCredit(), folwerCreditSetVos.get(0).getGoodsPurchase(), 2);
        orderBo.setRebate(orderBo.getTotal().multiply(BigDecimal.valueOf(points)).longValue());
        orderBo.setRemarks(bo.getRemarks());
        orderBo.setStatus(0L);

//        orderBo.setFreightAmount(transfee);
        orderBo.setAddrOrderId(bo.getAddrId());

//        orderBo.setActualTotal(total.add(transfee));

        FolwerAppletOrder add = MapstructUtils.convert(orderBo, FolwerAppletOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            orderBo.setOrderId(String.valueOf(add.getOrderId()));
            for (FolwerAppletOrderDetailBo folwerAppletOrderDetailBo : folwerAppletOrderDetailBos){
                folwerAppletOrderDetailBo.setOrderId(orderBo.getOrderId().toString());
                //商品详情插入
                Boolean b = folwerAppletOrderDetailService.insertByBo(folwerAppletOrderDetailBo);
            }

            if (bo.getDvyId() != null) {
                FolwerAppletOrderDvyBo folwerAppletOrderDvyBo = new FolwerAppletOrderDvyBo();
                folwerAppletOrderDvyBo.setOrderId(add.getOrderId());
                folwerAppletOrderDvyBo.setDvyId(Long.valueOf(bo.getDvyId()));
                folwerAppletOrderDvyBo.setInsulationNum(Long.valueOf(bo.getInsulationNum()));
                folwerAppletOrderDvyBo.setBasketIds(bo.getBasketIds());
                folwerAppletOrderDvyBo.setSkuId(bo.getSkuId());
                folwerAppletOrderDvyBo.setProdCount(bo.getProdCount());
                folwerAppletOrderDvyBo.setUserId(bo.getUserId());
                //从计算中获取运费
                FolwerAppletOrderDvyVo folwerAppletOrderDvyVo = folwerAppletOrderDvyService.insertByBo(folwerAppletOrderDvyBo);
                if (folwerAppletOrderDvyVo != null){
                    transfee = folwerAppletOrderDvyVo.getPackingAmount();
                }else {
                    transfee = new BigDecimal(0);
                }
                FolwerAppletOrderVo folwerAppletOrderVo = queryById(add.getOrderId());
                FolwerAppletOrder folwerAppletOrder = new FolwerAppletOrder();
                BeanUtil.copyProperties(folwerAppletOrderVo, folwerAppletOrder);
                folwerAppletOrder.setFreightAmount(transfee);
                folwerAppletOrder.setActualTotal(total.add(transfee));
                int i = baseMapper.updateById(folwerAppletOrder);
            }

            //放入缓存
            FolwerAppletOrderVo folwerAppletOrderVo =  this.queryById(add.getOrderId());
            RedisUtils.setCacheObject(CONFIRM_ORDER_CACHE_KEY + add.getOrderId(), folwerAppletOrderVo.getOrderId(), Duration.ofMinutes(15));
        }
        return R.ok(add.getOrderId().toString());
    }


    @Override
    public R<String> updateByOrderParam(OrderParamBo bo) throws Exception {
        if (bo.getOrderId() == null){
            return R.fail("订单ID不能为空");
        }
        FolwerAppletOrderVo folwerAppletOrderVo = baseMapper.selectVoById(Long.valueOf(bo.getOrderId()));
//        FolwerAppletOrderDvyBo folwerAppletOrderDvyBo = new FolwerAppletOrderDvyBo();
//        folwerAppletOrderDvyBo.setOrderId(folwerAppletOrderVo.getOrderId());
//        folwerAppletOrderDvyBo.setDvyId(Long.valueOf(bo.getDvyId()));
//        folwerAppletOrderDvyBo.setInsulationNum(Long.valueOf(bo.getInsulationNum()));
//        folwerAppletOrderDvyBo.setBasketIds(bo.getBasketIds());
//        folwerAppletOrderDvyBo.setSkuId(bo.getSkuId());
//        folwerAppletOrderDvyBo.setProdCount(bo.getProdCount());
//        BigDecimal transfee = folwerAppletOrderDvyService.insertByBo(folwerAppletOrderDvyBo).getPackingAmount();   //从计算中获取运费
//
//        BigDecimal transfeeOld = folwerAppletOrderVo.getFreightAmount();
//        BigDecimal total = folwerAppletOrderVo.getTotal().subtract(transfeeOld);

        FolwerAppletOrder update = BeanUtil.copyProperties(folwerAppletOrderVo, FolwerAppletOrder.class);
//        Long trans = transfee.longValue();
//        update.setFreightAmount(transfee);
//        update.setActualTotal(total.add(transfee));
        validEntityBeforeSave(update);
        boolean b = baseMapper.updateById(update) > 0;
        if(b){
//            FolwerAppletOrderVo folwerAppletOrderVo = queryById(update.getOrderId());
            if (folwerAppletOrderVo.getStatus() == 0L && RedisUtils.getCacheObject(CONFIRM_ORDER_CACHE_KEY + folwerAppletOrderVo.getOrderId()) != null){
                RedisUtils.setCacheObject(CONFIRM_ORDER_CACHE_KEY + folwerAppletOrderVo.getOrderId(), folwerAppletOrderVo.getOrderId(), true);
            }
        }
        return R.ok(bo.getOrderId().toString());
    }

    /**
     * 新增订单
     *
     * @param bo 订单
     * @return 是否新增成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<String> createByOrder(OrderParamBo bo) throws Exception {
        if (bo.getUserId() == null){
//            throw new Exception("用户ID不能为空");
            return R.fail("用户ID不能为空");
        }
        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(Long.valueOf(bo.getUserId()));
        if (appletUserInformationVo == null){
//            throw new Exception("用户不存在");
            return R.fail("用户不存在");
        }

        //总价
//        double total = 0.0;
//        //折扣价
//        double derlinePrice = 0.0;
//        //运费
//        double transfee = 0.0;
        //购物车商品总数
        int totalCount = 0;

        //总价
        BigDecimal total = new BigDecimal(0);
        //折扣价
        BigDecimal derlinePrice  = new BigDecimal(0);
        //运费
        BigDecimal transfee  = new BigDecimal(0);

//        List<FolwerAppletProductVo> productVos = new ArrayList<>();
        List<FolwerAppletOrderDetailBo> folwerAppletOrderDetailBos = new ArrayList<>();
        //立即购买
        if(bo.getProductItem() != null){
            FolwerAppletProductVo folwerAppletProductVo = productService.queryById(Long.valueOf(bo.getProductItem()));
            if (folwerAppletProductVo == null){
//                throw new Exception("商品不存在");
                return R.fail("商品不存在");
            }
            if (bo.getProdCount() == null){
//                throw new Exception("商品数量不能为空");
                return R.fail("商品数量不能为空");
            }
            if (bo.getSkuId() == null){
//                throw new Exception("规格ID不能为空");
                return R.fail("规格ID不能为空");
            }
            //订单详情
            FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();
            //单规格和多规格（目前不要单规格了）
//            if (bo.getSkuId().isEmpty()){
//                total = Arith.mul(folwerAppletProductVo.getOriPrice() ,bo.getProdCount());
//                folwerAppletOrderDetailBo.setOrderPrice(folwerAppletProductVo.getOriPrice());
//            } else {
//                FolwerAppletSkuVo folwerAppletSkuVo = folwerAppletSkuService.queryById(Long.valueOf(bo.getSkuId()));
//                total = folwerAppletSkuVo.getPrice().multiply(BigDecimal.valueOf(bo.getProdCount()));
//                folwerAppletOrderDetailBo.setOrderPrice(folwerAppletSkuVo.getPrice());
//            }
//            total = Arith.mul(folwerAppletProductVo.getOriPrice() ,bo.getProdCount());
            FolwerAppletSkuVo folwerAppletSkuVo = folwerAppletSkuService.queryById(Long.valueOf(bo.getSkuId()));
            total = folwerAppletSkuVo.getPrice().multiply(BigDecimal.valueOf(bo.getProdCount()));
            folwerAppletOrderDetailBo.setOrderPrice(folwerAppletSkuVo.getPrice());
            folwerAppletOrderDetailBo.setProductListPictureUrl(folwerAppletSkuVo.getSkuPicid());
//            transfee = folwerAppletProductVo.getDeliveryPrice();      //从商品中获取运费
            //优惠卷
//            if(bo.getUserChangeCoupon() != null){
//                //0:满减
//                if(bo.getUserChangeCoupon().equals(0)){
////                    total = Arith.mul(folwerAppletProductVo.getOriPrice() ,bo.getProdCount());
//                    MarketingCouponVo marketingCouponVo = marketingCouponService.queryById(Long.valueOf(bo.getCouponId()));
////                    derlinePrice = marketingCouponVo.getCouponSum().doubleValue();
//
//                    derlinePrice = derlinePrice.add(BigDecimal.valueOf(marketingCouponVo.getCouponSum().doubleValue()));
//
//                }else if(bo.getUserChangeCoupon().equals(1)){       //1：花券
////                    total = Arith.mul(folwerAppletProductVo.getOriPrice() ,bo.getProdCount());
////                    OneselfMemberLevelPrivilegeVo oneselfMemberLevelPrivilegeVo = oneselfMemberLevelPrivilegeService.queryById(Long.valueOf(bo.getCouponId()));
////                    derlinePrice = Arith.mul(folwerAppletProductVo.getOriPrice(), bo.getCouponCount());
//                }
//            }
//                folwerAppletOrderDetailBo.setOrderId(bo.getOrderId().toString());
            folwerAppletOrderDetailBo.setProductId(folwerAppletProductVo.getId());
            folwerAppletOrderDetailBo.setProductName(folwerAppletProductVo.getProductName());

            folwerAppletOrderDetailBo.setNumber(Long.valueOf(bo.getProdCount()));
            BigDecimal subtotal = total;//folwerAppletProductVo.getOriPrice().multiply(BigDecimal.valueOf(bo.getProdCount()));
            folwerAppletOrderDetailBo.setSubtotal(subtotal);
            if (bo.getSkuId() != null){
                folwerAppletOrderDetailBo.setSkuId(Long.valueOf(bo.getSkuId()));
            }
            folwerAppletOrderDetailBos.add(folwerAppletOrderDetailBo);
        }
        //购物车购买
        if(bo.getBasketIds() != null){
            for (String basketId : bo.getBasketIds()) {
                FolwerAppletBasketVo basketVo = basketService.queryById(Long.valueOf(basketId));
                if (basketVo == null){
//                    throw new Exception("购物车不存在");
                    return R.fail("购物车不存在");
                }
                if (!basketVo.getStatus().equals(1L)){
//                    throw new Exception("购物车状态为下架");
                    return R.fail("购物车状态为下架");
                }

                FolwerAppletProductVo productVo = productService.queryById(basketVo.getProdId());
                if (productVo == null){
//                    throw new Exception("商品不存在");
                    return R.fail("商品不存在");
                }
//                productVos.add(productVo);
                //订单详情
                FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();
//                if (basketVo.getSkuId() == null){
//                    double price = Arith.mul(productVo.getOriPrice() ,basketVo.getBasketCount());
//                    total = Arith.add(total,price);
//                    folwerAppletOrderDetailBo.setOrderPrice(productVo.getOriPrice());
//                } else {
//                    FolwerAppletSkuVo folwerAppletSkuVo = folwerAppletSkuService.queryById(Long.valueOf(basketVo.getSkuId()));
//                    double price = Arith.mul(folwerAppletSkuVo.getPrice(),basketVo.getBasketCount());
//                    total = Arith.add(total,price);
//                    folwerAppletOrderDetailBo.setOrderPrice(folwerAppletSkuVo.getPrice());
//                }

                FolwerAppletSkuVo folwerAppletSkuVo = folwerAppletSkuService.queryById(Long.valueOf(basketVo.getSkuId()));
                BigDecimal price = folwerAppletSkuVo.getPrice().multiply(BigDecimal.valueOf(basketVo.getBasketCount()));
                total = total.add(price);
                folwerAppletOrderDetailBo.setOrderPrice(folwerAppletSkuVo.getPrice());
                folwerAppletOrderDetailBo.setProductListPictureUrl(folwerAppletSkuVo.getSkuPicid());
//                double price = Arith.mul(productVo.getOriPrice() ,basketVo.getBasketCount());
//                total = Arith.add(total,price);
                BigDecimal priceDel = productVo.getDeliveryPrice().multiply(BigDecimal.valueOf(basketVo.getBasketCount()));
//                transfee = transfee.add(priceDel);
//                transfee = Arith.add(transfee, Arith.mul(productVo.getDeliveryPrice(), basketVo.getBasketCount()));


//                folwerAppletOrderDetailBo.setOrderId(bo.getOrderId().toString());
                folwerAppletOrderDetailBo.setProductId(productVo.getId());
                folwerAppletOrderDetailBo.setProductName(productVo.getProductName());
//                folwerAppletOrderDetailBo.setProductListPictureUrl(productVo.getProductListPictureUrl());

                folwerAppletOrderDetailBo.setNumber(basketVo.getBasketCount());
                BigDecimal subtotal = price;//productVo.getOriPrice().multiply(BigDecimal.valueOf(basketVo.getBasketCount()));
                folwerAppletOrderDetailBo.setSubtotal(subtotal);
                if (basketVo.getSkuId() != null){
                    folwerAppletOrderDetailBo.setSkuId(basketVo.getSkuId());
                }

                folwerAppletOrderDetailBos.add(folwerAppletOrderDetailBo);
            }

            //优惠券
//            if(bo.getUserChangeCoupon() != null){
//                //0:满减
//                if(bo.getUserChangeCoupon().equals(0)){
//                    MarketingCouponVo marketingCouponVo = marketingCouponService.queryById(Long.valueOf(bo.getCouponId()));
//                    derlinePrice = BigDecimal.valueOf(marketingCouponVo.getCouponSum().doubleValue());
//                }else if(bo.getUserChangeCoupon().equals(1)){       //1：花券
////                    total = Arith.mul(folwerAppletProductVo.getOriPrice() ,bo.getProdCount());
////                    OneselfMemberLevelPrivilegeVo oneselfMemberLevelPrivilegeVo = oneselfMemberLevelPrivilegeService.queryById(Long.valueOf(bo.getCouponId()));
////                    derlinePrice = Arith.mul(folwerAppletProductVo.getOriPrice(), bo.getCouponCount());
////                    List<Map<String, String> > couponIds = bo.getCouponIds();
////                    int count = 0;
////                    for (Map<String, String> map : couponIds) {
////                        for (String key: map.keySet()){
////                            FolwerAppletProductVo productVo = productService.queryById(Long.valueOf(key));
////                            derlinePrice = Arith.add(derlinePrice, productVo.getOriPrice());
////                        }
////                        count += 1;
////                    }
//                }
//            }
        }

        FolwerAppletOrderBo orderBo = new FolwerAppletOrderBo();
        orderBo.setUserId(String.valueOf(Long.valueOf(bo.getUserId())));
        orderBo.setUserName(appletUserInformationVo.getName());
        orderBo.setMemberLevelId(String.valueOf(appletUserInformationVo.getMemberLevelId()));

        orderBo.setTotal(total);
        FolwerCreditSetBo folwerCreditSetBo = new FolwerCreditSetBo();
        List<FolwerCreditSetVo> folwerCreditSetVos = folwerCreditSetService.queryList(folwerCreditSetBo);
        double points = Arith.div(folwerCreditSetVos.get(0).getGoodsCredit(), folwerCreditSetVos.get(0).getGoodsPurchase(), 2);
        orderBo.setRebate(orderBo.getTotal().multiply(BigDecimal.valueOf(points)).longValue());

        orderBo.setRemarks(bo.getRemarks());
        orderBo.setStatus(0L);

        //物流信息
//        FolwerDeliveryVo folwerDeliveryVo = deliveryService.queryById(bo.getDvyId());
//        orderBo.setDeliveryMode(folwerDeliveryVo.getDvyType());
//        orderBo.setDvyId(bo.getDvyId());
//        orderBo.setDvyName(folwerDeliveryVo.getDvyName());
        //是否分账
        if(!appletUserInformationVo.getParentId().equals(0L) && appletUserInformationVo.getParentId() != null){
            orderBo.setIsProfitSharing(0L);
        }

        //物流单号
//        long dataCenterId = 1L;  // 数据中心标识
//        long machineId = 1L;     // 机器标识
//
//        SnowflakeIdGenerator idGenerator = new SnowflakeIdGenerator(dataCenterId, machineId);
//        // 使用 idGenerator 生成唯一ID
//        long uniqueId = idGenerator.generateId();
//        orderBo.setDvyFlowId(String.valueOf(uniqueId));

        //满额包邮
////        orderBo.setFreightAmount(transfee);
//        FolwerOrderSetBo folwerOrderSetBo = new FolwerOrderSetBo();
//        List<FolwerOrderSetVo> folwerOrderSetVos = folwerOrderSetService.queryList(folwerOrderSetBo);
//        if (folwerOrderSetVos != null){
//            if(folwerOrderSetVos.get(0).getFreeShippingPrice() != null){
////                if(total.compareTo(BigDecimal.valueOf(folwerOrderSetVos.get(0).getFreeShippingPrice())) >= 0){
////                    transfee = BigDecimal.valueOf(0);
////                    orderBo.setFreightAmount( transfee);
////                }
//
//                if(total.compareTo(folwerOrderSetVos.get(0).getStartPrice()) < 0){
//                    BigDecimal num1 = new BigDecimal(String.valueOf(folwerOrderSetVos.get(0).getStartPrice()));
//                    BigDecimal num2 = new BigDecimal("100");
//                    BigDecimal result = num1.divide(num2, RoundingMode.HALF_EVEN);
//                    throw new Exception("您的支付金额未达" + result +"元以上，未满足支付需求");
//                }
//            }
//        }

        orderBo.setActualTotal(total.add(transfee));
//        FolwerPickAddrBo folwerPickAddrBo = new FolwerPickAddrBo();
//        folwerPickAddrBo.setUserId(bo.getUserId());
//        List<FolwerPickAddrVo> folwerPickAddrVos = folwerPickAddrService.queryList(folwerPickAddrBo);
//        if (folwerPickAddrVos != null){
//            orderBo.setAddrOrderId(String.valueOf(folwerPickAddrVos.get(0).getAddrId()));
//        }else {
//            throw new Exception("请选择收货地址");
//        }
        orderBo.setAddrOrderId(bo.getAddrId());
        FolwerAppletOrder add = MapstructUtils.convert(orderBo, FolwerAppletOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            orderBo.setOrderId(String.valueOf(add.getOrderId()));
            for (FolwerAppletOrderDetailBo folwerAppletOrderDetailBo : folwerAppletOrderDetailBos){
                folwerAppletOrderDetailBo.setOrderId(orderBo.getOrderId().toString());
                //商品详情插入
                Boolean b = folwerAppletOrderDetailService.insertByBo(folwerAppletOrderDetailBo);
            }
            //放入缓存
            FolwerAppletOrderVo folwerAppletOrderVo =  this.queryById(add.getOrderId());
            RedisUtils.setCacheObject(CONFIRM_ORDER_CACHE_KEY + add.getOrderId(), folwerAppletOrderVo.getOrderId(), Duration.ofMinutes(15));
        }
//        return add.getOrderId().toString();
        return R.ok(add.getOrderId().toString());
    }

    /**
     * 修改订单
     *
     * @param bo 订单
     * @return 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateByBo(FolwerAppletOrderBo bo) {
//        FolwerAppletOrder update = MapstructUtils.convert(bo, FolwerAppletOrder.class);

        FolwerAppletOrder update = BeanUtil.copyProperties(bo, FolwerAppletOrder.class);
        validEntityBeforeSave(update);
        boolean b = baseMapper.updateById(update) > 0;
        if(b){
            FolwerAppletOrderVo folwerAppletOrderVo = queryById(update.getOrderId());
            if (folwerAppletOrderVo.getStatus() == 0L && RedisUtils.getCacheObject(CONFIRM_ORDER_CACHE_KEY + folwerAppletOrderVo.getOrderId()) != null){
                RedisUtils.setCacheObject(CONFIRM_ORDER_CACHE_KEY + folwerAppletOrderVo.getOrderId(), folwerAppletOrderVo.getOrderId(), true);
            }
        }
        return b;
    }

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public FolwerAppletOrderVo createOrder(OrderParamBo orderParam) throws Exception {
//        FolwerAppletOrderBo bo = new FolwerAppletOrderBo();
//        //订单ID
////        bo.setOrderId(snowflake.nextId());
////        LoginUser user = LoginHelper.getLoginUser();
//        bo.setUserId(orderParam.getUserId());
//        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(orderParam.getUserId());
//        bo.setUserName(appletUserInformationVo.getName());
//        bo.setMemberLevelId(appletUserInformationVo.getMemberLevelId());
//        // 组装获取用户提交的购物车商品项
//        List<FolwerAppletProductVo> shopCartItems = this.getShopCartItemsByOrderItems(orderParam.getBasketIds(),orderParam.getProductItem(),orderParam.getUserId());
//        if (CollectionUtil.isEmpty(shopCartItems)) {
//            throw new Exception("请选择您需要的商品");
//        }
//        //总价
//        double total = 0.0;
//        //折扣价
//        double derlinePrice = 0.0;
//        //运费
//        double transfee = 0.0;
//        //购物车商品总数
//        int totalCount = 0;
//        List<Double> productPriceList = new ArrayList<Double>();
//        List<FolwerAppletOrderDetailVo> orderDetailVoList = null;
//        for (FolwerAppletProductVo shopCartItem : shopCartItems){
//            // 获取sku信息
//            FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(shopCartItem.getSkuId());
//            if (shopCartItem == null || folwerSkuVo == null) {
//                throw new Exception("购物车包含无法识别的商品");
//            }
//            if (shopCartItem.getStatus() != 1 || folwerSkuVo.getStatus() != 1) {
//                throw new Exception("商品[" + shopCartItem.getProductName() + "]已下架");
//            }
//            //订单详情
//            FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();
//            folwerAppletOrderDetailBo.setOrderId(bo.getOrderId().toString());
//            folwerAppletOrderDetailBo.setProductName(shopCartItem.getProductName());
//            folwerAppletOrderDetailBo.setProductListPictureUrl(shopCartItem.getProductListPictureUrl());
//            folwerAppletOrderDetailBo.setOrderPrice(shopCartItem.getOriPrice());
//            double price = 0.0;
//            if (!CollectionUtil.isEmpty(orderParam.getBasketIds()) && orderParam.getProductItem() == null) {
//                FolwerAppletBasketBo folwerAppletBasketBo = new FolwerAppletBasketBo();
//                folwerAppletBasketBo.setProdId(shopCartItem.getId());
//                int count = Math.toIntExact(basketService.queryList(folwerAppletBasketBo).get(0).getBasketCount());
//                price = Arith.mul(shopCartItem.getOriPrice(), count);
//                totalCount = (int) Arith.add(count, totalCount);
//                folwerAppletOrderDetailBo.setNumber((long) count);
//            }else {
//                price = Arith.mul(shopCartItem.getOriPrice(), orderParam.getProdCount());
//                totalCount = orderParam.getProdCount();
//                folwerAppletOrderDetailBo.setNumber(Long.valueOf(orderParam.getProdCount()));
//            }
//            folwerAppletOrderDetailBo.setSubtotal((long) price);
//
//            // 优惠 （未完）
//            if( orderParam.getUserChangeCoupon().equals(0)){        //-1:不参与优惠，0:满减，1：花券
//                Long CouponId = orderParam.getCouponIds().get(shopCartItem.getId());
//                //查询优惠券信息
//            }
//            else if (orderParam.getUserChangeCoupon().equals(1)){
//                Long CouponId = orderParam.getCouponIds().get(shopCartItem.getId());
//                //查询优惠券信息
//
//            } else if (orderParam.getUserChangeCoupon().equals(-1)) {
//
//            }
//            //运费相加
//            total = Arith.add(price, shopCartItem.getDeliveryPrice());
//
//            //运费
//            transfee = Arith.add(shopCartItem.getDeliveryPrice(), transfee);
//
//            //商品详情插入
//            Boolean b = folwerAppletOrderDetailService.insertByBo(folwerAppletOrderDetailBo);
//            if (b){
//                orderDetailVoList = folwerAppletOrderDetailService.queryList(folwerAppletOrderDetailBo);
//            }
//        }
//        bo.setTotal((long) total);
////        bo.setActualTotal((long) derlinePrice);
//        bo.setRemarks(orderParam.getRemarks());
//        bo.setStatus(0L);
//        FolwerDeliveryVo folwerDeliveryVo = deliveryService.queryById(orderParam.getDvyId());
//        bo.setDeliveryMode(folwerDeliveryVo.getDvyType());
//        bo.setDvyId(orderParam.getDvyId());
//        bo.setDvyName(folwerDeliveryVo.getDvyName());
//        //是否分账
//        bo.setIsProfitSharing(0L);
//        //物流单号
////        bo.setDvyFlowId();
//        bo.setFreightAmount((long) transfee);
//        FolwerPickAddrVo folwerPickAddrVo = folwerPickAddrService.queryById(orderParam.getAddrId());
//        bo.setAddrOrderId(orderParam.getAddrId());
//
//        FolwerAppletOrder add = MapstructUtils.convert(bo, FolwerAppletOrder.class);
//        validEntityBeforeSave(add);
//        boolean flag = baseMapper.insertOrUpdate(add);
//
//        FolwerAppletOrderVo folwerAppletOrderVo = new FolwerAppletOrderVo();
//        if (flag) {
//            bo.setOrderId(add.getOrderId());
//            folwerAppletOrderVo.setOrderDetails(orderDetailVoList);
//            //放入缓存
////            this.putConfirmOrderCache(orderParam.toString(), folwerAppletOrderVo);
//
//            FolwerAppletOrderVo cacheObject = RedisUtils.getCacheObject(CONFIRM_ORDER_CACHE_KEY + orderParam.getUserId());
//            if (cacheObject != null){
//                boolean b = RedisUtils.deleteObject(CONFIRM_ORDER_CACHE_KEY + orderParam.getUserId());
//                if (b){
//                    RedisUtils.setCacheObject(CONFIRM_ORDER_CACHE_KEY + orderParam.getUserId(), folwerAppletOrderVo, Duration.ofMinutes(15));
//                }
//            }else {
//                RedisUtils.setCacheObject(CONFIRM_ORDER_CACHE_KEY + orderParam.getUserId(), folwerAppletOrderVo, Duration.ofMinutes(15));
//            }
//
//
//        }
//
//
//        return folwerAppletOrderVo;
//    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R<WxJsapiResponse> submitOrders(PayParam payParam) throws Exception {
        FolwerAppletOrderVo folwerAppletOrderVo = this.queryById(Long.valueOf(payParam.getOrderNumbers()));
        if(folwerAppletOrderVo == null){
            return R.fail("订单不存在");
        }
        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(folwerAppletOrderVo.getUserId());
        if (appletUserInformationVo == null){
            return R.fail("用户不存在");
        }
//        Long cacheObject = RedisUtils.getCacheObject(CONFIRM_ORDER_CACHE_KEY + folwerAppletOrderVo.getOrderId());
//        if (cacheObject == null){
//            return R.fail("订单状态异常");
//        }

        WxPayRequest payJSAPIParam = new WxPayRequest();
        payJSAPIParam.setClientIp(IpUtils.getIpAddr());
        payJSAPIParam.setOutTradeNo(String.valueOf(folwerAppletOrderVo.getOrderId()));
        payJSAPIParam.setAmount(folwerAppletOrderVo.getActualTotal());
        payJSAPIParam.setOpenId(appletUserInformationVo.getOpenid());
        payJSAPIParam.setDescription("购买鲜花");
        //是否分账
        if (folwerAppletOrderVo.getIsProfitSharing() == 1){
            payJSAPIParam.setProfitSharing(folwerAppletOrderVo.getIsProfitSharing() == 1?true:false);
        }
        WxJsapiResponse wxJsapiResponse = payService.JsapiOrder(payJSAPIParam);
        if (wxJsapiResponse == null){
            return R.fail("支付失败");
        }
//        RedisUtils.deleteObject(CONFIRM_ORDER_CACHE_KEY + folwerAppletOrderVo.getOrderId());
        return R.ok(wxJsapiResponse);
    }

    @Override
    public R<String> refundOrder(WxRefundRequest wxRefundRequest) throws Exception {
        Refund refund = payService.refundOrder(wxRefundRequest);
        if (Objects.nonNull(refund) || Objects.isNull(refund)) {
            return buildRefundResult(refund);
        }
//                log.info("请求退款返回：" + refund);
        //接收退款返回参数
        //  Status status = refund.getStatus();
        if (Status.SUCCESS.equals(refund.getStatus().SUCCESS)) {
            //说明退款成功，开始接下来的业务操作
            //你的业务代码，根据请求返回状态修改对应订单状态
            return R.ok("退款成功");
        }
        if (Status.PROCESSING.equals(refund.getStatus().PROCESSING)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            return R.ok("退款中");
        }
        if (Status.ABNORMAL.equals(refund.getStatus().ABNORMAL)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            return R.fail("退款异常");
        }
        if (Status.CLOSED.equals(refund.getStatus().CLOSED)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            return  R.fail("退款关闭");
        }
        return null;
    }

    @Override
    @Transactional
    public FolwerAppletOrderVo queryOrder(String orderId) throws Exception {
        if (StringUtils.isBlank(orderId)){
            return null;
        }
        Transaction transaction = payService.transactionsOrder(orderId);
        if (Objects.nonNull(transaction) || Objects.isNull(transaction)) {
            return applySuccessfulPayment(transaction, 5L);
        }
        if (transaction == null){
            return null;
        }

        if (transaction.getTradeState().equals(Transaction.TradeStateEnum.SUCCESS)){
            FolwerAppletOrderVo folwerAppletOrderVo = this.queryById(Long.valueOf(orderId));
            if (folwerAppletOrderVo.getStatus().equals(0L)){
                FolwerAppletOrderBo folwerAppletOrderBo = new FolwerAppletOrderBo();
                BeanUtil.copyProperties(folwerAppletOrderVo, folwerAppletOrderBo);
                folwerAppletOrderBo.setStatus(5L);
                folwerAppletOrderBo.setOrderNumber(transaction.getTransactionId());
                SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                folwerAppletOrderBo.setPayTime(simpleDateFormat.parse(transaction.getSuccessTime()));
                folwerAppletOrderBo.setPayCallback(transaction.toString());
                AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(Long.valueOf(folwerAppletOrderVo.getUserId()));
                //进行分账
                if(folwerAppletOrderVo.getIsProfitSharing() == 1L){

                    if (appletUserInformationVo == null){
                        throw new Exception("用户不存在");
                    }
                    if(appletUserInformationVo.getParentId() != 0L && appletUserInformationVo.getParentId() != null){
                        AppletUserInformationVo informationParentVo = appletUserInformationService.queryById(appletUserInformationVo.getParentId());
                        AddReceiverResponse addReceiverResponse = sharingService.addReceiver("PERSONAL_OPENID", informationParentVo.getOpenid(), "USER");
                        if (addReceiverResponse.getAccount() != null){
                            PayProfitsharingParam profitSharingParam = new PayProfitsharingParam();
                            profitSharingParam.setOutOrderNo(String.valueOf(folwerAppletOrderVo.getOrderId()));
                            profitSharingParam.setType("PERSONAL_OPENID");
                            profitSharingParam.setTransactionId(transaction.getTransactionId());
                            profitSharingParam.setAccount(informationParentVo.getOpenid());
                            double mul = Arith.mul(folwerAppletOrderVo.getActualTotal(), 0.06);
                            profitSharingParam.setAmount((long) mul);
                            profitSharingParam.setDescription("分账");

                            OrdersEntity ordersEntity = sharingService.ordersSharing(profitSharingParam, "0");
                            if (ordersEntity.getState().equals("FINISHED")){
                                //分账成功
                                folwerAppletOrderBo.setIsProfitSharing(0L);
                            }
                        }
                    }

                }
                Boolean b = this.updateByBo(folwerAppletOrderBo);
                if (b){
                    //更新用户积分
                    AppletUserInformationBo appletUserInformationBo = BeanUtil.copyProperties(appletUserInformationVo, AppletUserInformationBo.class);
                    appletUserInformationBo.setPoints((long)Arith.add(appletUserInformationVo.getPoints(),folwerAppletOrderVo.getRebate()));
                    appletUserInformationService.updateByBo(appletUserInformationBo);

                    //修改库存和销量
                    for (FolwerAppletOrderDetailVo folwerAppletOrderDetailVo : folwerAppletOrderVo.getOrderDetails()){
                        FolwerAppletProductVo folwerAppletProductVo = folwerAppletProductService.queryById(folwerAppletOrderDetailVo.getProductId());
                        FolwerAppletProductBo folwerAppletProductBo = BeanUtil.copyProperties(folwerAppletProductVo, FolwerAppletProductBo.class);
                        folwerAppletProductBo.setTotalStocks((long) Arith.mul(folwerAppletProductVo.getTotalStocks(), folwerAppletOrderDetailVo.getNumber()));
                        folwerAppletProductBo.setSoldNum((long) Arith.add(folwerAppletProductVo.getSoldNum(), folwerAppletOrderDetailVo.getNumber()));
                        folwerAppletProductService.updateByBo(folwerAppletProductBo);
                    }
                }
            }
            return folwerAppletOrderVo;
        }

        return null;
    }

    @Override
    public FolwerAppletOrderVo payCallbackOrder(Transaction transaction) throws Exception {
        if (Objects.nonNull(transaction) || Objects.isNull(transaction)) {
            return applySuccessfulPayment(transaction, 1L);
        }
        log.info("支付回调===>{}", transaction);
        if (transaction.getTradeState().equals(Transaction.TradeStateEnum.SUCCESS)){
            FolwerAppletOrderVo folwerAppletOrderVo = this.queryById(Long.valueOf(transaction.getOutTradeNo()));
            if (folwerAppletOrderVo.getStatus().equals(0L)){
                FolwerAppletOrderBo folwerAppletOrderBo = new FolwerAppletOrderBo();
                BeanUtil.copyProperties(folwerAppletOrderVo, folwerAppletOrderBo);
                folwerAppletOrderBo.setStatus(1L);
                folwerAppletOrderBo.setOrderNumber(transaction.getTransactionId());
                SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                folwerAppletOrderBo.setPayTime(simpleDateFormat.parse(transaction.getSuccessTime()));
                folwerAppletOrderBo.setPayCallback(transaction.toString());
                AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(Long.valueOf(folwerAppletOrderVo.getUserId()));
                //进行分账
                if(folwerAppletOrderVo.getIsProfitSharing() == 1L){

                    if (appletUserInformationVo == null){
                        throw new Exception("用户不存在");
                    }
                    if(appletUserInformationVo.getParentId() != 0L && appletUserInformationVo.getParentId() != null){
                        AppletUserInformationVo informationParentVo = appletUserInformationService.queryById(appletUserInformationVo.getParentId());
                        AddReceiverResponse addReceiverResponse = sharingService.addReceiver("PERSONAL_OPENID", informationParentVo.getOpenid(), "USER");
                        if (addReceiverResponse.getAccount() != null){
                            PayProfitsharingParam profitSharingParam = new PayProfitsharingParam();
                            profitSharingParam.setOutOrderNo(String.valueOf(folwerAppletOrderVo.getOrderId()));
                            profitSharingParam.setType("PERSONAL_OPENID");
                            profitSharingParam.setTransactionId(transaction.getTransactionId());
                            profitSharingParam.setAccount(informationParentVo.getOpenid());
                            double mul = Arith.mul(folwerAppletOrderVo.getActualTotal(), 0.06);
                            profitSharingParam.setAmount((long) mul);
                            profitSharingParam.setDescription("分账");

                            OrdersEntity ordersEntity = sharingService.ordersSharing(profitSharingParam, "0");
                            if (ordersEntity.getState().equals("FINISHED")){
                                //分账成功
                                folwerAppletOrderBo.setIsProfitSharing(0L);
                            }
                        }
                    }

                }
                Boolean b = this.updateByBo(folwerAppletOrderBo);
                if (b){
                    //更新用户积分
                    AppletUserInformationBo appletUserInformationBo = BeanUtil.copyProperties(appletUserInformationVo, AppletUserInformationBo.class);
                    appletUserInformationBo.setPoints((long)Arith.add(appletUserInformationVo.getPoints(),folwerAppletOrderVo.getRebate()));
                    appletUserInformationService.updateByBo(appletUserInformationBo);
                    //修改库存和销量
                    for (FolwerAppletOrderDetailVo folwerAppletOrderDetailVo : folwerAppletOrderVo.getOrderDetails()){
                        FolwerAppletProductVo folwerAppletProductVo = folwerAppletProductService.queryById(folwerAppletOrderDetailVo.getProductId());
                        FolwerAppletProductBo folwerAppletProductBo = BeanUtil.copyProperties(folwerAppletProductVo, FolwerAppletProductBo.class);
                        folwerAppletProductBo.setTotalStocks((long) Arith.mul(folwerAppletProductVo.getTotalStocks(), folwerAppletOrderDetailVo.getNumber()));
                        folwerAppletProductBo.setSoldNum((long) Arith.add(folwerAppletProductVo.getSoldNum(), folwerAppletOrderDetailVo.getNumber()));
                        folwerAppletProductService.updateByBo(folwerAppletProductBo);
                    }
                }

            }
            return folwerAppletOrderVo;
        }

        return null;
    }

    private R<String> buildRefundResult(Refund refund) {
        if (Objects.isNull(refund) || Objects.isNull(refund.getStatus())) {
            return R.fail("退款状态未知");
        }
        Status status = refund.getStatus();
        if (Status.SUCCESS.equals(status)) {
            return R.ok("退款成功");
        }
        if (Status.PROCESSING.equals(status)) {
            return R.ok("退款处理中");
        }
        if (Status.ABNORMAL.equals(status)) {
            return R.fail("退款异常");
        }
        if (Status.CLOSED.equals(status)) {
            return R.fail("退款关闭");
        }
        return R.fail("退款状态未知");
    }

    private FolwerAppletOrderVo applySuccessfulPayment(Transaction transaction, Long targetStatus) throws Exception {
        if (Objects.isNull(transaction) || !Transaction.TradeStateEnum.SUCCESS.equals(transaction.getTradeState())) {
            return null;
        }

        FolwerAppletOrderVo folwerAppletOrderVo = this.queryById(Long.valueOf(transaction.getOutTradeNo()));
        if (Objects.isNull(folwerAppletOrderVo)) {
            return null;
        }
        if (!Long.valueOf(0L).equals(folwerAppletOrderVo.getStatus())) {
            return folwerAppletOrderVo;
        }

        FolwerAppletOrderBo folwerAppletOrderBo = new FolwerAppletOrderBo();
        BeanUtil.copyProperties(folwerAppletOrderVo, folwerAppletOrderBo);
        folwerAppletOrderBo.setStatus(targetStatus);
        folwerAppletOrderBo.setOrderNumber(transaction.getTransactionId());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        folwerAppletOrderBo.setPayTime(simpleDateFormat.parse(transaction.getSuccessTime()));
        folwerAppletOrderBo.setPayCallback(transaction.toString());

        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(Long.valueOf(folwerAppletOrderVo.getUserId()));
        applyProfitSharingIfNeeded(folwerAppletOrderVo, transaction, folwerAppletOrderBo, appletUserInformationVo);

        Boolean updated = this.updateByBo(folwerAppletOrderBo);
        if (Boolean.TRUE.equals(updated)) {
            applyPostPaymentSideEffects(folwerAppletOrderVo, appletUserInformationVo);
        }
        return folwerAppletOrderVo;
    }

    private void applyProfitSharingIfNeeded(FolwerAppletOrderVo orderVo, Transaction transaction,
                                            FolwerAppletOrderBo orderBo, AppletUserInformationVo userVo) throws Exception {
        if (!Long.valueOf(1L).equals(orderVo.getIsProfitSharing())) {
            return;
        }
        if (Objects.isNull(userVo)) {
            throw new Exception("用户不存在");
        }
        if (Objects.isNull(userVo.getParentId()) || Long.valueOf(0L).equals(userVo.getParentId())) {
            return;
        }

        AppletUserInformationVo informationParentVo = appletUserInformationService.queryById(userVo.getParentId());
        if (Objects.isNull(informationParentVo)) {
            return;
        }

        AddReceiverResponse addReceiverResponse = sharingService.addReceiver("PERSONAL_OPENID", informationParentVo.getOpenid(), "USER");
        if (Objects.isNull(addReceiverResponse) || Objects.isNull(addReceiverResponse.getAccount())) {
            return;
        }

        PayProfitsharingParam profitSharingParam = new PayProfitsharingParam();
        profitSharingParam.setOutOrderNo(String.valueOf(orderVo.getOrderId()));
        profitSharingParam.setType("PERSONAL_OPENID");
        profitSharingParam.setTransactionId(transaction.getTransactionId());
        profitSharingParam.setAccount(informationParentVo.getOpenid());
        double mul = Arith.mul(orderVo.getActualTotal(), 0.06);
        profitSharingParam.setAmount((long) mul);
        profitSharingParam.setDescription("分账");

        OrdersEntity ordersEntity = sharingService.ordersSharing(profitSharingParam, "0");
        if (Objects.nonNull(ordersEntity) && "FINISHED".equals(ordersEntity.getState())) {
            orderBo.setIsProfitSharing(0L);
        }
    }

    private void applyPostPaymentSideEffects(FolwerAppletOrderVo orderVo, AppletUserInformationVo userVo) {
        if (Objects.nonNull(userVo)) {
            AppletUserInformationBo appletUserInformationBo = BeanUtil.copyProperties(userVo, AppletUserInformationBo.class);
            appletUserInformationBo.setPoints((long) Arith.add(userVo.getPoints(), orderVo.getRebate()));
            appletUserInformationService.updateByBo(appletUserInformationBo);
        }

        if (CollectionUtil.isEmpty(orderVo.getOrderDetails())) {
            return;
        }

        for (FolwerAppletOrderDetailVo folwerAppletOrderDetailVo : orderVo.getOrderDetails()) {
            FolwerAppletProductVo folwerAppletProductVo = folwerAppletProductService.queryById(folwerAppletOrderDetailVo.getProductId());
            if (Objects.isNull(folwerAppletProductVo)) {
                continue;
            }
            FolwerAppletProductBo folwerAppletProductBo = BeanUtil.copyProperties(folwerAppletProductVo, FolwerAppletProductBo.class);
            folwerAppletProductBo.setTotalStocks((long) Arith.mul(folwerAppletProductVo.getTotalStocks(), folwerAppletOrderDetailVo.getNumber()));
            folwerAppletProductBo.setSoldNum((long) Arith.add(folwerAppletProductVo.getSoldNum(), folwerAppletOrderDetailVo.getNumber()));
            folwerAppletProductService.updateByBo(folwerAppletProductBo);
        }
    }


    OrdersEntity sharingResult(String outOrderNo, String transactionId, String userId) throws Exception {
        if (StringUtils.isBlank(outOrderNo) || StringUtils.isBlank(transactionId)){
            return null;
        }

        MarketingMemberPromotionPecordBo memberPromotionPecordBo = new MarketingMemberPromotionPecordBo();
        memberPromotionPecordBo.setMemberId(userId);

        List<MarketingMemberPromotionPecordVo> marketingMemberPromotionPecordVos = marketingMemberPromotionPecordService.queryList(memberPromotionPecordBo);
        if (CollectionUtil.isEmpty(marketingMemberPromotionPecordVos)){
            return null;
        }

        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(Long.valueOf(marketingMemberPromotionPecordVos.get(0).getMemberId()));


        AddReceiverResponse openid = sharingService.addReceiver(PERSONAL_OPENID, appletUserInformationVo.getOpenid(), PARTNER);
        if (openid == null){
            return null;
        }

        OrdersEntity ordersEntity = sharingService.sharingResult(outOrderNo, transactionId);
        if (ordersEntity == null){
            return null;
        }
//        if (ordersEntity.getState().equals(OrderStatus.SUCCESS)){
//            //分账成功
//            //更新订单状态
//            FolwerAppletOrderVo folwerAppletOrderVo = this.queryById(Long.valueOf(outOrderNo));
//            FolwerAppletOrderBofolwerAppletOrderBo = new FolwerAppletOrderBo();
//        }
        return ordersEntity;
    }

    @Override
    public R<String> ProfitsharingOrder(PayProfitsharingParam payProfitsharingParam) throws Exception {
        return null;
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
