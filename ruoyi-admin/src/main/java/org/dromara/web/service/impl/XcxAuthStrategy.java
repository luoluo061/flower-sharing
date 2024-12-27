package org.dromara.web.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjectUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.model.XcxLoginBody;
import org.dromara.common.core.domain.model.XcxLoginUser;
import org.dromara.common.core.enums.Status;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.mapper.MemberLevelMapper;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.web.domain.vo.LoginVo;
import org.dromara.web.domain.vo.XcxPhoneInfoVo;
import org.dromara.web.properties.InitialMemberLevelProperties;
import org.dromara.web.service.IAuthStrategy;
import org.dromara.web.service.SysLoginService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * 小程序认证策略
 *
 * @author Michelle.Chung
 */
@Slf4j
@Service("xcx" + IAuthStrategy.BASE_NAME)
@RequiredArgsConstructor
public class XcxAuthStrategy implements IAuthStrategy {

    private final SysLoginService loginService;

    private final IAppletUserInformationService appletUserInformationService;

    private final MemberLevelMapper memberLevelMapper;

    private final InitialMemberLevelProperties initialMemberLevelProperties;


    @Override
    public LoginVo login(String body, SysClientVo client) {
        XcxLoginBody loginBody = JsonUtils.parseObject(body, XcxLoginBody.class);
        ValidatorUtils.validate(loginBody);
        // xcxCode 为 小程序调用 wx.login 授权后获取
        String xcxCode = loginBody.getXcxCode();
        // 多个小程序识别使用
        String appid = loginBody.getAppid();

        //获取小程序
        String accessToken = loginService.getAccessToken();
        //获取手机号信息
        XcxPhoneInfoVo phoneInfo = loginService.getUserPhone(xcxCode, accessToken);
        //暂无code来使用，使用模拟数据
        /*XcxPhoneInfoVo phoneInfo = new XcxPhoneInfoVo();
        phoneInfo.setPhoneNumber("15912341234");*/
        //加载用户信息
        XcxLoginUser loginUser = loadUserByPhone(phoneInfo.getPhoneNumber());

        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        loginUser.setUserType(loginBody.getGrantType());
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
        loginVo.setPhone(phoneInfo.getPhoneNumber());
        return loginVo;
    }


    /*public LoginVo login(String body, SysClientVo client) {
        XcxLoginBody loginBody = JsonUtils.parseObject(body, XcxLoginBody.class);
        ValidatorUtils.validate(loginBody);
        // xcxCode 为 小程序调用 wx.login 授权后获取
        String xcxCode = loginBody.getXcxCode();
        // 多个小程序识别使用
        String appid = loginBody.getAppid();


        // 校验 appid + appsrcret + xcxCode 调用登录凭证校验接口 获取 session_key 与 openid
        String openid = "";
        // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
        SysUserVo user = loadUserByOpenid(openid);

        // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
        XcxLoginUser loginUser = new XcxLoginUser();
        loginUser.setTenantId(user.getTenantId());
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());
        loginUser.setNickname(user.getNickName());
        loginUser.setUserType(user.getUserType());
        loginUser.setClientKey(client.getClientKey());
        loginUser.setDeviceType(client.getDeviceType());
        loginUser.setOpenid(openid);

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
        loginVo.setOpenid(openid);
        return loginVo;
    }*/


    /**
     * 通过手机号加载用户信息
     * @param phone
     * @return
     */
    private XcxLoginUser loadUserByPhone(String phone) {
        // 通过手机号登录使用手机号作为唯一标识
        //先查询是否有该用户
        AppletUserInformationVo user = appletUserInformationService.getByPhone(phone);
        XcxLoginUser loginUser = new XcxLoginUser();
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在...准备插入用户信息", phone);
            AppletUserInformationBo bo = new AppletUserInformationBo();
            bo.setPhone(phone);
            bo.setUserType("xcx");
            bo.setMemberId(creaetMemberId());
            if (appletUserInformationService.insertByBo(bo)) {
                loginUser.setUserId(bo.getUserId());
                loginUser.setUserType(bo.getUserType());
                loginUser.setPhone(bo.getPhone());
            } else {
                throw new ServiceException("添加小程序用户失败");
            }
        } else if (Status.DISABLE.equals(user.getStatus())) {
            throw new ServiceException("登录用户：" + phone + "已被停用");
        } else {

            // 框架登录不限制从什么表查询 只要最终构建出 LoginUser 即可
            // 此处可根据登录用户的数据不同 自行创建 loginUser 属性不够用继承扩展就行了
            loginUser.setUserId(user.getUserId());
            loginUser.setUserType(user.getUserType());
            loginUser.setPhone(phone);
        }
        return loginUser;
    }

    /**
     * 创建会员编号
     */
    private String creaetMemberId() {
        // TODO 后期改为分布式锁生成
        Random random = new Random();
        int min = 1000000; // 最小7位数
        int max = 9999999; // 最大7位数
        int randomNumber = random.nextInt(max - min + 1) + min;
        return String.valueOf(randomNumber);
    }


}
