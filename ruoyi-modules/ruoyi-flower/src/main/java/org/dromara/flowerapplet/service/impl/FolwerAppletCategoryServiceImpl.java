package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerCategory;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flowerapplet.domain.FolwerAppletCategory;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCategoryVo;
import org.dromara.flowerapplet.mapper.FolwerAppletCategoryMapper;
import org.dromara.flowerapplet.service.IFolwerAppletCategoryService;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序端产品类目Service业务层处理
 *
 * @author Lion Li
 * @date 2025-01-02
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletCategoryServiceImpl implements IFolwerAppletCategoryService {

    private final FolwerAppletCategoryMapper baseMapper;

    private final ISysOssService sysOssService;
    /**
     * 查询小程序端产品类目
     *
     * @param id 主键
     * @return 小程序端产品类目
     */
    @Override
    public FolwerAppletCategoryVo queryById(Long id){
        FolwerAppletCategoryVo folwerCategoryVo = baseMapper.selectVoById(id);
        if (folwerCategoryVo != null){
            if (folwerCategoryVo.getIcon() != null){
                // 设置图片Url
                Collection<Long> ossIds  = new ArrayList<>();
                ossIds.add(Long.valueOf(folwerCategoryVo.getIcon()));
                if (!ossIds.isEmpty()){
                    Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
                    if (!stringStringMap.isEmpty()){
                        // 设置图片Url
                        folwerCategoryVo.setIconUrl(stringStringMap.get(folwerCategoryVo.getIcon()));
                    }
                }
            }else {
                folwerCategoryVo.setIconUrl("");
            }

            //二级分类
            if (!folwerCategoryVo.getParentId().equals(0L)){
                FolwerAppletCategoryBo childrenBo = new FolwerAppletCategoryBo();
                childrenBo.setParentId(folwerCategoryVo.getId());
                List<FolwerAppletCategoryVo> childrenFolwerCategoryVos = this.queryList(childrenBo);
                folwerCategoryVo.setChildren(childrenFolwerCategoryVos);
            }
        }

        return folwerCategoryVo;
    }

    /**
     * 分页查询小程序端产品类目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序端产品类目分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletCategoryVo> queryPageList(FolwerAppletCategoryBo bo, PageQuery pageQuery) {
        bo.setStatus(1L);
        LambdaQueryWrapper<FolwerAppletCategory> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!result.getRecords().isEmpty()){
            Map<String, String> longStringMap = sysOssService.listUrlByIds(
                result.getRecords().stream().
                    map(FolwerAppletCategoryVo::getIcon).
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
                    FolwerAppletCategoryBo childrenBo = new FolwerAppletCategoryBo();
                    childrenBo.setParentId(record.getId());
                    List<FolwerAppletCategoryVo> childrenFolwerCategoryVos = this.queryList(childrenBo);
                    record.setChildren(childrenFolwerCategoryVos);
                }
            });
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序端产品类目列表
     *
     * @param bo 查询条件
     * @return 小程序端产品类目列表
     */
    @Override
    public List<FolwerAppletCategoryVo> queryList(FolwerAppletCategoryBo bo) {
        bo.setStatus(1L);
        LambdaQueryWrapper<FolwerAppletCategory> lqw = buildQueryWrapper(bo);
        List<FolwerAppletCategoryVo> folwerAppletCategoryVos = baseMapper.selectVoList(lqw);
        if (!folwerAppletCategoryVos.isEmpty()){
            Map<String, String> longStringMap = sysOssService.listUrlByIds(
                folwerAppletCategoryVos.stream().
                    map(FolwerAppletCategoryVo::getIcon).
                    map(String::toString).
                    map(Long::parseLong).toList());
            if (!longStringMap.isEmpty()){
                // 设置图片Url
                folwerAppletCategoryVos.forEach(record ->
                    record.setIconUrl(longStringMap.get(record.getIcon()))
                );
            }
        }

        //二级分类
        folwerAppletCategoryVos.forEach(record ->{
            if(!record.getParentId().equals(0)){
                FolwerAppletCategoryBo childrenBo = new FolwerAppletCategoryBo();
                childrenBo.setParentId(record.getId());
                List<FolwerAppletCategoryVo> childrenFolwerAppletCategoryVos = this.queryList(childrenBo);
                record.setChildren(childrenFolwerAppletCategoryVos);
            }

        });

        return folwerAppletCategoryVos;
    }

    private LambdaQueryWrapper<FolwerAppletCategory> buildQueryWrapper(FolwerAppletCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletCategory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, FolwerAppletCategory::getParentId, bo.getParentId());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), FolwerAppletCategory::getIcon, bo.getIcon());
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), FolwerAppletCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(bo.getSeq() != null, FolwerAppletCategory::getSeq, bo.getSeq());
        lqw.eq(bo.getStatus() != null, FolwerAppletCategory::getStatus, bo.getStatus());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerAppletCategory::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    /**
     * 新增小程序端产品类目
     *
     * @param bo 小程序端产品类目
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletCategoryBo bo) {
        FolwerAppletCategory add = MapstructUtils.convert(bo, FolwerAppletCategory.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改小程序端产品类目
     *
     * @param bo 小程序端产品类目
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletCategoryBo bo) {
        FolwerAppletCategory update = MapstructUtils.convert(bo, FolwerAppletCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletCategory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除小程序端产品类目信息
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
