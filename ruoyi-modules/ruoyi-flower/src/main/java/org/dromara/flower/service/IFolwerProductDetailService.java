package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerProductDetailVo;
import org.dromara.flower.domain.bo.FolwerProductDetailBo;
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
public interface IFolwerProductDetailService {

    /**
     * 查询商品详情
     *
     * @param detailId 主键
     * @return 商品详情
     */
    FolwerProductDetailVo queryById(Long detailId);

    /**
     * 分页查询商品详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品详情分页列表
     */
    TableDataInfo<FolwerProductDetailVo> queryPageList(FolwerProductDetailBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的商品详情列表
     *
     * @param bo 查询条件
     * @return 商品详情列表
     */
    List<FolwerProductDetailVo> queryList(FolwerProductDetailBo bo);

    /**
     * 新增商品详情
     *
     * @param bo 商品详情
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerProductDetailBo bo);

    /**
     * 修改商品详情
     *
     * @param bo 商品详情
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerProductDetailBo bo);

    /**
     * 校验并批量删除商品详情信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
