package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerOrderDvyVo;
import org.dromara.flower.domain.bo.FolwerOrderDvyBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单物流Service接口
 *
 * @author mlhxj
 * @date 2025-09-28
 */
public interface IFolwerOrderDvyService {

    /**
     * 查询订单物流
     *
     * @param orderDevId 主键
     * @return 订单物流
     */
    FolwerOrderDvyVo queryById(Long orderDevId);

    /**
     * 分页查询订单物流列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单物流分页列表
     */
    TableDataInfo<FolwerOrderDvyVo> queryPageList(FolwerOrderDvyBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单物流列表
     *
     * @param bo 查询条件
     * @return 订单物流列表
     */
    List<FolwerOrderDvyVo> queryList(FolwerOrderDvyBo bo);

    /**
     * 新增订单物流
     *
     * @param bo 订单物流
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerOrderDvyBo bo);

    /**
     * 修改订单物流
     *
     * @param bo 订单物流
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerOrderDvyBo bo);

    /**
     * 校验并批量删除订单物流信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
