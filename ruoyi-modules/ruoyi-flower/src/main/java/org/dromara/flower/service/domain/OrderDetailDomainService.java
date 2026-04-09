package org.dromara.flower.service.domain;

import org.dromara.flower.domain.vo.FolwerOrderDetailVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDetailBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletBasketVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public FolwerAppletOrderDetailVo attachAppletSkuName(FolwerAppletOrderDetailVo detailVo,
                                                         Function<Long, String> skuNameLoader) {
        if (detailVo == null || detailVo.getSkuId() == null) {
            return detailVo;
        }
        String skuName = skuNameLoader.apply(detailVo.getSkuId());
        if (skuName != null) {
            detailVo.setSkuName(skuName);
        }
        return detailVo;
    }

    public void attachAppletSkuNames(List<FolwerAppletOrderDetailVo> detailVos,
                                     Function<Long, String> skuNameLoader) {
        if (detailVos == null || detailVos.isEmpty()) {
            return;
        }
        for (FolwerAppletOrderDetailVo detailVo : detailVos) {
            attachAppletSkuName(detailVo, skuNameLoader);
        }
    }

    public FolwerAppletOrderDetailBo buildDirectBuyDetail(FolwerAppletProductVo productVo,
                                                          FolwerAppletSkuVo skuVo,
                                                          Integer prodCount) {
        FolwerAppletOrderDetailBo detailBo = new FolwerAppletOrderDetailBo();
        BigDecimal subtotal = skuVo.getPrice().multiply(BigDecimal.valueOf(prodCount));
        detailBo.setOrderPrice(skuVo.getPrice());
        detailBo.setProductListPictureUrl(skuVo.getSkuPicid());
        detailBo.setProductId(productVo.getId());
        detailBo.setProductName(productVo.getProductName());
        detailBo.setNumber(Long.valueOf(prodCount));
        detailBo.setSubtotal(subtotal);
        detailBo.setSkuId(skuVo.getSkuId());
        return detailBo;
    }

    public FolwerAppletOrderDetailBo buildBasketDetail(FolwerAppletBasketVo basketVo,
                                                       FolwerAppletProductVo productVo,
                                                       FolwerAppletSkuVo skuVo) {
        FolwerAppletOrderDetailBo detailBo = new FolwerAppletOrderDetailBo();
        BigDecimal subtotal = skuVo.getPrice().multiply(BigDecimal.valueOf(basketVo.getBasketCount()));
        detailBo.setOrderPrice(skuVo.getPrice());
        detailBo.setProductListPictureUrl(skuVo.getSkuPicid());
        detailBo.setProductId(productVo.getId());
        detailBo.setProductName(productVo.getProductName());
        detailBo.setNumber(basketVo.getBasketCount());
        detailBo.setSubtotal(subtotal);
        detailBo.setSkuId(basketVo.getSkuId());
        return detailBo;
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
