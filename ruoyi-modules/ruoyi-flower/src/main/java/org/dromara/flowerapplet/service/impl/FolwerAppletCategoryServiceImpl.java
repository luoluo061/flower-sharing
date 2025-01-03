package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flowerapplet.domain.FolwerAppletCategory;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCategoryVo;
import org.dromara.flowerapplet.mapper.FolwerAppletCategoryMapper;
import org.dromara.flowerapplet.service.IFolwerAppletCategoryService;
import org.springframework.stereotype.Service;

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

    /**
     * 查询小程序端产品类目
     *
     * @param id 主键
     * @return 小程序端产品类目
     */
    @Override
    public FolwerAppletCategoryVo queryById(Long id){
        return baseMapper.selectVoById(id);
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
        LambdaQueryWrapper<FolwerAppletCategory> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
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
        LambdaQueryWrapper<FolwerAppletCategory> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletCategory> buildQueryWrapper(FolwerAppletCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletCategory> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), FolwerAppletCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(bo.getSeq() != null, FolwerAppletCategory::getSeq, bo.getSeq());
        lqw.eq(bo.getStatus() != null, FolwerAppletCategory::getStatus, bo.getStatus());
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
