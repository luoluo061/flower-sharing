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
import org.dromara.flower.domain.bo.FolwerCreditOrderDetailBo;
import org.dromara.flower.domain.vo.FolwerCreditOrderDetailVo;
import org.dromara.flower.domain.FolwerCreditOrderDetail;
import org.dromara.flower.mapper.FolwerCreditOrderDetailMapper;
import org.dromara.flower.service.IFolwerCreditOrderDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分订单详细Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class FolwerCreditOrderDetailServiceImpl implements IFolwerCreditOrderDetailService {

    private final FolwerCreditOrderDetailMapper baseMapper;

    /**
     * 查询积分订单详细
     *
     * @param id 主键
     * @return 积分订单详细
     */
    @Override
    public FolwerCreditOrderDetailVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询积分订单详细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分订单详细分页列表
     */
    @Override
    public TableDataInfo<FolwerCreditOrderDetailVo> queryPageList(FolwerCreditOrderDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCreditOrderDetail> lqw = buildQueryWrapper(bo);
        Page<FolwerCreditOrderDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分订单详细列表
     *
     * @param bo 查询条件
     * @return 积分订单详细列表
     */
    @Override
    public List<FolwerCreditOrderDetailVo> queryList(FolwerCreditOrderDetailBo bo) {
        LambdaQueryWrapper<FolwerCreditOrderDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerCreditOrderDetail> buildQueryWrapper(FolwerCreditOrderDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCreditOrderDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(StringUtils.isNotBlank(bo.getOrderId()), FolwerCreditOrderDetail::getOrderId, bo.getOrderId());
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerCreditOrderDetail::getProductName, bo.getProductName());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerCreditOrderDetail::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(bo.getOrderPrice() != null, FolwerCreditOrderDetail::getOrderPrice, bo.getOrderPrice());
        lqw.eq(bo.getNumber() != null, FolwerCreditOrderDetail::getNumber, bo.getNumber());
        lqw.eq(bo.getSubtotal() != null, FolwerCreditOrderDetail::getSubtotal, bo.getSubtotal());
        return lqw;
    }

    /**
     * 新增积分订单详细
     *
     * @param bo 积分订单详细
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCreditOrderDetailBo bo) {
        FolwerCreditOrderDetail add = MapstructUtils.convert(bo, FolwerCreditOrderDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改积分订单详细
     *
     * @param bo 积分订单详细
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerCreditOrderDetailBo bo) {
        FolwerCreditOrderDetail update = MapstructUtils.convert(bo, FolwerCreditOrderDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCreditOrderDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分订单详细信息
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
