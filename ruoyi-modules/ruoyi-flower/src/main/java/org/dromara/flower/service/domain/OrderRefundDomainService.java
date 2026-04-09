package org.dromara.flower.service.domain;

import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderRefundBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class OrderRefundDomainService {

    public FolwerOrderRefundBo prepareBackendRefundCreation(FolwerOrderVo orderVo) {
        FolwerOrderRefundBo refundBo = new FolwerOrderRefundBo();
        refundBo.setOrderId(String.valueOf(orderVo.getOrderId()));
        refundBo.setUserId(orderVo.getUserId());
        refundBo.setUserName(orderVo.getUserName());
        refundBo.setMemberLevelId(orderVo.getMemberLevelId());
        BigDecimal actualTotal = new BigDecimal(orderVo.getActualTotal());
        refundBo.setRefundAmount(actualTotal);
        refundBo.setActualTotal(actualTotal);
        refundBo.setRefundMsg("平台退款");
        refundBo.setRefundStatus(2L);
        refundBo.setStatus(orderVo.getStatus());
        refundBo.setApplyType(2L);
        return refundBo;
    }

    public FolwerOrderRefundBo prepareRefundStatusMutation(FolwerOrderRefundVo refundVo,
                                                           Long refundStatus,
                                                           String refundTime) {
        FolwerOrderRefundBo refundBo = new FolwerOrderRefundBo();
        refundBo.setRefundId(refundVo.getRefundId());
        refundBo.setUserId(refundVo.getUserId());
        refundBo.setUserName(refundVo.getUserName());
        refundBo.setMemberLevelId(refundVo.getMemberLevelId());
        refundBo.setOrderId(refundVo.getOrderId());
        refundBo.setActualTotal(new BigDecimal(refundVo.getActualTotal()));
        refundBo.setStatus(refundVo.getStatus());
        refundBo.setApplyType(refundVo.getApplyType());
        refundBo.setRefundMsg(refundVo.getRefundMsg());
        refundBo.setRefundAmount(new BigDecimal(refundVo.getRefundAmount()));
        refundBo.setBuyerMsg(refundVo.getBuyerMsg());
        refundBo.setRefundRemark(refundVo.getRefundRemark());
        refundBo.setRefundStatus(refundStatus);
        refundBo.setRefundTime(refundTime);
        return refundBo;
    }

    public FolwerAppletOrderBo prepareAppletRefundOrderMutation(FolwerAppletOrderVo orderVo) {
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
        orderBo.setActualTotal(new BigDecimal(orderVo.getActualTotal()));
        orderBo.setPayType(orderVo.getPayType());
        orderBo.setPayTime(orderVo.getPayTime());
        orderBo.setRemarks(orderVo.getRemarks());
        orderBo.setStatus(orderVo.getStatus());
        orderBo.setPayCallback(orderVo.getPayCallback());
        orderBo.setIsRefund(2L);
        orderBo.setIsProfitSharing(orderVo.getIsProfitSharing());
        orderBo.setFreightAmount(orderVo.getFreightAmount());
        if (orderVo.getAddrOrderId() != null) {
            orderBo.setAddrOrderId(String.valueOf(orderVo.getAddrOrderId()));
        }
        orderBo.setFinallyTime(orderVo.getFinallyTime());
        orderBo.setCancelTime(orderVo.getCancelTime());
        orderBo.setCancelMsg(orderVo.getCancelMsg());
        return orderBo;
    }

    public FolwerAppletOrderRefundBo prepareAppletRefundCreation(FolwerAppletOrderRefundBo refundBo) {
        FolwerAppletOrderRefundBo prepared = new FolwerAppletOrderRefundBo();
        prepared.setRefundId(refundBo.getRefundId());
        prepared.setUserId(refundBo.getUserId());
        prepared.setUserName(refundBo.getUserName());
        prepared.setMemberLevelId(refundBo.getMemberLevelId());
        prepared.setOrderId(refundBo.getOrderId());
        prepared.setActualTotal(refundBo.getActualTotal());
        prepared.setRefundStatus(refundBo.getRefundStatus());
        prepared.setStatus(refundBo.getStatus());
        prepared.setApplyType(refundBo.getApplyType());
        prepared.setRefundMsg(refundBo.getRefundMsg());
        prepared.setRefundAmount(refundBo.getRefundAmount());
        prepared.setRefundTime(refundBo.getRefundTime());
        prepared.setBuyerMsg(refundBo.getBuyerMsg());
        prepared.setRefundRemark(refundBo.getRefundRemark());
        prepared.setRefundRemarkPic(refundBo.getRefundRemarkPic());
        return prepared;
    }
}
