package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flowerapplet.domain.FolwerAppletSku;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletUserInformationVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.mapper.FolwerAppletSkuMapper;
import org.dromara.flowerapplet.service.IFlowerAppletUserInformationService;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 鍗曞搧SKUService涓氬姟灞傚鐞?
 *
 * @author mlhxj
 * @date 2025-01-16
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletSkuServiceImpl implements IFolwerAppletSkuService {

    private static final BigDecimal GUEST_PRICE_MASK = new BigDecimal("-1");
    private static final BigDecimal PENDING_AUTH_PRICE_MASK = new BigDecimal("-2");

    private final FolwerAppletSkuMapper baseMapper;
    private final IFlowerAppletUserInformationService flowerAppletUserInformationService;

    @Override
    public FolwerAppletSkuVo queryById(Long skuId) {
        FolwerAppletSkuVo folwerAppletSkuVo = baseMapper.selectVoById(skuId);
        if (folwerAppletSkuVo != null) {
            applySkuDisplayState(folwerAppletSkuVo, resolvePriceVisibility());
        }
        return folwerAppletSkuVo;
    }

    @Override
    public FolwerAppletSkuVo selsctById(Long skuId) {
        FolwerAppletSkuVo folwerAppletSkuVo = baseMapper.selectVoById(skuId);
        if (folwerAppletSkuVo != null) {
            folwerAppletSkuVo.setSkuName(getSkuName(folwerAppletSkuVo));
        }
        return folwerAppletSkuVo;
    }

    @Override
    public TableDataInfo<FolwerAppletSkuVo> queryPageList(FolwerAppletSkuBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletSku> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletSkuVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        PriceVisibility visibility = resolvePriceVisibility();
        result.getRecords().forEach(record -> applySkuDisplayState(record, visibility));
        result.setRecords(result.getRecords().stream()
            .sorted(Comparator.comparing(FolwerAppletSkuVo::getSeq, Comparator.nullsLast(Comparator.reverseOrder())))
            .collect(Collectors.toList()));
        return TableDataInfo.build(result);
    }

    @Override
    public java.util.List<FolwerAppletSkuColorVo> queryByColor(FolwerAppletSkuBo bo) {
        java.util.List<FolwerAppletSkuColorVo> folwerAppletSkuColorVos = baseMapper.selectByColor(bo);
        if (folwerAppletSkuColorVos.isEmpty()) {
            return null;
        }
        return folwerAppletSkuColorVos.stream()
            .filter(distinctByKey(FolwerAppletSkuColorVo::getColor))
            .collect(Collectors.toList());
    }

    private static <T> java.util.function.Predicate<T> distinctByKey(
        java.util.function.Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public java.util.List<FolwerAppletSkuColorVo> queryByLevel(FolwerAppletSkuBo bo) {
        java.util.List<FolwerAppletSkuColorVo> folwerAppletSkuColorVos = baseMapper.selectByLevel(bo);
        if (folwerAppletSkuColorVos.isEmpty()) {
            return null;
        }
        return folwerAppletSkuColorVos.stream()
            .filter(item -> item.getLevel() != null)
            .sorted(Comparator.comparing(FolwerAppletSkuColorVo::getLevel))
            .collect(Collectors.toList());
    }

    @Override
    public java.util.List<FolwerAppletSkuVo> queryList(FolwerAppletSkuBo bo) {
        LambdaQueryWrapper<FolwerAppletSku> lqw = buildQueryWrapper(bo);
        java.util.List<FolwerAppletSkuVo> folwerAppletSkuVos = baseMapper.selectVoList(lqw);
        folwerAppletSkuVos.forEach(skuVo -> skuVo.setSkuName(getSkuName(skuVo)));
        return folwerAppletSkuVos;
    }

    private String getSkuName(FolwerAppletSkuVo folwerAppletSkuVo) {
        return String.valueOf(folwerAppletSkuVo.getColour()) + " " + String.valueOf(folwerAppletSkuVo.getSize());
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

    private void applySkuDisplayState(FolwerAppletSkuVo skuVo, PriceVisibility visibility) {
        skuVo.setSkuName(getSkuName(skuVo));
        if (visibility == PriceVisibility.GUEST) {
            skuVo.setPrice(GUEST_PRICE_MASK);
        } else if (visibility == PriceVisibility.PENDING_AUTH) {
            skuVo.setPrice(PENDING_AUTH_PRICE_MASK);
        }
    }

    private LambdaQueryWrapper<FolwerAppletSku> buildQueryWrapper(FolwerAppletSkuBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletSku> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProdId() != null, FolwerAppletSku::getProdId, bo.getProdId());
        lqw.eq(StringUtils.isNotBlank(bo.getSkuPicid()), FolwerAppletSku::getSkuPicid, bo.getSkuPicid());
        lqw.eq(StringUtils.isNotBlank(bo.getSkuPictureId()), FolwerAppletSku::getSkuPictureId, bo.getSkuPictureId());
        lqw.eq(StringUtils.isNotBlank(bo.getColour()), FolwerAppletSku::getColour, bo.getColour());
        lqw.eq(StringUtils.isNotBlank(bo.getNumber()), FolwerAppletSku::getNumber, bo.getNumber());
        lqw.eq(bo.getWeight() != null, FolwerAppletSku::getWeight, bo.getWeight());
        lqw.eq(StringUtils.isNotBlank(bo.getSize()), FolwerAppletSku::getSize, bo.getSize());
        lqw.eq(bo.getPrice() != null, FolwerAppletSku::getPrice, bo.getPrice());
        lqw.eq(bo.getActualStocks() != null, FolwerAppletSku::getActualStocks, bo.getActualStocks());
        lqw.eq(bo.getStatus() != null, FolwerAppletSku::getStatus, bo.getStatus());
        lqw.eq(bo.getSkuId() != null, FolwerAppletSku::getSkuId, bo.getSkuId());
        lqw.eq(StringUtils.isNotBlank(bo.getColor()), FolwerAppletSku::getColor, bo.getColor());
        lqw.like(StringUtils.isNotBlank(bo.getColorCode()), FolwerAppletSku::getColorCode, bo.getColorCode());
        lqw.eq(StringUtils.isNotBlank(bo.getColorPic()), FolwerAppletSku::getColorPic, bo.getColorPic());
        lqw.like(StringUtils.isNotBlank(bo.getLevel()), FolwerAppletSku::getLevel, bo.getLevel());
        lqw.like(bo.getIsSource() != null, FolwerAppletSku::getIsSource, bo.getIsSource());
        lqw.like(StringUtils.isNotBlank(bo.getSource()), FolwerAppletSku::getSource, bo.getSource());
        lqw.eq(bo.getSeq() != null, FolwerAppletSku::getSeq, bo.getSeq());
        lqw.orderByDesc(FolwerAppletSku::getSeq);
        return lqw;
    }

    @Override
    public Boolean insertByBo(FolwerAppletSkuBo bo) {
        FolwerAppletSku add = MapstructUtils.convert(bo, FolwerAppletSku.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setSkuId(add.getSkuId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(FolwerAppletSkuBo bo) {
        FolwerAppletSku update = MapstructUtils.convert(bo, FolwerAppletSku.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    private void validEntityBeforeSave(FolwerAppletSku entity) {
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
