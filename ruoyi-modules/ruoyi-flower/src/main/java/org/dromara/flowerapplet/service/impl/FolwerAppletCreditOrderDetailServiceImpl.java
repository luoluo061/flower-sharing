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
import org.dromara.flower.domain.bo.FolwerSkuBo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditProductVo;
import org.dromara.flowerapplet.service.IFolwerAppletCreditProductService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditOrderDetailBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditOrderDetailVo;
import org.dromara.flowerapplet.domain.FolwerAppletCreditOrderDetail;
import org.dromara.flowerapplet.mapper.FolwerAppletCreditOrderDetailMapper;
import org.dromara.flowerapplet.service.IFolwerAppletCreditOrderDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分订单详细Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletCreditOrderDetailServiceImpl implements IFolwerAppletCreditOrderDetailService {

    private final FolwerAppletCreditOrderDetailMapper baseMapper;

    private final IFolwerAppletCreditProductService folwerAppletCreditProductService;

    private final IFolwerSkuService folwerSkuService;

    /**
     * 查询积分订单详细
     *
     * @param id 主键
     * @return 积分订单详细
     */
    @Override
    public FolwerAppletCreditOrderDetailVo queryById(Long id){
        FolwerAppletCreditOrderDetailVo creditOrderDetailVo = baseMapper.selectVoById(id);
        if (creditOrderDetailVo != null){
            FolwerAppletCreditProductVo creditProductVo = folwerAppletCreditProductService.queryById(creditOrderDetailVo.getProductId());
            if (creditProductVo == null){
                try {
                    throw new Exception("商品不存在");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            if (creditProductVo.getNormsType() == 1){
                FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(creditOrderDetailVo.getSkuId());
                creditOrderDetailVo.setProductSKU(folwerSkuVo.getColour() + ", " + folwerSkuVo.getWeight() + ", " + folwerSkuVo.getSize());
            }else {
                creditOrderDetailVo.setProductSKU("默认规格");
            }
        }
        return creditOrderDetailVo;
    }

    /**
     * 分页查询积分订单详细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分订单详细分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletCreditOrderDetailVo> queryPageList(FolwerAppletCreditOrderDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletCreditOrderDetail> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletCreditOrderDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (result != null){
            result.getRecords().forEach(item -> {
                FolwerAppletCreditProductVo folwerAppletCreditProductVo = folwerAppletCreditProductService.queryById(item.getProductId());
                if (folwerAppletCreditProductVo == null){
                    try {
                        throw new Exception("商品不存在");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (folwerAppletCreditProductVo.getNormsType() == 1){
                    FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(item.getSkuId());
                    item.setProductSKU(folwerSkuVo.getColour() + ", " + folwerSkuVo.getWeight() + ", " + folwerSkuVo.getSize());
                }else {
                    item.setProductSKU("默认规格");
                }
            });
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分订单详细列表
     *
     * @param bo 查询条件
     * @return 积分订单详细列表
     */
    @Override
    public List<FolwerAppletCreditOrderDetailVo> queryList(FolwerAppletCreditOrderDetailBo bo) {
        LambdaQueryWrapper<FolwerAppletCreditOrderDetail> lqw = buildQueryWrapper(bo);
        List<FolwerAppletCreditOrderDetailVo> creditOrderDetailVos = baseMapper.selectVoList(lqw);
        if (creditOrderDetailVos != null){
            creditOrderDetailVos.forEach(item -> {
                FolwerAppletCreditProductVo folwerAppletCreditProductVo = folwerAppletCreditProductService.queryById(item.getProductId());
                if (folwerAppletCreditProductVo == null){
                    try {
                        throw new Exception("商品不存在");
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
                if (folwerAppletCreditProductVo.getNormsType() == 1){
                    FolwerSkuVo folwerSkuVo = folwerSkuService.queryById(item.getSkuId());
                    item.setProductSKU(folwerSkuVo.getColour() + ", " + folwerSkuVo.getWeight() + ", " + folwerSkuVo.getSize());
                }else {
                    item.setProductSKU("默认规格");
                }
            });
        }
        return creditOrderDetailVos;
    }

    private LambdaQueryWrapper<FolwerAppletCreditOrderDetail> buildQueryWrapper(FolwerAppletCreditOrderDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletCreditOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderId()), FolwerAppletCreditOrderDetail::getOrderId, bo.getOrderId());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerAppletCreditOrderDetail::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerAppletCreditOrderDetail::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(bo.getOrderPrice() != null, FolwerAppletCreditOrderDetail::getOrderPrice, bo.getOrderPrice());
        lqw.eq(bo.getNumber() != null, FolwerAppletCreditOrderDetail::getNumber, bo.getNumber());
        lqw.eq(bo.getSubtotal() != null, FolwerAppletCreditOrderDetail::getSubtotal, bo.getSubtotal());
        return lqw;
    }

    /**
     * 新增积分订单详细
     *
     * @param bo 积分订单详细
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletCreditOrderDetailBo bo) {
//        FolwerAppletCreditOrderDetail add = MapstructUtils.convert(bo, FolwerAppletCreditOrderDetail.class);
        FolwerAppletCreditOrderDetail add = new FolwerAppletCreditOrderDetail();
        BeanUtil.copyProperties(bo, add);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改积分订单详细
     *
     * @param bo 积分订单详细
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletCreditOrderDetailBo bo) {
        FolwerAppletCreditOrderDetail update = MapstructUtils.convert(bo, FolwerAppletCreditOrderDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletCreditOrderDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分订单详细信息
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
