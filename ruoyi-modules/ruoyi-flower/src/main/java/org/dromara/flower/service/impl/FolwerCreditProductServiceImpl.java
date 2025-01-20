package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flower.domain.vo.*;
import org.dromara.flower.service.IFolwerCreditCategoryService;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerCreditProductBo;
import org.dromara.flower.domain.FolwerCreditProduct;
import org.dromara.flower.mapper.FolwerCreditProductMapper;
import org.dromara.flower.service.IFolwerCreditProductService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分商品管理Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class FolwerCreditProductServiceImpl implements IFolwerCreditProductService {

    private final FolwerCreditProductMapper baseMapper;

    private final IFolwerCreditCategoryService folwerCreditCategoryService;

    private final ISysOssService sysOssService;

    private final IFolwerSkuService folwerSkuService;

    /**
     * 查询积分商品管理
     *
     * @param id 主键
     * @return 积分商品管理
     */
    @Override
    public FolwerCreditProductVo queryById(Long id){

        FolwerCreditProductVo folwerCreditProductVo = baseMapper.selectVoById(id);

        if (folwerCreditProductVo != null){
              // 设置分类名称
            String categoryName = null;
            FolwerCreditCategoryVo folwerCategoryVo = folwerCreditCategoryService.queryById(folwerCreditProductVo.getCategoryId());
            if (folwerCategoryVo != null){
                if(!folwerCategoryVo.getParentId().equals(0L)){
                    FolwerCreditCategoryVo ParentFolwerCategoryVo = folwerCreditCategoryService.queryById(folwerCategoryVo.getParentId());
                    categoryName = ParentFolwerCategoryVo.getCategoryName();
                }
                if(categoryName == null){
                    categoryName = folwerCategoryVo.getCategoryName();
                }else {
                    categoryName = categoryName + "/" + folwerCategoryVo.getCategoryName();
                }
                folwerCreditProductVo.setCategoryName(categoryName);
            }else {
                folwerCreditProductVo.setCategoryName("");
            }
        }
        // 设置图片Url
        if (folwerCreditProductVo.getProductListPictureUrl() != null && !folwerCreditProductVo.getProductListPictureUrl().isEmpty()) {
            Collection<Long> ossIds = new ArrayList<>();
            ossIds.add(Long.valueOf(folwerCreditProductVo.getProductListPictureUrl()));
            Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
            if (!stringStringMap.isEmpty()) {
                // 设置图片Url
                folwerCreditProductVo.setProductListPicture(stringStringMap.get(folwerCreditProductVo.getProductListPictureUrl()));
            }
        }

        if (folwerCreditProductVo.getNormsType().equals(1L)) {
            List<FolwerSkuVo> folwerSkuVos = folwerSkuService.queryListByProdId(folwerCreditProductVo.getId());
            folwerCreditProductVo.setProdSKU(folwerSkuVos);
        }
        return folwerCreditProductVo;
    }

    /**
     * 分页查询积分商品管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分商品管理分页列表
     */
    @Override
    public TableDataInfo<FolwerCreditProductVo> queryPageList(FolwerCreditProductBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCreditProduct> lqw = buildQueryWrapper(bo);
        Page<FolwerCreditProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        if (!result.getRecords().isEmpty()){
            for (FolwerCreditProductVo vo : result.getRecords()){
                if (vo.getId() != null){
                    // 设置分类名称
                    String categoryName = null;
                    FolwerCreditCategoryVo folwerCategoryVo = folwerCreditCategoryService.queryById(vo.getCategoryId());
                    if (folwerCategoryVo != null){
                        if(!folwerCategoryVo.getParentId().equals(0L)){
                            FolwerCreditCategoryVo ParentFolwerCategoryVo = folwerCreditCategoryService.queryById(folwerCategoryVo.getParentId());
                            categoryName = ParentFolwerCategoryVo.getCategoryName();
                        }
                        if(categoryName == null){
                            categoryName = folwerCategoryVo.getCategoryName();
                        }else {
                            categoryName = categoryName + "/" + folwerCategoryVo.getCategoryName();
                        }
                        vo.setCategoryName(categoryName);
                    }else {
                        vo.setCategoryName("");
                    }
                }else {
                    vo.setCategoryName("");
                }
            }

            List<Long> longList = new ArrayList<>();
            result.getRecords().forEach(record ->{
                if (record.getProductListPictureUrl() != null && !record.getProductListPictureUrl().isEmpty()){
                    longList.add(Long.valueOf(record.getProductListPictureUrl()));
                }
                //多规格
                if (record.getNormsType().equals(1L)){
                    List<FolwerSkuVo> folwerSkuVos = folwerSkuService.queryListByProdId(record.getId());
                    record.setProdSKU(folwerSkuVos);
                }
            });

            if (!longList.isEmpty()){
                Map<String, String> longStringMap = sysOssService.listUrlByIds(longList);
                if (!longStringMap.isEmpty()){
                    // 设置图片Url
                    result.getRecords().forEach(record ->
                        record.setProductListPicture(longStringMap.get(record.getProductListPictureUrl()))
                    );
                }
            }
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分商品管理列表
     *
     * @param bo 查询条件
     * @return 积分商品管理列表
     */
    @Override
    public List<FolwerCreditProductVo> queryList(FolwerCreditProductBo bo) {
        LambdaQueryWrapper<FolwerCreditProduct> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerCreditProduct> buildQueryWrapper(FolwerCreditProductBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCreditProduct> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerCreditProduct::getProductName, bo.getProductName());
        lqw.eq(bo.getId() != null, FolwerCreditProduct::getId, bo.getId());
        lqw.eq(StringUtils.isNotBlank(bo.getUnit()), FolwerCreditProduct::getUnit, bo.getUnit());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerCreditProduct::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getProductCarouselPictureUrl()), FolwerCreditProduct::getProductCarouselPictureUrl, bo.getProductCarouselPictureUrl());
        lqw.eq(bo.getCategoryId() != null, FolwerCreditProduct::getCategoryId, bo.getCategoryId());
        lqw.eq(bo.getRedeemPrice() != null, FolwerCreditProduct::getRedeemPrice, bo.getRedeemPrice());
        lqw.eq(bo.getNormsType() != null, FolwerCreditProduct::getNormsType, bo.getNormsType());
        lqw.eq(StringUtils.isNotBlank(bo.getNormsPictureUrl()), FolwerCreditProduct::getNormsPictureUrl, bo.getNormsPictureUrl());
        lqw.eq(bo.getSoldNum() != null, FolwerCreditProduct::getSoldNum, bo.getSoldNum());
        lqw.eq(bo.getTotalStocks() != null, FolwerCreditProduct::getTotalStocks, bo.getTotalStocks());
        lqw.eq(bo.getWeight() != null, FolwerCreditProduct::getWeight, bo.getWeight());
        lqw.eq(bo.getDeliveryMode() != null, FolwerCreditProduct::getDeliveryMode, bo.getDeliveryMode());
        lqw.eq(bo.getDeliveryPrice() != null, FolwerCreditProduct::getDeliveryPrice, bo.getDeliveryPrice());
        lqw.eq(bo.getStatus() != null, FolwerCreditProduct::getStatus, bo.getStatus());
        lqw.eq(bo.getIsCoupon() != null, FolwerCreditProduct::getIsCoupon, bo.getIsCoupon());
        lqw.eq(bo.getIfRefund() != null, FolwerCreditProduct::getIfRefund, bo.getIfRefund());
        lqw.eq(bo.getIfFreeShipping() != null, FolwerCreditProduct::getIfFreeShipping, bo.getIfFreeShipping());
        lqw.eq(bo.getIfEarlyWarning() != null, FolwerCreditProduct::getIfEarlyWarning, bo.getIfEarlyWarning());
        lqw.eq(bo.getInventoryEarlyWarningNum() != null, FolwerCreditProduct::getInventoryEarlyWarningNum, bo.getInventoryEarlyWarningNum());
        lqw.eq(bo.getInventoryEarlyWarningProportion() != null, FolwerCreditProduct::getInventoryEarlyWarningProportion, bo.getInventoryEarlyWarningProportion());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerCreditProduct::getRemarks, bo.getRemarks());
        if (bo.getStartTime() != null && bo.getEndTime() != null){
            lqw.between(FolwerCreditProduct::getCreateTime, bo.getStartTime(), bo.getEndTime());
        }
//        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerCreditProduct::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    /**
     * 新增积分商品管理
     *
     * @param bo 积分商品管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCreditProductBo bo) throws Exception {
        stringToLong(bo);
        FolwerCreditProduct add = MapstructUtils.convert(bo, FolwerCreditProduct.class);
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
    public Boolean updateByBo(FolwerCreditProductBo bo) throws Exception {
        stringToLong(bo);
        FolwerCreditProduct update = MapstructUtils.convert(bo, FolwerCreditProduct.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCreditProduct entity){
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

    /**
     * 校验并批量修改商品状态
     *
     * @param ids     待修改的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否修改成功
     */
    @Override
    public Boolean updateStatusByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }

        if (ids.size() == 0)
        {
            return false;
        }

        for (Long id : ids)
        {
            FolwerCreditProduct product = baseMapper.selectById(id);
            product.setStatus((Long.valueOf(product.getStatus() == 1 ? 0 : 0)));
            baseMapper.updateById(product);
        }

        return true;
    }

    private void stringToLong(FolwerCreditProductBo bo) throws Exception{
        if (StringUtils.isNotBlank(bo.getWeightStr())) {
            bo.setWeight(Long.parseLong(bo.getWeightStr()));
        }
        if (StringUtils.isNotBlank(bo.getDeliveryPriceStr())) {
            bo.setDeliveryPrice(Long.parseLong(bo.getDeliveryPriceStr()));
        }
    }
}
