package org.dromara.flower.service.domain;

import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
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
}
