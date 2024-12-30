package org.dromara.flower.platform.mapper;


import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;

import java.util.List;

/**
 * 小程序用户信息Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-25
 */
public interface AppletUserInformationMapper extends BaseMapperPlus<AppletUserInformation, AppletUserInformationVo> {

    String selectMemberLevelByid(@Param("id") Long memberLevelId);

    void getLevelNamesByIds(@Param("resultHandler") MapResultHandler<Long, String> resultHandler, @Param("ids")List<Long> levelIds);

    void getParentNameByIds(@Param("resultHandler") MapResultHandler resultHandler, @Param("ids") List<Long> parentIds);
}
