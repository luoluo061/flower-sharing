package org.dromara.flower.service;

import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.bo.FlowerFriendsCommunityBo;
import org.dromara.flower.domain.vo.FlowerFriendsCommunityVo;

import java.util.Collection;
import java.util.List;

public interface IFlowerFriendsCommunityService {

    FlowerFriendsCommunityVo queryById(Long id);

    TableDataInfo<FlowerFriendsCommunityVo> queryPageList(FlowerFriendsCommunityBo bo, PageQuery pageQuery);

    List<FlowerFriendsCommunityVo> queryList(FlowerFriendsCommunityBo bo);

    Boolean insertByBo(FlowerFriendsCommunityBo bo);

    Boolean updateByBo(FlowerFriendsCommunityBo bo);

    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
