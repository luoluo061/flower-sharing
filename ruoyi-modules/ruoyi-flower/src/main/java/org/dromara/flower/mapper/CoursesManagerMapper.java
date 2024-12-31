package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.flower.domain.CoursesManager;
import org.dromara.flower.domain.vo.CoursesManagerVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 视频管理Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface CoursesManagerMapper extends BaseMapperPlus<CoursesManager, CoursesManagerVo> {

    void selectIdCoursesType(@Param("resultHandler") MapResultHandler<Long, String> resultHandler, @Param("ids")List<Long> ids);
}
