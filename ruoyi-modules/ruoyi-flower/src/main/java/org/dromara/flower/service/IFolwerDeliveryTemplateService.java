package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerDeliveryTemplateVo;
import org.dromara.flower.domain.bo.FolwerDeliveryTemplateBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 运费模板Service接口
 *
 * @author mlhxj
 * @date 2025-04-02
 */
public interface IFolwerDeliveryTemplateService {

    /**
     * 查询运费模板
     *
     * @param tempId 主键
     * @return 运费模板
     */
    FolwerDeliveryTemplateVo queryById(Long tempId);

    /**
     * 分页查询运费模板列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 运费模板分页列表
     */
    TableDataInfo<FolwerDeliveryTemplateVo> queryPageList(FolwerDeliveryTemplateBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的运费模板列表
     *
     * @param bo 查询条件
     * @return 运费模板列表
     */
    List<FolwerDeliveryTemplateVo> queryList(FolwerDeliveryTemplateBo bo);

    /**
     * 查询符合条件的运费模板子集列表
     *
     * @param bo 查询条件
     * @return 运费模板列表
     */
    List<FolwerDeliveryTemplateVo> queryChildrenList(FolwerDeliveryTemplateBo bo);

    /**
     * 新增运费模板List
     *
     * @param bos 运费模板
     * @return 是否新增成功
     */
    Boolean insertByBos(List<FolwerDeliveryTemplateBo> bos);

    /**
     * 新增运费模板
     *
     * @param bo 运费模板
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerDeliveryTemplateBo bo);

    /**
     * 修改运费模板
     *
     * @param bos 运费模板
     * @return 是否修改成功
     */
    Boolean updateByBo(List<FolwerDeliveryTemplateBo> bos);

    /**
     * 校验并批量删除运费模板信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
