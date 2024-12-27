package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerCreditProductVo;
import org.dromara.flower.domain.bo.FolwerCreditProductBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 积分商品管理Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface IFolwerCreditProductService {

    /**
     * 查询积分商品管理
     *
     * @param id 主键
     * @return 积分商品管理
     */
    FolwerCreditProductVo queryById(Long id);

    /**
     * 分页查询积分商品管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分商品管理分页列表
     */
    TableDataInfo<FolwerCreditProductVo> queryPageList(FolwerCreditProductBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的积分商品管理列表
     *
     * @param bo 查询条件
     * @return 积分商品管理列表
     */
    List<FolwerCreditProductVo> queryList(FolwerCreditProductBo bo);

    /**
     * 新增积分商品管理
     *
     * @param bo 积分商品管理
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerCreditProductBo bo);

    /**
     * 修改积分商品管理
     *
     * @param bo 积分商品管理
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerCreditProductBo bo);

    /**
     * 校验并批量删除积分商品管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 批量修改积分商品状态
     *
     * @param ids     待修改的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean updateStatusByIds(Collection<Long> ids, Boolean isValid);
}
