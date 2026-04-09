package org.dromara.flower.service.domain;

import org.dromara.flower.domain.vo.FolwerCreditGetrecordsVo;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("dev")
class PointsAssetDomainServiceTest {

    private final PointsAssetDomainService service = new PointsAssetDomainService();

    @Test
    void preparePointsEarnShouldAccumulateCurrentPoints() {
        AppletUserInformationVo userVo = new AppletUserInformationVo();
        userVo.setPoints(10L);

        AppletUserInformationBo result = service.preparePointsEarn(userVo, 5L);

        assertEquals(15L, result.getPoints());
    }

    @Test
    void applySourceLabelsShouldFillDisplayNames() {
        FolwerCreditGetrecordsVo first = new FolwerCreditGetrecordsVo();
        first.setCreditSourId(1L);
        FolwerCreditGetrecordsVo second = new FolwerCreditGetrecordsVo();
        second.setCreditSourId(2L);
        List<FolwerCreditGetrecordsVo> records = new ArrayList<>(List.of(first, second));

        service.applySourceLabels(records, id -> "label-" + id);

        assertEquals("label-1", first.getCreditSourName());
        assertEquals("label-2", second.getCreditSourName());
    }
}
