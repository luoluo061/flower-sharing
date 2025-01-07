package org.dromara.flower.service;

import org.dromara.flower.domain.vo.CoursesPlayedLogVo;
import org.dromara.flower.domain.bo.CoursesPlayedLogBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 课程管理-视频播放记录Service接口
 *
 * @author mlhxj
 * @date 2025-01-07
 */
public interface ICoursesPlayedLogService {

    /**
     * 查询课程管理-视频播放记录
     *
     * @param id 主键
     * @return 课程管理-视频播放记录
     */
    CoursesPlayedLogVo queryById(Long id);

    /**
     * 分页查询课程管理-视频播放记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程管理-视频播放记录分页列表
     */
    TableDataInfo<CoursesPlayedLogVo> queryPageList(CoursesPlayedLogBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的课程管理-视频播放记录列表
     *
     * @param bo 查询条件
     * @return 课程管理-视频播放记录列表
     */
    List<CoursesPlayedLogVo> queryList(CoursesPlayedLogBo bo);

    /**
     * 新增课程管理-视频播放记录
     *
     * @param bo 课程管理-视频播放记录
     * @return 是否新增成功
     */
    Boolean insertByBo(CoursesPlayedLogBo bo);

    /**
     * 修改课程管理-视频播放记录
     *
     * @param bo 课程管理-视频播放记录
     * @return 是否修改成功
     */
    Boolean updateByBo(CoursesPlayedLogBo bo);

    /**
     * 校验并批量删除课程管理-视频播放记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);
}
