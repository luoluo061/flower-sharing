package org.dromara.flower.service;

import org.dromara.flower.domain.vo.AppletUserAuthVo;
import org.dromara.flower.domain.bo.AppletUserAuthBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 小程序用户信息认证Service接口
 *
 * @author mlhxj
 * @date 2025-03-14
 */
public interface IAppletUserAuthService {

    /**
     * 查询小程序用户信息认证
     *
     * @param authId 主键
     * @return 小程序用户信息认证
     */
    AppletUserAuthVo queryById(Long authId);

    /**
     * 分页查询小程序用户信息认证列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息认证分页列表
     */
    TableDataInfo<AppletUserAuthVo> queryPageList(AppletUserAuthBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的小程序用户信息认证列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息认证列表
     */
    List<AppletUserAuthVo> queryList(AppletUserAuthBo bo);

    /**
     * 新增小程序用户信息认证
     *
     * @param bo 小程序用户信息认证
     * @return 是否新增成功
     */
    Boolean insertByBo(AppletUserAuthBo bo);

    /**
     * 修改小程序用户信息认证
     *
     * @param bo 小程序用户信息认证
     * @return 是否修改成功
     */
    Boolean updateByBo(AppletUserAuthBo bo);

    /**
     * 校验并批量删除小程序用户信息认证信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
