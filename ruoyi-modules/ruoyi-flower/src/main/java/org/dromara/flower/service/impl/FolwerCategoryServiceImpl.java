package org.dromara.flower.service.impl;

import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flowerapplet.domain.bo.FolwerAppletProductBo;
import org.dromara.flowerapplet.service.IFolwerAppletProductService;
import org.dromara.system.service.ISysOssService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerCategoryBo;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.domain.FolwerCategory;
import org.dromara.flower.mapper.FolwerCategoryMapper;
import org.dromara.flower.service.IFolwerCategoryService;

import java.util.*;
import java.util.stream.Stream;

/**
 * 产品类目Service业务层处理
 *
 * @author Lion Li
 * @date 2024-12-20
 */
@RequiredArgsConstructor
@Service
public class FolwerCategoryServiceImpl implements IFolwerCategoryService {

    private final FolwerCategoryMapper baseMapper;

    private final ISysOssService sysOssService;

    private final IFolwerAppletProductService folwerProductService;

    /**
     * 查询产品类目
     *
     * @param id 主键
     * @return 产品类目
     */
    @Override
    public FolwerCategoryVo queryById(Long id){

        FolwerCategoryVo folwerCategoryVo = baseMapper.selectVoById(id);
        if (folwerCategoryVo != null){
//            if (folwerCategoryVo.getIcon() != null){
//                // 设置图片Url
//                Collection<Long> ossIds  = new ArrayList<>();
//                ossIds.add(Long.valueOf(folwerCategoryVo.getIcon()));
//                if (!ossIds.isEmpty()){
//                    Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
//                    if (!stringStringMap.isEmpty()){
//                        // 设置图片Url
//                        folwerCategoryVo.setIconUrl(stringStringMap.get(folwerCategoryVo.getIcon()));
//                    }
//                }
//            }else {
//                folwerCategoryVo.setIconUrl("");
//            }

            //二级分类
            if (!folwerCategoryVo.getParentId().equals(0L)){
                FolwerCategoryBo childrenBo = new FolwerCategoryBo();
                childrenBo.setParentId(folwerCategoryVo.getId());
                List<FolwerCategoryVo> childrenFolwerCategoryVos = this.queryList(childrenBo);
                folwerCategoryVo.setChildren(childrenFolwerCategoryVos);
            }
        }

        return folwerCategoryVo;
    }

    /**
     * 分页查询产品类目列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 产品类目分页列表
     */
    @Override
    public TableDataInfo<FolwerCategoryVo> queryPageList(FolwerCategoryBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCategory> lqw = buildQueryWrapper(bo);
        Page<FolwerCategoryVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        if (!result.getRecords().isEmpty()){
//            Map<String, String> longStringMap = sysOssService.listUrlByIds(
//                result.getRecords().stream().
//                map(FolwerCategoryVo::getIcon).
//                map(Long::parseLong).toList());
//            if (!longStringMap.isEmpty()){
//                // 设置图片Url
//                result.getRecords().forEach(record ->
//                    record.setIconUrl(longStringMap.get(record.getIcon()))
//                );
//            }
            //二级分类
            result.getRecords().forEach(record ->{
                if(!record.getParentId().equals(0)){
                    FolwerCategoryBo childrenBo = new FolwerCategoryBo();
                    childrenBo.setParentId(record.getId());
                    List<FolwerCategoryVo> childrenFolwerCategoryVos = this.queryList(childrenBo);
                    record.setChildren(childrenFolwerCategoryVos);
                }
            });
        }

        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的产品类目列表
     *
     * @param bo 查询条件
     * @return 产品类目列表
     */
    @Override
    public List<FolwerCategoryVo> queryList(FolwerCategoryBo bo) {
        LambdaQueryWrapper<FolwerCategory> lqw = buildQueryWrapper(bo);
        List<FolwerCategoryVo> folwerCategoryVos = baseMapper.selectVoList(lqw);
//        if (!folwerCategoryVos.isEmpty()){
//            Map<String, String> longStringMap = sysOssService.listUrlByIds(
//                folwerCategoryVos.stream().
//                    map(FolwerCategoryVo::getIcon).
//                    map(String::toString).
//                    map(Long::parseLong).toList());
//            if (!longStringMap.isEmpty()){
//                // 设置图片Url
//                folwerCategoryVos.forEach(record ->
//                    record.setIconUrl(longStringMap.get(record.getIcon()))
//                );
//            }
//        }
        //二级分类
        folwerCategoryVos.forEach(record ->{
            if(!record.getParentId().equals(0)){
                FolwerCategoryBo childrenBo = new FolwerCategoryBo();
                childrenBo.setParentId(record.getId());
                List<FolwerCategoryVo> childrenFolwerCategoryVos = this.queryList(childrenBo);
                record.setChildren(childrenFolwerCategoryVos);
            }

        });
        return folwerCategoryVos;
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
//        lqw.eq(bo.getDeptId() != null, FolwerCategory::getDeptId, bo.getDeptId());
        return lqw;
    }

    /**
     * 新增产品类目
     *
     * @param bo 产品类目
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCategoryBo bo) {
        FolwerCategory add = MapstructUtils.convert(bo, FolwerCategory.class);
        LoginUser user = LoginHelper.getLoginUser();
//        bo.setDeptId(user.getDeptId());
        bo.setCreateBy(user.getUserId());
        bo.setCreateTime(new Date());
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改产品类目
     *
     * @param bo 产品类目
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerCategoryBo bo) {
        FolwerCategory update = MapstructUtils.convert(bo, FolwerCategory.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCategory entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除产品类目信息
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
        for (Long id : ids){
            FolwerAppletProductBo folwerProductBo = new FolwerAppletProductBo();
            folwerProductBo.setCategoryId(id);
            if (folwerProductService.queryList(folwerProductBo).size() > 0){
                FolwerCategoryVo folwerCategoryVo = this.queryById(id);
                throw new ServiceException("请先删除"+folwerCategoryVo.getCategoryName()+"该类目下的产品");
            }
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
