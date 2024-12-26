package org.dromara.system.platform.service;


import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.system.platform.domain.bo.AppletUserInformationBo;
import org.dromara.system.platform.domain.vo.AppletUserInformationVo;

import java.util.Collection;
import java.util.List;

/**
 * 小程序用户信息Service接口
 *
 * @author mlhxj
 * @date 2024-12-25
 */
public interface IAppletUserInformationService {

    /**
     * 查询小程序用户信息
     *
     * @param userId 主键
     * @return 小程序用户信息
     */
    AppletUserInformationVo queryById(Long userId);

    /**
     * 分页查询小程序用户信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息分页列表
     */
    TableDataInfo<AppletUserInformationVo> queryPageList(AppletUserInformationBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的小程序用户信息列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息列表
     */
    List<AppletUserInformationVo> queryList(AppletUserInformationBo bo);

    /**
     * 新增小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否新增成功
     */
    Boolean insertByBo(AppletUserInformationBo bo);

    /**
     * 修改小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否修改成功
     */
    Boolean updateByBo(AppletUserInformationBo bo);

    /**
     * 校验并批量删除小程序用户信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 通过手机获取用户
     * @param phone
     * @return
     */
    AppletUserInformationVo getByPhone(String phone);

    /**
     * 通过openid获取用户
     * @param openId
     * @return
     */
    AppletUserInformationVo getByOpenId(String openId);

    /**
     * 更新用户的状态
     * @param id
     * @param status
     * @return
     */
    boolean updateStatus(Long id, String status);

    Boolean updatePointsGoldByBo(AppletUserInformationBo bo);
}
