package org.dromara.flowerapplet.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.flowerapplet.domain.FlowerAppletFriendsCommunityComment;
import org.dromara.flowerapplet.domain.vo.FlowerAppletFriendsCommunityCommentVo;

import java.util.List;

/**
 * 花友圈--评论详情Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-31
 */
public interface FlowerAppletFriendsCommunityCommentMapper extends BaseMapperPlus<FlowerAppletFriendsCommunityComment, FlowerAppletFriendsCommunityCommentVo> {

    List<FlowerAppletFriendsCommunityCommentVo> selectVoListByCommunityId(@Param("communityId") Long communityId);
}
