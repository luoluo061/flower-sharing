package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.CoursesPlayedLogBo;
import org.dromara.flower.domain.vo.CoursesPlayedLogVo;
import org.dromara.flower.domain.CoursesPlayedLog;
import org.dromara.flower.mapper.CoursesPlayedLogMapper;
import org.dromara.flower.service.ICoursesPlayedLogService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程管理-视频播放记录Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-07
 */
@RequiredArgsConstructor
@Service
public class CoursesPlayedLogServiceImpl implements ICoursesPlayedLogService {

    private final CoursesPlayedLogMapper baseMapper;

    /**
     * 查询课程管理-视频播放记录
     *
     * @param id 主键
     * @return 课程管理-视频播放记录
     */
    @Override
    public CoursesPlayedLogVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询课程管理-视频播放记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程管理-视频播放记录分页列表
     */
    @Override
    public TableDataInfo<CoursesPlayedLogVo> queryPageList(CoursesPlayedLogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CoursesPlayedLog> lqw = buildQueryWrapper(bo);
        Page<CoursesPlayedLogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的课程管理-视频播放记录列表
     *
     * @param bo 查询条件
     * @return 课程管理-视频播放记录列表
     */
    @Override
    public List<CoursesPlayedLogVo> queryList(CoursesPlayedLogBo bo) {
        LambdaQueryWrapper<CoursesPlayedLog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CoursesPlayedLog> buildQueryWrapper(CoursesPlayedLogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CoursesPlayedLog> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, CoursesPlayedLog::getDeptId, bo.getDeptId());
        lqw.eq(bo.getCoursesManagerVideoId() != null, CoursesPlayedLog::getCoursesManagerVideoId, bo.getCoursesManagerVideoId());
        lqw.eq(bo.getCoursesManagerId() != null, CoursesPlayedLog::getCoursesManagerId, bo.getCoursesManagerId());
        lqw.eq(bo.getDuration() != null, CoursesPlayedLog::getDuration, bo.getDuration());
        lqw.eq(bo.getViewingTime() != null, CoursesPlayedLog::getViewingTime, bo.getViewingTime());
        lqw.eq(bo.getStatus() != null, CoursesPlayedLog::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增课程管理-视频播放记录
     *
     * @param bo 课程管理-视频播放记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CoursesPlayedLogBo bo) {
        CoursesPlayedLog add = MapstructUtils.convert(bo, CoursesPlayedLog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程管理-视频播放记录
     *
     * @param bo 课程管理-视频播放记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CoursesPlayedLogBo bo) {
        CoursesPlayedLog update = MapstructUtils.convert(bo, CoursesPlayedLog.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CoursesPlayedLog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除课程管理-视频播放记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
