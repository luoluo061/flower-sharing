package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerCreditGetrecords;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerCreditCategoryBo;
import org.dromara.flower.domain.vo.FolwerCreditCategoryVo;
import org.dromara.flower.domain.FolwerCreditCategory;
import org.dromara.flower.mapper.FolwerCreditCategoryMapper;
import org.dromara.flower.service.IFolwerCreditCategoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分商城产品类目Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class FolwerCreditCategoryServiceImpl implements IFolwerCreditCategoryService {

    private final FolwerCreditCategoryMapper baseMapper;

    private final ISysOssService sysOssService;

    /**
     * 查询积分商城产品类目
     *
     * @param id 主键
     * @return 积分商城产品类目
     */
    @Override
    public FolwerCreditCategoryVo queryById(Long id){
        FolwerCreditCategoryVo folwerCreditCategoryVo = baseMapper.selectVoById(id);
        // 设置图片Url
        Collection<Long> ossIds  = new ArrayList<>();
        ossIds.add(Long.valueOf(folwerCreditCategoryVo.getIcon()));
        if (!ossIds.isEmpty()){
            Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
            if (!stringStringMap.isEmpty()){
                // 设置图片Url
                folwerCreditCategoryVo.setIconUrl(stringStringMap.get(folwerCreditCategoryVo.getIcon()));
            }
        }
        //二级分类
        if (!folwerCreditCategoryVo.getParentId().equals(0)){
            FolwerCreditCategoryBo childrenBo = new FolwerCreditCategoryBo();
            childrenBo.setParentId(folwerCreditCategoryVo.getId());
            List<FolwerCreditCategoryVo> childrenFolwerCategoryVos = this.queryList(childrenBo);
            folwerCreditCategoryVo.setChildren(childrenFolwerCategoryVos);
        }
        return folwerCreditCategoryVo;
    }

    /**
     * 分页查询积分商城产品类目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分商城产品类目分页列表
     */
    @Override
    public TableDataInfo<FolwerCreditCategoryVo> queryPageList(FolwerCreditCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCreditCategory> lqw = buildQueryWrapper(bo);
        Page<FolwerCreditCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        if (!result.getRecords().isEmpty()){
            Map<String, String> longStringMap = sysOssService.listUrlByIds(
                result.getRecords().stream().
                    map(FolwerCreditCategoryVo::getIcon).
                    map(Long::parseLong).toList());
            if (!longStringMap.isEmpty()){
                // 设置图片Url
                result.getRecords().forEach(record ->
                    record.setIconUrl(longStringMap.get(record.getIcon()))
                );
            }
            //二级分类
            result.getRecords().forEach(record ->{
                if(!record.getParentId().equals(0)){
                    FolwerCreditCategoryBo childrenBo = new FolwerCreditCategoryBo();
                    childrenBo.setParentId(record.getId());
                    List<FolwerCreditCategoryVo> childrenFolwerCategoryVos = this.queryList(childrenBo);
                    record.setChildren(childrenFolwerCategoryVos);
                }
            });
        }


        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分商城产品类目列表
     *
     * @param bo 查询条件
     * @return 积分商城产品类目列表
     */
    @Override
    public List<FolwerCreditCategoryVo> queryList(FolwerCreditCategoryBo bo) {
        LambdaQueryWrapper<FolwerCreditCategory> lqw = buildQueryWrapper(bo);
        List<FolwerCreditCategoryVo> folwerCreditCategoryVos = baseMapper.selectVoList(lqw);
        if (!folwerCreditCategoryVos.isEmpty()){
            Map<String, String> longStringMap = sysOssService.listUrlByIds(
                folwerCreditCategoryVos.stream().
                    map(FolwerCreditCategoryVo::getIcon).
                    map(String::toString).
                    map(Long::parseLong).toList());
            if (!longStringMap.isEmpty()){
                // 设置图片Url
                folwerCreditCategoryVos.forEach(record ->
                    record.setIconUrl(longStringMap.get(record.getIcon()))
                );
            }
        }
        //二级分类
        folwerCreditCategoryVos.forEach(record ->{
            if(!record.getParentId().equals(0)){
                FolwerCreditCategoryBo childrenBo = new FolwerCreditCategoryBo();
                childrenBo.setParentId(record.getId());
                List<FolwerCreditCategoryVo> childrenFolwerCategoryVos = this.queryList(childrenBo);
                record.setChildren(childrenFolwerCategoryVos);
            }
        });
        return folwerCreditCategoryVos;
    }

    private LambdaQueryWrapper<FolwerCreditCategory> buildQueryWrapper(FolwerCreditCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCreditCategory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, FolwerCreditCategory::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), FolwerCreditCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), FolwerCreditCategory::getIcon, bo.getIcon());
        lqw.eq(bo.getSeq() != null, FolwerCreditCategory::getSeq, bo.getSeq());
        lqw.eq(bo.getStatus() != null, FolwerCreditCategory::getStatus, bo.getStatus());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerCreditCategory::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    /**
     * 新增积分商城产品类目
     *
     * @param bo 积分商城产品类目
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCreditCategoryBo bo) {
        FolwerCreditCategory add = MapstructUtils.convert(bo, FolwerCreditCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改积分商城产品类目
     *
     * @param bo 积分商城产品类目
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerCreditCategoryBo bo) {
        FolwerCreditCategory update = MapstructUtils.convert(bo, FolwerCreditCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCreditCategory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除积分商城产品类目信息
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
