package org.dromara.flower.service.domain;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.flower.domain.bo.FolwerOrderBo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Set;

@Service
public class OrderLifecycleDomainService {

    private static final Long PENDING_PAYMENT_STATUS = 0L;
    private static final Set<Long> TERMINAL_STATUSES = Set.of(2L, 3L, 4L, 8L, 9L);

    public FolwerAppletOrderBo preparePaidOrder(FolwerAppletOrderVo orderVo,
                                                Long targetStatus,
                                                String transactionId,
                                                Date payTime,
                                                String payCallback) {
        FolwerAppletOrderBo orderBo = new FolwerAppletOrderBo();
        BeanUtil.copyProperties(orderVo, orderBo);
        orderBo.setStatus(targetStatus);
        orderBo.setOrderNumber(transactionId);
        orderBo.setPayTime(payTime);
        orderBo.setPayCallback(payCallback);
        return orderBo;
    }

    public FolwerOrderBo prepareRefundingOrder(FolwerOrderVo orderVo) {
        FolwerOrderBo orderBo = new FolwerOrderBo();
        BeanUtil.copyProperties(orderVo, orderBo);
        orderBo.setStatus(2L);
        return orderBo;
    }

    public boolean isPendingPayment(Long status) {
        return PENDING_PAYMENT_STATUS.equals(status);
    }

    public boolean isTerminalState(Long status) {
        return status != null && TERMINAL_STATUSES.contains(status);
    }
}
