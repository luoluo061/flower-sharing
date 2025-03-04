package org.dromara.web.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.model.XcxLoginUser;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.system.service.ISysPermissionService;
import org.dromara.web.domain.vo.LoginVo;
import org.dromara.web.service.AppletLogin;
import org.dromara.web.service.IAuthStrategy;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppletLoginImpl implements AppletLogin {

    private final IAppletUserInformationService appletUserInformationService;

    private final ISysPermissionService permissionService;

    @Override
    public LoginVo getLoginVo(String phone, SysClientVo client) {
        XcxLoginUser loginUser = getLoginUser(phone);
        LoginVo loginVo = setLoginVo(loginUser, client);
        return loginVo;
    }

    public XcxLoginUser getLoginUser(String phone) {
        AppletUserInformationVo user = appletUserInformationService.getByPhone(phone);
        XcxLoginUser loginUser = new XcxLoginUser();
        if (user != null){
            // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
            // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
            loginUser.setUserId(user.getUserId());
            loginUser.setUserType(user.getUserType());
            loginUser.setPhone(phone);
            loginUser.setTenantId(user.getTenantId());
            loginUser.setDeptId(user.getDeptId());
            loginUser.setUsername(user.getName());
            loginUser.setNickname(user.getNickName());
            loginUser.setMenuPermission(permissionService.getMenuPermission(user.getUserId()));
            loginUser.setRolePermission(permissionService.getRolePermission(user.getUserId()));
        }
        return loginUser;
    }

    public LoginVo setLoginVo(XcxLoginUser loginUser, SysClientVo client) {
        SaLoginModel model = new SaLoginModel();
        model.setDevice(client.getDeviceType());
        // 自定义分配 不同用户体系 不同 token 授权时间 不设置默认走全局 yml 配置
        // 例如: 后台用户30分钟过期 app用户1天过期
        model.setTimeout(client.getTimeout());
        model.setActiveTimeout(client.getActiveTimeout());
        model.setExtra(LoginHelper.CLIENT_KEY, client.getClientId());
        // 生成token
        LoginHelper.login(loginUser, model);

        LoginVo loginVo = new LoginVo();
        loginVo.setAccessToken(StpUtil.getTokenValue());
        loginVo.setExpireIn(StpUtil.getTokenTimeout());
        loginVo.setClientId(client.getClientId());
        loginVo.setPhone(loginUser.getPhone());
        return loginVo;
    }
}
