package org.dromara.web.service;

import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.web.domain.vo.LoginVo;

public interface AppletLogin {

    /***
     * 获取登录信息 获取用户信息
     * @param phone
     * @param client
     * @return
     */
    LoginVo getLoginVo(String phone, SysClientVo client);
}
