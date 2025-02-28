package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderSetVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderSetBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单设置Service接口
 *
 * @author mlhxj
 * @date 2025-02-28
 */
public interface IFolwerAppletOrderSetService {

    /**
     * 查询订单设置
     *
     * @param id 主键
     * @return 订单设置
     */
    FolwerAppletOrderSetVo queryById(Long id);

    /**
     * 分页查询订单设置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单设置分页列表
     */
    TableDataInfo<FolwerAppletOrderSetVo> queryPageList(FolwerAppletOrderSetBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单设置列表
     *
     * @param bo 查询条件
     * @return 订单设置列表
     */
    List<FolwerAppletOrderSetVo> queryList(FolwerAppletOrderSetBo bo);

    /**
     * 新增订单设置
     *
     * @param bo 订单设置
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletOrderSetBo bo);

    /**
     * 修改订单设置
     *
     * @param bo 订单设置
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletOrderSetBo bo);

    /**
     * 校验并批量删除订单设置信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
