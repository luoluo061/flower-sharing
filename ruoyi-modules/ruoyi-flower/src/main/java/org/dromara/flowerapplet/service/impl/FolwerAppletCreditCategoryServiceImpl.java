package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCategoryBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCategoryVo;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletCreditCategoryBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletCreditCategoryVo;
import org.dromara.flowerapplet.domain.FolwerAppletCreditCategory;
import org.dromara.flowerapplet.mapper.FolwerAppletCreditCategoryMapper;
import org.dromara.flowerapplet.service.IFolwerAppletCreditCategoryService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 积分商城产品类目Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletCreditCategoryServiceImpl implements IFolwerAppletCreditCategoryService {

    private final FolwerAppletCreditCategoryMapper baseMapper;

    /**
     * 查询积分商城产品类目
     *
     * @param id 主键
     * @return 积分商城产品类目
     */
    @Override
    public FolwerAppletCreditCategoryVo queryById(Long id){

        if(id == null){
            return null;
        }
        FolwerAppletCreditCategoryVo folwerAppletCreditCategoryVo = baseMapper.selectVoById(id);
        if(folwerAppletCreditCategoryVo != null){
            //二级分类
            if (!folwerAppletCreditCategoryVo.getParentId().equals(0L)){
                FolwerAppletCreditCategoryBo childrenBo = new FolwerAppletCreditCategoryBo();
                childrenBo.setParentId(folwerAppletCreditCategoryVo.getId());
                List<FolwerAppletCreditCategoryVo> folwerAppletCreditCategoryVos = this.queryList(childrenBo);
                folwerAppletCreditCategoryVo.setChildren(folwerAppletCreditCategoryVos);
            }
        }
        return folwerAppletCreditCategoryVo;
    }

    /**
     * 分页查询积分商城产品类目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 积分商城产品类目分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletCreditCategoryVo> queryPageList(FolwerAppletCreditCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletCreditCategory> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletCreditCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        //二级分类
        getAllList(result.getRecords());
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的积分商城产品类目列表
     *
     * @param bo 查询条件
     * @return 积分商城产品类目列表
     */
    @Override
    public List<FolwerAppletCreditCategoryVo> queryList(FolwerAppletCreditCategoryBo bo) {
        LambdaQueryWrapper<FolwerAppletCreditCategory> lqw = buildQueryWrapper(bo);
        List<FolwerAppletCreditCategoryVo> folwerAppletCreditCategoryVos = baseMapper.selectVoList(lqw);
        if(!folwerAppletCreditCategoryVos.isEmpty()){
            //二级分类
            getAllList(folwerAppletCreditCategoryVos);
        }

        return folwerAppletCreditCategoryVos;
    }

    /***
     * 获取所有分类
     * @param folwerAppletCreditCategoryVos
     * @return
     */
    private void getAllList(List<FolwerAppletCreditCategoryVo> folwerAppletCreditCategoryVos) {
        folwerAppletCreditCategoryVos.forEach(record ->{
            if(!record.getParentId().equals(0)){
                FolwerAppletCreditCategoryBo childrenBo = new FolwerAppletCreditCategoryBo();
                childrenBo.setParentId(record.getId());
                record.setChildren(this.queryList(childrenBo));
            }
        });
    }

    private LambdaQueryWrapper<FolwerAppletCreditCategory> buildQueryWrapper(FolwerAppletCreditCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletCreditCategory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, FolwerAppletCreditCategory::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), FolwerAppletCreditCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), FolwerAppletCreditCategory::getIcon, bo.getIcon());
        lqw.eq(bo.getSeq() != null, FolwerAppletCreditCategory::getSeq, bo.getSeq());
        lqw.eq(bo.getStatus() != null, FolwerAppletCreditCategory::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增积分商城产品类目
     *
     * @param bo 积分商城产品类目
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletCreditCategoryBo bo) {
        FolwerAppletCreditCategory add = MapstructUtils.convert(bo, FolwerAppletCreditCategory.class);
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
    public Boolean updateByBo(FolwerAppletCreditCategoryBo bo) {
        FolwerAppletCreditCategory update = MapstructUtils.convert(bo, FolwerAppletCreditCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletCreditCategory entity){
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
