package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FlowerAppletUserAuthlogVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserAuthlogBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 小程序用户信息认证记录Service接口
 *
 * @author mlhxj
 * @date 2025-03-14
 */
public interface IFlowerAppletUserAuthlogService {

    /**
     * 查询小程序用户信息认证记录
     *
     * @param authlogId 主键
     * @return 小程序用户信息认证记录
     */
    FlowerAppletUserAuthlogVo queryById(Long authlogId);

    /**
     * 分页查询小程序用户信息认证记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息认证记录分页列表
     */
    TableDataInfo<FlowerAppletUserAuthlogVo> queryPageList(FlowerAppletUserAuthlogBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的小程序用户信息认证记录列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息认证记录列表
     */
    List<FlowerAppletUserAuthlogVo> queryList(FlowerAppletUserAuthlogBo bo);

    /**
     * 新增小程序用户信息认证记录
     *
     * @param bo 小程序用户信息认证记录
     * @return 是否新增成功
     */
    Boolean insertByBo(FlowerAppletUserAuthlogBo bo);

    /**
     * 修改小程序用户信息认证记录
     *
     * @param bo 小程序用户信息认证记录
     * @return 是否修改成功
     */
    Boolean updateByBo(FlowerAppletUserAuthlogBo bo);

    /**
     * 校验并批量删除小程序用户信息认证记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
