package org.dromara.web.service.impl;

import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.lock.executor.RedissonLockExecutor;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import javassist.expr.NewArray;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.UserConstants;
import org.dromara.common.core.domain.model.XcxLoginBody;
import org.dromara.common.core.domain.model.XcxLoginUser;
import org.dromara.common.core.enums.Status;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.CodeUtils;
import org.dromara.common.core.utils.DateUtils;
import org.dromara.common.core.utils.StreamUtils;
import org.dromara.common.core.utils.ValidatorUtils;
import org.dromara.common.json.utils.JsonUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.constant.LockKeyString;
import org.dromara.flower.domain.CoursesManager;
import org.dromara.flower.domain.MarketingMemberPromotionPecord;
import org.dromara.flower.mapper.MarketingMemberPromotionPecordMapper;
import org.dromara.flower.mapper.MemberLevelMapper;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.system.domain.SysRole;
import org.dromara.system.domain.SysUserRole;
import org.dromara.system.domain.bo.SysUserBo;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.system.domain.vo.SysRoleVo;
import org.dromara.system.mapper.SysRoleMapper;
import org.dromara.system.mapper.SysUserPostMapper;
import org.dromara.system.mapper.SysUserRoleMapper;
import org.dromara.system.service.ISysPermissionService;
import org.dromara.system.service.impl.SysUserServiceImpl;
import org.dromara.web.domain.vo.LoginVo;
import org.dromara.web.domain.vo.XcxPhoneInfoVo;
import org.dromara.web.properties.InitialMemberLevelProperties;
import org.dromara.web.service.IAuthStrategy;
import org.dromara.web.service.SysLoginService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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

    private final InitialMemberLevelProperties initialMemberLevelProperties;

    private final AppletUserInformationMapper appletUserInformationMapper;
    private final SysRoleMapper roleMapper;
    private final SysUserRoleMapper userRoleMapper;
    private final LockTemplate lockTemplate;
    private final MarketingMemberPromotionPecordMapper memberPromotionPecordMapper;
    private final ISysPermissionService permissionService;

    private final static Long ZERO = 0L;

    @Override
    @Transactional
    public LoginVo login(String body, SysClientVo client) {
        XcxLoginBody loginBody = JsonUtils.parseObject(body, XcxLoginBody.class);
        ValidatorUtils.validate(loginBody);
        // xcxCode 为 小程序调用 wx.login 授权后获取
        String xcxCode = loginBody.getXcxCode();
        // 多个小程序识别使用
        String appid = loginBody.getAppid();

        //获取小程序
//        String accessToken = loginService.getAccessToken();
        //获取手机号信息
//        XcxPhoneInfoVo phoneInfo = loginService.getUserPhone(xcxCode, accessToken);
        XcxPhoneInfoVo phoneInfo = new XcxPhoneInfoVo();
        phoneInfo.setPhoneNumber("15912341234");
        //暂无code来使用，使用模拟数据
        /*XcxPhoneInfoVo phoneInfo = new XcxPhoneInfoVo();
        phoneInfo.setPhoneNumber("15912341234");*/
        //加载用户信息
        XcxLoginUser loginUser = loadUserByPhone(phoneInfo.getPhoneNumber(), loginBody);

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
     *
     * @param phone
     * @return
     */

    private XcxLoginUser loadUserByPhone(String phone, XcxLoginBody loginBody) {
        // 通过手机号登录使用手机号作为唯一标识
        //先查询是否有该用户
        AppletUserInformationVo user = appletUserInformationService.getByPhone(phone);
        XcxLoginUser loginUser = new XcxLoginUser();
        if (ObjectUtil.isNull(user)) {
            log.info("登录用户：{} 不存在...准备插入用户信息", phone);
            AppletUserInformationBo aib = new AppletUserInformationBo();
            aib.setPhone(phone);
            aib.setUserType("xcx");
            aib.setMemberId(createMemberId());
            aib.setParentId(loginBody.getParentId() != null ? loginBody.getParentId() : ZERO);
            aib.setMemberLevelId(Long.parseLong(initialMemberLevelProperties.getInitialId()));
            aib.setOpenid(loginBody.getOpenid());
            // 后期更换
            aib.setCreateBy(1L);
            aib.setCreateDept(1877911738916859905L);
            aib.setDeptId(1877911738916859905L);
            if (appletUserInformationService.insertByBo(aib)) {
                loginUser.setUserId(aib.getUserId());
                loginUser.setUserType(aib.getUserType());
                loginUser.setPhone(aib.getPhone());
                // 后期修改为数据库查询或其他的地方获取
                loginUser.setTenantId("000000");
            } else {
                throw new ServiceException("添加小程序用户失败");
            }
            // 设置小程序用户 新增用户角色信息
            insertUserRole(aib.getUserId(),new Long[]{1871386666300637186L},true);
            if (loginBody.getParentId() != null && !ZERO.equals(loginBody.getParentId())){
                insertMarketingMemberPromotionPecord(aib,loginBody);
            }
        } else if (Status.DISABLE.equals(user.getStatus())) {
            throw new ServiceException("登录用户：" + phone + "已被停用");
        } else {

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
            AppletUserInformationBo bo = new AppletUserInformationBo();
            bo.setOpenid(loginBody.getOpenid());
            bo.setUserId(user.getUserId());
            appletUserInformationService.updateByBo(bo);
        }
        // 获取用户其它信息

        return loginUser;
    }

    /**
     * 保存推广攻记录
     * @param aib 新增小程序用户
     * @param loginBody 用户登陆信息
     */
    private void insertMarketingMemberPromotionPecord(AppletUserInformationBo aib, XcxLoginBody loginBody) {
        MarketingMemberPromotionPecord mmpp = new MarketingMemberPromotionPecord();
        AppletUserInformationVo app = appletUserInformationMapper.selectVoById(loginBody.getParentId());
        mmpp.setMemberId(app.getMemberId());
        // TODO 后期看如何生成编号
        mmpp.setPromotionId(IdUtil.fastSimpleUUID());
        mmpp.setMemberName(app.getName());
        mmpp.setPromotedPersonId(aib.getUserId());
        mmpp.setPromotedPersonName(aib.getName());
        mmpp.setPromotedPersonStatus(0L);
        mmpp.setPromotedPersonLevel(initialMemberLevelProperties.getInitialId());
        Date date =new Date();
        mmpp.setCreatedAt(date);
        mmpp.setCreateTime(date);
        boolean flag = memberPromotionPecordMapper.insert(mmpp)>0;
        if (!flag){
            throw new RuntimeException("请重新识别推广二维码!");
        }
    }

    /**
     * 创建会员编号
     *
     * @return 会员编号
     */
    private String createMemberId() {
        // 后期看是否需要调整 26670497793
        String coursesCode = "";
        final LockInfo lockInfo = lockTemplate.lock(LockKeyString.COURSES_CODE_LOCK_KEY, 30000L, 5000L, RedissonLockExecutor.class);
        if (null == lockInfo) {
            throw new RuntimeException("业务处理中,请稍后再试");
        }
        // 获取锁成功，处理业务
        try {
            try {
                QueryWrapper<AppletUserInformation> wrapper = new QueryWrapper<>();
                wrapper.orderByDesc("create_time");
                wrapper.last("limit 1");
                AppletUserInformation aui = appletUserInformationMapper.selectOne(wrapper);
                if (aui != null) {
                    // 获取 memberId
                    String memberId = aui.getMemberId();

                    // 将 memberId 转换为整数并加 1
                    Long memberIdInt = Long.parseLong(memberId);
                    Long newMemberIdInt = memberIdInt + 1;

                    // 将新的 memberId 转换回字符串
                    coursesCode = String.valueOf(newMemberIdInt);
                } else {
                    // 如果没有查询到记录，处理这种情况
                    coursesCode = "26670497794";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("执行简单方法1 , 当前线程:" + Thread.currentThread().getName());
        } finally {
            //释放锁
            lockTemplate.releaseLock(lockInfo);
        }
        //结束
        return coursesCode;
    }


    /**
     * 新增用户角色信息
     *
     * @param userId  用户ID
     * @param roleIds 角色组
     * @param clear   清除已存在的关联数据
     */
    private void insertUserRole(Long userId, Long[] roleIds, boolean clear) {
        // 小程序角色组 ID 目前写死 1871386666300637186
        roleIds = new Long[]{Long.parseLong(initialMemberLevelProperties.getAppletRoleId())};
        if (ArrayUtil.isNotEmpty(roleIds)) {
            List<Long> roleList = new ArrayList<>(List.of(roleIds));
            if (!LoginHelper.isSuperAdmin(userId)) {
                roleList.remove(UserConstants.SUPER_ADMIN_ID);
            }
            if (clear) {
                // 删除用户与角色关联
                userRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
            }
            // 新增用户与角色管理
            List<SysUserRole> list = StreamUtils.toList(roleList, roleId -> {
                SysUserRole ur = new SysUserRole();
                ur.setUserId(userId);
                ur.setRoleId(roleId);
                return ur;
            });
            userRoleMapper.insertBatch(list);
        }
    }
}
