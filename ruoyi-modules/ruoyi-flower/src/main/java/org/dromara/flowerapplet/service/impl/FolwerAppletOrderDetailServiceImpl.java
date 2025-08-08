package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDetailBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.FolwerAppletOrderDetail;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderDetailMapper;
import org.dromara.flowerapplet.service.IFolwerAppletOrderDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单详细Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletOrderDetailServiceImpl implements IFolwerAppletOrderDetailService {

    private final FolwerAppletOrderDetailMapper baseMapper;

    private final IFolwerAppletSkuService folwerAppletSkuService;

    /**
     * 查询订单详细
     *
     * @param id 主键
     * @return 订单详细
     */
    @Override
    public FolwerAppletOrderDetailVo queryById(Long id){
        FolwerAppletOrderDetailVo folwerAppletOrderDetailVo = baseMapper.selectVoById(id);
        if(folwerAppletOrderDetailVo.getSkuId() != null){
            folwerAppletOrderDetailVo.setSkuName(folwerAppletSkuService.queryById(folwerAppletOrderDetailVo.getSkuId()).getSkuName());
        }
        return folwerAppletOrderDetailVo;
    }

    /**
     * 分页查询订单详细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单详细分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletOrderDetailVo> queryPageList(FolwerAppletOrderDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletOrderDetail> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletOrderDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单详细列表
     *
     * @param bo 查询条件
     * @return 订单详细列表
     */
    @Override
    public List<FolwerAppletOrderDetailVo> queryList(FolwerAppletOrderDetailBo bo) {
        LambdaQueryWrapper<FolwerAppletOrderDetail> lqw = buildQueryWrapper(bo);
        List<FolwerAppletOrderDetailVo> folwerAppletOrderDetailVos = baseMapper.selectVoList(lqw);
        for (FolwerAppletOrderDetailVo folwerAppletOrderDetailVo : folwerAppletOrderDetailVos) {
            if(folwerAppletOrderDetailVo.getSkuId() != null){
                folwerAppletOrderDetailVo.setSkuName(folwerAppletSkuService.selsctById(folwerAppletOrderDetailVo.getSkuId()).getSkuName());
            }
        }
        return folwerAppletOrderDetailVos;
    }

    private LambdaQueryWrapper<FolwerAppletOrderDetail> buildQueryWrapper(FolwerAppletOrderDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderId()), FolwerAppletOrderDetail::getOrderId, bo.getOrderId());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerAppletOrderDetail::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerAppletOrderDetail::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(bo.getOrderPrice() != null, FolwerAppletOrderDetail::getOrderPrice, bo.getOrderPrice());
        lqw.eq(bo.getNumber() != null, FolwerAppletOrderDetail::getNumber, bo.getNumber());
        lqw.eq(bo.getSubtotal() != null, FolwerAppletOrderDetail::getSubtotal, bo.getSubtotal());
        return lqw;
    }

    /**
     * 新增订单详细
     *
     * @param bo 订单详细
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletOrderDetailBo bo) {
        FolwerAppletOrderDetail add = MapstructUtils.convert(bo, FolwerAppletOrderDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改订单详细
     *
     * @param bo 订单详细
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletOrderDetailBo bo) {
        FolwerAppletOrderDetail update = MapstructUtils.convert(bo, FolwerAppletOrderDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletOrderDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除订单详细信息
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
