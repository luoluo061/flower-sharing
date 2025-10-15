package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.domain.FolwerAppletProductDetail;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductDetailBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductDetailVo;
import org.dromara.flowerapplet.mapper.FolwerAppletProductDetailMapper;
import org.dromara.flowerapplet.service.IFolwerAppletProductDetailService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 商品详情Service业务层处理
 *
 * @author mlhxj
 * @date 2025-08-11
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletProductDetailServiceImpl implements IFolwerAppletProductDetailService {

    private final FolwerAppletProductDetailMapper baseMapper;

    /**
     * 查询商品详情
     *
     * @param detailId 主键
     * @return 商品详情
     */
    @Override
    public FolwerAppletProductDetailVo queryById(Long detailId){
        return baseMapper.selectVoById(detailId);
    }


    @Override
    public FolwerAppletProductDetailVo queryByProductId(Long productId) {
        LambdaQueryWrapper<FolwerAppletProductDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(FolwerAppletProductDetail::getSkuId, productId);
        FolwerAppletProductDetailVo folwerAppletProductDetailVo = baseMapper.selectVoOne(lqw);
        return folwerAppletProductDetailVo;
    }

    /**
     * 分页查询商品详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品详情分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletProductDetailVo> queryPageList(FolwerAppletProductDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletProductDetail> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletProductDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的商品详情列表
     *
     * @param bo 查询条件
     * @return 商品详情列表
     */
    @Override
    public List<FolwerAppletProductDetailVo> queryList(FolwerAppletProductDetailBo bo) {
        LambdaQueryWrapper<FolwerAppletProductDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletProductDetail> buildQueryWrapper(FolwerAppletProductDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletProductDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getSkuId() != null, FolwerAppletProductDetail::getSkuId, bo.getSkuId());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerAppletProductDetail::getRemarks, bo.getRemarks());
        return lqw;
    }

    /**
     * 新增商品详情
     *
     * @param bo 商品详情
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletProductDetailBo bo) {
        FolwerAppletProductDetail add = MapstructUtils.convert(bo, FolwerAppletProductDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setDetailId(add.getDetailId());
        }
        return flag;
    }

    /**
     * 修改商品详情
     *
     * @param bo 商品详情
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletProductDetailBo bo) {
        FolwerAppletProductDetail update = MapstructUtils.convert(bo, FolwerAppletProductDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletProductDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除商品详情信息
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
