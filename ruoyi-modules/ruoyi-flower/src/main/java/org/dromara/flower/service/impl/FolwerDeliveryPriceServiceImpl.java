package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerDeliveryArea;
import org.dromara.flower.domain.FolwerDeliveryPriceAdd;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerDeliveryPriceBo;
import org.dromara.flower.domain.vo.FolwerDeliveryPriceVo;
import org.dromara.flower.domain.FolwerDeliveryPrice;
import org.dromara.flower.mapper.FolwerDeliveryPriceMapper;
import org.dromara.flower.service.IFolwerDeliveryPriceService;

import java.util.*;
import java.util.concurrent.CompletableFuture;

/**
 * 物流计费Service业务层处理
 *
 * @author mlhxj
 * @date 2025-09-15
 */
@RequiredArgsConstructor
@Service
public class FolwerDeliveryPriceServiceImpl implements IFolwerDeliveryPriceService {

    private final FolwerDeliveryPriceMapper baseMapper;

    /**
     * 查询物流计费
     *
     * @param logisticId 主键
     * @return 物流计费
     */
    @Override
    public FolwerDeliveryPriceVo queryById(Long logisticId){
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
    public TableDataInfo<FolwerDeliveryPriceVo> queryPageList(FolwerDeliveryPriceBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerDeliveryPrice> lqw = buildQueryWrapper(bo);
        Page<FolwerDeliveryPriceVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的物流计费列表
     *
     * @param bo 查询条件
     * @return 物流计费列表
     */
    @Override
    public List<FolwerDeliveryPriceVo> queryList(FolwerDeliveryPriceBo bo) {
        LambdaQueryWrapper<FolwerDeliveryPrice> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerDeliveryPrice> buildQueryWrapper(FolwerDeliveryPriceBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerDeliveryPrice> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDvyId() != null, FolwerDeliveryPrice::getDvyId, bo.getDvyId());
        lqw.like(StringUtils.isNotBlank(bo.getProvince()), FolwerDeliveryPrice::getProvince, bo.getProvince());
        lqw.like(StringUtils.isNotBlank(bo.getCity()), FolwerDeliveryPrice::getCity, bo.getCity());
        lqw.like(StringUtils.isNotBlank(bo.getCounty()), FolwerDeliveryPrice::getCounty, bo.getCounty());
        lqw.eq(bo.getProvinceId() != null, FolwerDeliveryPrice::getProvinceId, bo.getProvinceId());
        lqw.eq(bo.getCityId() != null, FolwerDeliveryPrice::getCityId, bo.getCityId());
        lqw.eq(bo.getCountyId() != null, FolwerDeliveryPrice::getCountyId, bo.getCountyId());
        lqw.eq(bo.getFirstWeight() != null, FolwerDeliveryPrice::getFirstWeight, bo.getFirstWeight());
        lqw.eq(bo.getAdditionalWeight() != null, FolwerDeliveryPrice::getAdditionalWeight, bo.getAdditionalWeight());
        lqw.eq(bo.getFirstWeightPrice() != null, FolwerDeliveryPrice::getFirstWeightPrice, bo.getFirstWeightPrice());
        lqw.eq(bo.getAdditionalWeightPrice() != null, FolwerDeliveryPrice::getAdditionalWeightPrice, bo.getAdditionalWeightPrice());
        lqw.eq(bo.getAdditionalWeightPricel() != null, FolwerDeliveryPrice::getAdditionalWeightPricel, bo.getAdditionalWeightPricel());
        lqw.eq(bo.getStatus() != null, FolwerDeliveryPrice::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增物流计费
     *
     * @param add 物流计费
     * @return 是否新增成功
     */
    @Override
    @Async
    public CompletableFuture<Boolean> insertByBo(FolwerDeliveryPriceAdd add, List<FolwerDeliveryArea> areaList) {

        FolwerDeliveryPriceBo deliveryPriceBo = new FolwerDeliveryPriceBo();
        deliveryPriceBo.setDvyId(add.getDvyId());
        List<FolwerDeliveryPriceVo> folwerDeliveryPriceVos = this.queryList(deliveryPriceBo);
        if (folwerDeliveryPriceVos.size() > 0){
            return CompletableFuture.completedFuture(false);
        }

        return CompletableFuture.supplyAsync(() -> {

            List<FolwerDeliveryPrice> priceList = new ArrayList<>();
            for (FolwerDeliveryArea area : areaList) {
                // 1. 提取省份信息（第一级节点：省）
                Long provinceId = Long.valueOf(area.getId());
                String provinceName = area.getName();

                // 2. 遍历市级节点（第二级）
                List<FolwerDeliveryArea> cityNodes = area.getChildren();
                if (cityNodes == null || cityNodes.isEmpty()) {
                    return false; // 没有市级节点则退出
                }
                for (FolwerDeliveryArea cityNode : cityNodes) {
                    // 提取市级信息
                    Long cityId = Long.valueOf(cityNode.getId());
                    String cityName = cityNode.getName();

                    // 3. 遍历县级节点（第三级）
                    List<FolwerDeliveryArea> countyNodes = cityNode.getChildren();
                    if (countyNodes == null || countyNodes.isEmpty()) {
                        continue; // 没有县级节点则跳过
                    }

                    for (FolwerDeliveryArea countyNode : countyNodes) {
                        // 提取县级信息
                        Long countyId = Long.valueOf(countyNode.getId());
                        String countyName = countyNode.getName();

                        // 4. 创建Region对象（扁平结构）
                        FolwerDeliveryPrice region = new FolwerDeliveryPrice();
                        region.setDvyId(add.getDvyId());
                        region.setProvinceId(provinceId);
                        region.setProvince(provinceName);
                        region.setCityId(cityId);
                        region.setCity(cityName);
                        region.setCountyId(countyId);
                        region.setCounty(countyName);
                        region.setFirstWeight(add.getFirstWeight());
                        region.setAdditionalWeight(add.getAdditionalWeight());
                        region.setFirstWeightPrice(add.getFirstWeightPrice());
                        region.setAdditionalWeightPrice(add.getAdditionalWeightPrice());
                        region.setAdditionalWeightPricel(add.getAdditionalWeightPricel());
                        region.setStatus(add.getStatus());

                        priceList.add(region);
                        // 5. 保存到数据库
//                        int insert = baseMapper.insert(region);
                    }
                }
            }
            return baseMapper.insertBatch(priceList);
//            return false;
        });
//        return false;
    }

    /**
     * 修改物流计费
     *
     * @param bos 物流计费
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(List<FolwerDeliveryPriceBo> bos) {
        List<FolwerDeliveryPrice> priceList = new ArrayList<>();
        for (FolwerDeliveryPriceBo bo : bos){
            FolwerDeliveryPrice update = MapstructUtils.convert(bo, FolwerDeliveryPrice.class);
            validEntityBeforeSave(update);
            priceList.add(update);
        }
        boolean b = baseMapper.insertOrUpdateBatch(priceList);
        return b;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerDeliveryPrice entity){
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
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * 校验并通过物流公司ID删除物流计费信息
     *
     * @param dvyId     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByDvyId(Long dvyId, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("dvy_id", dvyId);

        return baseMapper.deleteByMap(map) > 0;
    }
}
