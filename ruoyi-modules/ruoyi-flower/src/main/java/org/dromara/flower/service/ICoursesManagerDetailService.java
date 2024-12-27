package org.dromara.flower.service;

import org.dromara.flower.domain.vo.CoursesManagerDetailVo;
import org.dromara.flower.domain.bo.CoursesManagerDetailBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程管理-视频管理-课程详情(富文本)Service接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface ICoursesManagerDetailService {

    /**
     * 查询课程管理-视频管理-课程详情(富文本)
     *
     * @param id 主键
     * @return 课程管理-视频管理-课程详情(富文本)
     */
    CoursesManagerDetailVo queryById(Long id);

    /**
     * 分页查询课程管理-视频管理-课程详情(富文本)列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程管理-视频管理-课程详情(富文本)分页列表
     */
    TableDataInfo<CoursesManagerDetailVo> queryPageList(CoursesManagerDetailBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的课程管理-视频管理-课程详情(富文本)列表
     *
     * @param bo 查询条件
     * @return 课程管理-视频管理-课程详情(富文本)列表
     */
    List<CoursesManagerDetailVo> queryList(CoursesManagerDetailBo bo);

    /**
     * 新增课程管理-视频管理-课程详情(富文本)
     *
     * @param bo 课程管理-视频管理-课程详情(富文本)
     * @return 是否新增成功
     */
    Boolean insertByBo(CoursesManagerDetailBo bo);

    /**
     * 修改课程管理-视频管理-课程详情(富文本)
     *
     * @param bo 课程管理-视频管理-课程详情(富文本)
     * @return 是否修改成功
     */
    Boolean updateByBo(CoursesManagerDetailBo bo);

    /**
     * 校验并批量删除课程管理-视频管理-课程详情(富文本)信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
