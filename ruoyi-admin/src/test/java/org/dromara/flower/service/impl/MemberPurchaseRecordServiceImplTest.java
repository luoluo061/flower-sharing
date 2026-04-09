package org.dromara.flower.service.impl;

import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.OneselfMemberLevelPrivilege;
import org.dromara.flower.domain.vo.MemberLevelPrivilegeVo;
import org.dromara.flower.mapper.MemberLevelPrivilegeMapper;
import org.dromara.flower.mapper.MemberPurchaseRecordMapper;
import org.dromara.flower.mapper.OneselfMemberLevelPrivilegeMapper;
import org.dromara.flower.service.domain.MemberAssetDomainService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class MemberPurchaseRecordServiceImplTest {

    @Mock
    private MemberPurchaseRecordMapper baseMapper;
    @Mock
    private OneselfMemberLevelPrivilegeMapper oneselfMemberLevelPrivilegeMapper;
    @Mock
    private MemberLevelPrivilegeMapper memberLevelPrivilegeMapper;
    @Mock
    private MemberAssetDomainService memberAssetDomainService;

    @InjectMocks
    private MemberPurchaseRecordServiceImpl service;

    @Test
    void memberDomainHelpersShouldUseMemberAssetDomainRules() {
        MemberPurchaseRecord record = new MemberPurchaseRecord();
        record.setId(10L);
        record.setMemberLevelId(1L);
        MemberLevelPrivilegeVo privilegeVo = new MemberLevelPrivilegeVo();
        privilegeVo.setName("vip");
        OneselfMemberLevelPrivilege snapshot = new OneselfMemberLevelPrivilege();

        when(memberLevelPrivilegeMapper.selectVoList(any())).thenReturn(List.of(privilegeVo));
        when(memberAssetDomainService.prepareMemberPrivilegeSnapshot(any(), any())).thenReturn(List.of(snapshot));

        ReflectionTestUtils.invokeMethod(service, "validEntityBeforeSave", record);
        ReflectionTestUtils.invokeMethod(service, "createOneselfMemberInfo", record);

        verify(memberAssetDomainService).preparePurchaseRecordForCreate(any(MemberPurchaseRecord.class));
        verify(memberAssetDomainService).prepareMemberPrivilegeSnapshot(any(MemberPurchaseRecord.class), any());
        ArgumentCaptor<List<OneselfMemberLevelPrivilege>> captor = ArgumentCaptor.forClass(List.class);
        verify(oneselfMemberLevelPrivilegeMapper).insertBatch(captor.capture());
        assertEquals(1, captor.getValue().size());
        verify(baseMapper, never()).insert(any(MemberPurchaseRecord.class));
    }
}
