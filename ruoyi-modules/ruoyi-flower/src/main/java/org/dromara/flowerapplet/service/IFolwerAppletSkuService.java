package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletProductColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuColorVo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletSkuVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletSkuBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 单品SKUService接口
 *
 * @author mlhxj
 * @date 2025-01-16
 */
public interface IFolwerAppletSkuService {

    /**
     * 查询单品SKU
     *
     * @param skuId 主键
     * @return 单品SKU
     */
    FolwerAppletSkuVo queryById(Long skuId);

    /**
     * 分页查询单品SKU列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 单品SKU分页列表
     */
    TableDataInfo<FolwerAppletSkuVo> queryPageList(FolwerAppletSkuBo bo, PageQuery pageQuery);

    /**
     * 根据颜色查询小程序端商品管理
     *
     * @param bo 查询条件
     * @return 小程序端商品管理
     */
    List<FolwerAppletSkuColorVo> queryByColor(FolwerAppletSkuBo bo);

    /**
     * 根据等级查询小程序端商品管理
     *
     * @param bo 查询条件
     * @return 小程序端商品管理
     */
    List<FolwerAppletSkuColorVo> queryByLevel(FolwerAppletSkuBo bo);

    /**
     * 查询符合条件的单品SKU列表
     *
     * @param bo 查询条件
     * @return 单品SKU列表
     */
    List<FolwerAppletSkuVo> queryList(FolwerAppletSkuBo bo);

    /**
     * 新增单品SKU
     *
     * @param bo 单品SKU
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletSkuBo bo);

    /**
     * 修改单品SKU
     *
     * @param bo 单品SKU
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletSkuBo bo);

    /**
     * 校验并批量删除单品SKU信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
