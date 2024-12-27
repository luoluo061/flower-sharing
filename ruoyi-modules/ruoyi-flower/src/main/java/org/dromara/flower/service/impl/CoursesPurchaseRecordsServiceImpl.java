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
import org.dromara.flower.domain.bo.CoursesPurchaseRecordsBo;
import org.dromara.flower.domain.vo.CoursesPurchaseRecordsVo;
import org.dromara.flower.domain.CoursesPurchaseRecords;
import org.dromara.flower.mapper.CoursesPurchaseRecordsMapper;
import org.dromara.flower.service.ICoursesPurchaseRecordsService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 课程管理-课程购买记录Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class CoursesPurchaseRecordsServiceImpl implements ICoursesPurchaseRecordsService {

    private final CoursesPurchaseRecordsMapper baseMapper;

    /**
     * 查询课程管理-课程购买记录
     *
     * @param id 主键
     * @return 课程管理-课程购买记录
     */
    @Override
    public CoursesPurchaseRecordsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询课程管理-课程购买记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程管理-课程购买记录分页列表
     */
    @Override
    public TableDataInfo<CoursesPurchaseRecordsVo> queryPageList(CoursesPurchaseRecordsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CoursesPurchaseRecords> lqw = buildQueryWrapper(bo);
        Page<CoursesPurchaseRecordsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的课程管理-课程购买记录列表
     *
     * @param bo 查询条件
     * @return 课程管理-课程购买记录列表
     */
    @Override
    public List<CoursesPurchaseRecordsVo> queryList(CoursesPurchaseRecordsBo bo) {
        LambdaQueryWrapper<CoursesPurchaseRecords> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CoursesPurchaseRecords> buildQueryWrapper(CoursesPurchaseRecordsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CoursesPurchaseRecords> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, CoursesPurchaseRecords::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getCode()), CoursesPurchaseRecords::getCode, bo.getCode());
        lqw.like(StringUtils.isNotBlank(bo.getCoursesName()), CoursesPurchaseRecords::getCoursesName, bo.getCoursesName());
        lqw.eq(StringUtils.isNotBlank(bo.getMemberId()), CoursesPurchaseRecords::getMemberId, bo.getMemberId());
        lqw.eq(bo.getAppletUserInformationId() != null, CoursesPurchaseRecords::getAppletUserInformationId, bo.getAppletUserInformationId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), CoursesPurchaseRecords::getName, bo.getName());
        lqw.eq(bo.getPrice() != null, CoursesPurchaseRecords::getPrice, bo.getPrice());
        lqw.eq(bo.getStatus() != null, CoursesPurchaseRecords::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增课程管理-课程购买记录
     *
     * @param bo 课程管理-课程购买记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CoursesPurchaseRecordsBo bo) {
        CoursesPurchaseRecords add = MapstructUtils.convert(bo, CoursesPurchaseRecords.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程管理-课程购买记录
     *
     * @param bo 课程管理-课程购买记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CoursesPurchaseRecordsBo bo) {
        CoursesPurchaseRecords update = MapstructUtils.convert(bo, CoursesPurchaseRecords.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CoursesPurchaseRecords entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除课程管理-课程购买记录信息
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
