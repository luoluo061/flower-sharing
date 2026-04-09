package org.dromara.flower.service.domain;

import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.springframework.stereotype.Service;

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
}
