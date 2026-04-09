package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.FolwerOrder;
import org.dromara.flower.domain.bo.FolwerOrderBo;
import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.flower.domain.bo.FolwerPickAddrBo;
import org.dromara.flower.domain.vo.FolwerOrderInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.mapper.FolwerOrderMapper;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.IFolwerOrderRefundService;
import org.dromara.flower.service.IFolwerOrderService;
import org.dromara.flower.service.IFolwerPickAddrService;
import org.dromara.flower.service.IMemberLevelService;
import org.dromara.flower.service.domain.OrderFulfillmentDomainService;
import org.dromara.flower.service.domain.OrderLifecycleDomainService;
import org.dromara.flower.service.domain.OrderRefundDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 璁㈠崟Service涓氬姟灞傚鐞?
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class FolwerOrderServiceImpl implements IFolwerOrderService {

    private final FolwerOrderMapper baseMapper;
    private final IMemberLevelService memberLevelService;
    private final IAppletUserInformationService appletUserInformationService;
    private final IFolwerPickAddrService folwerPickAddrService;
    private final IFolwerOrderRefundService folwerOrderRefundService;
    private final OrderFulfillmentDomainService orderFulfillmentDomainService;
    private final OrderLifecycleDomainService orderLifecycleDomainService;
    private final OrderRefundDomainService orderRefundDomainService;

    @Override
    public FolwerOrderVo queryById(Long orderId) {
        FolwerOrderVo folwerOrderVo = baseMapper.selectVoById(orderId);
        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(folwerOrderVo.getUserId());
        if (appletUserInformationVo != null) {
            folwerOrderVo.setUserName(appletUserInformationVo.getNickName());
            folwerOrderVo.setUserPhone(appletUserInformationVo.getPhone());
        }
        FolwerPickAddrBo folwerPickAddrBo = new FolwerPickAddrBo();
        folwerPickAddrBo.setUserId(String.valueOf(folwerOrderVo.getUserId()));
        List<FolwerPickAddrVo> folwerPickAddrVos = folwerPickAddrService.queryList(folwerPickAddrBo);
        if (folwerPickAddrVos != null && !folwerPickAddrVos.isEmpty()) {
            orderFulfillmentDomainService.applyAddress(folwerOrderVo, folwerPickAddrVos.get(0));
        }
        return folwerOrderVo;
    }

    @Override
    public FolwerOrderInfoVo queryInfoById(Long orderId) {
        return baseMapper.selectOrderInfoVoById(orderId);
    }

    @Override
    public TableDataInfo<FolwerOrderVo> queryPageList(FolwerOrderBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerOrder> lqw = buildQueryWrapper(bo);
        Page<FolwerOrderVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        result.getRecords().forEach(folwerOrderVo -> {
            MemberLevelVo memberLevelVo = memberLevelService.queryById(folwerOrderVo.getMemberLevelId());
            if (memberLevelVo != null) {
                folwerOrderVo.setMemberLevelName(memberLevelVo.getGradeName());
            }
            AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(folwerOrderVo.getUserId());
            if (appletUserInformationVo != null) {
                folwerOrderVo.setUserName(appletUserInformationVo.getNickName());
            }
            FolwerPickAddrVo folwerPickAddrVo = folwerPickAddrService.queryById(folwerOrderVo.getAddrOrderId());
            if (folwerPickAddrVo != null) {
                orderFulfillmentDomainService.applyAddress(folwerOrderVo, folwerPickAddrVo);
            }
        });
        return TableDataInfo.build(result);
    }

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
        lqw.eq(bo.getFreightAmount() != null, FolwerOrder::getFreightAmount, bo.getFreightAmount());
        lqw.eq(bo.getAddrOrderId() != null, FolwerOrder::getAddrOrderId, bo.getAddrOrderId());
        lqw.eq(bo.getFinallyTime() != null, FolwerOrder::getFinallyTime, bo.getFinallyTime());
        lqw.eq(bo.getCancelTime() != null, FolwerOrder::getCancelTime, bo.getCancelTime());
        lqw.eq(StringUtils.isNotBlank(bo.getCancelMsg()), FolwerOrder::getCancelMsg, bo.getCancelMsg());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerOrder::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

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

    @Override
    public Boolean updateByBo(FolwerOrderBo bo) {
        FolwerOrder update = MapstructUtils.convert(bo, FolwerOrder.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    private void validEntityBeforeSave(FolwerOrder entity) {
        // TODO data validation hook for future Stage 2 cleanup.
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO business validation hook for future Stage 2 cleanup.
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    @Transactional
    public String createRefund(Long orderId) {
        FolwerOrderVo folwerOrderVo = this.queryById(orderId);
        if (folwerOrderVo != null && folwerOrderVo.getStatus() == 5L) {
            FolwerOrderBo folwerOrderBo = orderLifecycleDomainService.prepareRefundingOrder(folwerOrderVo);
            Boolean updated = this.updateByBo(folwerOrderBo);
            if (updated) {
                FolwerOrderRefundBo folwerOrderRefundBo = orderRefundDomainService.prepareBackendRefundCreation(folwerOrderVo);
                Boolean inserted = folwerOrderRefundService.insertByBo(folwerOrderRefundBo);
                if (inserted && folwerOrderRefundBo.getRefundId() != null) {
                    return folwerOrderRefundBo.getRefundId().toString();
                }
            }
        }
        return null;
    }
}
