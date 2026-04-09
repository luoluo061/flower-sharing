package org.dromara.flower.service.domain;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.flower.domain.vo.FolwerCreditGetrecordsVo;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flowerapplet.util.Arith;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Service
public class PointsAssetDomainService {

    public AppletUserInformationBo preparePointsEarn(AppletUserInformationVo userVo, Long pointsDelta) {
        if (Objects.isNull(userVo)) {
            return null;
        }
        AppletUserInformationBo userBo = BeanUtil.copyProperties(userVo, AppletUserInformationBo.class);
        long currentPoints = Objects.requireNonNullElse(userVo.getPoints(), 0L);
        long delta = Objects.requireNonNullElse(pointsDelta, 0L);
        userBo.setPoints((long) Arith.add(currentPoints, delta));
        return userBo;
    }

    public void applySourceLabel(FolwerCreditGetrecordsVo record, String sourceName) {
        if (Objects.nonNull(record)) {
            record.setCreditSourName(sourceName);
        }
    }

    public void applySourceLabels(List<FolwerCreditGetrecordsVo> records, Function<Long, String> labelResolver) {
        if (Objects.isNull(records) || Objects.isNull(labelResolver)) {
            return;
        }
        records.forEach(record -> applySourceLabel(record, labelResolver.apply(record.getCreditSourId())));
    }
}
