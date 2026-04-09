package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.FolwerCategory;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.mapper.FolwerCategoryMapper;
import org.dromara.flower.service.IFolwerCategoryService;
import org.dromara.flower.service.domain.ProductCategoryDomainService;
import org.dromara.flower.service.support.ProductCategoryHierarchySupport;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class FolwerCategoryServiceImpl implements IFolwerCategoryService {

    private final FolwerCategoryMapper baseMapper;
    private final ISysOssService sysOssService;
    private final IFolwerAppletProductService folwerProductService;
    private final ProductCategoryDomainService productCategoryDomainService;

    @Override
    public FolwerCategoryVo queryById(Long id) {
        FolwerCategoryVo folwerCategoryVo = baseMapper.selectVoById(id);
        populateChildrenIfNeeded(folwerCategoryVo);
        return folwerCategoryVo;
    }

    @Override
    public TableDataInfo<FolwerCategoryVo> queryPageList(FolwerCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCategory> lqw = buildQueryWrapper(bo);
        Page<FolwerCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!result.getRecords().isEmpty()) {
            result.getRecords().forEach(this::populateChildrenIfNeeded);
        }
        return TableDataInfo.build(result);
    }

    @Override
    public List<FolwerCategoryVo> queryList(FolwerCategoryBo bo) {
        LambdaQueryWrapper<FolwerCategory> lqw = buildQueryWrapper(bo);
        List<FolwerCategoryVo> folwerCategoryVos = baseMapper.selectVoList(lqw);
        folwerCategoryVos.forEach(this::populateChildrenIfNeeded);
        return folwerCategoryVos;
    }

    protected void populateChildrenIfNeeded(FolwerCategoryVo record) {
        productCategoryDomainService.populateChildren(
            record,
            FolwerCategoryVo::getParentId,
            FolwerCategoryVo::getId,
            this::loadChildren,
            FolwerCategoryVo::setChildren
        );
    }

    private List<FolwerCategoryVo> loadChildren(Long parentId) {
        FolwerCategoryBo childrenBo = new FolwerCategoryBo();
        childrenBo.setParentId(parentId);
        return this.queryList(childrenBo);
    }

    private LambdaQueryWrapper<FolwerCategory> buildQueryWrapper(FolwerCategoryBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCategory> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getParentId() != null, FolwerCategory::getParentId, bo.getParentId());
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), FolwerCategory::getCategoryName, bo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), FolwerCategory::getIcon, bo.getIcon());
        lqw.eq(bo.getSeq() != null, FolwerCategory::getSeq, bo.getSeq());
        lqw.eq(bo.getStatus() != null, FolwerCategory::getStatus, bo.getStatus());
        lqw.eq(bo.getIsShowFeature() != null, FolwerCategory::getIsShowFeature, bo.getIsShowFeature());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerCategory::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    @Override
    public Boolean insertByBo(FolwerCategoryBo bo) {
        FolwerCategory add = MapstructUtils.convert(bo, FolwerCategory.class);
        LoginUser user = LoginHelper.getLoginUser();
        bo.setCreateBy(user.getUserId());
        bo.setCreateTime(new Date());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    @Override
    public Boolean updateByBo(FolwerCategoryBo bo) {
        FolwerCategory update = MapstructUtils.convert(bo, FolwerCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    private void validEntityBeforeSave(FolwerCategory entity) {
        // TODO add entity validation when write-path cleanup starts.
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO add business validation when delete-path cleanup starts.
        }
        ids.forEach(this::validateCategoryDelete);
        return baseMapper.deleteByIds(ids) > 0;
    }

    private void validateCategoryDelete(Long categoryId) {
        if (!hasProductsUnderCategory(categoryId)) {
            return;
        }
        FolwerCategoryVo folwerCategoryVo = this.queryById(categoryId);
        throw new ServiceException("Please remove products under category " + folwerCategoryVo.getCategoryName() + " first");
    }

    private boolean hasProductsUnderCategory(Long categoryId) {
        FolwerAppletProductBo folwerProductBo = new FolwerAppletProductBo();
        folwerProductBo.setCategoryId(categoryId);
        return !folwerProductService.queryList(folwerProductBo).isEmpty();
    }
}
