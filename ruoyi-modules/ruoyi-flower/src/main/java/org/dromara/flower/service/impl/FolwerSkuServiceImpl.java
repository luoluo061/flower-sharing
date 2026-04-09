package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.FolwerSku;
import org.dromara.flower.domain.bo.FolwerSkuBo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.mapper.FolwerSkuMapper;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flower.service.domain.ProductSkuAggregateDomainService;
import org.dromara.flower.service.support.ProductSkuAggregateSupport;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 单品SKUService业务层处理
 *
 * @author mlhxj
 * @date 2024-12-26
 */
@RequiredArgsConstructor
@Service
public class FolwerSkuServiceImpl implements IFolwerSkuService {

    private final FolwerSkuMapper baseMapper;

    private final ISysOssService sysOssService;

    private final IFolwerAppletProductService folwerProductService;
    private final ProductSkuAggregateDomainService productSkuAggregateDomainService;

    /**
     * 查询单品SKU
     *
     * @param skuId 主键
     * @return 单品SKU
     */
    @Override
    public FolwerSkuVo queryById(Long skuId) {
        return baseMapper.selectVoById(skuId);
    }

    /**
     * 分页查询单品SKU列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 单品SKU分页列表
     */
    @Override
    public TableDataInfo<FolwerSkuVo> queryPageList(FolwerSkuBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerSku> lqw = buildQueryWrapper(bo);
        Page<FolwerSkuVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        applySkuPictureUrls(result.getRecords());
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的单品SKU列表
     *
     * @param bo 查询条件
     * @return 单品SKU列表
     */
    @Override
    public List<FolwerSkuVo> queryList(FolwerSkuBo bo) {
        LambdaQueryWrapper<FolwerSku> lqw = buildQueryWrapper(bo);
        List<FolwerSkuVo> folwerSkuVos = baseMapper.selectVoList(lqw);
        applySkuPictureUrls(folwerSkuVos);
        return folwerSkuVos;
    }

    /**
     * 查询符合条件的单品SKU列表
     *
     * @param prodId 查询条件
     * @return 单品SKU列表
     */
    @Override
    public List<FolwerSkuVo> queryListByProdId(long prodId) {
        FolwerSkuBo bo = new FolwerSkuBo();
        bo.setProdId(prodId);
        LambdaQueryWrapper<FolwerSku> lqw = buildQueryWrapper(bo);
        List<FolwerSkuVo> folwerSkuVos = baseMapper.selectVoList(lqw);
        applySkuPictureUrls(folwerSkuVos);
        return folwerSkuVos;
    }

    protected void applySkuPictureUrls(List<FolwerSkuVo> skuVos) {
        if (skuVos == null || skuVos.isEmpty()) {
            return;
        }

        List<Long> ossIds = new ArrayList<>();
        for (FolwerSkuVo vo : skuVos) {
            if (StringUtils.isNotBlank(vo.getSkuPicid())) {
                ossIds.add(Long.valueOf(vo.getSkuPicid()));
            }
        }

        if (ossIds.isEmpty()) {
            return;
        }

        Map<String, String> urlMap = sysOssService.listUrlByIds(ossIds);
        if (urlMap.isEmpty()) {
            return;
        }

        for (FolwerSkuVo vo : skuVos) {
            if (StringUtils.isNotBlank(vo.getSkuPicid())) {
                vo.setSkuPicidURL(urlMap.get(vo.getSkuPicid()));
            }
        }
    }

    private LambdaQueryWrapper<FolwerSku> buildQueryWrapper(FolwerSkuBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerSku> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProdId() != null, FolwerSku::getProdId, bo.getProdId());
        lqw.eq(StringUtils.isNotBlank(bo.getSkuPicid()), FolwerSku::getSkuPicid, bo.getSkuPicid());
        lqw.eq(StringUtils.isNotBlank(bo.getSkuPictureId()), FolwerSku::getSkuPictureId, bo.getSkuPictureId());
        lqw.eq(StringUtils.isNotBlank(bo.getColour()), FolwerSku::getColour, bo.getColour());
        lqw.eq(StringUtils.isNotBlank(bo.getNumber()), FolwerSku::getNumber, bo.getNumber());
        lqw.eq(bo.getWeight() != null, FolwerSku::getWeight, bo.getWeight());
        lqw.eq(StringUtils.isNotBlank(bo.getSize()), FolwerSku::getSize, bo.getSize());
        lqw.eq(bo.getBoxId() != null, FolwerSku::getBoxId, bo.getBoxId());
        lqw.eq(bo.getPrice() != null, FolwerSku::getPrice, bo.getPrice());
        lqw.eq(bo.getActualStocks() != null, FolwerSku::getActualStocks, bo.getActualStocks());
        lqw.eq(bo.getStatus() != null, FolwerSku::getStatus, bo.getStatus());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerSku::getCreateTime, bo.getStartTime(), bo.getEndTime());
        lqw.like(StringUtils.isNotBlank(bo.getColor()), FolwerSku::getColor, bo.getColor());
        lqw.like(StringUtils.isNotBlank(bo.getColorCode()), FolwerSku::getColorCode, bo.getColorCode());
        lqw.like(StringUtils.isNotBlank(bo.getColorPic()), FolwerSku::getColorPic, bo.getColorPic());
        lqw.like(StringUtils.isNotBlank(bo.getLevel()), FolwerSku::getLevel, bo.getLevel());
        lqw.like(bo.getIsSource() != null, FolwerSku::getIsSource, bo.getIsSource());
        lqw.like(StringUtils.isNotBlank(bo.getSource()), FolwerSku::getSource, bo.getSource());
        lqw.eq(bo.getSeq() != null, FolwerSku::getSeq, bo.getSeq());
        return lqw;
    }

    /**
     * 新增单品SKU
     *
     * @param bo 单品SKU
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerSkuBo bo) {
        FolwerSku add = MapstructUtils.convert(bo, FolwerSku.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setSkuId(add.getSkuId());
            refreshProductAggregateAfterSkuInsert(add.getProdId());
        }
        return flag;
    }

    @Override
    public Boolean batchInsertByBo(List<FolwerSkuBo> bos) {
        List<FolwerSku> skuArrayList = new ArrayList<>();
        for (FolwerSkuBo bo : bos) {
            validEntityBeforeSave(MapstructUtils.convert(bo, FolwerSku.class));
            skuArrayList.add(MapstructUtils.convert(bo, FolwerSku.class));
        }

        Collection<FolwerSku> entityList = skuArrayList;
        return baseMapper.insertBatch(entityList);
    }

    /**
     * 修改单品SKU
     *
     * @param bo 单品SKU
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerSkuBo bo) {
        FolwerSku update = MapstructUtils.convert(bo, FolwerSku.class);
        validEntityBeforeSave(update);
        boolean b = baseMapper.updateById(update) > 0;
        if (b) {
            if (update.getProdId() != null) {
                refreshProductAggregateAfterSkuUpdate(update.getProdId());
            }
        }

        return b;
    }

    protected void refreshProductAggregateAfterSkuInsert(Long prodId) {
        ProductSkuAggregateSupport.ProductAggregateSnapshot snapshot =
            productSkuAggregateDomainService.buildInsertSnapshot(queryListByProdId(prodId));
        applyProductAggregateUpdate(prodId, snapshot);
    }

    protected void refreshProductAggregateAfterSkuUpdate(Long prodId) {
        ProductSkuAggregateSupport.ProductAggregateSnapshot snapshot =
            productSkuAggregateDomainService.buildUpdateSnapshot(queryListByProdId(prodId));
        applyProductAggregateUpdate(prodId, snapshot);
    }

    private void applyProductAggregateUpdate(Long prodId, ProductSkuAggregateSupport.ProductAggregateSnapshot snapshot) {
        FolwerAppletProductVo folwerProductVo = folwerProductService.queryById(prodId);
        FolwerAppletProductBo folwerProductBo = productSkuAggregateDomainService.applySnapshotToProductBo(folwerProductVo, snapshot);
        folwerProductService.updateByBo(folwerProductBo);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerSku entity) {
        // TODO 做一些数据校验,如唯一约束
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
        if (isValid) {
            // TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

}
