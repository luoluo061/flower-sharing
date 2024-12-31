package org.dromara.flower.service;

import org.dromara.flower.domain.vo.CoursesManagerVo;
import org.dromara.flower.domain.bo.CoursesManagerBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 视频管理Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface ICoursesManagerService {

    /**
     * 查询视频管理
     *
     * @param id 主键
     * @return 视频管理
     */
    CoursesManagerVo queryById(Long id);

    /**
     * 分页查询视频管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 视频管理分页列表
     */
    TableDataInfo<CoursesManagerVo> queryPageList(CoursesManagerBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的视频管理列表
     *
     * @param bo 查询条件
     * @return 视频管理列表
     */
    List<CoursesManagerVo> queryList(CoursesManagerBo bo);

    /**
     * 新增视频管理
     *
     * @param bo 视频管理
     * @return 是否新增成功
     */
    Boolean insertByBo(CoursesManagerBo bo);

    /**
     * 修改视频管理
     *
     * @param bo 视频管理
     * @return 是否修改成功
     */
    Boolean updateByBo(CoursesManagerBo bo);

    /**
     * 校验并批量删除视频管理信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    int getCoursesTypeInfo();
}
