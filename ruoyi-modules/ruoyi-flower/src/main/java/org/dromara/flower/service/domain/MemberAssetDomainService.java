package org.dromara.flower.service.domain;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.flower.domain.OneselfMemberLevelPrivilege;
import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class MemberAssetDomainService {

    public MemberPurchaseRecord preparePurchaseRecordForCreate(MemberPurchaseRecord purchaseRecord) {
        if (Objects.isNull(purchaseRecord)) {
            return null;
        }
        Date now = new Date();
        purchaseRecord.setCreateTime(now);
        purchaseRecord.setEndTime(resolveMemberEndTime(now));
        purchaseRecord.setStatus(0L);
        purchaseRecord.setPayStatus(0L);
        return purchaseRecord;
    }

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

    public MemberLevelVo attachPrivileges(MemberLevelVo memberLevelVo, List<MemberLevelPrivilegeVo> privileges) {
        if (Objects.nonNull(memberLevelVo)) {
            memberLevelVo.setPrivilegeVos(privileges);
        }
        return memberLevelVo;
    }

    public MemberPurchaseRecordVo attachMemberLevel(MemberPurchaseRecordVo recordVo, MemberLevelVo memberLevelVo) {
        if (Objects.nonNull(recordVo)) {
            recordVo.setMemberLevelVo(memberLevelVo);
        }
        return recordVo;
    }

    public Date resolveMemberEndTime() {
        return resolveMemberEndTime(new Date());
    }

    public Date resolveMemberEndTime(Date startTime) {
        ZonedDateTime baseTime = Objects.isNull(startTime)
            ? ZonedDateTime.now()
            : ZonedDateTime.ofInstant(startTime.toInstant(), ZonedDateTime.now().getZone());
        return Date.from(baseTime.plusYears(1).toInstant());
    }
}
