package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.flower.domain.MemberLevel;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;
import java.util.Map;

/**
 * 会员等级Mapper接口
 *
 * @author chzl
 * @date 2024-12-24
 */
public interface MemberLevelMapper extends BaseMapperPlus<MemberLevel, MemberLevelVo> {

    void selectMapByIds(@Param("resultHandler") MapResultHandler resultHandler, @Param("ids") List<Long> ids);

    void selectIdMapGrade(@Param("resultHandler")MapResultHandler<Long, String> resultHandler);

    MemberLevelVo selectMemberLevel(@Param("memberLevelId") Long memberLevelId);


    List<MemberLevelVo> selectMemberLevelIds(@Param("ids") List<Long> ids);

    MemberLevelVo selectMemberLevelId(@Param("id")Long id);
}
