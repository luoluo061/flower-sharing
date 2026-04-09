package org.dromara.flower.service.impl;

import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.MemberPointsExchangeGold;
import org.dromara.flower.mapper.MemberPointsExchangeGoldMapper;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.service.domain.PointsAssetDomainService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Tag("dev")
class MemberPointsExchangeGoldServiceImplTest {

    @Mock
    private MemberPointsExchangeGoldMapper baseMapper;
    @Mock
    private AppletUserInformationMapper appletUserInformationMapper;
    @Mock
    private PointsAssetDomainService pointsAssetDomainService;

    @InjectMocks
    private MemberPointsExchangeGoldServiceImpl service;

    @Test
    void exchangeGoldShouldUsePointsAssetDomainRules() {
        LoginUser loginUser = new LoginUser();
        loginUser.setUsername("tester");
        AppletUserInformation user = new AppletUserInformation();
        user.setUserId(1L);
        user.setPoints(20L);
        user.setGold(5L);
        MemberPointsExchangeGold record = new MemberPointsExchangeGold();
        AppletUserInformationBo bo = new AppletUserInformationBo();
        bo.setUserId(1L);
        bo.setModifiedValue(10L);
        bo.setPoints(10L);

        when(appletUserInformationMapper.selectById(1L)).thenReturn(user);
        when(pointsAssetDomainService.resolveExchangePoints(20L, 10L)).thenReturn(10L);
        when(pointsAssetDomainService.applyPointsExchange(user, 10L)).thenReturn(user);
        when(pointsAssetDomainService.preparePointsExchangeRecord(10L, 10L, "tester")).thenReturn(record);
        when(baseMapper.insert(record)).thenReturn(1);

        try (MockedStatic<LoginHelper> mockedLogin = mockStatic(LoginHelper.class)) {
            mockedLogin.when(LoginHelper::getLoginUser).thenReturn(loginUser);
            assertTrue(service.exchangeGoldByBo(bo));
        }

        verify(pointsAssetDomainService).resolveExchangePoints(20L, 10L);
        verify(pointsAssetDomainService).applyPointsExchange(user, 10L);
        verify(pointsAssetDomainService).preparePointsExchangeRecord(10L, 10L, "tester");
    }
}
