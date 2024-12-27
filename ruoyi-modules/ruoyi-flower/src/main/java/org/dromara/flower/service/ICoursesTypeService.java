package org.dromara.flower.service;

import org.dromara.flower.domain.vo.CoursesTypeVo;
import org.dromara.flower.domain.bo.CoursesTypeBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程分类Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface ICoursesTypeService {

    /**
     * 查询课程分类
     *
     * @param id 主键
     * @return 课程分类
     */
    CoursesTypeVo queryById(Long id);

    /**
     * 分页查询课程分类列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程分类分页列表
     */
    TableDataInfo<CoursesTypeVo> queryPageList(CoursesTypeBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的课程分类列表
     *
     * @param bo 查询条件
     * @return 课程分类列表
     */
    List<CoursesTypeVo> queryList(CoursesTypeBo bo);

    /**
     * 新增课程分类
     *
     * @param bo 课程分类
     * @return 是否新增成功
     */
    Boolean insertByBo(CoursesTypeBo bo);

    /**
     * 修改课程分类
     *
     * @param bo 课程分类
     * @return 是否修改成功
     */
    Boolean updateByBo(CoursesTypeBo bo);

    /**
     * 校验并批量删除课程分类信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
