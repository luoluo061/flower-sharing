package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerProductDetailBo;
import org.dromara.flower.domain.vo.FolwerProductDetailVo;
import org.dromara.flower.domain.FolwerProductDetail;
import org.dromara.flower.mapper.FolwerProductDetailMapper;
import org.dromara.flower.service.IFolwerProductDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 商品详情Service业务层处理
 *
 * @author mlhxj
 * @date 2025-08-11
 */
@RequiredArgsConstructor
@Service
public class FolwerProductDetailServiceImpl implements IFolwerProductDetailService {

    private final FolwerProductDetailMapper baseMapper;

    /**
     * 查询商品详情
     *
     * @param detailId 主键
     * @return 商品详情
     */
    @Override
    public FolwerProductDetailVo queryById(Long detailId){
        return baseMapper.selectVoById(detailId);
    }

    /**
     * 分页查询商品详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品详情分页列表
     */
    @Override
    public TableDataInfo<FolwerProductDetailVo> queryPageList(FolwerProductDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerProductDetail> lqw = buildQueryWrapper(bo);
        Page<FolwerProductDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的商品详情列表
     *
     * @param bo 查询条件
     * @return 商品详情列表
     */
    @Override
    public List<FolwerProductDetailVo> queryList(FolwerProductDetailBo bo) {
        LambdaQueryWrapper<FolwerProductDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerProductDetail> buildQueryWrapper(FolwerProductDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerProductDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getSkuId() != null, FolwerProductDetail::getSkuId, bo.getSkuId());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerProductDetail::getRemarks, bo.getRemarks());
        return lqw;
    }

    /**
     * 新增商品详情
     *
     * @param bo 商品详情
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerProductDetailBo bo) {
        FolwerProductDetail add = toEntity(bo);
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
    public Boolean updateByBo(FolwerProductDetailBo bo) {
        FolwerProductDetail update = toEntity(bo);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    protected FolwerProductDetail toEntity(FolwerProductDetailBo bo) {
        return MapstructUtils.convert(bo, FolwerProductDetail.class);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerProductDetail entity){
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
