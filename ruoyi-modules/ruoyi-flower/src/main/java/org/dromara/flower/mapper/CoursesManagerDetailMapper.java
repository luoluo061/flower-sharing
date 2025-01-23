package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.CoursesManagerDetail;
import org.dromara.flower.domain.vo.CoursesManagerDetailVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 课程管理-视频管理-课程详情(富文本)Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface CoursesManagerDetailMapper extends BaseMapperPlus<CoursesManagerDetail, CoursesManagerDetailVo> {

    List<CoursesManagerDetailVo> selectVoByCoursesManagerIds(@Param("ids") List<Long> list);

    List<CoursesManagerDetailVo> selectVoByCoursesManagerId(@Param("id")Long id);
}
