package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.convert.Convert;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.vo.*;
import org.dromara.flowerapplet.service.IFlowerAppletUserInformationService;
import org.dromara.flowerapplet.service.IFolwerAppletCategoryService;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.FolwerAppletProduct;
import org.dromara.flowerapplet.mapper.FolwerAppletProductMapper;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    private final IFlowerAppletUserInformationService flowerAppletUserInformationService;

    private final IFolwerAppletCategoryService folwerCategoryService;

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
//        if (folwerAppletProductVo != null) {
//            if (folwerAppletProductVo.getNormsType().equals(1L)) {
//                FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
//                folwerAppletSkuBo.setProdId(folwerAppletProductVo.getId());
//                folwerAppletSkuBo.setStatus(1L);
//                List<FolwerAppletSkuVo> folwerAppletSkuVos = folwerAppletSkuService.queryList(folwerAppletSkuBo);
//                folwerAppletProductVo.setSkuList(folwerAppletSkuVos);
//            }
//        }
        return folwerAppletProductVo;
    }

    /**
     * 查询大分类下所有商品
     *
     * @param categoryId 主键
     * @return 小程序端商品管理
     */
    @Override
    public List<FolwerAppletProductVo> queryAllBycategoryId(Long categoryId, int pageNum, int pageSize){
        int offset = (pageNum - 1) * pageSize;
        if (!LoginHelper.isLogin()) {
            if (categoryId == 0L) {
                List<FolwerAppletProductVo> productVos = baseMapper.selectListByAll(pageSize,  offset);
                productVos.stream().skip(offset).limit(pageSize).collect(Collectors.groupingBy(FolwerAppletProductVo::getCategoryId));
                if (!productVos.isEmpty()) {
                    productVos.forEach(item -> {
                        item.setOriPrice(new BigDecimal("-1"));
                        item.setDerlinePrice(new BigDecimal("-1"));
                    });
                }
                return productVos;
            }
            FolwerAppletCategoryBo folwerAppletCategoryBo = new FolwerAppletCategoryBo();
            folwerAppletCategoryBo.setParentId(categoryId);
            folwerAppletCategoryBo.setStatus(1L);
            List<FolwerAppletCategoryVo> CategoryVo = folwerCategoryService.queryList(folwerAppletCategoryBo);

            if (CategoryVo == null) {
                return null;
            }
            List<FolwerAppletCategoryVo> folwerAppletCategoryVos = CategoryVo.stream()
                .sorted(Comparator.comparingLong(FolwerAppletCategoryVo::getSeq).reversed())
                .collect(Collectors.toList());

            List<FolwerAppletProductVo> productVoAll = new ArrayList<>();
            for (FolwerAppletCategoryVo folwerAppletCategoryVo : folwerAppletCategoryVos){
                FolwerAppletProductBo bo = new FolwerAppletProductBo();
                bo.setStatus(1L);
                bo.setCategoryId(folwerAppletCategoryVo.getId());
                LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
                List<FolwerAppletProductVo> productVo = baseMapper.selectVoList(lqw);
                List<FolwerAppletProductVo> productVos = productVo.stream().
                    sorted(Comparator.comparingLong(FolwerAppletProductVo::getSoldNum).reversed())
                    .collect(Collectors.toList());
                productVoAll.addAll(productVos);
            }
            List<FolwerAppletProductVo> productVos = productVoAll.stream().skip(offset).limit(pageSize).collect(Collectors.toList());

            if (!productVos.isEmpty()) {
                productVos.forEach(item -> {
                        item.setOriPrice(new BigDecimal("-1"));
                        item.setDerlinePrice(new BigDecimal("-1"));
                    });
            }
            return productVos;
        } else if (LoginHelper.isLogin()) {
            Long userId = LoginHelper.getUserId();
            if (userId != null) {
                FlowerAppletUserInformationVo flowerAppletUserInformationVo = flowerAppletUserInformationService.queryById(userId);
                //认证功能
                if (flowerAppletUserInformationVo.getIsAuth() == 1L) {
                    if (categoryId == 0L) {
                        List<FolwerAppletProductVo> productVos = baseMapper.selectListByAll(pageSize, offset);
                        return productVos;
                    }
//                    List<FolwerAppletProductVo> productVos = baseMapper.selectAllByCategoryId(categoryId, pageSize, offset);
//                    if (productVos == null) {
//                        return null;
//                    }
                    FolwerAppletCategoryBo folwerAppletCategoryBo = new FolwerAppletCategoryBo();
                    folwerAppletCategoryBo.setParentId(categoryId);
                    folwerAppletCategoryBo.setStatus(1L);
                    List<FolwerAppletCategoryVo> CategoryVo = folwerCategoryService.queryList(folwerAppletCategoryBo);
                    if (CategoryVo == null) {
                        return null;
                    }
                    List<FolwerAppletCategoryVo> folwerAppletCategoryVos = CategoryVo.stream()
                        .sorted(Comparator.comparingLong(FolwerAppletCategoryVo::getSeq).reversed())
                        .collect(Collectors.toList());
                    List<FolwerAppletProductVo> productVoAll = new ArrayList<>();
                    for (FolwerAppletCategoryVo folwerAppletCategoryVo : folwerAppletCategoryVos){
                        FolwerAppletProductBo bo = new FolwerAppletProductBo();
                        bo.setStatus(1L);
                        bo.setCategoryId(folwerAppletCategoryVo.getId());
                        LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
                        List<FolwerAppletProductVo> productVo = baseMapper.selectVoList(lqw);
                        List<FolwerAppletProductVo> productVos = productVo.stream().
                            sorted(Comparator.comparingLong(FolwerAppletProductVo::getSoldNum).reversed())
                            .collect(Collectors.toList());
                        productVoAll.addAll(productVos);
                    }
                    List<FolwerAppletProductVo> productVos = productVoAll.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
                    return productVos;
                } else if (flowerAppletUserInformationVo.getIsAuth() == 0L) {
                    if (categoryId == 0L) {
                        List<FolwerAppletProductVo> productVos = baseMapper.selectListByAll(pageSize, offset);
//                        if (!productVos.isEmpty()) {
//                            productVos.forEach(item -> {
//                                item.setOriPrice(new BigDecimal("-2"));
//                                item.setDerlinePrice(new BigDecimal("-2"));
//                            });
//                        }
                        return productVos;
                    }
//                    List<FolwerAppletProductVo> productVos = baseMapper.selectAllByCategoryId(categoryId, pageSize, offset);
//                    if (productVos == null) {
//                        return null;
//                    }
                    FolwerAppletCategoryBo folwerAppletCategoryBo = new FolwerAppletCategoryBo();
                    folwerAppletCategoryBo.setParentId(categoryId);
                    folwerAppletCategoryBo.setStatus(1L);
                    List<FolwerAppletCategoryVo> CategoryVo = folwerCategoryService.queryList(folwerAppletCategoryBo);
                    if (CategoryVo == null) {
                        return null;
                    }
                    List<FolwerAppletCategoryVo> folwerAppletCategoryVos = CategoryVo.stream()
                        .sorted(Comparator.comparingLong(FolwerAppletCategoryVo::getSeq).reversed())
                        .collect(Collectors.toList());
                    List<FolwerAppletProductVo> productVoAll = new ArrayList<>();
                    for (FolwerAppletCategoryVo folwerAppletCategoryVo : folwerAppletCategoryVos){
                        FolwerAppletProductBo bo = new FolwerAppletProductBo();
                        bo.setStatus(1L);
                        bo.setCategoryId(folwerAppletCategoryVo.getId());
                        LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
                        List<FolwerAppletProductVo> productVo = baseMapper.selectVoList(lqw);
                        List<FolwerAppletProductVo> productVos = productVo.stream().
                            sorted(Comparator.comparingLong(FolwerAppletProductVo::getSoldNum).reversed())
                            .collect(Collectors.toList());
                        productVoAll.addAll(productVos);
                    }
                    List<FolwerAppletProductVo> productVos = productVoAll.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
//                    if (!productVos.isEmpty()) {
//                        productVos.forEach(item -> {
//                            item.setOriPrice(new BigDecimal("-2"));
//                            item.setDerlinePrice(new BigDecimal("-2"));
//                        });
//                    }
                    return productVos;
                }

            }
        }
        return null;
    }

    /**
     * 查询商品颜色
     *
     * @param bo 主键
     * @return 小程序端商品管理
     */
    @Override
    public List<FolwerAppletProductColorVo> queryByColor(FolwerAppletProductBo bo){
        stringToLong(bo);
//      bo.setStatus(1L);
//        LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
        List<FolwerAppletProductColorVo> productVos = baseMapper.selectByColor(bo);
        if (productVos.isEmpty()){
            return null;
        }
//        List<FolwerAppletProductColorVo> productColorVos = new ArrayList<>();
//        for (FolwerAppletProductVo productVo : productVos){
//            FolwerAppletProductColorVo productColorVo =BeanUtil.copyProperties(productVo, FolwerAppletProductColorVo.class);
//            productColorVos.add(productColorVo);
//        }
//        //去重
//        List<FolwerAppletProductColorVo> uniqueList = removeDuplicatesUsingLinkedHashSet(productColorVos);
        List<FolwerAppletProductColorVo> collect = productVos.stream()
            .filter(distinctByKey(FolwerAppletProductColorVo::getColor))
            .collect(Collectors.toList());

        return collect;
    }
    // 自定义去重工具方法
    private static <T> java.util.function.Predicate<T> distinctByKey(
        java.util.function.Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    /**
     * 查询商品等级
     * @param bo 查询条件
     * @return
     */
    @Override
    public List<FolwerAppletProductColorVo> queryByLevel(FolwerAppletProductBo bo) {
        stringToLong(bo);
        List<FolwerAppletProductColorVo> productVos = baseMapper.selectByLevel(bo);
        if (productVos.isEmpty()){
            return null;
        }
        List<FolwerAppletProductColorVo> sortedByLevel = productVos.stream()
            .filter(item -> item.getLevel() != null)
            .sorted(Comparator.comparing(FolwerAppletProductColorVo::getLevel)) // 按等级升序
            .collect(java.util.stream.Collectors.toList());
        return sortedByLevel;
    }

    /**
     * 查询商品销量
     * @param bo 查询条件
     * @return
     */
    @Override
    public List<FolwerAppletProductColorVo> queryBySoldNum(FolwerAppletProductBo bo) {
        stringToLong(bo);
        List<FolwerAppletProductColorVo> productVos = baseMapper.selectBySoldNum(bo);
        if (productVos.isEmpty()){
            return null;
        }
        return productVos;
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
        if (!LoginHelper.isLogin()){
            stringToLong(bo);
//            bo.setStatus(1L);
            LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
            Page<FolwerAppletProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
            if (!result.getRecords().isEmpty()) {
                result.getRecords().forEach(item -> {
//                    if (item.getNormsType().equals(1L)) {
//                        FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
//                        folwerAppletSkuBo.setProdId(item.getId());
//                        folwerAppletSkuBo.setStatus(1L);
//                        List<FolwerAppletSkuVo> folwerAppletSkuVos = folwerAppletSkuService.queryList(folwerAppletSkuBo);
//                        item.setSkuList(folwerAppletSkuVos);
//                    }
                    item.setOriPrice(new BigDecimal("-1"));
                    item.setDerlinePrice(new BigDecimal("-1"));
                });

//                if (!result.getRecords().isEmpty()) {
//                    // 批量查询 SKU 数据
//                    Map<Long, List<FolwerAppletSkuVo>> skuMap = getSkuMap(result.getRecords());
//                    result.getRecords().forEach(item -> {
//                        if (item.getNormsType().equals(1L)) {
//                            item.setSkuList(skuMap.getOrDefault(item.getId(), new ArrayList<>()));
//                            item.setOriPrice(new BigDecimal("-1"));
//                            item.setDerlinePrice(new BigDecimal("-1"));
//                        }
//                    });
//                }

                // 倒序排序，null值排最后
                result.setRecords(result.getRecords().stream()
                    .sorted(Comparator.comparing(FolwerAppletProductVo::getSeq,Comparator.nullsLast(Comparator.reverseOrder())))
                    .collect(Collectors.toList()));
            }
            return TableDataInfo.build(result);
        } else if (LoginHelper.isLogin()) {
            Long userId = LoginHelper.getUserId();
            if (userId != null){
                FlowerAppletUserInformationVo flowerAppletUserInformationVo = flowerAppletUserInformationService.queryById(userId);
                //认证功能
                if (flowerAppletUserInformationVo.getIsAuth() == 1L){
                    stringToLong(bo);
//                  bo.setStatus(1L);
                    LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
                    Page<FolwerAppletProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
//                    if (!result.getRecords().isEmpty()) {
//                        result.getRecords().forEach(item -> {
//                            if (item.getNormsType().equals(1L)) {
//                                FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
//                                folwerAppletSkuBo.setProdId(item.getId());
//                                folwerAppletSkuBo.setStatus(1L);
//                                List<FolwerAppletSkuVo> folwerAppletSkuVos = folwerAppletSkuService.queryList(folwerAppletSkuBo);
//                                item.setSkuList(folwerAppletSkuVos);
//                            }
//                        });
//                    }
//
//                    if (!result.getRecords().isEmpty()) {
//                        // 批量查询 SKU 数据
//                        Map<Long, List<FolwerAppletSkuVo>> skuMap = getSkuMap(result.getRecords());
//                        result.getRecords().forEach(item -> {
//                            if (item.getNormsType().equals(1L)) {
//                                item.setSkuList(skuMap.getOrDefault(item.getId(), new ArrayList<>()));
//                            }
//                        });
//                    }

                    // 倒序排序，null值排最后
                    result.setRecords(result.getRecords().stream()
                        .sorted(Comparator.comparing(FolwerAppletProductVo::getSeq,Comparator.nullsLast(Comparator.reverseOrder())))
                        .collect(Collectors.toList()));

                    return TableDataInfo.build(result);
                }else if (flowerAppletUserInformationVo.getIsAuth() == 0L){
                    stringToLong(bo);
//                  bo.setStatus(1L);
                    LambdaQueryWrapper<FolwerAppletProduct> lqw = buildQueryWrapper(bo);
                    Page<FolwerAppletProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
//                    if (!result.getRecords().isEmpty()) {
//                        result.getRecords().forEach(item -> {
////                            if (item.getNormsType().equals(1L)) {
////                                FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
////                                folwerAppletSkuBo.setProdId(item.getId());
////                                folwerAppletSkuBo.setStatus(1L);
////                                List<FolwerAppletSkuVo> folwerAppletSkuVos = folwerAppletSkuService.queryList(folwerAppletSkuBo);
////                                item.setSkuList(folwerAppletSkuVos);
////
////                            }
//                            item.setOriPrice(new BigDecimal("-2"));
//                            item.setDerlinePrice(new BigDecimal("-2"));
//                        });
//                    }

//                    if (!result.getRecords().isEmpty()) {
//                        // 批量查询 SKU 数据
//                        Map<Long, List<FolwerAppletSkuVo>> skuMap = getSkuMap(result.getRecords());
//                        result.getRecords().forEach(item -> {
//                            if (item.getNormsType().equals(1L)) {
//                                item.setSkuList(skuMap.getOrDefault(item.getId(), new ArrayList<>()));
//                                item.setOriPrice(new BigDecimal("-2"));
//                                item.setDerlinePrice(new BigDecimal("-2"));
//                            }
//                        });
//                    }

                    // 倒序排序，null值排最后
                    result.setRecords(result.getRecords().stream()
                        .sorted(Comparator.comparing(FolwerAppletProductVo::getSeq,Comparator.nullsLast(Comparator.reverseOrder())))
                        .collect(Collectors.toList()));

                    return TableDataInfo.build(result);
                }
            }
        }
        return TableDataInfo.build(new Page<>());
    }

    private Map<Long, List<FolwerAppletSkuVo>> getSkuMap(List<FolwerAppletProductVo> products) {
        List<Long> prodIds = new ArrayList<>();
        for (FolwerAppletProductVo product : products) {
            if (product.getNormsType().equals(1L)) {
                  prodIds.add(product.getId());
            }

        }

        if (prodIds.isEmpty()) {
            return new HashMap<>();
        }

        FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
        folwerAppletSkuBo.setStatus(1L);
        List<FolwerAppletSkuVo> allSkuVos = new ArrayList<>();
        // 这里可以根据实际情况批量查询
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
//        for (FolwerAppletProductVo productVo : productVos) {
//            if (productVo.getNormsType().equals(1L)) {
//                FolwerAppletSkuBo folwerAppletSkuBo = new FolwerAppletSkuBo();
//                folwerAppletSkuBo.setProdId(productVo.getId());
//                folwerAppletSkuBo.setStatus(1L);
//                List<FolwerAppletSkuVo> folwerAppletSkuVos = folwerAppletSkuService.queryList(folwerAppletSkuBo);
//                productVo.setSkuList(folwerAppletSkuVos);
//            }
//        }
        return productVos;
    }

    private void stringToLong(FolwerAppletProductBo bo) {
        if (bo.getCategoryIdStr() != null && !bo.getCategoryIdStr().equals("")) {
//            bo.setCategoryId(Long.parseLong(bo.getCategoryIdStr())));
            bo.setCategoryId(Convert.toLong(bo.getCategoryIdStr()));
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
//        lqw.orderByDesc(FolwerAppletProduct::getSeq);
//        lqw.groupBy(FolwerAppletProduct::getCategoryId);
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
