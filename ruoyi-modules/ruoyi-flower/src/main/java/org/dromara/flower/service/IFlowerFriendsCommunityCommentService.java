package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FlowerFriendsCommunityCommentVo;
import org.dromara.flower.domain.bo.FlowerFriendsCommunityCommentBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 花友圈--评论详情Service接口
 *
 * @author mlhxj
 * @date 2024-12-31
 */
public interface IFlowerFriendsCommunityCommentService {

    /**
     * 查询花友圈--评论详情
     *
     * @param id 主键
     * @return 花友圈--评论详情
     */
    FlowerFriendsCommunityCommentVo queryById(Long id);

    /**
     * 分页查询花友圈--评论详情列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 花友圈--评论详情分页列表
     */
    TableDataInfo<FlowerFriendsCommunityCommentVo> queryPageList(FlowerFriendsCommunityCommentBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的花友圈--评论详情列表
     *
     * @param bo 查询条件
     * @return 花友圈--评论详情列表
     */
    List<FlowerFriendsCommunityCommentVo> queryList(FlowerFriendsCommunityCommentBo bo);

    /**
     * 新增花友圈--评论详情
     *
     * @param bo 花友圈--评论详情
     * @return 是否新增成功
     */
    Boolean insertByBo(FlowerFriendsCommunityCommentBo bo);

    /**
     * 修改花友圈--评论详情
     *
     * @param bo 花友圈--评论详情
     * @return 是否修改成功
     */
    Boolean updateByBo(FlowerFriendsCommunityCommentBo bo);

    /**
     * 校验并批量删除花友圈--评论详情信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
