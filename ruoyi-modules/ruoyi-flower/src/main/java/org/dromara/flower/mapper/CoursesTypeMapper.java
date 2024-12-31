package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.CoursesType;
import org.dromara.flower.domain.vo.CoursesTypeVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

import java.util.List;

/**
 * 课程分类Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface CoursesTypeMapper extends BaseMapperPlus<CoursesType, CoursesTypeVo> {

    List<CoursesTypeVo> selectChildList(@Param("ids") List<Long> parentIds);
}
