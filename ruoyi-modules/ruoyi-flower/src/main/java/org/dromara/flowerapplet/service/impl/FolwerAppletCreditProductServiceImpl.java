package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditProductVo;
import org.dromara.flowerapplet.domain.FolwerAppletCreditProduct;
import org.dromara.flowerapplet.mapper.FolwerAppletCreditProductMapper;
import org.dromara.flowerapplet.service.IFolwerAppletCreditProductService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分商品管理Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletCreditProductServiceImpl implements IFolwerAppletCreditProductService {

    private final FolwerAppletCreditProductMapper baseMapper;

    /**
     * 查询积分商品管理
     *
     * @param id 主键
     * @return 积分商品管理
     */
    @Override
    public FolwerAppletCreditProductVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询积分商品管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分商品管理分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletCreditProductVo> queryPageList(FolwerAppletCreditProductBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletCreditProduct> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletCreditProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分商品管理列表
     *
     * @param bo 查询条件
     * @return 积分商品管理列表
     */
    @Override
    public List<FolwerAppletCreditProductVo> queryList(FolwerAppletCreditProductBo bo) {
        LambdaQueryWrapper<FolwerAppletCreditProduct> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletCreditProduct> buildQueryWrapper(FolwerAppletCreditProductBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletCreditProduct> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerAppletCreditProduct::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getUnit()), FolwerAppletCreditProduct::getUnit, bo.getUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerAppletCreditProduct::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getProductCarouselPictureUrl()), FolwerAppletCreditProduct::getProductCarouselPictureUrl, bo.getProductCarouselPictureUrl());
        lqw.eq(bo.getCategoryId() != null, FolwerAppletCreditProduct::getCategoryId, bo.getCategoryId());
        lqw.eq(bo.getRedeemPrice() != null, FolwerAppletCreditProduct::getRedeemPrice, bo.getRedeemPrice());
        lqw.eq(bo.getNormsType() != null, FolwerAppletCreditProduct::getNormsType, bo.getNormsType());
        lqw.eq(StringUtils.isNotBlank(bo.getNormsPictureUrl()), FolwerAppletCreditProduct::getNormsPictureUrl, bo.getNormsPictureUrl());
        lqw.eq(bo.getSoldNum() != null, FolwerAppletCreditProduct::getSoldNum, bo.getSoldNum());
        lqw.eq(bo.getTotalStocks() != null, FolwerAppletCreditProduct::getTotalStocks, bo.getTotalStocks());
        lqw.eq(bo.getWeight() != null, FolwerAppletCreditProduct::getWeight, bo.getWeight());
        lqw.eq(bo.getDeliveryMode() != null, FolwerAppletCreditProduct::getDeliveryMode, bo.getDeliveryMode());
        lqw.eq(bo.getDeliveryPrice() != null, FolwerAppletCreditProduct::getDeliveryPrice, bo.getDeliveryPrice());
        lqw.eq(bo.getStatus() != null, FolwerAppletCreditProduct::getStatus, bo.getStatus());
        lqw.eq(bo.getIsCoupon() != null, FolwerAppletCreditProduct::getIsCoupon, bo.getIsCoupon());
        lqw.eq(bo.getIfRefund() != null, FolwerAppletCreditProduct::getIfRefund, bo.getIfRefund());
        lqw.eq(bo.getIfFreeShipping() != null, FolwerAppletCreditProduct::getIfFreeShipping, bo.getIfFreeShipping());
        lqw.eq(bo.getIfEarlyWarning() != null, FolwerAppletCreditProduct::getIfEarlyWarning, bo.getIfEarlyWarning());
        lqw.eq(bo.getInventoryEarlyWarningNum() != null, FolwerAppletCreditProduct::getInventoryEarlyWarningNum, bo.getInventoryEarlyWarningNum());
        lqw.eq(bo.getInventoryEarlyWarningProportion() != null, FolwerAppletCreditProduct::getInventoryEarlyWarningProportion, bo.getInventoryEarlyWarningProportion());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerAppletCreditProduct::getRemarks, bo.getRemarks());
        return lqw;
    }

    /**
     * 新增积分商品管理
     *
     * @param bo 积分商品管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletCreditProductBo bo) {
        FolwerAppletCreditProduct add = MapstructUtils.convert(bo, FolwerAppletCreditProduct.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改积分商品管理
     *
     * @param bo 积分商品管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletCreditProductBo bo) {
        FolwerAppletCreditProduct update = MapstructUtils.convert(bo, FolwerAppletCreditProduct.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletCreditProduct entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分商品管理信息
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
