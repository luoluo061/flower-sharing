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
import org.dromara.flower.domain.bo.CoursesManagerDetailBo;
import org.dromara.flower.domain.vo.CoursesManagerDetailVo;
import org.dromara.flower.domain.CoursesManagerDetail;
import org.dromara.flower.mapper.CoursesManagerDetailMapper;
import org.dromara.flower.service.ICoursesManagerDetailService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程管理-视频管理-课程详情(富文本)Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class CoursesManagerDetailServiceImpl implements ICoursesManagerDetailService {

    private final CoursesManagerDetailMapper baseMapper;

    /**
     * 查询课程管理-视频管理-课程详情(富文本)
     *
     * @param id 主键
     * @return 课程管理-视频管理-课程详情(富文本)
     */
    @Override
    public CoursesManagerDetailVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询课程管理-视频管理-课程详情(富文本)列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程管理-视频管理-课程详情(富文本)分页列表
     */
    @Override
    public TableDataInfo<CoursesManagerDetailVo> queryPageList(CoursesManagerDetailBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CoursesManagerDetail> lqw = buildQueryWrapper(bo);
        Page<CoursesManagerDetailVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的课程管理-视频管理-课程详情(富文本)列表
     *
     * @param bo 查询条件
     * @return 课程管理-视频管理-课程详情(富文本)列表
     */
    @Override
    public List<CoursesManagerDetailVo> queryList(CoursesManagerDetailBo bo) {
        LambdaQueryWrapper<CoursesManagerDetail> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CoursesManagerDetail> buildQueryWrapper(CoursesManagerDetailBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CoursesManagerDetail> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, CoursesManagerDetail::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getContent()), CoursesManagerDetail::getContent, bo.getContent());
        lqw.eq(bo.getCoursesManagerId() != null, CoursesManagerDetail::getCoursesManagerId, bo.getCoursesManagerId());
        return lqw;
    }

    /**
     * 新增课程管理-视频管理-课程详情(富文本)
     *
     * @param bo 课程管理-视频管理-课程详情(富文本)
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CoursesManagerDetailBo bo) {
        CoursesManagerDetail add = MapstructUtils.convert(bo, CoursesManagerDetail.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程管理-视频管理-课程详情(富文本)
     *
     * @param bo 课程管理-视频管理-课程详情(富文本)
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CoursesManagerDetailBo bo) {
        CoursesManagerDetail update = MapstructUtils.convert(bo, CoursesManagerDetail.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CoursesManagerDetail entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除课程管理-视频管理-课程详情(富文本)信息
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
