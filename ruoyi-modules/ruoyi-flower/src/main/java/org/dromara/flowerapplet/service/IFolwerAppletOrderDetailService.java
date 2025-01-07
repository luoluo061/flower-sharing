package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDetailVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDetailBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单详细Service接口
 *
 * @author mlhxj
 * @date 2025-01-07
 */
public interface IFolwerAppletOrderDetailService {

    /**
     * 查询订单详细
     *
     * @param id 主键
     * @return 订单详细
     */
    FolwerAppletOrderDetailVo queryById(Long id);

    /**
     * 分页查询订单详细列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单详细分页列表
     */
    TableDataInfo<FolwerAppletOrderDetailVo> queryPageList(FolwerAppletOrderDetailBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单详细列表
     *
     * @param bo 查询条件
     * @return 订单详细列表
     */
    List<FolwerAppletOrderDetailVo> queryList(FolwerAppletOrderDetailBo bo);

    /**
     * 新增订单详细
     *
     * @param bo 订单详细
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletOrderDetailBo bo);

    /**
     * 修改订单详细
     *
     * @param bo 订单详细
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletOrderDetailBo bo);

    /**
     * 校验并批量删除订单详细信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
