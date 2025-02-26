package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.domain.FolwerAppletProduct;
import org.dromara.flowerapplet.mapper.FolwerAppletProductMapper;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序端商品管理Service业务层处理
 *
 * @author LL
 * @date 2024-12-31
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletProductServiceImpl implements IFolwerAppletProductService {

    private final FolwerAppletProductMapper baseMapper;

    private final IFolwerAppletSkuService folwerAppletSkuService;

    /**
     * 查询小程序端商品管理
     *
     * @param id 主键
     * @return 小程序端商品管理
     */
    @Override
    public FolwerAppletProductVo queryById(Long id){
        FolwerAppletProductVo folwerAppletProductVo = baseMapper.selectVoById(id);
        if (folwerAppletProductVo == null){
            return null;
        }
        if (folwerAppletProductVo != null) {
            if (folwerAppletProductVo.getNormsType().equals(1L)) {
                FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
                folwerAppletSkuBo.setProdId(folwerAppletProductVo.getId());
                List<FolwerAppletSkuVo> folwerAppletSkuVos = folwerAppletSkuService.queryList(folwerAppletSkuBo);
                folwerAppletProductVo.setSkuList(folwerAppletSkuVos);
            }
        }
        return folwerAppletProductVo;
    }

    /**
     * 分页查询小程序端商品管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序端商品管理分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletProductVo> queryPageList(FolwerAppletProductBo bo, PageQuery pageQuery) {
        stringToLong(bo);
        LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!result.getRecords().isEmpty()) {
            result.getRecords().forEach(item -> {
                if (item.getNormsType().equals(1L)) {
                    FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
                    folwerAppletSkuBo.setProdId(item.getId());
                    List<FolwerAppletSkuVo> folwerAppletSkuVos = folwerAppletSkuService.queryList(folwerAppletSkuBo);
                    item.setSkuList(folwerAppletSkuVos);
                }
            });
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序端商品管理列表
     *
     * @param bo 查询条件
     * @return 小程序端商品管理列表
     */
    @Override
    public List<FolwerAppletProductVo> queryList(FolwerAppletProductBo bo) {
        stringToLong(bo);
        LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
        List<FolwerAppletProductVo> productVos = baseMapper.selectVoList(lqw);
        for (FolwerAppletProductVo productVo : productVos) {
            if (productVo.getNormsType().equals(1L)) {
                FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
                folwerAppletSkuBo.setProdId(productVo.getId());
                List<FolwerAppletSkuVo> folwerAppletSkuVos = folwerAppletSkuService.queryList(folwerAppletSkuBo);
                productVo.setSkuList(folwerAppletSkuVos);
            }
        }
        return productVos;
    }

    private void stringToLong(FolwerAppletProductBo bo) {
        if (StringUtils.isNotBlank(bo.getCategoryIdStr())) {
            bo.setCategoryId(Long.parseLong(bo.getCategoryIdStr()));
        }
    }

    private LambdaQueryWrapper<FolwerAppletProduct> buildQueryWrapper(FolwerAppletProductBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletProduct> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerAppletProduct::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getUnit()), FolwerAppletProduct::getUnit, bo.getUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerAppletProduct::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getProductCarouselPictureUrl()), FolwerAppletProduct::getProductCarouselPictureUrl, bo.getProductCarouselPictureUrl());
        lqw.eq(bo.getCategoryId() != null, FolwerAppletProduct::getCategoryId, bo.getCategoryId());
        lqw.eq(bo.getOriPrice() != null, FolwerAppletProduct::getOriPrice, bo.getOriPrice());
        lqw.eq(bo.getDerlinePrice() != null, FolwerAppletProduct::getDerlinePrice, bo.getDerlinePrice());
        lqw.eq(bo.getNormsType() != null, FolwerAppletProduct::getNormsType, bo.getNormsType());
        lqw.eq(bo.getSkuId() != null, FolwerAppletProduct::getSkuId, bo.getSkuId());
        lqw.eq(StringUtils.isNotBlank(bo.getNormsPictureUrl()), FolwerAppletProduct::getNormsPictureUrl, bo.getNormsPictureUrl());
        lqw.eq(bo.getSoldNum() != null, FolwerAppletProduct::getSoldNum, bo.getSoldNum());
        lqw.eq(bo.getTotalStocks() != null, FolwerAppletProduct::getTotalStocks, bo.getTotalStocks());
        lqw.eq(bo.getWeight() != null, FolwerAppletProduct::getWeight, bo.getWeight());
        lqw.eq(bo.getDeliveryMode() != null, FolwerAppletProduct::getDeliveryMode, bo.getDeliveryMode());
        lqw.eq(bo.getDeliveryPrice() != null, FolwerAppletProduct::getDeliveryPrice, bo.getDeliveryPrice());
        lqw.eq(bo.getStatus() != null, FolwerAppletProduct::getStatus, bo.getStatus());
        lqw.eq(bo.getIsCoupon() != null, FolwerAppletProduct::getIsCoupon, bo.getIsCoupon());
        lqw.eq(bo.getIfRefund() != null, FolwerAppletProduct::getIfRefund, bo.getIfRefund());
        lqw.eq(bo.getIfFreeShipping() != null, FolwerAppletProduct::getIfFreeShipping, bo.getIfFreeShipping());
        lqw.eq(bo.getIfEarlyWarning() != null, FolwerAppletProduct::getIfEarlyWarning, bo.getIfEarlyWarning());
        lqw.eq(bo.getInventoryEarlyWarningNum() != null, FolwerAppletProduct::getInventoryEarlyWarningNum, bo.getInventoryEarlyWarningNum());
        lqw.eq(bo.getInventoryEarlyWarningProportion() != null, FolwerAppletProduct::getInventoryEarlyWarningProportion, bo.getInventoryEarlyWarningProportion());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerAppletProduct::getRemarks, bo.getRemarks());
        return lqw;
    }

    /**
     * 新增小程序端商品管理
     *
     * @param bo 小程序端商品管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletProductBo bo) {
        FolwerAppletProduct add = MapstructUtils.convert(bo, FolwerAppletProduct.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改小程序端商品管理
     *
     * @param bo 小程序端商品管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletProductBo bo) {
        FolwerAppletProduct update = MapstructUtils.convert(bo, FolwerAppletProduct.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletProduct entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除小程序端商品管理信息
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
