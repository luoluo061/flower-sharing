package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditSetVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditSetBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 积分配置Service接口
 *
 * @author mlhxj
 * @date 2025-01-15
 */
public interface IFolwerAppletCreditSetService {

    /**
     * 查询积分配置
     *
     * @param id 主键
     * @return 积分配置
     */
    FolwerAppletCreditSetVo queryById(Long id);

    /**
     * 分页查询积分配置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分配置分页列表
     */
    TableDataInfo<FolwerAppletCreditSetVo> queryPageList(FolwerAppletCreditSetBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的积分配置列表
     *
     * @param bo 查询条件
     * @return 积分配置列表
     */
    List<FolwerAppletCreditSetVo> queryList(FolwerAppletCreditSetBo bo);

    /**
     * 新增积分配置
     *
     * @param bo 积分配置
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletCreditSetBo bo);

    /**
     * 修改积分配置
     *
     * @param bo 积分配置
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletCreditSetBo bo);

    /**
     * 校验并批量删除积分配置信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
