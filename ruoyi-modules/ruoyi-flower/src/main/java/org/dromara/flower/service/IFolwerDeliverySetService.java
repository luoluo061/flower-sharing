package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerDeliverySetVo;
import org.dromara.flower.domain.bo.FolwerDeliverySetBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 物流设置Service接口
 *
 * @author mlhxj
 * @date 2025-08-01
 */
public interface IFolwerDeliverySetService {

    /**
     * 查询物流设置
     *
     * @param deliverySetId 主键
     * @return 物流设置
     */
    FolwerDeliverySetVo queryById(Long deliverySetId);

    /**
     * 分页查询物流设置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流设置分页列表
     */
    TableDataInfo<FolwerDeliverySetVo> queryPageList(FolwerDeliverySetBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的物流设置列表
     *
     * @param bo 查询条件
     * @return 物流设置列表
     */
    List<FolwerDeliverySetVo> queryList(FolwerDeliverySetBo bo);

    /**
     * 新增物流设置
     *
     * @param bo 物流设置
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerDeliverySetBo bo);

    /**
     * 修改物流设置
     *
     * @param bo 物流设置
     * @return 是否修改成功
     */
    Boolean updateByBo(List<FolwerDeliverySetBo> bos);

    /**
     * 校验并批量删除物流设置信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
