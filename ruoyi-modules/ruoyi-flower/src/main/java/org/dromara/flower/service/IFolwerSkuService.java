package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.domain.bo.FolwerSkuBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 单品SKUService接口
 *
 * @author mlhxj
 * @date 2024-12-26
 */
public interface IFolwerSkuService {

    /**
     * 查询单品SKU
     *
     * @param skuId 主键
     * @return 单品SKU
     */
    FolwerSkuVo queryById(Long skuId);

    /**
     * 分页查询单品SKU列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 单品SKU分页列表
     */
    TableDataInfo<FolwerSkuVo> queryPageList(FolwerSkuBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的单品SKU列表
     *
     * @param bo 查询条件
     * @return 单品SKU列表
     */
    List<FolwerSkuVo> queryList(FolwerSkuBo bo);


    /***
     * 根据商品ID查询单品SKU列表
     *
     * @param prodId
     * @return
     */
    List<FolwerSkuVo> queryListByProdId(long prodId);

    /**
     * 新增单品SKU
     *
     * @param bo 单品SKU
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerSkuBo bo);

    /**
     * 批量新增单品SKU
     *
     * @param bo 单品SKU
     * @return 是否新增成功
     */
    Boolean batchInsertByBo(List<FolwerSkuBo> bo);

    /**
     * 修改单品SKU
     *
     * @param bo 单品SKU
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerSkuBo bo);

    /**
     * 校验并批量删除单品SKU信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
