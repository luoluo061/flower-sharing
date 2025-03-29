package org.dromara.flower.service;

import org.dromara.common.core.domain.R;
import org.dromara.flower.domain.vo.FolwerDeliveryBoxVo;
import org.dromara.flower.domain.bo.FolwerDeliveryBoxBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;

import java.util.Collection;
import java.util.List;

/**
 * 物流箱型Service接口
 *
 * @author mlhxj
 * @date 2025-03-29
 */
public interface IFolwerDeliveryBoxService {

    /**
     * 查询物流箱型
     *
     * @param boxId 主键
     * @return 物流箱型
     */
    FolwerDeliveryBoxVo queryById(Long boxId);

    /**
     * 分页查询物流箱型列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 物流箱型分页列表
     */
    TableDataInfo<FolwerDeliveryBoxVo> queryPageList(FolwerDeliveryBoxBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的物流箱型列表
     *
     * @param bo 查询条件
     * @return 物流箱型列表
     */
    List<FolwerDeliveryBoxVo> queryList(FolwerDeliveryBoxBo bo);

    /**
     * 新增物流箱型
     *
     * @param bo 物流箱型
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerDeliveryBoxBo bo);

    /**
     * 修改物流箱型
     *
     * @param bo 物流箱型
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerDeliveryBoxBo bo);

    /**
     * 校验并批量删除物流箱型信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    R<List<FolwerSkuVo>> deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
