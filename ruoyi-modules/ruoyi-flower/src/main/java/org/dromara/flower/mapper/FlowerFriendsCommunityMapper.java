package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.flower.domain.FlowerFriendsCommunity;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityVo;

@Mapper
public interface FlowerFriendsCommunityMapper
    extends BaseMapperPlus<FlowerFriendsCommunity, FlowerFriendsCommunityVo> {
}
