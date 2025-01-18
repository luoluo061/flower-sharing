package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.javassist.expr.NewArray;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.utils.TreeBuildUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.CoursesType;
import org.dromara.flower.domain.bo.CoursesTypeBo;
import org.dromara.flower.domain.vo.CoursesTypeVo;
import org.dromara.flower.mapper.CoursesTypeMapper;
import org.dromara.flower.service.ICoursesTypeService;
import org.dromara.flowerapplet.service.ICoursesAppletTypeService;
import org.springframework.stereotype.Service;

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
public class CoursesAppletTypeServiceImpl implements ICoursesAppletTypeService {

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
        bo.setParentId(String.valueOf(ZERO));
        LambdaQueryWrapper<CoursesType> lqw = buildQueryWrapper(bo);
        Page<CoursesTypeVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        List<CoursesTypeVo> child = new ArrayList<>();
        // 构造子级
        if (!result.getRecords().isEmpty()) {
            // 查询所有的数据
            LambdaQueryWrapper<CoursesType> all = new LambdaQueryWrapper<>();
            all.eq(CoursesType::getDelFlag, 0);
            List<CoursesTypeVo> list = baseMapper.selectVoList(all);
            child = buildTree(list);
            if (!child.isEmpty()){
                result.setRecords(child);
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

    /**
     * 下拉类型树结构列表
     * @return
     */
    @Override
    public R<List<Tree<Long>>> getCoursesTypeTree() {
        LambdaQueryWrapper<CoursesType> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CoursesType::getDelFlag, 0);
        List<CoursesTypeVo> list = baseMapper.selectVoList(lqw);
        return R.ok(buildCoursesTypeTree(list));
    }

    public static List<CoursesTypeVo> buildTree(List<CoursesTypeVo> nodes) {
        // 存储所有节点的 Map，key 是节点 ID，value 是节点对象
        Map<Long, CoursesTypeVo> nodeMap = new HashMap<>();
        // 存储根节点
        List<CoursesTypeVo> roots = new ArrayList<>();

        // 1. 将所有节点放入 nodeMap
        for (CoursesTypeVo node : nodes) {
            nodeMap.put(node.getId(), node);
        }

        // 2. 遍历节点，根据 parentId 将子节点加入父节点的 child 列表
        for (CoursesTypeVo node : nodes) {
            if (node.getParentId() == null || node.getParentId() == 0) {
                // 如果 parentId 为 null 或 0，表示是根节点
                roots.add(node);
            } else {
                // 找到父节点并加入 child 列表
                CoursesTypeVo parent = nodeMap.get(node.getParentId());
                if (parent != null) {
                    parent.getChildren().add(node);
                }
            }
        }

        return roots;
    }

    /**
     * 构建前端所需要下拉树结构
     *
     * @param coursesTypeVos 课程类型列表
     * @return 下拉树结构列表
     */
    @Override
    public List<Tree<Long>> buildCoursesTypeTree(List<CoursesTypeVo> coursesTypeVos) {
        if (CollUtil.isEmpty(coursesTypeVos)) {
            return CollUtil.newArrayList();
        }
        return TreeBuildUtils.build(coursesTypeVos, (dept, tree) ->
            tree.setId(dept.getId())
                .setParentId(dept.getParentId())
                .setName(dept.getName())
                .setWeight(dept.getSort()));
    }

    /**
     * 查询课程分类一级目录
     */
    @Override
    public R<List<Map<String, String>>> getCoursesTypePrimary() {
        LambdaQueryWrapper<CoursesType> lqw = new LambdaQueryWrapper<>();
        lqw.eq(CoursesType::getParentId, 0);
        lqw.eq(CoursesType::getStatus,1);
        List<CoursesTypeVo> list = baseMapper.selectVoList(lqw);
        List<Map<String, String>> result = list.stream()
            .map(v -> {
                Map<String, String> map = new HashMap<>();
                map.put("value", v.getId().toString());
                map.put("label", v.getName());
                return map;
            })
            .collect(Collectors.toList());

        return R.ok(result);
    }
}

