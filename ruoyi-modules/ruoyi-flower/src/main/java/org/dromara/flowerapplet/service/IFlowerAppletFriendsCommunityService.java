package org.dromara.flowerapplet.service;

import org.dromara.flowerapplet.domain.vo.FlowerAppletFriendsCommunityVo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletFriendsCommunityBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 花友圈Service接口
 *
 * @author mlhxj
 * @date 2025-10-20
 */
public interface IFlowerAppletFriendsCommunityService {

    /**
     * 查询花友圈
     *
     * @param id 主键
     * @return 花友圈
     */
    FlowerAppletFriendsCommunityVo queryById(Long id);

    /**
     * 分页查询花友圈列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 花友圈分页列表
     */
    TableDataInfo<FlowerAppletFriendsCommunityVo> queryPageList(FlowerAppletFriendsCommunityBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的花友圈列表
     *
     * @param bo 查询条件
     * @return 花友圈列表
     */
    List<FlowerAppletFriendsCommunityVo> queryList(FlowerAppletFriendsCommunityBo bo);

    /**
     * 新增花友圈
     *
     * @param bo 花友圈
     * @return 是否新增成功
     */
    Boolean insertByBo(FlowerAppletFriendsCommunityBo bo);

    /**
     * 修改花友圈
     *
     * @param bo 花友圈
     * @return 是否修改成功
     */
    Boolean updateByBo(FlowerAppletFriendsCommunityBo bo);

    /**
     * 校验并批量删除花友圈信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
