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
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.domain.FolwerAppletSku;
import org.dromara.flowerapplet.mapper.FolwerAppletSkuMapper;
import org.dromara.flowerapplet.service.IFolwerAppletSkuService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

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

    /**
     * 查询单品SKU
     *
     * @param skuId 主键
     * @return 单品SKU
     */
    @Override
    public FolwerAppletSkuVo queryById(Long skuId){
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
    public TableDataInfo<FolwerAppletSkuVo> queryPageList(FolwerAppletSkuBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletSku> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletSkuVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
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
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletSku> buildQueryWrapper(FolwerAppletSkuBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletSku> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getProdId() != null, FolwerAppletSku::getProdId, bo.getProdId());
        lqw.eq(StringUtils.isNotBlank(bo.getSkuPicid()), FolwerAppletSku::getSkuPicid, bo.getSkuPicid());
        lqw.eq(StringUtils.isNotBlank(bo.getColour()), FolwerAppletSku::getColour, bo.getColour());
        lqw.eq(StringUtils.isNotBlank(bo.getNumber()), FolwerAppletSku::getNumber, bo.getNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getWeight()), FolwerAppletSku::getWeight, bo.getWeight());
        lqw.eq(StringUtils.isNotBlank(bo.getSize()), FolwerAppletSku::getSize, bo.getSize());
        lqw.eq(bo.getPrice() != null, FolwerAppletSku::getPrice, bo.getPrice());
        lqw.eq(bo.getActualStocks() != null, FolwerAppletSku::getActualStocks, bo.getActualStocks());
        lqw.eq(bo.getStatus() != null, FolwerAppletSku::getStatus, bo.getStatus());
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
