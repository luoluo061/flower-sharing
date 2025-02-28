package org.dromara.flower.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerProductComm;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.service.IFolwerProductService;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.system.service.ISysOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerSkuBo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.domain.FolwerSku;
import org.dromara.flower.mapper.FolwerSkuMapper;
import org.dromara.flower.service.IFolwerSkuService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

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

//    private final IFolwerAppletProductService folwerAppletProductService;


    /**
     * 查询单品SKU
     *
     * @param skuId 主键
     * @return 单品SKU
     */
    @Override
    public FolwerSkuVo queryById(Long skuId){
        FolwerSkuVo folwerSkuVo = baseMapper.selectVoById(skuId);

        if (folwerSkuVo.getSkuPicid() != null && !folwerSkuVo.getSkuPicid().isEmpty())
        {
            Collection<Long> ossIds = new ArrayList<>();

            ossIds.add(Long.valueOf(folwerSkuVo.getSkuPicid()));
            Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
            if (!stringStringMap.isEmpty()){
                // 设置图片Url
                folwerSkuVo.setSkuPicidURL(stringStringMap.get(folwerSkuVo.getSkuPicid()));
            }
        }

        return folwerSkuVo;
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

        List<Long> longList = new ArrayList<>();
        result.getRecords().forEach(record ->{
                if (record.getSkuPicid() != null && !record.getSkuPicid().isEmpty()){
                    longList.add(Long.valueOf(record.getSkuPicid()));
                }
            }
        );
        if (!longList.isEmpty()){
            Map<String, String> longStringMap = sysOssService.listUrlByIds(longList);
            if (!longStringMap.isEmpty()){
                // 设置图片Url
                result.getRecords().forEach(record ->
                    record.setSkuPicidURL(longStringMap.get(record.getSkuPicid()))
                );
            }
        }

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
        for (FolwerSkuVo vo : folwerSkuVos){
            if (vo.getSkuPicid() != null && !vo.getSkuPicid().isEmpty())
            {
                Collection<Long> ossIds = new ArrayList<>();

                ossIds.add(Long.valueOf(vo.getSkuPicid()));
                Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
                if (!stringStringMap.isEmpty()){
                    // 设置图片Url
                    vo.setSkuPicidURL(stringStringMap.get(vo.getSkuPicid()));
                }
            }
        }
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
        for (FolwerSkuVo vo : folwerSkuVos){
            if (vo.getSkuPicid() != null && !vo.getSkuPicid().isEmpty())
            {
                Collection<Long> ossIds = new ArrayList<>();

                ossIds.add(Long.valueOf(vo.getSkuPicid()));
                Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
                if (!stringStringMap.isEmpty()){
                    // 设置图片Url
                    vo.setSkuPicidURL(stringStringMap.get(vo.getSkuPicid()));
                }
            }
        }
        return folwerSkuVos;
    }

    private LambdaQueryWrapper<FolwerSku> buildQueryWrapper(FolwerSkuBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerSku> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProdId() != null, FolwerSku::getProdId, bo.getProdId());
        lqw.eq(StringUtils.isNotBlank(bo.getSkuPicid()), FolwerSku::getSkuPicid, bo.getSkuPicid());
        lqw.eq(StringUtils.isNotBlank(bo.getColour()), FolwerSku::getColour, bo.getColour());
        lqw.eq(StringUtils.isNotBlank(bo.getNumber()), FolwerSku::getNumber, bo.getNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getWeight()), FolwerSku::getWeight, bo.getWeight());
        lqw.eq(StringUtils.isNotBlank(bo.getSize()), FolwerSku::getSize, bo.getSize());
        lqw.eq(bo.getPrice() != null, FolwerSku::getPrice, bo.getPrice());
        lqw.eq(bo.getActualStocks() != null, FolwerSku::getActualStocks, bo.getActualStocks());
        lqw.eq(bo.getStatus() != null, FolwerSku::getStatus, bo.getStatus());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerSku::getCreateTime, bo.getStartTime(), bo.getEndTime());
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

//            intermediateBean.updateProductInfo(add.getProdId());

            List<FolwerSkuVo> folwerSkuVos = this.queryListByProdId(add.getProdId());
            BigDecimal maxPrace = new BigDecimal(-999999999);
            BigDecimal minPrace = new BigDecimal(999999999);
            Long maxStocks = 0L;
            if (folwerSkuVos.size() > 0){
                for (FolwerSkuVo folwerSkuVo : folwerSkuVos){
                    if (folwerSkuVo.getPrice().compareTo(maxPrace) > 0) {
                        maxPrace = folwerSkuVo.getPrice().setScale(2, RoundingMode.HALF_UP);
                    }
                    if (folwerSkuVo.getMinPrice().compareTo(minPrace) < 0) {
                        minPrace = folwerSkuVo.getMinPrice().setScale(2, RoundingMode.HALF_UP);
                    }
                    maxStocks =+ folwerSkuVo.getActualStocks();
                }
            }
            FolwerAppletProductVo folwerProductVo = folwerProductService.queryById(add.getProdId());
            FolwerAppletProductBo folwerProductBo = BeanUtil.copyProperties(folwerProductVo, FolwerAppletProductBo.class);
            folwerProductBo.setOriPrice(maxPrace);
            folwerProductBo.setDerlinePrice(minPrace);
            folwerProductBo.setTotalStocks(maxStocks);
            folwerProductService.updateByBo(folwerProductBo);
        }
        return flag;
    }

    @Override
    public Boolean batchInsertByBo(List<FolwerSkuBo> bos){
        List<FolwerSku> skuArrayList = new ArrayList<>();
        for (FolwerSkuBo bo : bos){
            validEntityBeforeSave(MapstructUtils.convert(bo, FolwerSku.class));
            skuArrayList.add(MapstructUtils.convert(bo, FolwerSku.class));
        }

        Collection<FolwerSku> entityList = skuArrayList;
        boolean b = baseMapper.insertBatch(entityList);
        return b;
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
        if (b){
            if (update.getProdId() != null){
                List<FolwerSkuVo> folwerSkuVos = this.queryListByProdId(update.getProdId());
                BigDecimal maxPrace = new BigDecimal(0);
                BigDecimal minPrace = new BigDecimal(0);
                Long maxStocks = 0L;
                if (folwerSkuVos.size() > 0){
                    for (FolwerSkuVo folwerSkuVo : folwerSkuVos){
                        if (folwerSkuVo.getPrice().compareTo(maxPrace) > 0) {
                            maxPrace = folwerSkuVo.getPrice();
                        }
                        if (folwerSkuVo.getMinPrice().compareTo(minPrace) < 0) {
                            minPrace = folwerSkuVo.getMinPrice();
                        }
                        maxStocks =+ folwerSkuVo.getActualStocks();
                    }
                }
                FolwerAppletProductVo folwerProductVo = folwerProductService.queryById(update.getProdId());
                FolwerAppletProductBo folwerProductBo = BeanUtil.copyProperties(folwerProductVo, FolwerAppletProductBo.class);
                folwerProductBo.setOriPrice(maxPrace);
                folwerProductBo.setDerlinePrice(minPrace);
                folwerProductBo.setTotalStocks(maxStocks);
                folwerProductService.updateByBo(folwerProductBo);
            }
        }

        return b;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerSku entity){
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
