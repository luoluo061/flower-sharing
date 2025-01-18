package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditGetrecordsVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditGetrecordsBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 积分获取记录Service接口
 *
 * @author mlhxj
 * @date 2025-01-17
 */
public interface IFolwerAppletCreditGetrecordsService {

    /**
     * 查询积分获取记录
     *
     * @param recordId 主键
     * @return 积分获取记录
     */
    FolwerAppletCreditGetrecordsVo queryById(Long recordId);

    /**
     * 分页查询积分获取记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分获取记录分页列表
     */
    TableDataInfo<FolwerAppletCreditGetrecordsVo> queryPageList(FolwerAppletCreditGetrecordsBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的积分获取记录列表
     *
     * @param bo 查询条件
     * @return 积分获取记录列表
     */
    List<FolwerAppletCreditGetrecordsVo> queryList(FolwerAppletCreditGetrecordsBo bo);

    /**
     * 新增积分获取记录
     *
     * @param bo 积分获取记录
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerAppletCreditGetrecordsBo bo);

    /**
     * 修改积分获取记录
     *
     * @param bo 积分获取记录
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerAppletCreditGetrecordsBo bo);

    /**
     * 校验并批量删除积分获取记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
