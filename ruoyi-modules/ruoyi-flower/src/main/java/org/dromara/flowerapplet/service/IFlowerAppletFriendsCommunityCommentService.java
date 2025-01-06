package org.dromara.flowerapplet.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flowerapplet.domain.bo.FlowerAppletFriendsCommunityCommentBo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletFriendsCommunityCommentVo;


import java.util.Collection;
import java.util.List;

/**
 * 花友圈--评论详情Service接口
 *
 * @author mlhxj
 * @date 2024-12-31
 */
public interface IFlowerAppletFriendsCommunityCommentService {

    /**
     * 查询花友圈--评论详情
     *
     * @param id 主键
     * @return 花友圈--评论详情
     */
    FlowerAppletFriendsCommunityCommentVo queryById(Long id);

    /**
     * 分页查询花友圈--评论详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 花友圈--评论详情分页列表
     */
    TableDataInfo<FlowerAppletFriendsCommunityCommentVo> queryPageList(FlowerAppletFriendsCommunityCommentBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的花友圈--评论详情列表
     *
     * @param bo 查询条件
     * @return 花友圈--评论详情列表
     */
    List<FlowerAppletFriendsCommunityCommentVo> queryList(FlowerAppletFriendsCommunityCommentBo bo);

    /**
     * 新增花友圈--评论详情
     *
     * @param bo 花友圈--评论详情
     * @return 是否新增成功
     */
    Boolean insertByBo(FlowerAppletFriendsCommunityCommentBo bo);

    /**
     * 修改花友圈--评论详情
     *
     * @param bo 花友圈--评论详情
     * @return 是否修改成功
     */
    Boolean updateByBo(FlowerAppletFriendsCommunityCommentBo bo);

    /**
     * 校验并批量删除花友圈--评论详情信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
