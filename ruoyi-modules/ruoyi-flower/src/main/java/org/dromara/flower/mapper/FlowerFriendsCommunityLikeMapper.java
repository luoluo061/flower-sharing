package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.flower.domain.FlowerFriendsCommunityLike;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityLikeVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 花友圈--点赞详情Mapper接口
 *
 * @author mlhxj
 * @date 2025-01-23
 */
public interface FlowerFriendsCommunityLikeMapper extends BaseMapperPlus<FlowerFriendsCommunityLike, FlowerFriendsCommunityLikeVo> {

    List<Long> selectVoListByIdsAndUserId(@Param("ids") List<Long> ids,@Param("userId") Long userId);
}
