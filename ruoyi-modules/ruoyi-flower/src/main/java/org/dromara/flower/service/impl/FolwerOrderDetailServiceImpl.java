package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerDelivery;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerOrderDetailBo;
import org.dromara.flower.domain.vo.FolwerOrderDetailVo;
import org.dromara.flower.domain.FolwerOrderDetail;
import org.dromara.flower.mapper.FolwerOrderDetailMapper;
import org.dromara.flower.service.IFolwerOrderDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单详细Service业务层处理
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class FolwerOrderDetailServiceImpl implements IFolwerOrderDetailService {

    private final FolwerOrderDetailMapper baseMapper;

    /**
     * 查询订单详细
     *
     * @param id 主键
     * @return 订单详细
     */
    @Override
    public FolwerOrderDetailVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询订单详细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单详细分页列表
     */
    @Override
    public TableDataInfo<FolwerOrderDetailVo> queryPageList(FolwerOrderDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerOrderDetail> lqw = buildQueryWrapper(bo);
        Page<FolwerOrderDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单详细列表
     *
     * @param bo 查询条件
     * @return 订单详细列表
     */
    @Override
    public List<FolwerOrderDetailVo> queryList(FolwerOrderDetailBo bo) {
        LambdaQueryWrapper<FolwerOrderDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerOrderDetail> buildQueryWrapper(FolwerOrderDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderId()), FolwerOrderDetail::getOrderId, bo.getOrderId());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerOrderDetail::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerOrderDetail::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(bo.getOrderPrice() != null, FolwerOrderDetail::getOrderPrice, bo.getOrderPrice());
        lqw.eq(bo.getNumber() != null, FolwerOrderDetail::getNumber, bo.getNumber());
        lqw.eq(bo.getSubtotal() != null, FolwerOrderDetail::getSubtotal, bo.getSubtotal());
        return lqw;
    }

    /**
     * 新增订单详细
     *
     * @param bo 订单详细
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerOrderDetailBo bo) {
        FolwerOrderDetail add = MapstructUtils.convert(bo, FolwerOrderDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改订单详细
     *
     * @param bo 订单详细
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerOrderDetailBo bo) {
        FolwerOrderDetail update = MapstructUtils.convert(bo, FolwerOrderDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerOrderDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除订单详细信息
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
