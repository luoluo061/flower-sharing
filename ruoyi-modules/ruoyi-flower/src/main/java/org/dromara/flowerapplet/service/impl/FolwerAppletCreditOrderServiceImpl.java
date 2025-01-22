package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.redis.utils.RedisUtils;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flowerapplet.domain.PayParam;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditGetrecordsBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditOrderDetailBo;
import org.dromara.flowerapplet.domain.bo.OrderParamBo;
import org.dromara.flowerapplet.domain.vo.*;
import org.dromara.flowerapplet.service.*;
import org.dromara.flowerapplet.util.Arith;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditOrderBo;
import org.dromara.flowerapplet.domain.FolwerAppletCreditOrder;
import org.dromara.flowerapplet.mapper.FolwerAppletCreditOrderMapper;

import java.time.Duration;
import java.util.*;

/**
 * 积分订单Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletCreditOrderServiceImpl implements IFolwerAppletCreditOrderService {

    private final FolwerAppletCreditOrderMapper baseMapper;

    private final IAppletUserInformationService appletUserInformationService;

    private final IFolwerAppletCreditProductService folwerAppletCreditProductService;

    private final IFolwerAppletCreditOrderDetailService folwerAppletCreditOrderDetailService;

    private final IFolwerAppletCreditGetrecordsService folwerAppletCreditGetrecordsService;

    private static final String CONFIRM_CREDITORDER_CACHE_KEY  = "CreditOrder:";

    /**
     * 查询积分订单
     *
     * @param orderId 主键
     * @return 积分订单
     */
    @Override
    public FolwerAppletCreditOrderVo queryById(Long orderId){
        FolwerAppletCreditOrderVo creditOrderVo = baseMapper.selectVoById(orderId);
        if (creditOrderVo != null){
            FolwerAppletCreditOrderDetailBo creditOrderDetailBo = new FolwerAppletCreditOrderDetailBo();
            creditOrderDetailBo.setOrderId(String.valueOf(creditOrderVo.getOrderId()));
            List<FolwerAppletCreditOrderDetailVo> creditOrderDetailVos = folwerAppletCreditOrderDetailService.queryList(creditOrderDetailBo);
            if (creditOrderDetailVos == null){
                try {
                    throw new Exception("商品不存在");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            creditOrderVo.setFolwerAppletCreditOrderDetailList(creditOrderDetailVos);
        }

        return creditOrderVo;
    }

    /**
     * 分页查询积分订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分订单分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletCreditOrderVo> queryPageList(FolwerAppletCreditOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletCreditOrder> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletCreditOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if(result.getRecords() != null){
            result.getRecords().forEach(item -> {
                FolwerAppletCreditOrderDetailBo creditOrderDetailBo = new FolwerAppletCreditOrderDetailBo();
                creditOrderDetailBo.setOrderId(String.valueOf(item.getOrderId()));
                List<FolwerAppletCreditOrderDetailVo> creditOrderDetailVos = folwerAppletCreditOrderDetailService.queryList(creditOrderDetailBo);
                if (creditOrderDetailVos == null){
                    try {
                        throw new Exception("商品不存在");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                item.setFolwerAppletCreditOrderDetailList(creditOrderDetailVos);
            });
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分订单列表
     *
     * @param bo 查询条件
     * @return 积分订单列表
     */
    @Override
    public List<FolwerAppletCreditOrderVo> queryList(FolwerAppletCreditOrderBo bo) {
        LambdaQueryWrapper<FolwerAppletCreditOrder> lqw = buildQueryWrapper(bo);
        List<FolwerAppletCreditOrderVo> creditOrderVos = baseMapper.selectVoList(lqw);
        if (creditOrderVos != null){
            creditOrderVos.forEach(item -> {
                FolwerAppletCreditOrderDetailBo creditOrderDetailBo = new FolwerAppletCreditOrderDetailBo();
                creditOrderDetailBo.setOrderId(String.valueOf(item.getOrderId()));
                List<FolwerAppletCreditOrderDetailVo> creditOrderDetailVos = folwerAppletCreditOrderDetailService.queryList(creditOrderDetailBo);
                if (creditOrderDetailVos == null){
                    try {
                        throw new Exception("商品不存在");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                item.setFolwerAppletCreditOrderDetailList(creditOrderDetailVos);
            });
        }
        return creditOrderVos;
    }

    private LambdaQueryWrapper<FolwerAppletCreditOrder> buildQueryWrapper(FolwerAppletCreditOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletCreditOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerAppletCreditOrder::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerAppletCreditOrder::getUserName, bo.getUserName());
        lqw.eq(bo.getMemberLevelId() != null, FolwerAppletCreditOrder::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(bo.getActualTotal() != null, FolwerAppletCreditOrder::getActualTotal, bo.getActualTotal());
        lqw.eq(bo.getPayTime() != null, FolwerAppletCreditOrder::getPayTime, bo.getPayTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerAppletCreditOrder::getRemarks, bo.getRemarks());
        lqw.eq(bo.getStatus() != null, FolwerAppletCreditOrder::getStatus, bo.getStatus());
        lqw.eq(bo.getDeliveryMode() != null, FolwerAppletCreditOrder::getDeliveryMode, bo.getDeliveryMode());
        lqw.eq(bo.getDvyId() != null, FolwerAppletCreditOrder::getDvyId, bo.getDvyId());
        lqw.like(StringUtils.isNotBlank(bo.getDvyName()), FolwerAppletCreditOrder::getDvyName, bo.getDvyName());
        lqw.eq(StringUtils.isNotBlank(bo.getDvyFlowId()), FolwerAppletCreditOrder::getDvyFlowId, bo.getDvyFlowId());
        lqw.eq(bo.getFreightAmount() != null, FolwerAppletCreditOrder::getFreightAmount, bo.getFreightAmount());
        lqw.eq(bo.getAddrOrderId() != null, FolwerAppletCreditOrder::getAddrOrderId, bo.getAddrOrderId());
        lqw.eq(bo.getDvyTime() != null, FolwerAppletCreditOrder::getDvyTime, bo.getDvyTime());
        lqw.eq(bo.getFinallyTime() != null, FolwerAppletCreditOrder::getFinallyTime, bo.getFinallyTime());
        lqw.eq(bo.getCancelTime() != null, FolwerAppletCreditOrder::getCancelTime, bo.getCancelTime());
        lqw.eq(StringUtils.isNotBlank(bo.getCancelMsg()), FolwerAppletCreditOrder::getCancelMsg, bo.getCancelMsg());
        return lqw;
    }

    /**
     * 新增积分订单
     *
     * @param bo 积分订单
     * @return 是否新增成功
     */
    @Override
    public FolwerAppletCreditOrderVo insertByBo(OrderParamBo bo) throws Exception {

//        FolwerAppletCreditOrderVo cacheObject = RedisUtils.getCacheObject(CONFIRM_CREDITORDER_CACHE_KEY + bo.getUserId());
//        if (cacheObject != null){
//            return cacheObject;
//        }

        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(bo.getUserId());
        if (appletUserInformationVo == null){
            throw new Exception("用户不存在");
        }
        FolwerAppletCreditProductVo folwerAppletCreditProductVo = folwerAppletCreditProductService.queryById(bo.getProductItem());
        if (folwerAppletCreditProductVo == null){
            throw new Exception("商品不存在");
        }
        double points = Arith.mul(folwerAppletCreditProductVo.getRedeemPrice(), bo.getProdCount());
        double sun = Arith.sub(appletUserInformationVo.getPoints(), points);
        if (sun < 0){
            throw new Exception("积分不足");
        }

//        FolwerAppletCreditOrder add = MapstructUtils.convert(bo, FolwerAppletCreditOrder.class);
        FolwerAppletCreditOrder add = new FolwerAppletCreditOrder();
        add.setUserId(bo.getUserId());
        add.setUserName(appletUserInformationVo.getName());
        add.setMemberLevelId(appletUserInformationVo.getMemberLevelId());
        add.setActualTotal((long) points);
        add.setPayTime(new Date());
        add.setRemarks(bo.getRemarks());
        add.setFreightAmount(folwerAppletCreditProductVo.getDeliveryPrice());
        add.setStatus(0L);
        add.setFreightAmount(folwerAppletCreditProductVo.getDeliveryPrice());

        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            FolwerAppletCreditOrderDetailBo folwerAppletCreditOrderDetailBo = new FolwerAppletCreditOrderDetailBo();
            folwerAppletCreditOrderDetailBo.setOrderId(String.valueOf(add.getOrderId()));
            folwerAppletCreditOrderDetailBo.setProductId(bo.getProductItem());
            if(bo.getSkuId() != null){
                folwerAppletCreditOrderDetailBo.setSkuId(bo.getSkuId());
            }
            folwerAppletCreditOrderDetailBo.setProductName(folwerAppletCreditProductVo.getProductName());
            folwerAppletCreditOrderDetailBo.setProductListPictureUrl(folwerAppletCreditProductVo.getProductListPictureUrl());
            folwerAppletCreditOrderDetailBo.setOrderPrice(folwerAppletCreditProductVo.getRedeemPrice());
            folwerAppletCreditOrderDetailBo.setNumber(Long.valueOf(bo.getProdCount()));
            folwerAppletCreditOrderDetailBo.setSubtotal((long) points);
            Boolean b = folwerAppletCreditOrderDetailService.insertByBo(folwerAppletCreditOrderDetailBo);
            FolwerAppletCreditOrderVo folwerAppletCreditOrderVo = this.queryById(add.getOrderId());
            folwerAppletCreditOrderVo.setFolwerAppletCreditOrderDetailList(folwerAppletCreditOrderDetailService.queryList(folwerAppletCreditOrderDetailBo));

            //放入缓存
            RedisUtils.setCacheObject(CONFIRM_CREDITORDER_CACHE_KEY + add.getOrderId(), add.getOrderId(), Duration.ofMinutes(15));
//            FolwerAppletCreditOrderVo cacheObject = RedisUtils.getCacheObject(CONFIRM_CREDITORDER_CACHE_KEY + bo.getUserId());
//            if (cacheObject != null){
//                boolean deleteObject = RedisUtils.deleteObject(CONFIRM_CREDITORDER_CACHE_KEY + add.getOrderId());
//                if (deleteObject){
//                    RedisUtils.setCacheObject(CONFIRM_CREDITORDER_CACHE_KEY + add.getOrderId(), folwerAppletCreditOrderVo, Duration.ofMinutes(15));
//                }
//            }else {
//                RedisUtils.setCacheObject(CONFIRM_CREDITORDER_CACHE_KEY + add.getOrderId(), folwerAppletCreditOrderVo, Duration.ofMinutes(15));
//            }
            return folwerAppletCreditOrderVo;
        }
        return null;
    }

    /**
     * 修改积分订单
     *
     * @param bo 积分订单
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletCreditOrderBo bo) {
        FolwerAppletCreditOrder update = MapstructUtils.convert(bo, FolwerAppletCreditOrder.class);
        validEntityBeforeSave(update);
        if (update.getStatus() == 0L && RedisUtils.getCacheObject(CONFIRM_CREDITORDER_CACHE_KEY + update.getOrderId()) != null){
            RedisUtils.setCacheObject(CONFIRM_CREDITORDER_CACHE_KEY + update.getOrderId(), update.getOrderId(), true);
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletCreditOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分订单信息
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

    @Override
    public String submitOrders(PayParam payParam) throws Exception {
        FolwerAppletCreditOrderVo folwerAppletCreditOrderVo = this.queryById(payParam.getOrderNumbers());
        if(folwerAppletCreditOrderVo == null){
            return "订单不存在";
        }
        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(folwerAppletCreditOrderVo.getUserId());
        if (appletUserInformationVo == null){
            return "用户不存在";
        }
        Long cacheObject = RedisUtils.getCacheObject(CONFIRM_CREDITORDER_CACHE_KEY + folwerAppletCreditOrderVo.getOrderId());
        if (cacheObject == null){
            return "订单状态异常";
        }
        double sub = Arith.sub(appletUserInformationVo.getPoints(), folwerAppletCreditOrderVo.getActualTotal());
        if (sub < 0){
            return "积分不足";

        }
        if (folwerAppletCreditOrderVo.getStatus() == 0){
            AppletUserInformationBo appletUserInformationBo = BeanUtil.copyProperties(appletUserInformationVo, AppletUserInformationBo.class);
            appletUserInformationBo.setPoints((long) sub);
            Boolean b = appletUserInformationService.updateByBo(appletUserInformationBo);
            if (b){
                FolwerAppletCreditOrderBo add = BeanUtil.copyProperties(folwerAppletCreditOrderVo, FolwerAppletCreditOrderBo.class);
                add.setStatus(1L);
                add.setPayTime(new Date());
                Boolean updateCreditOrderByBo = this.updateByBo(add);
                if (updateCreditOrderByBo){
                    FolwerAppletCreditGetrecordsBo folwerAppletCreditGetrecordsBo = new FolwerAppletCreditGetrecordsBo();
                    folwerAppletCreditGetrecordsBo.setUserId(folwerAppletCreditOrderVo.getUserId());
                    folwerAppletCreditGetrecordsBo.setUserName(folwerAppletCreditOrderVo.getUserName());
                    folwerAppletCreditGetrecordsBo.setMemberLevelId(folwerAppletCreditOrderVo.getMemberLevelId());
                    folwerAppletCreditGetrecordsBo.setCreditSourId(6L);
                    folwerAppletCreditGetrecordsBo.setGetTotal(String.valueOf(folwerAppletCreditOrderVo.getActualTotal()));
                    folwerAppletCreditGetrecordsBo.setGetTime(new Date());
                    folwerAppletCreditGetrecordsService.insertByBo(folwerAppletCreditGetrecordsBo);
                    RedisUtils.deleteObject(CONFIRM_CREDITORDER_CACHE_KEY + folwerAppletCreditOrderVo.getOrderId());
                    return "订单提交成功";
                }
            }
        }

        return "订单提交失败";
    }
}
