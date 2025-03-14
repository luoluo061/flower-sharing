package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FlowerAppletUserAuthVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserAuthBo;
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
public interface IFlowerAppletUserAuthService {

    /**
     * 查询小程序用户信息认证
     *
     * @param authId 主键
     * @return 小程序用户信息认证
     */
    FlowerAppletUserAuthVo queryById(Long authId);

    /**
     * 分页查询小程序用户信息认证列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息认证分页列表
     */
    TableDataInfo<FlowerAppletUserAuthVo> queryPageList(FlowerAppletUserAuthBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的小程序用户信息认证列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息认证列表
     */
    List<FlowerAppletUserAuthVo> queryList(FlowerAppletUserAuthBo bo);

    /**
     * 新增小程序用户信息认证
     *
     * @param bo 小程序用户信息认证
     * @return 是否新增成功
     */
    Boolean insertByBo(FlowerAppletUserAuthBo bo);

    /**
     * 修改小程序用户信息认证
     *
     * @param bo 小程序用户信息认证
     * @return 是否修改成功
     */
    Boolean updateByBo(FlowerAppletUserAuthBo bo);

    /**
     * 校验并批量删除小程序用户信息认证信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
