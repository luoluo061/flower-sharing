package org.dromara.flower.service.domain;

import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("dev")
class OrderFulfillmentDomainServiceTest {

    private final OrderFulfillmentDomainService service = new OrderFulfillmentDomainService();

    @Test
    void applyAddressShouldPopulateBackendDeliveryFields() {
        FolwerOrderVo orderVo = new FolwerOrderVo();
        FolwerPickAddrVo addrVo = new FolwerPickAddrVo();
        addrVo.setProvince("Guangdong");
        addrVo.setCity("Shenzhen");
        addrVo.setArea("Nanshan");
        addrVo.setAddr("Science Park");
        addrVo.setMobile("13800138000");
        addrVo.setAddrName("Tang");

        service.applyAddress(orderVo, addrVo);

        assertEquals("GuangdongShenzhenNanshanScience Park", orderVo.getAddr());
        assertEquals("13800138000", orderVo.getMobile());
        assertEquals("Tang", orderVo.getAddrName());
    }
}
