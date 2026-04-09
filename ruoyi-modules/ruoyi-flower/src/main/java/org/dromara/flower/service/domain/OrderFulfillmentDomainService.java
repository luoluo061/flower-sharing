package org.dromara.flower.service.domain;

import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDvyVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderFulfillmentDomainService {

    public void applyAddress(FolwerOrderVo orderVo, FolwerPickAddrVo pickAddrVo) {
        if (orderVo == null || pickAddrVo == null) {
            return;
        }
        orderVo.setAddr(buildAddress(pickAddrVo));
        orderVo.setMobile(pickAddrVo.getMobile());
        orderVo.setAddrName(pickAddrVo.getAddrName());
    }

    public String buildAddress(FolwerPickAddrVo pickAddrVo) {
        if (pickAddrVo == null) {
            return null;
        }
        return nullSafe(pickAddrVo.getProvince())
            + nullSafe(pickAddrVo.getCity())
            + nullSafe(pickAddrVo.getArea())
            + nullSafe(pickAddrVo.getAddr());
    }

    private String nullSafe(String value) {
        return value == null ? "" : value;
    }

    public BigDecimal resolveFreightAmount(FolwerAppletOrderDvyVo orderDvyVo) {
        if (orderDvyVo == null || orderDvyVo.getPackingAmount() == null) {
            return BigDecimal.ZERO;
        }
        return orderDvyVo.getPackingAmount();
    }

    public FolwerAppletOrderBo prepareAppletFreightUpdate(FolwerAppletOrderVo orderVo, BigDecimal freightAmount) {
        FolwerAppletOrderBo orderBo = new FolwerAppletOrderBo();
        orderBo.setOrderId(String.valueOf(orderVo.getOrderId()));
        orderBo.setUserId(String.valueOf(orderVo.getUserId()));
        orderBo.setUserName(orderVo.getUserName());
        if (orderVo.getMemberLevelId() != null) {
            orderBo.setMemberLevelId(String.valueOf(orderVo.getMemberLevelId()));
        }
        orderBo.setOrderNumber(orderVo.getOrderNumber());
        orderBo.setTotal(orderVo.getTotal());
        orderBo.setRebate(orderVo.getRebate());
        orderBo.setActualTotal(orderVo.getTotal().add(freightAmount));
        orderBo.setPayType(orderVo.getPayType());
        orderBo.setPayTime(orderVo.getPayTime());
        orderBo.setRemarks(orderVo.getRemarks());
        orderBo.setStatus(orderVo.getStatus());
        orderBo.setPayCallback(orderVo.getPayCallback());
        orderBo.setIsRefund(orderVo.getIsRefund());
        orderBo.setIsProfitSharing(orderVo.getIsProfitSharing());
        orderBo.setFreightAmount(freightAmount);
        if (orderVo.getAddrOrderId() != null) {
            orderBo.setAddrOrderId(String.valueOf(orderVo.getAddrOrderId()));
        }
        orderBo.setFinallyTime(orderVo.getFinallyTime());
        orderBo.setCancelTime(orderVo.getCancelTime());
        orderBo.setCancelMsg(orderVo.getCancelMsg());
        return orderBo;
    }
}
