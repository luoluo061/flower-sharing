package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 产品类目Service接口
 *
 * @author Lion Li
 * @date 2024-12-20
 */
public interface IFolwerCategoryService {

    /**
     * 查询产品类目
     *
     * @param id 主键
     * @return 产品类目
     */
    FolwerCategoryVo queryById(Long id);

    /**
     * 分页查询产品类目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 产品类目分页列表
     */
    TableDataInfo<FolwerCategoryVo> queryPageList(FolwerCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的产品类目列表
     *
     * @param bo 查询条件
     * @return 产品类目列表
     */
    List<FolwerCategoryVo> queryList(FolwerCategoryBo bo);

    /**
     * 新增产品类目
     *
     * @param bo 产品类目
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerCategoryBo bo);

    /**
     * 修改产品类目
     *
     * @param bo 产品类目
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerCategoryBo bo);

    /**
     * 校验并批量删除产品类目信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
