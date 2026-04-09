package org.dromara.flower.service.domain;

import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.OneselfMemberLevelPrivilege;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Tag("dev")
class MemberAssetDomainServiceTest {

    private final MemberAssetDomainService service = new MemberAssetDomainService();

    @Test
    void prepareMemberPrivilegeSnapshotShouldCreateOwnedPrivileges() {
        MemberPurchaseRecord record = new MemberPurchaseRecord();
        record.setId(10L);
        MemberLevelPrivilegeVo privilegeVo = new MemberLevelPrivilegeVo();
        privilegeVo.setId(20L);
        privilegeVo.setMemberLevelId(30L);

        List<OneselfMemberLevelPrivilege> result = service.prepareMemberPrivilegeSnapshot(record, List.of(privilegeVo));

        assertEquals(1, result.size());
        assertEquals(10L, result.get(0).getMemberPurchaseRecordId());
        assertNotNull(result.get(0).getEndTime());
    }
}
