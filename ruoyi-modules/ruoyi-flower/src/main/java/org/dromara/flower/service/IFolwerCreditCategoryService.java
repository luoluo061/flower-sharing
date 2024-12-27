package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerCreditCategoryVo;
import org.dromara.flower.domain.bo.FolwerCreditCategoryBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 积分商城产品类目Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface IFolwerCreditCategoryService {

    /**
     * 查询积分商城产品类目
     *
     * @param id 主键
     * @return 积分商城产品类目
     */
    FolwerCreditCategoryVo queryById(Long id);

    /**
     * 分页查询积分商城产品类目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分商城产品类目分页列表
     */
    TableDataInfo<FolwerCreditCategoryVo> queryPageList(FolwerCreditCategoryBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的积分商城产品类目列表
     *
     * @param bo 查询条件
     * @return 积分商城产品类目列表
     */
    List<FolwerCreditCategoryVo> queryList(FolwerCreditCategoryBo bo);

    /**
     * 新增积分商城产品类目
     *
     * @param bo 积分商城产品类目
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerCreditCategoryBo bo);

    /**
     * 修改积分商城产品类目
     *
     * @param bo 积分商城产品类目
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerCreditCategoryBo bo);

    /**
     * 校验并批量删除积分商城产品类目信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
