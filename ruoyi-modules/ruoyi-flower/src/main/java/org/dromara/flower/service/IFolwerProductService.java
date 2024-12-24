package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 商品管理Service接口
 *
 * @author Lion Li
 * @date 2024-12-20
 */
public interface IFolwerProductService {

    /**
     * 查询商品管理
     *
     * @param id 主键
     * @return 商品管理
     */
    FolwerProductVo queryById(Long id);

    /**
     * 分页查询商品管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品管理分页列表
     */
    TableDataInfo<FolwerProductVo> queryPageList(FolwerProductBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的商品管理列表
     *
     * @param bo 查询条件
     * @return 商品管理列表
     */
    List<FolwerProductVo> queryList(FolwerProductBo bo);

    /**
     * 新增商品管理
     *
     * @param bo 商品管理
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerProductBo bo);

    /**
     * 修改商品管理
     *
     * @param bo 商品管理
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerProductBo bo);

    /**
     * 校验并批量删除商品管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 校验并批量修改商品状态
     *
     * @param ids     待修改的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否修改成功
     */
    Boolean updateStatusByIds(Collection<Long> ids, Boolean isValid);
}
