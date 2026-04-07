package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.bean.BeanUtil;
import io.github.linpeilie.Converter;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.bo.*;
import org.dromara.flower.domain.vo.*;
import org.dromara.flower.service.*;
import org.dromara.flowerapplet.domain.bo.FolwerAppletDeliveryPriceBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDetailBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletDeliveryPriceVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.service.IFolwerAppletBasketService;
import org.dromara.flowerapplet.service.IFolwerAppletDeliveryPriceService;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDetailService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDvyBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDvyVo;
import org.dromara.flowerapplet.domain.FolwerAppletOrderDvy;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderDvyMapper;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDvyService;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

/**
 * 订单物流Service业务层处理
 *
 * @author mlhxj
 * @date 2025-09-02
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletOrderDvyServiceImpl implements IFolwerAppletOrderDvyService {

    private final FolwerAppletOrderDvyMapper baseMapper;

    private final IFolwerDeliveryService folwerDeliveryService;

    private final IFolwerSkuService folwerSkuService;

    private final IFolwerPickAddrService folwerPickAddrService;

    private final IFolwerDeliveryTemplateService folwerDeliveryTemplateService;

    private final IFolwerAppletBasketService folwerBasketService;

    private final Converter converter;

    private final IFolwerDeliverySetService folwerDeliverySetService;

    private final IFolwerDeliveryBoxService folwerDeliveryBoxService;

    private final IFolwerAppletDeliveryPriceService folwerAppletDeliveryPriceService;

    private final IFolwerDeliveryTemperatureService folwerDeliveryTemperatureService;

    private final IFolwerOrderService folwerOrderService;

    private final IFolwerAppletOrderDetailService folwerAppletOrderDetailService;

    /**
     * 查询订单物流
     *
     * @param orderDevId 主键
     * @return 订单物流
     */
    @Override
    public FolwerAppletOrderDvyVo queryById(Long orderDevId){
        return baseMapper.selectVoById(orderDevId);
    }

    /**
     * 分页查询订单物流列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单物流分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletOrderDvyVo> queryPageList(FolwerAppletOrderDvyBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletOrderDvy> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletOrderDvyVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单物流列表
     *
     * @param bo 查询条件
     * @return 订单物流列表
     */
    @Override
    public List<FolwerAppletOrderDvyVo> queryList(FolwerAppletOrderDvyBo bo) {
        LambdaQueryWrapper<FolwerAppletOrderDvy> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletOrderDvy> buildQueryWrapper(FolwerAppletOrderDvyBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletOrderDvy> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOrderId() != null, FolwerAppletOrderDvy::getOrderId, bo.getOrderId());
        lqw.eq(bo.getDvyId() != null, FolwerAppletOrderDvy::getDvyId, bo.getDvyId());
//        lqw.like(StringUtils.isNotBlank(bo.getDvyName()), FolwerAppletOrderDvy::getDvyName, bo.getDvyName());
//        lqw.eq(bo.getDvyWeight() != null, FolwerAppletOrderDvy::getDvyWeight, bo.getDvyWeight());
//        lqw.eq(bo.getFlowerNum() != null, FolwerAppletOrderDvy::getFlowerNum, bo.getFlowerNum());
//        lqw.eq(bo.getDvyNum() != null, FolwerAppletOrderDvy::getDvyNum, bo.getDvyNum());
//        lqw.eq(bo.getFirstWeight() != null, FolwerAppletOrderDvy::getFirstWeight, bo.getFirstWeight());
//        lqw.eq(bo.getAdditionalWeight() != null, FolwerAppletOrderDvy::getAdditionalWeight, bo.getAdditionalWeight());
//        lqw.eq(bo.getFirstWeightPrice() != null, FolwerAppletOrderDvy::getFirstWeightPrice, bo.getFirstWeightPrice());
//        lqw.eq(bo.getAdditionalWeightPrice() != null, FolwerAppletOrderDvy::getAdditionalWeightPrice, bo.getAdditionalWeightPrice());
//        lqw.eq(bo.getFreightAmount() != null, FolwerAppletOrderDvy::getFreightAmount, bo.getFreightAmount());
        lqw.eq(bo.getInsulationNum() != null, FolwerAppletOrderDvy::getInsulationNum, bo.getInsulationNum());
//        lqw.eq(bo.getInsulationAmount() != null, FolwerAppletOrderDvy::getInsulationAmount, bo.getInsulationAmount());
//        lqw.eq(bo.getIceNum() != null, FolwerAppletOrderDvy::getIceNum, bo.getIceNum());
//        lqw.eq(bo.getIceAmount() != null, FolwerAppletOrderDvy::getIceAmount, bo.getIceAmount());
//        lqw.eq(bo.getLaborPrice() != null, FolwerAppletOrderDvy::getLaborPrice, bo.getLaborPrice());
//        lqw.eq(bo.getMaterialPrace() != null, FolwerAppletOrderDvy::getMaterialPrace, bo.getMaterialPrace());
//        lqw.eq(bo.getBoxPrice() != null, FolwerAppletOrderDvy::getBoxPrice, bo.getBoxPrice());
//        lqw.eq(bo.getPackingAmount() != null, FolwerAppletOrderDvy::getPackingAmount, bo.getPackingAmount());
        return lqw;
    }

    /**
     * 新增订单物流
     *
     * @param bo 订单物流
     * @return 是否新增成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FolwerAppletOrderDvyVo insertByBo(FolwerAppletOrderDvyBo bo) throws Exception {
//        FolwerAppletOrderDvy folwerAppletOrderDvy = this.logisticsCalculation(bo);
        FolwerAppletOrderDvy folwerAppletOrderDvy = this.calculateTheCost(bo);
        if (folwerAppletOrderDvy == null){
            return null;
        }
        validEntityBeforeSave(folwerAppletOrderDvy);
        boolean flag = baseMapper.insert(folwerAppletOrderDvy) > 0;
        FolwerAppletOrderDvyVo folwerAppletOrderDvyVo = new FolwerAppletOrderDvyVo();
        if (flag) {
            bo.setOrderDevId(folwerAppletOrderDvy.getOrderDevId());
            folwerAppletOrderDvyVo = queryById(folwerAppletOrderDvy.getOrderDevId());
        }
        return folwerAppletOrderDvyVo;
    }

    /**
     * 累加多个BigDecimal数值
     *
     * @param numbers 要相加的BigDecimal列表
     * @return 累加后的结果
     */
    private BigDecimal addAll(List<BigDecimal> numbers) {
        // 初始化总和为0
        BigDecimal sum = BigDecimal.ZERO;

        // 循环累加
        for (BigDecimal num : numbers) {
            // 避免空指针异常（如果列表中可能有null）
            if (num != null) {
                sum = sum.add(num);
            }
        }
        return sum;
    }

    /**
     * 修改订单物流
     *
     * @param bo 订单物流
     * @return 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FolwerAppletOrderDvyVo updateByBo(FolwerAppletOrderDvyBo bo) throws Exception {
//         FolwerAppletOrderDvy folwerAppletOrderDvy = this.logisticsCalculation(bo);
        FolwerAppletOrderDvy folwerAppletOrderDvy = this.calculateTheCost(bo);
        if (folwerAppletOrderDvy == null){
            return null;
        }
        folwerAppletOrderDvy.setOrderDevId(bo.getOrderDevId());
        validEntityBeforeSave(folwerAppletOrderDvy);

        FolwerAppletOrderDvyVo folwerAppletOrderDvyVo = new FolwerAppletOrderDvyVo();
        if(baseMapper.updateById(folwerAppletOrderDvy) > 0){
            folwerAppletOrderDvyVo = queryById(bo.getOrderDevId());
            FolwerOrderVo folwerOrderVo = folwerOrderService.queryById(bo.getOrderId());

            //更新订单金额
//            BigDecimal transfeeOld = folwerOrderVo.getFreightAmount();
//            BigDecimal total = folwerOrderVo.getActualTotal().subtract(transfeeOld);

            BigDecimal transfee = folwerAppletOrderDvy.getPackingAmount().setScale(0, RoundingMode.HALF_EVEN);
            BigDecimal total = folwerOrderVo.getTotal().add(transfee);
            FolwerOrderBo folwerOrderBo = new FolwerOrderBo();
            BeanUtil.copyProperties(folwerOrderVo, folwerOrderBo);

            folwerOrderBo.setFreightAmount(transfee);
            folwerOrderBo.setActualTotal(total);
            Boolean b = folwerOrderService.updateByBo(folwerOrderBo);

        }
        return folwerAppletOrderDvyVo;
    }

    /**
     * 修改订单物流
     *
     * @param bo 订单物流
     * @return 是否修改成功
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FolwerAppletOrderDvyVo continuationUpdateByBo(FolwerAppletOrderDvyBo bo) throws Exception {
//         FolwerAppletOrderDvy folwerAppletOrderDvy = this.logisticsCalculation(bo);

        FolwerAppletOrderDvy folwerAppletOrderDvy = this.calculateTheCost(bo);
        if (folwerAppletOrderDvy == null){
            return null;
        }
        folwerAppletOrderDvy.setOrderDevId(bo.getOrderDevId());
        validEntityBeforeSave(folwerAppletOrderDvy);

        FolwerAppletOrderDvyVo folwerAppletOrderDvyVo = new FolwerAppletOrderDvyVo();
        if(baseMapper.updateById(folwerAppletOrderDvy) > 0){
            folwerAppletOrderDvyVo = queryById(bo.getOrderDevId());
            FolwerOrderVo folwerOrderVo = folwerOrderService.queryById(bo.getOrderId());

            //更新订单金额
//            BigDecimal transfeeOld = folwerOrderVo.getFreightAmount();
//            BigDecimal total = folwerOrderVo.getActualTotal().subtract(transfeeOld);

            BigDecimal transfee = folwerAppletOrderDvy.getPackingAmount().setScale(0, RoundingMode.HALF_EVEN);
            BigDecimal total = folwerOrderVo.getTotal().add(transfee);
            FolwerOrderBo folwerOrderBo = new FolwerOrderBo();
            BeanUtil.copyProperties(folwerOrderVo, folwerOrderBo);

            folwerOrderBo.setFreightAmount(transfee);
            folwerOrderBo.setActualTotal(total);
            Boolean b = folwerOrderService.updateByBo(folwerOrderBo);

        }
        return folwerAppletOrderDvyVo;
    }

    private FolwerAppletOrderDvy logisticsCalculation(FolwerAppletOrderDvyBo bo) throws Exception {
        //总费用
        BigDecimal totalPrice = new BigDecimal(0);

        //物料费
        BigDecimal materialPrace = new BigDecimal(0);
        //包材费
        BigDecimal packagePrace = new BigDecimal(0);
        //人工费
        BigDecimal laborPrace = new BigDecimal(0);
        //运费
        BigDecimal transfee = new BigDecimal(0);

        FolwerPickAddrBo folwerPickAddrBo = new FolwerPickAddrBo();
        folwerPickAddrBo.setUserId(bo.getUserId());
        List<FolwerPickAddrVo> folwerPickAddrVos = folwerPickAddrService.queryList(folwerPickAddrBo);
        FolwerPickAddrVo folwerPickAddrVo = new FolwerPickAddrVo();
        if (folwerPickAddrVos == null && folwerPickAddrVos.size() <= 0){
            throw new Exception("请选择收货地址");
        }else {
            folwerPickAddrVo = folwerPickAddrVos.get(0);
        }
        //温度
        double weatherByTemperature = 0.0;
        FolwerDeliveryTemperatureBo folwerDeliveryTemperatureBo = new FolwerDeliveryTemperatureBo();
        folwerDeliveryTemperatureBo.setAreaCode(folwerPickAddrVo.getCityId());
        List<FolwerDeliveryTemperatureVo> folwerDeliveryTemperatureVos = folwerDeliveryTemperatureService.queryList(folwerDeliveryTemperatureBo);
        if (folwerDeliveryTemperatureVos != null && folwerDeliveryTemperatureVos.size() > 0){

            Double wendu = folwerDeliveryTemperatureService.getWeatherByTemperature(folwerDeliveryTemperatureVos.get(0).getCityCode().toString());
            weatherByTemperature = wendu;
        }
        //物流设置
        FolwerDeliverySetBo deliverySetBo = new FolwerDeliverySetBo();
        List<FolwerDeliverySetVo> folwerDeliverySetVos = folwerDeliverySetService.queryList(deliverySetBo);
        FolwerDeliverySetVo folwerDeliverySetVo = folwerDeliverySetVos.get(0);
        laborPrace = folwerDeliverySetVo.getLaborPrice(); //人工费

        Double weight = 0.0;
        Double size = 0.0;
        Integer productNum = 0;
        if (bo.getBasketIds() != null) {
            for (String basketId : bo.getBasketIds()) {
                FolwerAppletBasketVo folwerAppletBasketVo = folwerBasketService.queryById(Long.parseLong(basketId));
                FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(folwerAppletBasketVo.getSkuId());
                weight = weight + folwerSkuVo.getWeight() * folwerAppletBasketVo.getBasketCount();
                Double skuSize = Double.parseDouble(folwerSkuVo.getSize());
                if (size.compareTo(skuSize) < 0){
                    size = skuSize;
                }
                productNum = Math.toIntExact(productNum + folwerAppletBasketVo.getBasketCount());
            }
        } else {
            if (bo.getSkuId() != null) {
                FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(Long.valueOf(bo.getSkuId()));
                weight = weight + folwerSkuVo.getWeight() * bo.getProdCount();
                size = Double.valueOf(folwerSkuVo.getSize());
                productNum = bo.getProdCount();
            }
        }

        FolwerDeliveryBoxVo deliveryBoxVo = new FolwerDeliveryBoxVo();
        Integer boxNum = 0;
        Double iceBottleWeightTotal = 0.0;

        if (weight > 0) {
            FolwerDeliveryBoxBo folwerDeliveryBoxBo = new FolwerDeliveryBoxBo();
            folwerDeliveryBoxBo.setStatus(1L);
            List<FolwerDeliveryBoxVo> folwerDeliveryBoxVos = folwerDeliveryBoxService.queryList(folwerDeliveryBoxBo);

            for (FolwerDeliveryBoxVo folwerDeliveryBoxVo : folwerDeliveryBoxVos){
                if (size.compareTo(Double.valueOf(folwerDeliveryBoxVo.getLength().toString()))< 0){
                    deliveryBoxVo = folwerDeliveryBoxVo;
                }
            }
            Double iceBottleWeight = 0.0;//folwerDeliverySetVo.getIceBottleNum() * folwerDeliverySetVo.getIceBottleWeight();
            boxNum = (int)Math.ceil(weight / (deliveryBoxVo.getPackagPrice()-iceBottleWeight));
            iceBottleWeightTotal = iceBottleWeight * boxNum;
        }
        weight = weight + iceBottleWeightTotal;

        //箱子费用
        BigDecimal boxPrace = deliveryBoxVo.getCostPrice().multiply(new BigDecimal(boxNum));
        BigDecimal insulationPrace = new BigDecimal(0);
        Integer insulationTotalNum = 0;
        BigDecimal iceBottlePrace = new BigDecimal(0);
        Integer iceBottleTotalNum = 0;

        //保温棉
        if(bo.getInsulationNum() == null || bo.getInsulationNum().equals(0L)){
            double insulationTemperature = Math.abs(Math.ceil(weatherByTemperature - deliveryBoxVo.getUseInsulationStarttime()));
            double insulationTemperatureNum = Math.ceil(insulationTemperature / deliveryBoxVo.getUseInsulationEndtime());
            BigDecimal insulationPra= deliveryBoxVo.getInsulationCotton().multiply(new BigDecimal(insulationTemperatureNum));
            insulationPrace = insulationPra.multiply(new BigDecimal(boxNum));
            insulationTotalNum = Math.multiplyExact(boxNum, (int) insulationTemperatureNum);
        }else {
            BigDecimal insulationPra = deliveryBoxVo.getInsulationCotton().multiply(new BigDecimal(bo.getInsulationNum()));
            insulationPrace = insulationPra.multiply(new BigDecimal(boxNum));
            insulationTotalNum = Math.multiplyExact(boxNum, bo.getInsulationNum().intValue());
        }

        //冰瓶
//        if (Double.compare(weatherByTemperature, folwerDeliverySetVo.getUseInsulationStarttime()) >=0 ){
//            double iceBottleTemperature = Math.abs(Math.ceil(weatherByTemperature - folwerDeliverySetVo.getUseIceBottleStarttime()));
//            double iceBottleTemperatureNum = Math.ceil(iceBottleTemperature / folwerDeliverySetVo.getUseIceBottleEndtime());
//            iceBottleTotalNum = Math.multiplyExact(boxNum, (int) (iceBottleTemperatureNum + folwerDeliverySetVo.getIceBottleNum()));
//            iceBottlePrace = folwerDeliverySetVo.getIceBottle().multiply(new BigDecimal(iceBottleTotalNum));
//        }else {
//            iceBottleTotalNum = Math.multiplyExact(boxNum, folwerDeliverySetVo.getIceBottleNum());
//            iceBottlePrace = BigDecimal.ZERO;//folwerDeliverySetVo.getIceBottle().multiply(new BigDecimal(iceBottleTotalNum));
//        }

        // 调用累加方法
        materialPrace = insulationPrace.add(iceBottlePrace);
        //包材费
        packagePrace = boxPrace;

        //运费
        FolwerAppletOrderDvy folwerAppletOrderDvy = new FolwerAppletOrderDvy();
        if (bo.getDvyId() == null || bo.getDvyId() == 0L) {
            transfee = new BigDecimal(0);
        }else {
            //判断是否在配送省份 FolwerDeliveryTemplate这张表改为存放不能运输的省份
            FolwerDeliveryVo folwerDeliveryVo = folwerDeliveryService.queryById(bo.getDvyId());
            if (folwerDeliveryVo == null) {
//            throw new Exception("配送公司不存在");
                transfee = new BigDecimal(0);
            }else {

                if (folwerDeliveryVo.getIsCod() == 2L){
                    Long provinceId = null;
                    Long cityId = null;
                    Long countyId = null;

                    if (bo.getUserId() != null) {

                        if (folwerPickAddrVos != null && folwerPickAddrVos.size() > 0) {
                            provinceId = folwerPickAddrVos.get(0).getProvinceId();
                            cityId = folwerPickAddrVos.get(0).getCityId();
                        }
                        FolwerDeliveryTemplateBo folwerDeliveryTemplateBo = new FolwerDeliveryTemplateBo();
                        folwerDeliveryTemplateBo.setParentId(provinceId);
                        List<FolwerDeliveryTemplateVo> folwerDeliveryTemplateVos = folwerDeliveryTemplateService.queryList(folwerDeliveryTemplateBo);
                        if (folwerDeliveryTemplateVos != null && folwerDeliveryTemplateVos.size() > 0) {
//                    throw new Exception("该省份不支持配送");
                            return  null;
                        }
                    }
                    //判断货运方式
                    FolwerAppletDeliveryPriceBo fadpBo = new FolwerAppletDeliveryPriceBo();
                    fadpBo.setDvyId(bo.getDvyId());
                    fadpBo.setProvinceId(provinceId);
                    fadpBo.setCityId(cityId);
                    fadpBo.setCountyId(countyId);

                    List<FolwerAppletDeliveryPriceVo> folwerAppletDeliveryPriceVos = folwerAppletDeliveryPriceService.queryList(fadpBo);
                    if (folwerAppletDeliveryPriceVos != null && folwerAppletDeliveryPriceVos.size() > 0){
                        FolwerAppletDeliveryPriceVo folwerAppletDeliveryPrice = folwerAppletDeliveryPriceVos.get(0);

                        if (Double.compare(folwerAppletDeliveryPrice.getFirstWeight(), weight) > 0) {
                            BigDecimal bweight = new BigDecimal(folwerAppletDeliveryPrice.getFirstWeight());
                            transfee = transfee.add(folwerAppletDeliveryPrice.getFirstWeightPrice());
                        } else {
                            BigDecimal firstWeight = new BigDecimal(folwerAppletDeliveryPrice.getFirstWeight());
                            transfee = transfee.add(folwerAppletDeliveryPrice.getFirstWeightPrice());
                            BigDecimal bweight = new BigDecimal(weight);
                            BigDecimal subtract = bweight.subtract(firstWeight);
                            transfee = transfee.add(folwerAppletDeliveryPrice.getAdditionalWeightPrice().multiply(subtract));
                        }
                        folwerAppletOrderDvy.setDvyName(folwerDeliveryVo.getDvyName());
                        folwerAppletOrderDvy.setFirstWeight(folwerAppletDeliveryPrice.getFirstWeight());
                        folwerAppletOrderDvy.setAdditionalWeight(folwerAppletDeliveryPrice.getAdditionalWeight());
                        folwerAppletOrderDvy.setFirstWeightPrice(folwerAppletDeliveryPrice.getFirstWeightPrice());
                        folwerAppletOrderDvy.setAdditionalWeightPrice(folwerAppletDeliveryPrice.getAdditionalWeightPrice());
                    }
                }
            }
        }

        //物料总费用
        List<BigDecimal> material= Arrays.asList(
            materialPrace,
            packagePrace,
            laborPrace
        );
        BigDecimal materialTotalPrace= addAll(material);

        // 准备多个需要相加的数据（推荐用字符串构造，避免精度问题）
        List<BigDecimal> numbers = Arrays.asList(
            materialTotalPrace,
            transfee
        );

        // 调用累加方法
        totalPrice = addAll(numbers);

        folwerAppletOrderDvy.setOrderId(bo.getOrderId());
        folwerAppletOrderDvy.setDvyId(bo.getDvyId());
        folwerAppletOrderDvy.setDvyWeight(keepTwoDecimal(weight));
        folwerAppletOrderDvy.setFlowerNum(Long.valueOf(productNum));
        folwerAppletOrderDvy.setDvyNum(Long.valueOf(boxNum));
        folwerAppletOrderDvy.setFreightAmount(transfee);
        folwerAppletOrderDvy.setInsulationNum(Long.valueOf(insulationTotalNum));
        folwerAppletOrderDvy.setInsulationAmount(insulationPrace);
        folwerAppletOrderDvy.setIceNum(Long.valueOf(iceBottleTotalNum));
        folwerAppletOrderDvy.setIceAmount(iceBottlePrace);
        folwerAppletOrderDvy.setLaborPrice(folwerDeliverySetVo.getLaborPrice());
        folwerAppletOrderDvy.setBoxPrice(packagePrace);
        folwerAppletOrderDvy.setMaterialPrace(materialTotalPrace);
        folwerAppletOrderDvy.setPackingAmount(totalPrice);

        return folwerAppletOrderDvy;
    }

    private FolwerAppletOrderDvy calculateTheCost(FolwerAppletOrderDvyBo bo) throws Exception {
        //总费用
        BigDecimal totalPrice = new BigDecimal(0);

        //物料费
        BigDecimal materialPrace = new BigDecimal(0);
        //包材费
        BigDecimal packagePrace = new BigDecimal(0);
        //人工费
        BigDecimal laborPrace = new BigDecimal(0);
        //运费
        BigDecimal transfee = new BigDecimal(0);

        FolwerPickAddrBo folwerPickAddrBo = new FolwerPickAddrBo();
        folwerPickAddrBo.setUserId(bo.getUserId());
        List<FolwerPickAddrVo> folwerPickAddrVos = folwerPickAddrService.queryList(folwerPickAddrBo);
        FolwerPickAddrVo folwerPickAddrVo = new FolwerPickAddrVo();
        if (folwerPickAddrVos == null && folwerPickAddrVos.size() <= 0){
            throw new Exception("请选择收货地址");
        }else {
            folwerPickAddrVo = folwerPickAddrVos.get(0);
        }
        //温度
        double weatherByTemperature = 0.0;
        FolwerDeliveryTemperatureBo folwerDeliveryTemperatureBo = new FolwerDeliveryTemperatureBo();
        folwerDeliveryTemperatureBo.setAreaCode(folwerPickAddrVo.getCityId());
        List<FolwerDeliveryTemperatureVo> folwerDeliveryTemperatureVos = folwerDeliveryTemperatureService.queryList(folwerDeliveryTemperatureBo);
        if (folwerDeliveryTemperatureVos != null && folwerDeliveryTemperatureVos.size() > 0){

            Double wendu = folwerDeliveryTemperatureService.getWeatherByTemperature(folwerDeliveryTemperatureVos.get(0).getCityCode().toString());
            weatherByTemperature = wendu;
        }

        //物流设置
//        FolwerDeliverySetBo deliverySetBo = new FolwerDeliverySetBo();
//        List<FolwerDeliverySetVo> folwerDeliverySetVos = folwerDeliverySetService.queryList(deliverySetBo);
//        FolwerDeliverySetVo folwerDeliverySetVo = folwerDeliverySetVos.get(0);
//        laborPrace = folwerDeliverySetVo.getLaborPrice(); //人工费

        //花材重量
        Double flowerWeight = 0.0;
        //尺寸
        Double size = 0.0;
        //购买花的数量
        Integer productNum = 0;
        Map<Long, FolwerSkuVo> folwerSkuVoMap = new HashMap<>();
        if (bo.getIsContinuePay() == 0){
            if (bo.getBasketIds() != null) {
                for (String basketId : bo.getBasketIds()) {
                    FolwerAppletBasketVo folwerAppletBasketVo = folwerBasketService.queryById(Long.parseLong(basketId));
                    FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(folwerAppletBasketVo.getSkuId());
                    flowerWeight = flowerWeight + folwerSkuVo.getWeight() * folwerAppletBasketVo.getBasketCount();
                    Double skuSize = Double.parseDouble(folwerSkuVo.getSize());
                    if (size.compareTo(skuSize) < 0){
                        size = skuSize;
                    }
                    productNum = Math.toIntExact(productNum + folwerAppletBasketVo.getBasketCount());
                    folwerSkuVoMap.put(folwerSkuVo.getSkuId(), folwerSkuVo);
                }
            } else {
                if (bo.getSkuId() != null) {
                    FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(Long.valueOf(bo.getSkuId()));
                    flowerWeight = flowerWeight + folwerSkuVo.getWeight() * bo.getProdCount();
                    size = Double.valueOf(folwerSkuVo.getSize());
                    productNum = bo.getProdCount();
                    folwerSkuVoMap.put(folwerSkuVo.getSkuId(), folwerSkuVo);
                }
            }
        }if (bo.getIsContinuePay() == 1){
            FolwerAppletOrderDetailBo folwerAppletOrderDetailBo = new FolwerAppletOrderDetailBo();
            folwerAppletOrderDetailBo.setOrderId(String.valueOf(bo.getOrderId()));
            List<FolwerAppletOrderDetailVo> folwerAppletOrderDetailVos = folwerAppletOrderDetailService.queryList(folwerAppletOrderDetailBo);
            for ( FolwerAppletOrderDetailVo  folwerAppletOrderDetailVo : folwerAppletOrderDetailVos){
                FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(folwerAppletOrderDetailVo.getSkuId());
                flowerWeight = flowerWeight + folwerSkuVo.getWeight() * folwerAppletOrderDetailVo.getNumber();
                Double skuSize = Double.parseDouble(folwerSkuVo.getSize());
                if (size.compareTo(skuSize) < 0){
                    size = skuSize;
                }
                productNum = Math.toIntExact(productNum + folwerAppletOrderDetailVo.getNumber());
                folwerSkuVoMap.put(folwerSkuVo.getSkuId(), folwerSkuVo);
            }

        }

        FolwerDeliveryBoxVo deliveryBoxVo = new FolwerDeliveryBoxVo();
        Integer boxNum = 0;
        Double iceBottleWeightTotal = 0.0;
        Double weight = 0.0;
        if (flowerWeight > 0) {
            FolwerDeliveryBoxBo folwerDeliveryBoxBo = new FolwerDeliveryBoxBo();
            folwerDeliveryBoxBo.setStatus(1L);
            List<FolwerDeliveryBoxVo> folwerDeliveryBoxVos = folwerDeliveryBoxService.queryList(folwerDeliveryBoxBo);

            for (FolwerDeliveryBoxVo folwerDeliveryBoxVo : folwerDeliveryBoxVos){
                if (size.compareTo(Double.valueOf(folwerDeliveryBoxVo.getLength().toString()))< 0){
                    deliveryBoxVo = folwerDeliveryBoxVo;
                }
            }

            //花的冰瓶数
            Integer iceBottleFlowerNum = 0;
            if (bo.getBasketIds() != null) {
                for (String basketId : bo.getBasketIds()) {
                    FolwerAppletBasketVo folwerAppletBasketVo = folwerBasketService.queryById(Long.parseLong(basketId));
                    FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(folwerAppletBasketVo.getSkuId());
                    FolwerDeliverySetBo deliverySetBo = new FolwerDeliverySetBo();
                    deliverySetBo.setSkuId(folwerSkuVo.getSkuId());
                    FolwerDeliverySetVo folwerDeliverySetVo = folwerDeliverySetService.queryList(deliverySetBo).get(0);
                    if(folwerDeliverySetVo.getIceBottleNum() != null){
                        Integer iceBottlePNum = (int) Math.ceil(folwerDeliverySetVo.getIceBottleNum() * folwerAppletBasketVo.getBasketCount());
                        iceBottleFlowerNum = Math.toIntExact(iceBottleFlowerNum + iceBottlePNum);
                    }
//                    BigDecimal laborFlowerPrace = folwerDeliverySetVo.getLaborPrice().multiply(new BigDecimal(folwerAppletBasketVo.getBasketCount()));
                    BigDecimal materialFlowerPrace = new BigDecimal(0);
                    if (folwerDeliverySetVo.getStatus() == 1L){
                        materialFlowerPrace = folwerDeliverySetVo.getSecondLaborPrice().multiply(new BigDecimal(folwerAppletBasketVo.getBasketCount()));
                    }
                    BigDecimal flowerLab = materialFlowerPrace.add(materialFlowerPrace);
                    laborPrace = laborPrace.add(flowerLab);
                }
            } else {
                if (bo.getSkuId() != null) {
                    FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(Long.valueOf(bo.getSkuId()));
                    FolwerDeliverySetBo deliverySetBo = new FolwerDeliverySetBo();
                    deliverySetBo.setSkuId(folwerSkuVo.getSkuId());
                    FolwerDeliverySetVo folwerDeliverySetVo = folwerDeliverySetService.queryList(deliverySetBo).get(0);
                    if(folwerDeliverySetVo.getIceBottleNum() != null){
                        Integer iceBottlePNum = (int) Math.ceil(folwerDeliverySetVo.getIceBottleNum() * bo.getProdCount());
                        iceBottleFlowerNum = Math.toIntExact(iceBottleFlowerNum + iceBottlePNum);
                    }

//                    BigDecimal laborFlowerPrace = folwerDeliverySetVo.getLaborPrice().multiply(new BigDecimal(bo.getProdCount()));
                    BigDecimal materialFlowerPrace = new BigDecimal(0);
                    if (folwerDeliverySetVo.getStatus() == 1L){
                        materialFlowerPrace = folwerDeliverySetVo.getSecondLaborPrice().multiply(new BigDecimal(bo.getProdCount()));
                    }
                    laborPrace = laborPrace.add(materialFlowerPrace);
                }
            }

            //箱子的冰瓶数
//            Integer iceBottleBoxNum = (int)Math.ceil(productNum * deliveryBoxVo.getIceBunch());
            Integer iceBottleBoxNum = (int)Math.ceil(deliveryBoxVo.getBundle() * deliveryBoxVo.getIceBunch());
            //每箱冰瓶重量
            Double iceBottleBoxWeight = deliveryBoxVo.getIceBottleWeight() * iceBottleBoxNum;
            double flowerBoxWeight = deliveryBoxVo.getPackagPrice() - iceBottleBoxWeight;
            boxNum = (int)Math.ceil(flowerWeight / flowerBoxWeight);
            //冰瓶总数
            Integer iceBottleTotalNum = iceBottleBoxNum * boxNum + iceBottleFlowerNum;
            //冰瓶价格
            BigDecimal iceBottlePrace = deliveryBoxVo.getIceBottleCost().multiply(new BigDecimal(iceBottleTotalNum));
            //冰瓶总重量
            Double iceBottleWeight = boxNum * iceBottleBoxWeight;
//            Double combinedFlowerIceWeight = flowerWeight + iceBottleWeight;

//            Integer boxNumWeight = (int)Math.ceil(combinedFlowerIceWeight/deliveryBoxVo.getPackagPrice());
//            Integer boxNumbunch = (int)Math.ceil(productNum / deliveryBoxVo.getIceBunch());
//            if (boxNumWeight > boxNumbunch){
//                boxNum = boxNumbunch;
//            }else if (boxNumWeight < boxNumbunch){
//                boxNum = boxNumWeight;
//            }
            Double boxWeight = boxNum * deliveryBoxVo.getBoxWeight();
            weight = flowerWeight + iceBottleWeight + boxWeight;
            //箱子费用
            BigDecimal boxPrace = deliveryBoxVo.getCostPrice().multiply(new BigDecimal(boxNum));
            //人工费
            laborPrace = laborPrace.add(deliveryBoxVo.getVolume());

//            //每箱保温棉数量
            Integer insulationBoxNum = 0;
            //保温棉总数量
            Integer insulationTotalNum = 0;
            BigDecimal insulationPrace = new BigDecimal(0);
            //保温棉
            if(bo.getInsulationNum() == null){ // || bo.getInsulationNum().equals(0L)){
                double insulationTemperature = Math.abs(Math.ceil(weatherByTemperature - deliveryBoxVo.getUseInsulationStarttime()));
                double insulationTemperatureNum = Math.ceil(insulationTemperature / deliveryBoxVo.getUseInsulationEndtime());
                BigDecimal insulationPra= deliveryBoxVo.getInsulationCotton().multiply(new BigDecimal(insulationTemperatureNum));
                insulationPrace = insulationPra.multiply(new BigDecimal(boxNum));
                insulationBoxNum = (int)insulationTemperatureNum;
                insulationTotalNum = Math.multiplyExact(boxNum, (int) insulationTemperatureNum);
            }else {
                BigDecimal insulationPra = deliveryBoxVo.getInsulationCotton().multiply(new BigDecimal(bo.getInsulationNum()));
                insulationPrace = insulationPra.multiply(new BigDecimal(boxNum));
                insulationTotalNum = Math.multiplyExact(boxNum, bo.getInsulationNum().intValue());
                insulationBoxNum = Math.toIntExact(bo.getInsulationNum());
            }

        // 调用累加方法
        materialPrace = insulationPrace.add(iceBottlePrace);
        //包材费
        packagePrace = boxPrace;

        //运费
        FolwerAppletOrderDvy folwerAppletOrderDvy = new FolwerAppletOrderDvy();
        if (bo.getDvyId() == null || bo.getDvyId() == 0L) {
            transfee = new BigDecimal(0);
        }else {
            //判断是否在配送省份 FolwerDeliveryTemplate这张表改为存放不能运输的省份
            FolwerDeliveryVo folwerDeliveryVo = folwerDeliveryService.queryById(bo.getDvyId());
            if (folwerDeliveryVo == null) {
//            throw new Exception("配送公司不存在");
                transfee = new BigDecimal(0);
            }else {

                if (folwerDeliveryVo.getIsCod() == 2L){
                    Long provinceId = null;
                    Long cityId = null;
                    Long countyId = null;

                    if (bo.getUserId() != null) {

                        if (folwerPickAddrVos != null && folwerPickAddrVos.size() > 0) {
                            provinceId = folwerPickAddrVos.get(0).getProvinceId();
                            cityId = folwerPickAddrVos.get(0).getCityId();
                        }
                        FolwerDeliveryTemplateBo folwerDeliveryTemplateBo = new FolwerDeliveryTemplateBo();
                        folwerDeliveryTemplateBo.setParentId(provinceId);
                        List<FolwerDeliveryTemplateVo> folwerDeliveryTemplateVos = folwerDeliveryTemplateService.queryList(folwerDeliveryTemplateBo);
                        if (folwerDeliveryTemplateVos != null && folwerDeliveryTemplateVos.size() > 0) {
//                    throw new Exception("该省份不支持配送");
                            return  null;
                        }
                    }
                    //判断货运方式
                    FolwerAppletDeliveryPriceBo fadpBo = new FolwerAppletDeliveryPriceBo();
                    fadpBo.setDvyId(bo.getDvyId());
                    fadpBo.setProvinceId(provinceId);
                    fadpBo.setCityId(cityId);
                    fadpBo.setCountyId(countyId);

                    List<FolwerAppletDeliveryPriceVo> folwerAppletDeliveryPriceVos = folwerAppletDeliveryPriceService.queryList(fadpBo);
                    if (folwerAppletDeliveryPriceVos != null && folwerAppletDeliveryPriceVos.size() > 0){
                        FolwerAppletDeliveryPriceVo folwerAppletDeliveryPrice = folwerAppletDeliveryPriceVos.get(0);

                        if (Double.compare(folwerAppletDeliveryPrice.getFirstWeight(), weight) > 0) {
                            BigDecimal bweight = new BigDecimal(folwerAppletDeliveryPrice.getFirstWeight());
                            transfee = transfee.add(folwerAppletDeliveryPrice.getFirstWeightPrice());
                        } else {
                            BigDecimal firstWeight = new BigDecimal(folwerAppletDeliveryPrice.getFirstWeight());
                            transfee = transfee.add(folwerAppletDeliveryPrice.getFirstWeightPrice());
                            BigDecimal bweight = new BigDecimal(weight);
                            BigDecimal subtract = bweight.subtract(firstWeight);
                            transfee = transfee.add(folwerAppletDeliveryPrice.getAdditionalWeightPrice().multiply(subtract));
                        }
                        folwerAppletOrderDvy.setDvyName(folwerDeliveryVo.getDvyName());
                        folwerAppletOrderDvy.setFirstWeight(folwerAppletDeliveryPrice.getFirstWeight());
                        folwerAppletOrderDvy.setAdditionalWeight(folwerAppletDeliveryPrice.getAdditionalWeight());
                        folwerAppletOrderDvy.setFirstWeightPrice(folwerAppletDeliveryPrice.getFirstWeightPrice());
                        folwerAppletOrderDvy.setAdditionalWeightPrice(folwerAppletDeliveryPrice.getAdditionalWeightPrice());
                    }
                }
            }
        }

        //物料总费用
        List<BigDecimal> material= Arrays.asList(
            materialPrace,
            packagePrace,
            laborPrace
        );
        BigDecimal materialTotalPrace= addAll(material);

        // 准备多个需要相加的数据（推荐用字符串构造，避免精度问题）
        List<BigDecimal> numbers = Arrays.asList(
            materialTotalPrace,
            transfee
        );

        // 调用累加方法
        totalPrice = addAll(numbers);

        folwerAppletOrderDvy.setOrderId(bo.getOrderId());
        folwerAppletOrderDvy.setDvyId(bo.getDvyId());
        folwerAppletOrderDvy.setDvyWeight(keepTwoDecimal(weight));
        folwerAppletOrderDvy.setFlowerNum(Long.valueOf(productNum));
        folwerAppletOrderDvy.setDvyNum(Long.valueOf(boxNum));
        folwerAppletOrderDvy.setFreightAmount(transfee.setScale(0, RoundingMode.HALF_EVEN));
        folwerAppletOrderDvy.setInsulationNum(Long.valueOf(insulationBoxNum));
        folwerAppletOrderDvy.setInsulationTotalNum(Long.valueOf(insulationTotalNum));
        folwerAppletOrderDvy.setInsulationAmount(insulationPrace.setScale(0, RoundingMode.HALF_EVEN));
        folwerAppletOrderDvy.setIceNum(Long.valueOf(iceBottleTotalNum));
        folwerAppletOrderDvy.setIceAmount(iceBottlePrace.setScale(0, RoundingMode.HALF_EVEN));
        folwerAppletOrderDvy.setLaborPrice(laborPrace.setScale(0, RoundingMode.HALF_EVEN));
        folwerAppletOrderDvy.setBoxPrice(packagePrace.setScale(0, RoundingMode.HALF_EVEN));
        folwerAppletOrderDvy.setMaterialPrace(materialTotalPrace.setScale(0, RoundingMode.HALF_EVEN));
        folwerAppletOrderDvy.setPackingAmount(totalPrice.setScale(0, RoundingMode.HALF_EVEN));

        return folwerAppletOrderDvy;
        }
        return null;

    }

    /**
     * 将Double类型数据保留两位小数，保持数据类型不变
     * @param num 原始数据
     * @return 保留两位小数后的Double
     */
    public Double keepTwoDecimal(Double num) {
        if (num == null) {
            return null;
        }
        // 使用BigDecimal处理精度，采用四舍五入模式
        BigDecimal bd = new BigDecimal(num.toString());
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletOrderDvy entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除订单物流信息
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
