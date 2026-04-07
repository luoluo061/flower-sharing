package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flowerapplet.domain.vo.*;
import org.dromara.flowerapplet.service.IFlowerAppletUserInformationService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.FolwerAppletSku;
import org.dromara.flowerapplet.mapper.FolwerAppletSkuMapper;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 单品SKUService业务层处理
 *
 * @author mlhxj
 * @date 2025-01-16
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletSkuServiceImpl implements IFolwerAppletSkuService {

    private final FolwerAppletSkuMapper baseMapper;
    private final IFlowerAppletUserInformationService flowerAppletUserInformationService;

    /**
     * 查询单品SKU
     *
     * @param skuId 主键
     * @return 单品SKU
     */
    @Override
    public FolwerAppletSkuVo queryById(Long skuId){
        FolwerAppletSkuVo folwerAppletSkuVo = baseMapper.selectVoById(skuId);
        if(folwerAppletSkuVo != null){
//            folwerAppletSkuVo.setSkuName(getSkuName(folwerAppletSkuVo));
            if (!LoginHelper.isLogin()) {
                folwerAppletSkuVo.setSkuName(getSkuName(folwerAppletSkuVo));
                folwerAppletSkuVo.setPrice(new BigDecimal("-1"));
            }
            else if (LoginHelper.isLogin()) {
                Long userId = LoginHelper.getUserId();
                if (userId != null) {
                    FlowerAppletUserInformationVo flowerAppletUserInformationVo = flowerAppletUserInformationService.queryById(userId);
                    //认证功能
                    if (flowerAppletUserInformationVo.getIsAuth() == 1L) {
                        folwerAppletSkuVo.setSkuName(getSkuName(folwerAppletSkuVo));
                    } else if (flowerAppletUserInformationVo.getIsAuth() == 0L) {
                        folwerAppletSkuVo.setSkuName(getSkuName(folwerAppletSkuVo));
//                        返回正常价格
//                        folwerAppletSkuVo.setPrice(new BigDecimal("-2"));
                    }
                }
            }
        }
        return folwerAppletSkuVo;
    }

    /**
     * 查询单品SKU
     *
     * @param skuId 主键
     * @return 单品SKU
     */
    @Override
    public FolwerAppletSkuVo selsctById(Long skuId){
        FolwerAppletSkuVo folwerAppletSkuVo = baseMapper.selectVoById(skuId);
        if(folwerAppletSkuVo != null){
            folwerAppletSkuVo.setSkuName(getSkuName(folwerAppletSkuVo));
        }
        return folwerAppletSkuVo;
    }

    /**
     * 分页查询单品SKU列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 单品SKU分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletSkuVo> queryPageList(FolwerAppletSkuBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletSku> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletSkuVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!LoginHelper.isLogin()) {
            result.getRecords().forEach(record -> {
                record.setSkuName(getSkuName(record));
                record.setPrice(new BigDecimal("-1"));
            });
        }
        else if (LoginHelper.isLogin()) {
            Long userId = LoginHelper.getUserId();
            if (userId != null) {
                FlowerAppletUserInformationVo flowerAppletUserInformationVo = flowerAppletUserInformationService.queryById(userId);
                //认证功能
                if (flowerAppletUserInformationVo.getIsAuth() == 1L) {
                    result.getRecords().forEach(record -> {
                        record.setSkuName(getSkuName(record));
                    });
                } else if (flowerAppletUserInformationVo.getIsAuth() == 0L) {
//                    result.getRecords().forEach(record -> {
//                        record.setSkuName(getSkuName(record));
//                        record.setPrice(new BigDecimal("-2"));
//                    });
                    // 不管认不认证都返回价格
                    result.getRecords().forEach(record -> {
                    record.setSkuName(getSkuName(record));
                    });
                }
            }
        }
        // 倒序排序，null值排最后
        result.setRecords(result.getRecords().stream()
            .sorted(Comparator.comparing(FolwerAppletSkuVo::getSeq, Comparator.nullsLast(Comparator.reverseOrder())))
            .collect(Collectors.toList()));

        return TableDataInfo.build(result);
    }

    @Override
    public List<FolwerAppletSkuColorVo> queryByColor(FolwerAppletSkuBo bo) {
        List<FolwerAppletSkuColorVo> folwerAppletSkuColorVos = baseMapper.selectByColor(bo);
        if (folwerAppletSkuColorVos.isEmpty()){
            return null;
        }
        List<FolwerAppletSkuColorVo> collect = folwerAppletSkuColorVos.stream()
            .filter(distinctByKey(FolwerAppletSkuColorVo::getColor))
            .collect(Collectors.toList());
        return collect;
    }
    // 自定义去重工具方法
    private static <T> java.util.function.Predicate<T> distinctByKey(
        java.util.function.Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    @Override
    public List<FolwerAppletSkuColorVo> queryByLevel(FolwerAppletSkuBo bo) {
        List<FolwerAppletSkuColorVo> folwerAppletSkuColorVos = baseMapper.selectByLevel(bo);
        if (folwerAppletSkuColorVos.isEmpty()){
            return null;
        }
        List<FolwerAppletSkuColorVo> skuLevel = folwerAppletSkuColorVos.stream()
            .filter(item -> item.getLevel() != null)
            .sorted(Comparator.comparing(FolwerAppletSkuColorVo::getLevel))
            .collect(Collectors.toList());
        return skuLevel;
    }

    /**
     * 查询符合条件的单品SKU列表
     *
     * @param bo 查询条件
     * @return 单品SKU列表
     */
    @Override
    public List<FolwerAppletSkuVo> queryList(FolwerAppletSkuBo bo) {
        LambdaQueryWrapper<FolwerAppletSku> lqw = buildQueryWrapper(bo);
        List<FolwerAppletSkuVo> folwerAppletSkuVos = baseMapper.selectVoList(lqw);
        folwerAppletSkuVos.forEach(skuVos ->{
            skuVos.setSkuName(getSkuName(skuVos));
        });
        return folwerAppletSkuVos;
    }

    private String getSkuName(FolwerAppletSkuVo folwerAppletSkuVo){
        if(StringUtils.isNotBlank(folwerAppletSkuVo.getColour()) && StringUtils.isNotBlank(folwerAppletSkuVo.getSize())){
            return folwerAppletSkuVo.getColour() + " " + folwerAppletSkuVo.getSize();
        }
        return folwerAppletSkuVo.getColour() + " " + folwerAppletSkuVo.getSize();
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

    /**
     * 新增单品SKU
     *
     * @param bo 单品SKU
     * @return 是否新增成功
     */
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

    /**
     * 修改单品SKU
     *
     * @param bo 单品SKU
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletSkuBo bo) {
        FolwerAppletSku update = MapstructUtils.convert(bo, FolwerAppletSku.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletSku entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除单品SKU信息
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
