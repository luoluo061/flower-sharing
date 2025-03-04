package org.dromara.web.service;

import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.SpringUtils;
import org.dromara.system.domain.vo.SysClientVo;
import org.dromara.web.domain.vo.LoginVo;

public interface AppletLogin {

    LoginVo getLoginVo(String phone, SysClientVo client);
}
