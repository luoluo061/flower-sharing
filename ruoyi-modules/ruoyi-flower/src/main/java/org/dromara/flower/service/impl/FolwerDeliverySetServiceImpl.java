package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerDeliveryPrice;
import org.dromara.flower.domain.bo.FolwerDeliveryPriceBo;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerDeliverySetBo;
import org.dromara.flower.domain.vo.FolwerDeliverySetVo;
import org.dromara.flower.domain.FolwerDeliverySet;
import org.dromara.flower.mapper.FolwerDeliverySetMapper;
import org.dromara.flower.service.IFolwerDeliverySetService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 物流设置Service业务层处理
 *
 * @author mlhxj
 * @date 2025-08-01
 */
@RequiredArgsConstructor
@Service
public class FolwerDeliverySetServiceImpl implements IFolwerDeliverySetService {

    private final FolwerDeliverySetMapper baseMapper;

    /**
     * 查询物流设置
     *
     * @param deliverySetId 主键
     * @return 物流设置
     */
    @Override
    public FolwerDeliverySetVo queryById(Long deliverySetId){
        return baseMapper.selectVoById(deliverySetId);
    }

    /**
     * 分页查询物流设置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流设置分页列表
     */
    @Override
    public TableDataInfo<FolwerDeliverySetVo> queryPageList(FolwerDeliverySetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerDeliverySet> lqw = buildQueryWrapper(bo);
        Page<FolwerDeliverySetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的物流设置列表
     *
     * @param bo 查询条件
     * @return 物流设置列表
     */
    @Override
    public List<FolwerDeliverySetVo> queryList(FolwerDeliverySetBo bo) {
        LambdaQueryWrapper<FolwerDeliverySet> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerDeliverySet> buildQueryWrapper(FolwerDeliverySetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerDeliverySet> lqw = Wrappers.lambdaQuery();
        lqw.eq((bo.getProdId() != null), FolwerDeliverySet::getProdId, bo.getProdId());
        lqw.eq(bo.getSkuId() != null, FolwerDeliverySet::getSkuId, bo.getSkuId());
        lqw.eq(bo.getStatus() != null, FolwerDeliverySet::getStatus, bo.getStatus());
        lqw.eq(bo.getLaborPrice() != null, FolwerDeliverySet::getLaborPrice, bo.getLaborPrice());
//        lqw.eq(bo.getInsulationCotton() != null, FolwerDeliverySet::getInsulationCotton, bo.getInsulationCotton());
//        lqw.eq(bo.getUseInsulationStarttime() != null, FolwerDeliverySet::getUseInsulationStarttime, bo.getUseInsulationStarttime());
//        lqw.eq(bo.getUseInsulationEndtime() != null, FolwerDeliverySet::getUseInsulationEndtime, bo.getUseInsulationEndtime());
//        lqw.eq(bo.getIceBottle() != null, FolwerDeliverySet::getIceBottle, bo.getIceBottle());
        lqw.eq(bo.getIceBottleNum() != null, FolwerDeliverySet::getIceBottleNum, bo.getIceBottleNum());
//        lqw.eq(bo.getUseIceBottleStarttime() != null, FolwerDeliverySet::getUseIceBottleStarttime, bo.getUseIceBottleStarttime());
//        lqw.eq(bo.getUseIceBottleEndtime() != null, FolwerDeliverySet::getUseIceBottleEndtime, bo.getUseIceBottleEndtime());
        return lqw;
    }

    /**
     * 新增物流设置
     *
     * @param bo 物流设置
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerDeliverySetBo bo) {
        FolwerDeliverySet add = MapstructUtils.convert(bo, FolwerDeliverySet.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDeliverySetId(add.getDeliverySetId());
        }
        return flag;
    }

    /**
     * 修改物流设置
     *
     * @param bos 物流设置
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(List<FolwerDeliverySetBo> bos) {
        List<FolwerDeliverySet> deliverySetList = new ArrayList<>();
        for (FolwerDeliverySetBo bo : bos){
            FolwerDeliverySet update = MapstructUtils.convert(bo, FolwerDeliverySet.class);
            validEntityBeforeSave(update);
            deliverySetList.add(update);
        }
        boolean b = baseMapper.insertOrUpdateBatch(deliverySetList);
        return b;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerDeliverySet entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除物流设置信息
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
