package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerCreditProduct;
import org.dromara.flower.domain.vo.FolwerCreditOrderInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderRefundInfoVo;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerCreditOrderBo;
import org.dromara.flower.domain.vo.FolwerCreditOrderVo;
import org.dromara.flower.domain.FolwerCreditOrder;
import org.dromara.flower.mapper.FolwerCreditOrderMapper;
import org.dromara.flower.service.IFolwerCreditOrderService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分订单Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class FolwerCreditOrderServiceImpl implements IFolwerCreditOrderService {

    private final FolwerCreditOrderMapper baseMapper;

    /**
     * 查询积分订单
     *
     * @param orderId 主键
     * @return 积分订单
     */
    @Override
    public FolwerCreditOrderVo queryById(Long orderId){
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
    public TableDataInfo<FolwerCreditOrderVo> queryPageList(FolwerCreditOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCreditOrder> lqw = buildQueryWrapper(bo);
        Page<FolwerCreditOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询积分订单详情
     * @param orderId
     * @return
     */
    @Override
    public FolwerCreditOrderInfoVo queryInfoById(Long orderId) {
        FolwerCreditOrderInfoVo folwerCreditOrderInfoVo = baseMapper.selectCreditOrderInfoVoById(orderId);
        if (folwerCreditOrderInfoVo != null){
            int i = folwerCreditOrderInfoVo.getStatus().intValue();
            if (folwerCreditOrderInfoVo != null) {
                switch (i) {
                    case 0:
                        folwerCreditOrderInfoVo.setStatusStr("待兑换");
                        break;
                    case 1:
                        folwerCreditOrderInfoVo.setStatusStr("已兑换");
                        break;
                    case 2:
                        folwerCreditOrderInfoVo.setStatusStr("待发货");
                        break;
                    case 3:
                        folwerCreditOrderInfoVo.setStatusStr("待收货");
                        break;
                    case 4:
                        folwerCreditOrderInfoVo.setStatusStr("待评价");
                        break;
                }
            }
            folwerCreditOrderInfoVo.setActualTotalStr("积分余额");
        }


        return folwerCreditOrderInfoVo;
    }



    /**
     * 查询符合条件的积分订单列表
     *
     * @param bo 查询条件
     * @return 积分订单列表
     */
    @Override
    public List<FolwerCreditOrderVo> queryList(FolwerCreditOrderBo bo) {
        LambdaQueryWrapper<FolwerCreditOrder> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerCreditOrder> buildQueryWrapper(FolwerCreditOrderBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCreditOrder> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerCreditOrder::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerCreditOrder::getUserName, bo.getUserName());
        lqw.eq(bo.getMemberLevelId() != null, FolwerCreditOrder::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(bo.getActualTotal() != null, FolwerCreditOrder::getActualTotal, bo.getActualTotal());
        lqw.eq(bo.getPayTime() != null, FolwerCreditOrder::getPayTime, bo.getPayTime());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerCreditOrder::getRemarks, bo.getRemarks());
        lqw.eq(bo.getStatus() != null, FolwerCreditOrder::getStatus, bo.getStatus());
        lqw.eq(bo.getDeliveryMode() != null, FolwerCreditOrder::getDeliveryMode, bo.getDeliveryMode());
        lqw.eq(bo.getDvyId() != null, FolwerCreditOrder::getDvyId, bo.getDvyId());
        lqw.like(StringUtils.isNotBlank(bo.getDvyName()), FolwerCreditOrder::getDvyName, bo.getDvyName());
        lqw.eq(StringUtils.isNotBlank(bo.getDvyFlowId()), FolwerCreditOrder::getDvyFlowId, bo.getDvyFlowId());
        lqw.eq(bo.getFreightAmount() != null, FolwerCreditOrder::getFreightAmount, bo.getFreightAmount());
        lqw.eq(bo.getAddrOrderId() != null, FolwerCreditOrder::getAddrOrderId, bo.getAddrOrderId());
        lqw.eq(bo.getDvyTime() != null, FolwerCreditOrder::getDvyTime, bo.getDvyTime());
        lqw.eq(bo.getFinallyTime() != null, FolwerCreditOrder::getFinallyTime, bo.getFinallyTime());
        lqw.eq(bo.getCancelTime() != null, FolwerCreditOrder::getCancelTime, bo.getCancelTime());
        lqw.eq(StringUtils.isNotBlank(bo.getCancelMsg()), FolwerCreditOrder::getCancelMsg, bo.getCancelMsg());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerCreditOrder::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    /**
     * 新增积分订单
     *
     * @param bo 积分订单
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCreditOrderBo bo) {
        FolwerCreditOrder add = MapstructUtils.convert(bo, FolwerCreditOrder.class);
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
    public Boolean updateByBo(FolwerCreditOrderBo bo) {
        FolwerCreditOrder update = MapstructUtils.convert(bo, FolwerCreditOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCreditOrder entity){
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
