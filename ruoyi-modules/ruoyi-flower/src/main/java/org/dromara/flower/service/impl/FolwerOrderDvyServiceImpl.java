package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerOrderDvyBo;
import org.dromara.flower.domain.vo.FolwerOrderDvyVo;
import org.dromara.flower.domain.FolwerOrderDvy;
import org.dromara.flower.mapper.FolwerOrderDvyMapper;
import org.dromara.flower.service.IFolwerOrderDvyService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单物流Service业务层处理
 *
 * @author mlhxj
 * @date 2025-09-28
 */
@RequiredArgsConstructor
@Service
public class FolwerOrderDvyServiceImpl implements IFolwerOrderDvyService {

    private final FolwerOrderDvyMapper baseMapper;

    /**
     * 查询订单物流
     *
     * @param orderDevId 主键
     * @return 订单物流
     */
    @Override
    public FolwerOrderDvyVo queryById(Long orderDevId){
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
    public TableDataInfo<FolwerOrderDvyVo> queryPageList(FolwerOrderDvyBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerOrderDvy> lqw = buildQueryWrapper(bo);
        Page<FolwerOrderDvyVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单物流列表
     *
     * @param bo 查询条件
     * @return 订单物流列表
     */
    @Override
    public List<FolwerOrderDvyVo> queryList(FolwerOrderDvyBo bo) {
        LambdaQueryWrapper<FolwerOrderDvy> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerOrderDvy> buildQueryWrapper(FolwerOrderDvyBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerOrderDvy> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getOrderId() != null, FolwerOrderDvy::getOrderId, bo.getOrderId());
        lqw.eq(bo.getDvyId() != null, FolwerOrderDvy::getDvyId, bo.getDvyId());
        lqw.like(StringUtils.isNotBlank(bo.getDvyName()), FolwerOrderDvy::getDvyName, bo.getDvyName());
        lqw.eq(bo.getDvyWeight() != null, FolwerOrderDvy::getDvyWeight, bo.getDvyWeight());
        lqw.eq(bo.getFlowerNum() != null, FolwerOrderDvy::getFlowerNum, bo.getFlowerNum());
        lqw.eq(bo.getDvyNum() != null, FolwerOrderDvy::getDvyNum, bo.getDvyNum());
        lqw.eq(bo.getFirstWeight() != null, FolwerOrderDvy::getFirstWeight, bo.getFirstWeight());
        lqw.eq(bo.getAdditionalWeight() != null, FolwerOrderDvy::getAdditionalWeight, bo.getAdditionalWeight());
        lqw.eq(bo.getFirstWeightPrice() != null, FolwerOrderDvy::getFirstWeightPrice, bo.getFirstWeightPrice());
        lqw.eq(bo.getAdditionalWeightPrice() != null, FolwerOrderDvy::getAdditionalWeightPrice, bo.getAdditionalWeightPrice());
        lqw.eq(bo.getFreightAmount() != null, FolwerOrderDvy::getFreightAmount, bo.getFreightAmount());
        lqw.eq(bo.getInsulationNum() != null, FolwerOrderDvy::getInsulationNum, bo.getInsulationNum());
        lqw.eq(bo.getInsulationAmount() != null, FolwerOrderDvy::getInsulationAmount, bo.getInsulationAmount());
        lqw.eq(bo.getIceNum() != null, FolwerOrderDvy::getIceNum, bo.getIceNum());
        lqw.eq(bo.getIceAmount() != null, FolwerOrderDvy::getIceAmount, bo.getIceAmount());
        lqw.eq(bo.getLaborPrice() != null, FolwerOrderDvy::getLaborPrice, bo.getLaborPrice());
        lqw.eq(bo.getBoxPrice() != null, FolwerOrderDvy::getBoxPrice, bo.getBoxPrice());
        lqw.eq(bo.getPackingAmount() != null, FolwerOrderDvy::getPackingAmount, bo.getPackingAmount());
        return lqw;
    }

    /**
     * 新增订单物流
     *
     * @param bo 订单物流
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerOrderDvyBo bo) {
        FolwerOrderDvy add = MapstructUtils.convert(bo, FolwerOrderDvy.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOrderDevId(add.getOrderDevId());
        }
        return flag;
    }

    /**
     * 修改订单物流
     *
     * @param bo 订单物流
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerOrderDvyBo bo) {
        FolwerOrderDvy update = MapstructUtils.convert(bo, FolwerOrderDvy.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerOrderDvy entity){
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
