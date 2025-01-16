package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditProductVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditProductBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 积分商品管理Service接口
 *
 * @author mlhxj
 * @date 2025-01-15
 */
public interface IFolwerAppletCreditProductService {

    /**
     * 查询积分商品管理
     *
     * @param id 主键
     * @return 积分商品管理
     */
    FolwerAppletCreditProductVo queryById(Long id);

    /**
     * 分页查询积分商品管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分商品管理分页列表
     */
    TableDataInfo<FolwerAppletCreditProductVo> queryPageList(FolwerAppletCreditProductBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的积分商品管理列表
     *
     * @param bo 查询条件
     * @return 积分商品管理列表
     */
    List<FolwerAppletCreditProductVo> queryList(FolwerAppletCreditProductBo bo);

    /**
     * 新增积分商品管理
     *
     * @param bo 积分商品管理
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletCreditProductBo bo);

    /**
     * 修改积分商品管理
     *
     * @param bo 积分商品管理
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletCreditProductBo bo);

    /**
     * 校验并批量删除积分商品管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
