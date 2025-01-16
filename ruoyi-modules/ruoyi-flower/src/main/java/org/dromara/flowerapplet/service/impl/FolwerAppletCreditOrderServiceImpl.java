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
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditOrderVo;
import org.dromara.flowerapplet.domain.FolwerAppletCreditOrder;
import org.dromara.flowerapplet.mapper.FolwerAppletCreditOrderMapper;
import org.dromara.flowerapplet.service.IFolwerAppletCreditOrderService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分订单Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletCreditOrderServiceImpl implements IFolwerAppletCreditOrderService {

    private final FolwerAppletCreditOrderMapper baseMapper;

    /**
     * 查询积分订单
     *
     * @param orderId 主键
     * @return 积分订单
     */
    @Override
    public FolwerAppletCreditOrderVo queryById(Long orderId){
        return baseMapper.selectVoById(orderId);
    }

    /**
     * 分页查询积分订单列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分订单分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletCreditOrderVo> queryPageList(FolwerAppletCreditOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletCreditOrder> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletCreditOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分订单列表
     *
     * @param bo 查询条件
     * @return 积分订单列表
     */
    @Override
    public List<FolwerAppletCreditOrderVo> queryList(FolwerAppletCreditOrderBo bo) {
        LambdaQueryWrapper<FolwerAppletCreditOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletCreditOrder> buildQueryWrapper(FolwerAppletCreditOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletCreditOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerAppletCreditOrder::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerAppletCreditOrder::getUserName, bo.getUserName());
        lqw.eq(bo.getMemberLevelId() != null, FolwerAppletCreditOrder::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(bo.getActualTotal() != null, FolwerAppletCreditOrder::getActualTotal, bo.getActualTotal());
        lqw.eq(bo.getPayTime() != null, FolwerAppletCreditOrder::getPayTime, bo.getPayTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerAppletCreditOrder::getRemarks, bo.getRemarks());
        lqw.eq(bo.getStatus() != null, FolwerAppletCreditOrder::getStatus, bo.getStatus());
        lqw.eq(bo.getDeliveryMode() != null, FolwerAppletCreditOrder::getDeliveryMode, bo.getDeliveryMode());
        lqw.eq(bo.getDvyId() != null, FolwerAppletCreditOrder::getDvyId, bo.getDvyId());
        lqw.like(StringUtils.isNotBlank(bo.getDvyName()), FolwerAppletCreditOrder::getDvyName, bo.getDvyName());
        lqw.eq(StringUtils.isNotBlank(bo.getDvyFlowId()), FolwerAppletCreditOrder::getDvyFlowId, bo.getDvyFlowId());
        lqw.eq(bo.getFreightAmount() != null, FolwerAppletCreditOrder::getFreightAmount, bo.getFreightAmount());
        lqw.eq(bo.getAddrOrderId() != null, FolwerAppletCreditOrder::getAddrOrderId, bo.getAddrOrderId());
        lqw.eq(bo.getDvyTime() != null, FolwerAppletCreditOrder::getDvyTime, bo.getDvyTime());
        lqw.eq(bo.getFinallyTime() != null, FolwerAppletCreditOrder::getFinallyTime, bo.getFinallyTime());
        lqw.eq(bo.getCancelTime() != null, FolwerAppletCreditOrder::getCancelTime, bo.getCancelTime());
        lqw.eq(StringUtils.isNotBlank(bo.getCancelMsg()), FolwerAppletCreditOrder::getCancelMsg, bo.getCancelMsg());
        return lqw;
    }

    /**
     * 新增积分订单
     *
     * @param bo 积分订单
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletCreditOrderBo bo) {
        FolwerAppletCreditOrder add = MapstructUtils.convert(bo, FolwerAppletCreditOrder.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setOrderId(add.getOrderId());
        }
        return flag;
    }

    /**
     * 修改积分订单
     *
     * @param bo 积分订单
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletCreditOrderBo bo) {
        FolwerAppletCreditOrder update = MapstructUtils.convert(bo, FolwerAppletCreditOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletCreditOrder entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分订单信息
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
