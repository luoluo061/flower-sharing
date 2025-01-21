package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.CoursesManagerVideo;
import org.dromara.flower.domain.vo.CoursesManagerVideoVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 课程管理-视频管理-视频Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface CoursesManagerVideoMapper extends BaseMapperPlus<CoursesManagerVideo, CoursesManagerVideoVo> {

    List<CoursesManagerVideoVo> getVideoByCoursesManagerId(@Param("id") Long id);

    List<CoursesManagerVideoVo> selectVoByCoursesManagerIds(@Param("ids") List<Long> list);
}
