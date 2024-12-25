package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerOrderSetVo;
import org.dromara.flower.domain.bo.FolwerOrderSetBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 订单设置Service接口
 *
 * @author Lion Li
 * @date 2024-12-25
 */
public interface IFolwerOrderSetService {

    /**
     * 查询订单设置
     *
     * @param id 主键
     * @return 订单设置
     */
    FolwerOrderSetVo queryById(Long id);

    /**
     * 分页查询订单设置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单设置分页列表
     */
    TableDataInfo<FolwerOrderSetVo> queryPageList(FolwerOrderSetBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的订单设置列表
     *
     * @param bo 查询条件
     * @return 订单设置列表
     */
    List<FolwerOrderSetVo> queryList(FolwerOrderSetBo bo);

    /**
     * 新增订单设置
     *
     * @param bo 订单设置
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerOrderSetBo bo);

    /**
     * 修改订单设置
     *
     * @param bo 订单设置
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerOrderSetBo bo);

    /**
     * 校验并批量删除订单设置信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
