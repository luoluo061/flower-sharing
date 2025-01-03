package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCategoryVo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 小程序端产品类目Service接口
 *
 * @author Lion Li
 * @date 2025-01-02
 */
public interface IFolwerAppletCategoryService {

    /**
     * 查询小程序端产品类目
     *
     * @param id 主键
     * @return 小程序端产品类目
     */
    FolwerAppletCategoryVo queryById(Long id);

    /**
     * 分页查询小程序端产品类目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序端产品类目分页列表
     */
    TableDataInfo<FolwerAppletCategoryVo> queryPageList(FolwerAppletCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的小程序端产品类目列表
     *
     * @param bo 查询条件
     * @return 小程序端产品类目列表
     */
    List<FolwerAppletCategoryVo> queryList(FolwerAppletCategoryBo bo);

    /**
     * 新增小程序端产品类目
     *
     * @param bo 小程序端产品类目
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletCategoryBo bo);

    /**
     * 修改小程序端产品类目
     *
     * @param bo 小程序端产品类目
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletCategoryBo bo);

    /**
     * 校验并批量删除小程序端产品类目信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
