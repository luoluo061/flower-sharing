package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.mapper.FolwerProductMapper;
import org.dromara.flower.service.IFolwerCategoryService;
import org.dromara.flower.service.IFolwerProductService;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flower.service.domain.ProductCoreDomainService;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysOssService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class FolwerProductServiceImpl implements IFolwerProductService {

    @Resource
    private final FolwerProductMapper baseMapper;
    private final IFolwerCategoryService folwerCategoryService;
    private final ISysOssService sysOssService;
    @Resource
    private final SysOssMapper sysOssMapper;
    private final IFolwerSkuService folwerSkuService;
    private final ProductCoreDomainService productCoreDomainService;

    @Override
    public FolwerProductVo queryById(Long id) {
        FolwerProductVo folwerProductVo = baseMapper.selectVoById(id);
        applyCategoryName(folwerProductVo);
        return folwerProductVo;
    }

    @Override
    public TableDataInfo<FolwerProductVo> queryPageList(FolwerProductBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerProduct> lqw = buildQueryWrapper(bo);
        Page<FolwerProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!result.getRecords().isEmpty()) {
            result.getRecords().forEach(this::applyCategoryNameWithExistingPageSemantics);
        }
        return TableDataInfo.build(result);
    }

    @Override
    public java.util.List<FolwerProductVo> queryList(FolwerProductBo bo) {
        LambdaQueryWrapper<FolwerProduct> lqw = buildQueryWrapper(bo);
        java.util.List<FolwerProductVo> folwerProductVos = baseMapper.selectVoList(lqw);
        folwerProductVos.forEach(this::applyCategoryName);
        return folwerProductVos;
    }

    protected void applyCategoryName(FolwerProductVo vo) {
        if (vo == null) {
            return;
        }
        vo.setCategoryName(resolveCategoryName(vo.getCategoryId(), false));
    }

    protected void applyCategoryNameWithExistingPageSemantics(FolwerProductVo vo) {
        if (vo == null || vo.getId() == null) {
            if (vo != null) {
                vo.setCategoryName("");
            }
            return;
        }
        vo.setCategoryName(resolveCategoryName(vo.getCategoryId(), true));
    }

    protected String resolveCategoryName(Long categoryId, boolean preservePageListParentRule) {
        FolwerCategoryVo categoryVo = folwerCategoryService.queryById(categoryId);
        if (categoryVo == null) {
            if (preservePageListParentRule) {
                throw new RuntimeException(categoryId + " category does not exist");
            }
            return "";
        }

        String categoryName = null;
        if (shouldLoadParentCategory(categoryVo, preservePageListParentRule)) {
            FolwerCategoryVo parentCategoryVo = folwerCategoryService.queryById(categoryVo.getParentId());
            if (parentCategoryVo != null) {
                categoryName = parentCategoryVo.getCategoryName();
            }
        }

        if (categoryName == null) {
            return categoryVo.getCategoryName();
        }
        return categoryName + "/" + categoryVo.getCategoryName();
    }

    private boolean shouldLoadParentCategory(FolwerCategoryVo categoryVo, boolean preservePageListParentRule) {
        if (categoryVo == null || Objects.equals(categoryVo.getParentId(), 0L)) {
            return false;
        }
        if (!preservePageListParentRule) {
            return true;
        }
        return categoryVo.getParentId() != null && categoryVo.getParentId().toString().length() >= 19;
    }

    private LambdaQueryWrapper<FolwerProduct> buildQueryWrapper(FolwerProductBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerProduct> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerProduct::getProductName, bo.getProductName());
        lqw.eq(bo.getId() != null, FolwerProduct::getId, bo.getId());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerProduct::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(bo.getCategoryId() != null, FolwerProduct::getCategoryId, bo.getCategoryId());
        lqw.eq(bo.getOriPrice() != null, FolwerProduct::getOriPrice, bo.getOriPrice());
        lqw.eq(bo.getSoldNum() != null, FolwerProduct::getSoldNum, bo.getSoldNum());
        lqw.eq(bo.getTotalStocks() != null, FolwerProduct::getTotalStocks, bo.getTotalStocks());
        lqw.eq(bo.getStatus() != null, FolwerProduct::getStatus, bo.getStatus());
        lqw.eq(bo.getIfRefund() != null, FolwerProduct::getIfRefund, bo.getIfRefund());
        lqw.eq(bo.getSeq() != null, FolwerProduct::getSeq, bo.getSeq());
        lqw.eq(bo.getIsRecommend() != null, FolwerProduct::getIsRecommend, bo.getIsRecommend());
        lqw.eq(bo.getIfEarlyWarning() != null, FolwerProduct::getIfEarlyWarning, bo.getIfEarlyWarning());
        lqw.eq(bo.getInventoryEarlyWarningNum() != null, FolwerProduct::getInventoryEarlyWarningNum, bo.getInventoryEarlyWarningNum());
        lqw.eq(bo.getInventoryEarlyWarningProportion() != null, FolwerProduct::getInventoryEarlyWarningProportion, bo.getInventoryEarlyWarningProportion());
        lqw.like(StringUtils.isNotBlank(bo.getColor()), FolwerProduct::getColor, bo.getColor());
        lqw.like(StringUtils.isNotBlank(bo.getColorCode()), FolwerProduct::getColorCode, bo.getColorCode());
        lqw.like(StringUtils.isNotBlank(bo.getColorPic()), FolwerProduct::getColorPic, bo.getColorPic());
        lqw.like(StringUtils.isNotBlank(bo.getLevel()), FolwerProduct::getLevel, bo.getLevel());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerProduct::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    @Override
    public Boolean insertByBo(FolwerProductBo bo) {
        FolwerProduct folwerProduct = new FolwerProduct();
        BeanUtils.copyProperties(bo, folwerProduct);
        applyCurrentWriteDefaults(folwerProduct, bo);

        validEntityBeforeSave(folwerProduct);
        boolean flag = baseMapper.insert(folwerProduct) > 0;
        if (flag) {
            bo.setId(folwerProduct.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(FolwerProductBo bo) {
        FolwerProduct update = toEntity(bo);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    protected FolwerProduct toEntity(FolwerProductBo bo) {
        return MapstructUtils.convert(bo, FolwerProduct.class);
    }

    protected void applyCurrentWriteDefaults(FolwerProduct product, FolwerProductBo bo) {
        productCoreDomainService.prepareProductForCreate(product, bo.getDeliveryPrice());
    }

    protected BigDecimal normalizeDeliveryPrice(String deliveryPrice) {
        return productCoreDomainService.prepareProductForCreate(new FolwerProduct(), deliveryPrice).getDeliveryPrice();
    }

    private void validEntityBeforeSave(FolwerProduct entity) {
        // TODO add entity validation when write-path cleanup starts.
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO add business validation when delete-path cleanup starts.
        }
        return performCurrentDirectDelete(ids);
    }

    @Override
    public Boolean updateStatusByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO add business validation when status cleanup starts.
        }

        if (ids.isEmpty()) {
            return false;
        }

        ids.forEach(this::applyCurrentBatchStatus);

        return true;
    }

    private void applyCurrentBatchStatus(Long productId) {
        FolwerProduct product = baseMapper.selectById(productId);
        // Keep the existing batch-update behavior locked until the write-path fix is designed.
        productCoreDomainService.prepareStatusMutation(product);
        baseMapper.updateById(product);
    }

    protected boolean performCurrentDirectDelete(Collection<Long> ids) {
        return baseMapper.deleteByIds(ids) > 0;
    }
}
