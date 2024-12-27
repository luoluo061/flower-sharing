package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerDeliveryVo;
import org.dromara.flower.domain.bo.FolwerDeliveryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 物流公司Service接口
 *
 * @author mlhxj
 * @date 2024-12-26
 */
public interface IFolwerDeliveryService {

    /**
     * 查询物流公司
     *
     * @param dvyId 主键
     * @return 物流公司
     */
    FolwerDeliveryVo queryById(Long dvyId);

    /**
     * 分页查询物流公司列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流公司分页列表
     */
    TableDataInfo<FolwerDeliveryVo> queryPageList(FolwerDeliveryBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的物流公司列表
     *
     * @param bo 查询条件
     * @return 物流公司列表
     */
    List<FolwerDeliveryVo> queryList(FolwerDeliveryBo bo);

    /**
     * 新增物流公司
     *
     * @param bo 物流公司
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerDeliveryBo bo);

    /**
     * 修改物流公司
     *
     * @param bo 物流公司
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerDeliveryBo bo);

    /**
     * 校验并批量删除物流公司信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
