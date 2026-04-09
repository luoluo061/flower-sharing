package org.dromara.flower.service.domain;

import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
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
}
