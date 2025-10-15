package org.dromara.flower.wrapper;

import org.dromara.flower.domain.FolwerDeliveryArea;
import org.dromara.flower.domain.FolwerDeliveryPriceAdd;

import java.util.List;

public class DeliveryWrapper {
    private FolwerDeliveryPriceAdd folwerDeliveryPriceAdd;
    private List<FolwerDeliveryArea> folwerDeliveryArea;

    // 必须提供getter和setter方法
    public FolwerDeliveryPriceAdd getFolwerDeliveryPriceAdd() {
        return folwerDeliveryPriceAdd;
    }

    public void setFolwerDeliveryPriceAdd(FolwerDeliveryPriceAdd folwerDeliveryPriceAdd) {
        this.folwerDeliveryPriceAdd = folwerDeliveryPriceAdd;
    }

    public List<FolwerDeliveryArea> getFolwerDeliveryArea() {
        return folwerDeliveryArea;
    }

    public void setFolwerDeliveryArea(List<FolwerDeliveryArea> folwerDeliveryArea) {
        this.folwerDeliveryArea = folwerDeliveryArea;
    }
}
