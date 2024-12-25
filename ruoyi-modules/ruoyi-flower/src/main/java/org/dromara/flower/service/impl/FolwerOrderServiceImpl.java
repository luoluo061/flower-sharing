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
import org.dromara.flower.domain.bo.FolwerOrderBo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.domain.FolwerOrder;
import org.dromara.flower.mapper.FolwerOrderMapper;
import org.dromara.flower.service.IFolwerOrderService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单Service业务层处理
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class FolwerOrderServiceImpl implements IFolwerOrderService {

    private final FolwerOrderMapper baseMapper;

    /**
     * 查询订单
     *
     * @param orderId 主键
     * @return 订单
     */
    @Override
    public FolwerOrderVo queryById(Long orderId){
        return baseMapper.selectVoById(orderId);
    }

    /**
     * 分页查询订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单分页列表
     */
    @Override
    public TableDataInfo<FolwerOrderVo> queryPageList(FolwerOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerOrder> lqw = buildQueryWrapper(bo);
        Page<FolwerOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单列表
     *
     * @param bo 查询条件
     * @return 订单列表
     */
    @Override
    public List<FolwerOrderVo> queryList(FolwerOrderBo bo) {
        LambdaQueryWrapper<FolwerOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerOrder> buildQueryWrapper(FolwerOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerOrder::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerOrder::getUserName, bo.getUserName());
        lqw.eq(bo.getMemberLevelId() != null, FolwerOrder::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderNumber()), FolwerOrder::getOrderNumber, bo.getOrderNumber());
        lqw.eq(bo.getTotal() != null, FolwerOrder::getTotal, bo.getTotal());
        lqw.eq(bo.getActualTotal() != null, FolwerOrder::getActualTotal, bo.getActualTotal());
        lqw.eq(bo.getPayType() != null, FolwerOrder::getPayType, bo.getPayType());
        lqw.eq(bo.getPayTime() != null, FolwerOrder::getPayTime, bo.getPayTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerOrder::getRemarks, bo.getRemarks());
        lqw.eq(bo.getStatus() != null, FolwerOrder::getStatus, bo.getStatus());
        lqw.eq(bo.getApplyType() != null, FolwerOrder::getApplyType, bo.getApplyType());
        lqw.eq(bo.getRefundId() != null, FolwerOrder::getRefundId, bo.getRefundId());
        lqw.eq(bo.getRefundAmount() != null, FolwerOrder::getRefundAmount, bo.getRefundAmount());
        lqw.eq(bo.getRefundTime() != null, FolwerOrder::getRefundTime, bo.getRefundTime());
        lqw.eq(StringUtils.isNotBlank(bo.getBuyerMsg()), FolwerOrder::getBuyerMsg, bo.getBuyerMsg());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundRemark()), FolwerOrder::getRefundRemark, bo.getRefundRemark());
        return lqw;
    }

    /**
     * 新增订单
     *
     * @param bo 订单
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerOrderBo bo) {
        FolwerOrder add = MapstructUtils.convert(bo, FolwerOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOrderId(add.getOrderId());
        }
        return flag;
    }

    /**
     * 修改订单
     *
     * @param bo 订单
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerOrderBo bo) {
        FolwerOrder update = MapstructUtils.convert(bo, FolwerOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除订单信息
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
