package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerProductCommVo;
import org.dromara.flower.domain.bo.FolwerProductCommBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 商品评价Service接口
 *
 * @author Lion Li
 * @date 2024-12-25
 */
public interface IFolwerProductCommService {

    /**
     * 查询商品评价
     *
     * @param prodCommId 主键
     * @return 商品评价
     */
    FolwerProductCommVo queryById(Long prodCommId);

    /**
     * 分页查询商品评价列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品评价分页列表
     */
    TableDataInfo<FolwerProductCommVo> queryPageList(FolwerProductCommBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的商品评价列表
     *
     * @param bo 查询条件
     * @return 商品评价列表
     */
    List<FolwerProductCommVo> queryList(FolwerProductCommBo bo);

    /**
     * 新增商品评价
     *
     * @param bo 商品评价
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerProductCommBo bo);

    /**
     * 修改商品评价
     *
     * @param bo 商品评价
     * @return 是否修改成功
     */
    Boolean updateByBo(FolwerProductCommBo bo);

    /**
     * 校验并批量删除商品评价信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
