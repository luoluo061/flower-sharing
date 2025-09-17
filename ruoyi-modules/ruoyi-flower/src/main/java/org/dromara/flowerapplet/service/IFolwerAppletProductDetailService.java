package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletProductDetailVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductDetailBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 商品详情Service接口
 *
 * @author mlhxj
 * @date 2025-08-11
 */
public interface IFolwerAppletProductDetailService {

    /**
     * 查询商品详情
     *
     * @param detailId 主键
     * @return 商品详情
     */
    FolwerAppletProductDetailVo queryById(Long detailId);

    /**
     * 查询商品详情
     *
     * @param productId 主键
     * @return 商品详情
     */
    FolwerAppletProductDetailVo queryByProductId(Long productId);

    /**
     * 分页查询商品详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品详情分页列表
     */
    TableDataInfo<FolwerAppletProductDetailVo> queryPageList(FolwerAppletProductDetailBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的商品详情列表
     *
     * @param bo 查询条件
     * @return 商品详情列表
     */
    List<FolwerAppletProductDetailVo> queryList(FolwerAppletProductDetailBo bo);

    /**
     * 新增商品详情
     *
     * @param bo 商品详情
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletProductDetailBo bo);

    /**
     * 修改商品详情
     *
     * @param bo 商品详情
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletProductDetailBo bo);

    /**
     * 校验并批量删除商品详情信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
