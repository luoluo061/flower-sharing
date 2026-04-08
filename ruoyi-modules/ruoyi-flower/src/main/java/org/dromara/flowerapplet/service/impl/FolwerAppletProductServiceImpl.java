package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flowerapplet.domain.FolwerAppletProduct;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletUserInformationVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCategoryVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.mapper.FolwerAppletProductMapper;
import org.dromara.flowerapplet.service.IFlowerAppletUserInformationService;
import org.dromara.flowerapplet.service.IFolwerAppletCategoryService;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 灏忕▼搴忕鍟嗗搧绠＄悊Service涓氬姟灞傚鐞?
 *
 * @author LL
 * @date 2024-12-31
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletProductServiceImpl implements IFolwerAppletProductService {

    private static final BigDecimal GUEST_PRICE_MASK = new BigDecimal("-1");
    private static final BigDecimal PENDING_AUTH_PRICE_MASK = new BigDecimal("-2");

    private final FolwerAppletProductMapper baseMapper;
    private final IFolwerAppletSkuService folwerAppletSkuService;
    private final IFlowerAppletUserInformationService flowerAppletUserInformationService;
    private final IFolwerAppletCategoryService folwerCategoryService;

    @Override
    public FolwerAppletProductVo queryById(Long id) {
        FolwerAppletProductVo folwerAppletProductVo = baseMapper.selectVoById(id);
        if (folwerAppletProductVo == null) {
            return null;
        }
        return folwerAppletProductVo;
    }

    @Override
    public List<FolwerAppletProductVo> queryAllBycategoryId(Long categoryId, int pageNum, int pageSize) {
        int offset = (pageNum - 1) * pageSize;
        PriceVisibility visibility = resolvePriceVisibility();
        if (visibility == null) {
            return null;
        }

        List<FolwerAppletProductVo> productVos = categoryId == 0L
            ? baseMapper.selectListByAll(pageSize, offset)
            : loadCategoryProducts(categoryId, offset, pageSize);
        applyPriceMask(productVos, visibility);
        return productVos;
    }

    @Override
    public List<FolwerAppletProductColorVo> queryByColor(FolwerAppletProductBo bo) {
        stringToLong(bo);
        List<FolwerAppletProductColorVo> productVos = baseMapper.selectByColor(bo);
        if (productVos.isEmpty()) {
            return null;
        }
        return productVos.stream()
            .filter(distinctByKey(FolwerAppletProductColorVo::getColor))
            .collect(Collectors.toList());
    }

    private static <T> java.util.function.Predicate<T> distinctByKey(
        java.util.function.Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public List<FolwerAppletProductColorVo> queryByLevel(FolwerAppletProductBo bo) {
        stringToLong(bo);
        List<FolwerAppletProductColorVo> productVos = baseMapper.selectByLevel(bo);
        if (productVos.isEmpty()) {
            return null;
        }
        return productVos.stream()
            .filter(item -> item.getLevel() != null)
            .sorted(Comparator.comparing(FolwerAppletProductColorVo::getLevel))
            .collect(Collectors.toList());
    }

    @Override
    public List<FolwerAppletProductColorVo> queryBySoldNum(FolwerAppletProductBo bo) {
        stringToLong(bo);
        List<FolwerAppletProductColorVo> productVos = baseMapper.selectBySoldNum(bo);
        if (productVos.isEmpty()) {
            return null;
        }
        return productVos;
    }

    @Override
    public TableDataInfo<FolwerAppletProductVo> queryPageList(FolwerAppletProductBo bo, PageQuery pageQuery) {
        PriceVisibility visibility = resolvePriceVisibility();
        if (visibility == null) {
            return TableDataInfo.build(new Page<>());
        }
        Page<FolwerAppletProductVo> result = querySortedPage(bo, pageQuery);
        applyPriceMask(result.getRecords(), visibility);
        return TableDataInfo.build(result);
    }

    private Map<Long, List<FolwerAppletSkuVo>> getSkuMap(List<FolwerAppletProductVo> products) {
        List<Long> prodIds = new ArrayList<>();
        for (FolwerAppletProductVo product : products) {
            if (Objects.equals(product.getNormsType(), 1L)) {
                prodIds.add(product.getId());
            }
        }

        if (prodIds.isEmpty()) {
            return new HashMap<>();
        }

        FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
        folwerAppletSkuBo.setStatus(1L);
        List<FolwerAppletSkuVo> allSkuVos = new ArrayList<>();
        for (Long prodId : prodIds) {
            folwerAppletSkuBo.setProdId(prodId);
            allSkuVos.addAll(folwerAppletSkuService.queryList(folwerAppletSkuBo));
        }

        Map<Long, List<FolwerAppletSkuVo>> skuMap = new HashMap<>();
        for (FolwerAppletSkuVo skuVo : allSkuVos) {
            skuMap.computeIfAbsent(skuVo.getProdId(), k -> new ArrayList<>()).add(skuVo);
        }
        return skuMap;
    }

    @Override
    public List<FolwerAppletProductVo> queryList(FolwerAppletProductBo bo) {
        stringToLong(bo);
        LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private void stringToLong(FolwerAppletProductBo bo) {
        if (StringUtils.isNotBlank(bo.getCategoryIdStr())) {
            bo.setCategoryId(Convert.toLong(bo.getCategoryIdStr()));
        }
    }

    protected boolean isUserLoggedIn() {
        return LoginHelper.isLogin();
    }

    protected Long getCurrentUserId() {
        return LoginHelper.getUserId();
    }

    protected Long getCurrentUserAuthStatus(Long userId) {
        FlowerAppletUserInformationVo userInformationVo = flowerAppletUserInformationService.queryById(userId);
        return userInformationVo == null ? null : userInformationVo.getIsAuth();
    }

    private PriceVisibility resolvePriceVisibility() {
        if (!isUserLoggedIn()) {
            return PriceVisibility.GUEST;
        }
        Long userId = getCurrentUserId();
        if (userId == null) {
            return null;
        }
        Long authStatus = getCurrentUserAuthStatus(userId);
        if (Objects.equals(authStatus, 1L)) {
            return PriceVisibility.AUTHENTICATED;
        }
        if (Objects.equals(authStatus, 0L)) {
            return PriceVisibility.PENDING_AUTH;
        }
        return null;
    }

    private List<FolwerAppletProductVo> loadCategoryProducts(Long categoryId, int offset, int pageSize) {
        List<FolwerAppletCategoryVo> childCategories = loadSortedChildCategories(categoryId);
        if (childCategories == null) {
            return null;
        }
        List<FolwerAppletProductVo> products = new ArrayList<>();
        for (FolwerAppletCategoryVo childCategory : childCategories) {
            products.addAll(loadSortedProductsByCategoryId(childCategory.getId()));
        }
        return products.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
    }

    private List<FolwerAppletCategoryVo> loadSortedChildCategories(Long categoryId) {
        FolwerAppletCategoryBo categoryBo = new FolwerAppletCategoryBo();
        categoryBo.setParentId(categoryId);
        categoryBo.setStatus(1L);
        List<FolwerAppletCategoryVo> childCategories = folwerCategoryService.queryList(categoryBo);
        if (childCategories == null) {
            return null;
        }
        return childCategories.stream()
            .sorted(Comparator.comparingLong(FolwerAppletCategoryVo::getSeq).reversed())
            .collect(Collectors.toList());
    }

    private List<FolwerAppletProductVo> loadSortedProductsByCategoryId(Long categoryId) {
        FolwerAppletProductBo productBo = new FolwerAppletProductBo();
        productBo.setStatus(1L);
        productBo.setCategoryId(categoryId);
        return baseMapper.selectVoList(buildQueryWrapper(productBo)).stream()
            .sorted(Comparator.comparingLong(FolwerAppletProductVo::getSoldNum).reversed())
            .collect(Collectors.toList());
    }

    private Page<FolwerAppletProductVo> querySortedPage(FolwerAppletProductBo bo, PageQuery pageQuery) {
        stringToLong(bo);
        Page<FolwerAppletProductVo> result = baseMapper.selectVoPage(pageQuery.build(), buildQueryWrapper(bo));
        result.setRecords(result.getRecords().stream()
            .sorted(Comparator.comparing(FolwerAppletProductVo::getSeq, Comparator.nullsLast(Comparator.reverseOrder())))
            .collect(Collectors.toList()));
        return result;
    }

    private void applyPriceMask(List<FolwerAppletProductVo> productVos, PriceVisibility visibility) {
        if (productVos == null || productVos.isEmpty()) {
            return;
        }
        BigDecimal mask = switch (visibility) {
            case GUEST -> GUEST_PRICE_MASK;
            case PENDING_AUTH -> PENDING_AUTH_PRICE_MASK;
            case AUTHENTICATED -> null;
        };
        if (mask == null) {
            return;
        }
        productVos.forEach(item -> {
            item.setOriPrice(mask);
            item.setDerlinePrice(mask);
        });
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
        lqw.eq(bo.getIsRecommend() != null, FolwerAppletProduct::getIsRecommend, bo.getIsRecommend());
        lqw.eq(bo.getIsCoupon() != null, FolwerAppletProduct::getIsCoupon, bo.getIsCoupon());
        lqw.eq(bo.getIfRefund() != null, FolwerAppletProduct::getIfRefund, bo.getIfRefund());
        lqw.eq(bo.getSeq() != null, FolwerAppletProduct::getSeq, bo.getSeq());
        lqw.eq(bo.getIfEarlyWarning() != null, FolwerAppletProduct::getIfEarlyWarning, bo.getIfEarlyWarning());
        lqw.eq(bo.getInventoryEarlyWarningNum() != null, FolwerAppletProduct::getInventoryEarlyWarningNum, bo.getInventoryEarlyWarningNum());
        lqw.eq(bo.getInventoryEarlyWarningProportion() != null, FolwerAppletProduct::getInventoryEarlyWarningProportion, bo.getInventoryEarlyWarningProportion());
        lqw.eq(StringUtils.isNotBlank(bo.getColor()), FolwerAppletProduct::getColor, bo.getColor());
        lqw.like(StringUtils.isNotBlank(bo.getColorCode()), FolwerAppletProduct::getColorCode, bo.getColorCode());
        lqw.eq(StringUtils.isNotBlank(bo.getColorPic()), FolwerAppletProduct::getColorPic, bo.getColorPic());
        lqw.like(StringUtils.isNotBlank(bo.getLevel()), FolwerAppletProduct::getLevel, bo.getLevel());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerAppletProduct::getRemarks, bo.getRemarks());
        return lqw;
    }

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

    @Override
    public Boolean updateByBo(FolwerAppletProductBo bo) {
        FolwerAppletProduct update = MapstructUtils.convert(bo, FolwerAppletProduct.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    private void validEntityBeforeSave(FolwerAppletProduct entity) {
        // TODO 鍋氫竴浜涙暟鎹牎楠?濡傚敮涓€绾︽潫
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO 鍋氫竴浜涗笟鍔′笂鐨勬牎楠?鍒ゆ柇鏄惁闇€瑕佹牎楠?
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    private enum PriceVisibility {
        GUEST,
        PENDING_AUTH,
        AUTHENTICATED
    }
}
