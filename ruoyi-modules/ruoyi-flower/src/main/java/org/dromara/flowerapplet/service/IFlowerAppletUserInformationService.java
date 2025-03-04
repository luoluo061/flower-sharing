package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FlowerAppletUserInformationVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserInformationBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 小程序用户信息Service接口
 *
 * @author mlhxj
 * @date 2025-03-03
 */
public interface IFlowerAppletUserInformationService {

    /**
     * 查询小程序用户信息
     *
     * @param userId 主键
     * @return 小程序用户信息
     */
    FlowerAppletUserInformationVo queryById(Long userId);

    /**
     * 分页查询小程序用户信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息分页列表
     */
    TableDataInfo<FlowerAppletUserInformationVo> queryPageList(FlowerAppletUserInformationBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的小程序用户信息列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息列表
     */
    List<FlowerAppletUserInformationVo> queryList(FlowerAppletUserInformationBo bo);

    /**
     * 新增小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否新增成功
     */
    Boolean insertByBo(FlowerAppletUserInformationBo bo);

    /**
     * 修改小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否修改成功
     */
    Boolean updateByBo(FlowerAppletUserInformationBo bo);

    /**
     * 校验并批量删除小程序用户信息信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
