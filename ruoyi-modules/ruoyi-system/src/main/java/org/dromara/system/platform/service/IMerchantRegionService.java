package org.dromara.system.platform.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.system.platform.domain.bo.MerchantRegionBo;
import org.dromara.system.platform.domain.vo.MerchantRegionVo;


import java.util.Collection;
import java.util.List;

/**
 * 行政区划代码Service接口
 *
 * @author LionLi
 * @date 2024-11-11
 */
public interface IMerchantRegionService {

    /**
     * 查询行政区划代码
     *
     * @param id 主键
     * @return 行政区划代码
     */
    MerchantRegionVo queryById(Long id);

    /**
     * 分页查询行政区划代码列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 行政区划代码分页列表
     */
    TableDataInfo<MerchantRegionVo> queryPageList(MerchantRegionBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的行政区划代码列表
     *
     * @param bo 查询条件
     * @return 行政区划代码列表
     */
    List<MerchantRegionVo> queryList(MerchantRegionBo bo);

    /**
     * 新增行政区划代码
     *
     * @param bo 行政区划代码
     * @return 是否新增成功
     */
    Boolean insertByBo(MerchantRegionBo bo);

    /**
     * 修改行政区划代码
     *
     * @param bo 行政区划代码
     * @return 是否修改成功
     */
    Boolean updateByBo(MerchantRegionBo bo);

    /**
     * 校验并批量删除行政区划代码信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    List<MerchantRegionVo> getRegionList(Long id);
}
