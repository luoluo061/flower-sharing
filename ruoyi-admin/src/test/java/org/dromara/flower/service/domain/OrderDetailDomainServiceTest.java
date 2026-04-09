package org.dromara.flower.service.domain;

import org.dromara.flower.domain.vo.FolwerOrderDetailVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("dev")
class OrderDetailDomainServiceTest {

    private final OrderDetailDomainService service = new OrderDetailDomainService();

    @Test
    void attachAppletOrderDetailsShouldPopulateTotalNum() {
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        FolwerAppletOrderDetailVo first = new FolwerAppletOrderDetailVo();
        first.setNumber(2L);
        FolwerAppletOrderDetailVo second = new FolwerAppletOrderDetailVo();
        second.setNumber(3L);

        service.attachAppletOrderDetails(orderVo, List.of(first, second));

        assertEquals(5L, orderVo.getTotalNum());
        assertEquals(2, orderVo.getOrderDetails().size());
    }

    @Test
    void attachAppletOrderDetailsForListShouldLoadByOrderId() {
        FolwerAppletOrderVo orderVo = new FolwerAppletOrderVo();
        orderVo.setOrderId(88L);
        FolwerAppletOrderDetailVo detailVo = new FolwerAppletOrderDetailVo();
        detailVo.setNumber(1L);

        List<FolwerAppletOrderVo> orders = new ArrayList<>();
        orders.add(orderVo);
        service.attachAppletOrderDetails(orders, orderId -> List.of(detailVo));

        assertEquals(1L, orderVo.getTotalNum());
        assertEquals(1, orderVo.getOrderDetails().size());
    }

    @Test
    void attachBackendSkuDetailsShouldAttachLoadedSku() {
        FolwerOrderDetailVo detailVo = new FolwerOrderDetailVo();
        detailVo.setSkuId(9L);
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setSkuId(9L);

        service.attachBackendSkuDetails(List.of(detailVo), skuId -> skuVo);

        assertEquals(9L, detailVo.getFolwerSkuVo().getSkuId());
    }

    @Test
    void attachBackendSkuDetailShouldAttachLoadedSkuToSingleRecord() {
        FolwerOrderDetailVo detailVo = new FolwerOrderDetailVo();
        detailVo.setSkuId(12L);
        FolwerSkuVo skuVo = new FolwerSkuVo();
        skuVo.setSkuId(12L);

        service.attachBackendSkuDetail(detailVo, skuId -> skuVo);

        assertEquals(12L, detailVo.getFolwerSkuVo().getSkuId());
    }
}
