package org.dromara.flowerapplet.service.impl;

import io.github.linpeilie.Converter;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.bo.FolwerDeliveryBoxBo;
import org.dromara.flower.domain.bo.FolwerDeliverySetBo;
import org.dromara.flower.domain.bo.FolwerDeliveryTemplateBo;
import org.dromara.flower.domain.bo.FolwerPickAddrBo;
import org.dromara.flower.domain.vo.*;
import org.dromara.flower.service.*;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;
import org.dromara.flowerapplet.service.IFolwerAppletBasketService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletDeliveryPriceBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletDeliveryPriceVo;
import org.dromara.flowerapplet.domain.FolwerAppletDeliveryPrice;
import org.dromara.flowerapplet.mapper.FolwerAppletDeliveryPriceMapper;
import org.dromara.flowerapplet.service.IFolwerAppletDeliveryPriceService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物流计费Service业务层处理
 *
 * @author mlhxj
 * @date 2025-07-16
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletDeliveryPriceServiceImpl implements IFolwerAppletDeliveryPriceService {

    private final FolwerAppletDeliveryPriceMapper baseMapper;

    private final IFolwerDeliveryService folwerDeliveryService;

    private final IFolwerSkuService folwerSkuService;

    private final IFolwerPickAddrService folwerPickAddrService;

    private final IFolwerDeliveryTemplateService folwerDeliveryTemplateService;

    private final IFolwerAppletBasketService folwerBasketService;

    private final Converter converter;

    private final IFolwerDeliverySetService folwerDeliverySetService;

    private final IFolwerDeliveryBoxService folwerDeliveryBoxService;


    /**
     * 查询物流计费
     *
     * @param logisticId 主键
     * @return 物流计费
     */
    @Override
    public FolwerAppletDeliveryPriceVo queryById(Long logisticId) {
        return baseMapper.selectVoById(logisticId);
    }

    /**
     * 分页查询物流计费列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流计费分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletDeliveryPriceVo> queryPageList(FolwerAppletDeliveryPriceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletDeliveryPrice> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletDeliveryPriceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的物流计费列表
     *
     * @param bo 查询条件
     * @return 物流计费列表
     */
    @Override
    public List<FolwerAppletDeliveryPriceVo> queryList(FolwerAppletDeliveryPriceBo bo) {
        LambdaQueryWrapper<FolwerAppletDeliveryPrice> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletDeliveryPrice> buildQueryWrapper(FolwerAppletDeliveryPriceBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletDeliveryPrice> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDvyId() != null, FolwerAppletDeliveryPrice::getDvyId, bo.getDvyId());
        lqw.eq(bo.getProvinceId() != null, FolwerAppletDeliveryPrice::getProvinceId, bo.getProvinceId());
        lqw.eq(StringUtils.isNotBlank(bo.getProvince()), FolwerAppletDeliveryPrice::getProvince, bo.getProvince());
        lqw.eq(bo.getCityId() != null, FolwerAppletDeliveryPrice::getCityId, bo.getCityId());
        lqw.eq(StringUtils.isNotBlank(bo.getCity()), FolwerAppletDeliveryPrice::getCity, bo.getCity());
        lqw.eq(bo.getCountyId() != null, FolwerAppletDeliveryPrice::getCountyId, bo.getCountyId());
        lqw.eq(StringUtils.isNotBlank(bo.getCounty()), FolwerAppletDeliveryPrice::getCounty, bo.getCounty());
        lqw.eq(bo.getFirstWeight() != null, FolwerAppletDeliveryPrice::getFirstWeight, bo.getFirstWeight());
        lqw.eq(bo.getAdditionalWeight() != null, FolwerAppletDeliveryPrice::getAdditionalWeight, bo.getAdditionalWeight());
        lqw.eq(bo.getFirstWeightPrice() != null, FolwerAppletDeliveryPrice::getFirstWeightPrice, bo.getFirstWeightPrice());
        lqw.eq(bo.getAdditionalWeightPrice() != null, FolwerAppletDeliveryPrice::getAdditionalWeightPrice, bo.getAdditionalWeightPrice());
        lqw.eq(bo.getAdditionalWeightPricel() != null, FolwerAppletDeliveryPrice::getAdditionalWeightPricel, bo.getAdditionalWeightPricel());
        lqw.eq(bo.getStatus() != null, FolwerAppletDeliveryPrice::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增物流计费
     *
     * @param bo 物流计费
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletDeliveryPriceBo bo) {
        FolwerAppletDeliveryPrice add = MapstructUtils.convert(bo, FolwerAppletDeliveryPrice.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setLogisticId(add.getLogisticId());
        }
        return flag;
    }

    /**
     * 修改物流计费
     *
     * @param bo 物流计费
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletDeliveryPriceBo bo) {
        FolwerAppletDeliveryPrice update = MapstructUtils.convert(bo, FolwerAppletDeliveryPrice.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletDeliveryPrice entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除物流计费信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public BigDecimal calculateFreight(Long deliveryId, Long userId, List<String> basketIds, Long skuId, Integer skuByNum, int insulationNum ) throws Exception {
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

        if (deliveryId == null || deliveryId == 0L) {
            return totalPrice;
        }

        FolwerDeliverySetBo deliverySetBo = new FolwerDeliverySetBo();
        List<FolwerDeliverySetVo> folwerDeliverySetVos = folwerDeliverySetService.queryList(deliverySetBo);
        FolwerDeliverySetVo folwerDeliverySetVo = folwerDeliverySetVos.get(0);
        laborPrace = folwerDeliverySetVo.getLaborPrice();

        Double weight = 0.0;
        Double size = 0.0;
        if (basketIds != null && basketIds.size() > 0) {
            for (String basketId : basketIds) {
                FolwerAppletBasketVo folwerAppletBasketVo = folwerBasketService.queryById(Long.parseLong(basketId));
                FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(folwerAppletBasketVo.getSkuId());
                weight = weight + folwerSkuVo.getWeight() * folwerAppletBasketVo.getBasketCount();
                if (size.compareTo(converter.convert(folwerSkuVo.getSize(), Double.class)) < 0){
                    size = converter.convert(folwerSkuVo.getSize(), Double.class);
                }
            }
        } else {
            if (skuId != null) {
                FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(skuId);
                weight = weight + folwerSkuVo.getWeight() * skuByNum;
                size = Double.valueOf(folwerSkuVo.getSize());
            }
        }

        FolwerDeliveryBoxVo deliveryBoxVo = new FolwerDeliveryBoxVo();
        Integer boxNum = 0;
        if (weight > 0) {
            FolwerDeliveryBoxBo folwerDeliveryBoxBo = new FolwerDeliveryBoxBo();
            folwerDeliveryBoxBo.setStatus(1L);
            List<FolwerDeliveryBoxVo> folwerDeliveryBoxVos = folwerDeliveryBoxService.queryList(folwerDeliveryBoxBo);

            for (FolwerDeliveryBoxVo folwerDeliveryBoxVo : folwerDeliveryBoxVos){
                if (size.compareTo(Double.valueOf(folwerDeliveryBoxVo.getLength().toString()))< 0){
                    deliveryBoxVo = folwerDeliveryBoxVo;
                }
            }
            boxNum = (int)Math.ceil(weight / deliveryBoxVo.getPackagPrice());
        }
        //箱子费用
        BigDecimal boxPrace = deliveryBoxVo.getCostPrice().multiply(new BigDecimal(boxNum));
        BigDecimal insulationPrace = new BigDecimal(0);
        BigDecimal iceBottlePrace = new BigDecimal(0);

        insulationPrace = folwerDeliverySetVo.getInsulationCotton().multiply(new BigDecimal(insulationNum));

        if(folwerDeliverySetVo.getUseIceBottleStarttime() == 0L && folwerDeliverySetVo.getUseIceBottleEndtime() == 0L){
            iceBottlePrace = new BigDecimal(0);
        }
        if (folwerDeliverySetVo.getUseIceBottleStarttime() <= LocalDate.now().getMonthValue() && folwerDeliverySetVo.getUseInsulationStarttime() <= folwerDeliverySetVo.getUseIceBottleEndtime()){
            long IceBottleTotalNum = Math.multiplyExact(boxNum, folwerDeliverySetVo.getIceBottleNum());
            iceBottlePrace = folwerDeliverySetVo.getIceBottle().multiply(new BigDecimal(IceBottleTotalNum));
        }

        // 调用累加方法
        materialPrace = insulationPrace.add(iceBottlePrace);
        //包材费
        packagePrace = boxPrace;


        //运费
        if (deliveryId == null || deliveryId == 0L) {
            transfee = new BigDecimal(0);
            return transfee;
        }
        //判断是否在配送省份 FolwerDeliveryTemplate这张表改为存放不能运输的省份
        FolwerDeliveryVo folwerDeliveryVo = folwerDeliveryService.queryById(deliveryId);
        if (folwerDeliveryVo == null) {
            throw new Exception("配送公司不存在");
        }
        Long provinceId = null;
        if (userId != null) {
            FolwerPickAddrBo bo = new FolwerPickAddrBo();
            bo.setUserId(userId.toString());
            List<FolwerPickAddrVo> folwerPickAddrVos = folwerPickAddrService.queryList(bo);
            if (folwerPickAddrVos != null && folwerPickAddrVos.size() > 0) {
                provinceId = folwerPickAddrVos.get(0).getProvinceId();
            }
            FolwerDeliveryTemplateBo folwerDeliveryTemplateBo = new FolwerDeliveryTemplateBo();
            folwerDeliveryTemplateBo.setParentId(provinceId);
            List<FolwerDeliveryTemplateVo> folwerDeliveryTemplateVos = folwerDeliveryTemplateService.queryList(folwerDeliveryTemplateBo);
            if (folwerDeliveryTemplateVos != null && folwerDeliveryTemplateVos.size() > 0) {
                throw new Exception("该省份不支持配送");
            }
        }
        //判断货运方式
        FolwerAppletDeliveryPriceBo fadpBo = new FolwerAppletDeliveryPriceBo();
        fadpBo.setDvyId(deliveryId);
        fadpBo.setProvinceId(provinceId);
        LambdaQueryWrapper<FolwerAppletDeliveryPrice> deliveryPriceLambdaQueryWrapper = buildQueryWrapper(fadpBo);

        FolwerAppletDeliveryPrice folwerAppletDeliveryPrice = baseMapper.selectOne(deliveryPriceLambdaQueryWrapper);



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
        // 准备多个需要相加的数据（推荐用字符串构造，避免精度问题）
        List<BigDecimal> numbers = Arrays.asList(
            materialPrace,
            packagePrace,
            laborPrace,
            transfee
        );

        // 调用累加方法
        totalPrice = addAll(numbers);
        return totalPrice;
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
}
