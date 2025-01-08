package org.dromara.flowerapplet.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.bo.FolwerPickAddrBo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;

import java.util.Collection;
import java.util.List;

/**
 * 用户配送地址Service接口
 *
 * @author mlhxj
 * @date 2024-12-25
 */
public interface IFolwerAppletPickAddrService {

    /**
     * 查询用户配送地址
     *
     * @param addrId 主键
     * @return 用户配送地址
     */
    FolwerPickAddrVo queryById(Long addrId);

    /**
     * 分页查询用户配送地址列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户配送地址分页列表
     */
    TableDataInfo<FolwerPickAddrVo> queryPageList(FolwerPickAddrBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的用户配送地址列表
     *
     * @param bo 查询条件
     * @return 用户配送地址列表
     */
    List<FolwerPickAddrVo> queryList(FolwerPickAddrBo bo);

    /**
     * 新增用户配送地址
     *
     * @param bo 用户配送地址
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerPickAddrBo bo);

    /**
     * 修改用户配送地址
     *
     * @param bo 用户配送地址
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerPickAddrBo bo);

    /**
     * 校验并批量删除用户配送地址信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
