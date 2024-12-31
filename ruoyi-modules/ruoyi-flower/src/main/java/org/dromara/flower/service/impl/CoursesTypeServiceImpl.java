package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.CoursesTypeBo;
import org.dromara.flower.domain.vo.CoursesTypeVo;
import org.dromara.flower.domain.CoursesType;
import org.dromara.flower.mapper.CoursesTypeMapper;
import org.dromara.flower.service.ICoursesTypeService;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 课程分类Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class CoursesTypeServiceImpl implements ICoursesTypeService {

    private final CoursesTypeMapper baseMapper;

    private static final Long ZERO = 0L;

    /**
     * 查询课程分类
     *
     * @param id 主键
     * @return 课程分类
     */
    @Override
    public CoursesTypeVo queryById(Long id) {
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询课程分类列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程分类分页列表
     */
    @Override
    public TableDataInfo<CoursesTypeVo> queryPageList(CoursesTypeBo bo, PageQuery pageQuery) {
        bo.setParentId(ZERO);
        LambdaQueryWrapper<CoursesType> lqw = buildQueryWrapper(bo);
        Page<CoursesTypeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        // 构造子级
        if (!result.getRecords().isEmpty()) {
            List<Long> parentIds = result.getRecords().stream()
                .filter(Objects::nonNull)
                .map(CoursesTypeVo::getId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();

            if (!parentIds.isEmpty()) {
                List<CoursesTypeVo> childs = baseMapper.selectChildList(parentIds);
                Map<Long, List<CoursesTypeVo>> childMap;

                if (!childs.isEmpty()) {
                    childMap = childs.stream()
                        .collect(Collectors.groupingBy(CoursesTypeVo::getParentId));
                } else {
                    childMap = new HashMap<>();
                }

                result.getRecords().forEach(vo -> {
                    vo.setChild(childMap.getOrDefault(vo.getId(), Collections.emptyList()));
                });
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的课程分类列表
     *
     * @param bo 查询条件
     * @return 课程分类列表
     */
    @Override
    public List<CoursesTypeVo> queryList(CoursesTypeBo bo) {
        LambdaQueryWrapper<CoursesType> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CoursesType> buildQueryWrapper(CoursesTypeBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CoursesType> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, CoursesType::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), CoursesType::getName, bo.getName());
        lqw.eq(bo.getStatus() != null, CoursesType::getStatus, bo.getStatus());
        lqw.eq(bo.getSort() != null, CoursesType::getSort, bo.getSort());
        lqw.eq(bo.getParentId() != null, CoursesType::getParentId, bo.getParentId());
        return lqw;
    }

    /**
     * 新增课程分类
     *
     * @param bo 课程分类
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CoursesTypeBo bo) {
        CoursesType add = MapstructUtils.convert(bo, CoursesType.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程分类
     *
     * @param bo 课程分类
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CoursesTypeBo bo) {
        CoursesType update = MapstructUtils.convert(bo, CoursesType.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CoursesType entity) {
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除课程分类信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
