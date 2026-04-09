package org.dromara.flower.service.domain;

import org.dromara.flower.domain.vo.FolwerOrderDetailVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class OrderDetailDomainService {

    public FolwerOrderDetailVo attachBackendSkuDetail(FolwerOrderDetailVo orderDetailVo,
                                                      Function<Long, FolwerSkuVo> skuLoader) {
        if (orderDetailVo == null || orderDetailVo.getSkuId() == null) {
            return orderDetailVo;
        }
        FolwerSkuVo skuVo = skuLoader.apply(orderDetailVo.getSkuId());
        if (skuVo != null) {
            orderDetailVo.setFolwerSkuVo(skuVo);
        }
        return orderDetailVo;
    }

    public FolwerAppletOrderVo attachAppletOrderDetails(FolwerAppletOrderVo orderVo,
                                                        List<FolwerAppletOrderDetailVo> orderDetails) {
        if (orderVo == null || orderDetails == null) {
            return orderVo;
        }
        orderVo.setOrderDetails(orderDetails);
        long totalNum = 0L;
        for (FolwerAppletOrderDetailVo detailVo : orderDetails) {
            if (detailVo.getNumber() != null) {
                totalNum += detailVo.getNumber();
            }
        }
        orderVo.setTotalNum(totalNum);
        return orderVo;
    }

    public void attachAppletOrderDetails(List<FolwerAppletOrderVo> orderVos,
                                         Function<Long, List<FolwerAppletOrderDetailVo>> detailLoader) {
        if (orderVos == null || orderVos.isEmpty()) {
            return;
        }
        for (FolwerAppletOrderVo orderVo : orderVos) {
            attachAppletOrderDetails(orderVo, detailLoader.apply(orderVo.getOrderId()));
        }
    }

    public void attachBackendSkuDetails(List<FolwerOrderDetailVo> orderDetailVos,
                                        Function<Long, FolwerSkuVo> skuLoader) {
        if (orderDetailVos == null || orderDetailVos.isEmpty()) {
            return;
        }
        for (FolwerOrderDetailVo orderDetailVo : orderDetailVos) {
            attachBackendSkuDetail(orderDetailVo, skuLoader);
        }
    }
}
