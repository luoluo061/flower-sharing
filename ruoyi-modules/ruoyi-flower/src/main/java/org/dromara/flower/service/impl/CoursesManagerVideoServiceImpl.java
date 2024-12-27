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
import org.dromara.flower.domain.bo.CoursesManagerVideoBo;
import org.dromara.flower.domain.vo.CoursesManagerVideoVo;
import org.dromara.flower.domain.CoursesManagerVideo;
import org.dromara.flower.mapper.CoursesManagerVideoMapper;
import org.dromara.flower.service.ICoursesManagerVideoService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程管理-视频管理-视频Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class CoursesManagerVideoServiceImpl implements ICoursesManagerVideoService {

    private final CoursesManagerVideoMapper baseMapper;

    /**
     * 查询课程管理-视频管理-视频
     *
     * @param id 主键
     * @return 课程管理-视频管理-视频
     */
    @Override
    public CoursesManagerVideoVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询课程管理-视频管理-视频列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程管理-视频管理-视频分页列表
     */
    @Override
    public TableDataInfo<CoursesManagerVideoVo> queryPageList(CoursesManagerVideoBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CoursesManagerVideo> lqw = buildQueryWrapper(bo);
        Page<CoursesManagerVideoVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的课程管理-视频管理-视频列表
     *
     * @param bo 查询条件
     * @return 课程管理-视频管理-视频列表
     */
    @Override
    public List<CoursesManagerVideoVo> queryList(CoursesManagerVideoBo bo) {
        LambdaQueryWrapper<CoursesManagerVideo> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CoursesManagerVideo> buildQueryWrapper(CoursesManagerVideoBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CoursesManagerVideo> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, CoursesManagerVideo::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getVideoName()), CoursesManagerVideo::getVideoName, bo.getVideoName());
        lqw.eq(bo.getOrder() != null, CoursesManagerVideo::getOrder, bo.getOrder());
        lqw.eq(bo.getStatus() != null, CoursesManagerVideo::getStatus, bo.getStatus());
        lqw.eq(bo.getUrl() != null, CoursesManagerVideo::getUrl, bo.getUrl());
        lqw.eq(bo.getCoursesManagerId() != null, CoursesManagerVideo::getCoursesManagerId, bo.getCoursesManagerId());
        return lqw;
    }

    /**
     * 新增课程管理-视频管理-视频
     *
     * @param bo 课程管理-视频管理-视频
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CoursesManagerVideoBo bo) {
        CoursesManagerVideo add = MapstructUtils.convert(bo, CoursesManagerVideo.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程管理-视频管理-视频
     *
     * @param bo 课程管理-视频管理-视频
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CoursesManagerVideoBo bo) {
        CoursesManagerVideo update = MapstructUtils.convert(bo, CoursesManagerVideo.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CoursesManagerVideo entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除课程管理-视频管理-视频信息
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
