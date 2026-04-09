package org.dromara.flower.service.domain;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.flower.domain.OneselfMemberLevelPrivilege;
import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MemberAssetDomainService {

    public List<OneselfMemberLevelPrivilege> prepareMemberPrivilegeSnapshot(MemberPurchaseRecord purchaseRecord,
                                                                            List<MemberLevelPrivilegeVo> privileges) {
        List<OneselfMemberLevelPrivilege> snapshots = new ArrayList<>();
        if (Objects.isNull(purchaseRecord) || Objects.isNull(privileges) || privileges.isEmpty()) {
            return snapshots;
        }

        Date endTime = resolveMemberEndTime();
        privileges.forEach(privilege -> {
            OneselfMemberLevelPrivilege snapshot = BeanUtil.copyProperties(privilege, OneselfMemberLevelPrivilege.class);
            if (Objects.nonNull(snapshot)) {
                snapshot.setId(null);
                snapshot.setMemberPurchaseRecordId(purchaseRecord.getId());
                snapshot.setEndTime(endTime);
                snapshots.add(snapshot);
            }
        });
        return snapshots;
    }

    public Date resolveMemberEndTime() {
        return Date.from(ZonedDateTime.now().plusYears(1).toInstant());
    }
}
