package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.FolwerAppletProduct;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 小程序端商品管理Service接口
 *
 * @author LL
 * @date 2024-12-31
 */
public interface IFolwerAppletProductService {

    /**
     * 查询小程序端商品管理
     *
     * @param id 主键
     * @return 小程序端商品管理
     */
    FolwerAppletProductVo queryById(Long id);

    /**
     * 查询大分类下所有商品
     *
     * @param categoryId 主键
     * @return 小程序端商品管理
     */
    List<FolwerAppletProductVo> queryAllBycategoryId(Long categoryId, int pageNum, int pageSize);

    /**
     * 根据颜色查询小程序端商品管理
     *
     * @param bo 查询条件
     * @return 小程序端商品管理
     */
    List<FolwerAppletProductColorVo> queryByColor(FolwerAppletProductBo bo);

    /**
     * 根据等级查询小程序端商品管理
     *
     * @param bo 查询条件
     * @return 小程序端商品管理
     */
    List<FolwerAppletProductColorVo> queryByLevel(FolwerAppletProductBo bo);

    /**
     * 根据销量查询小程序端商品管理
     *
     * @param bo 查询条件
     * @return 小程序端商品管理
     */
    List<FolwerAppletProductColorVo> queryBySoldNum(FolwerAppletProductBo bo);

    /**
     * 分页查询小程序端商品管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序端商品管理分页列表
     */
    TableDataInfo<FolwerAppletProductVo> queryPageList(FolwerAppletProductBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的小程序端商品管理列表
     *
     * @param bo 查询条件
     * @return 小程序端商品管理列表
     */
    List<FolwerAppletProductVo> queryList(FolwerAppletProductBo bo);

    /**
     * 新增小程序端商品管理
     *
     * @param bo 小程序端商品管理
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletProductBo bo);

    /**
     * 修改小程序端商品管理
     *
     * @param bo 小程序端商品管理
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletProductBo bo);

    /**
     * 校验并批量删除小程序端商品管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
