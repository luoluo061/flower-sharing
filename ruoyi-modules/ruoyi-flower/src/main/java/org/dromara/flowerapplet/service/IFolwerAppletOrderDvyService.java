package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderDvyVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderDvyBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
 * 订单物流Service接口
 *
 * @author mlhxj
 * @date 2025-09-02
 */
public interface IFolwerAppletOrderDvyService {

    /**
     * 查询订单物流
     *
     * @param orderDevId 主键
     * @return 订单物流
     */
    FolwerAppletOrderDvyVo queryById(Long orderDevId);

    /**
     * 分页查询订单物流列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单物流分页列表
     */
    TableDataInfo<FolwerAppletOrderDvyVo> queryPageList(FolwerAppletOrderDvyBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单物流列表
     *
     * @param bo 查询条件
     * @return 订单物流列表
     */
    List<FolwerAppletOrderDvyVo> queryList(FolwerAppletOrderDvyBo bo);

    /**
     * 新增订单物流
     *
     * @param bo 订单物流
     * @return 是否新增成功
     */
    FolwerAppletOrderDvyVo insertByBo(FolwerAppletOrderDvyBo bo) throws Exception;

    /**
     * 修改订单物流
     *
     * @param bo 订单物流
     * @return 是否修改成功
     */
    FolwerAppletOrderDvyVo updateByBo(FolwerAppletOrderDvyBo bo) throws Exception;

    @Transactional(rollbackFor = Exception.class)
    FolwerAppletOrderDvyVo continuationUpdateByBo(FolwerAppletOrderDvyBo bo) throws Exception;

    /**
     * 校验并批量删除订单物流信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
