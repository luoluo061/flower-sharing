package org.dromara.flower.service.impl;

import jakarta.annotation.Resource;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.vo.FolwerCategoryVo;
import org.dromara.flower.domain.vo.FolwerSkuVo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.service.IFolwerCategoryService;
import org.dromara.flower.service.IFolwerSkuService;
import org.dromara.flowerapplet.domain.FolwerAppletCategory;
import org.dromara.system.domain.SysOss;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.ISysOssService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerProductBo;
import org.dromara.flower.domain.vo.FolwerProductVo;
import org.dromara.flower.domain.FolwerProduct;
import org.dromara.flower.mapper.FolwerProductMapper;
import org.dromara.flower.service.IFolwerProductService;

import java.math.BigDecimal;
import java.util.*;

/**
 * 商品管理Service业务层处理
 *
 * @author Lion Li
 * @date 2024-12-20
 */
@RequiredArgsConstructor
@Service
public class FolwerProductServiceImpl implements IFolwerProductService {

    @Resource
    private final FolwerProductMapper baseMapper;

    private final IFolwerCategoryService folwerCategoryService;

    private final ISysOssService sysOssService;

    @Resource
    private final SysOssMapper sysOssMapper;

    private final IFolwerSkuService folwerSkuService;

    /**
     * 查询商品管理
     *
     * @param id 主键
     * @return 商品管理
     */
    @Override
    public FolwerProductVo queryById(Long id) {
        FolwerProductVo folwerProductVo = baseMapper.selectVoById(id);
        if (folwerProductVo != null) {
            // 设置分类名称
            String categoryName = null;
            FolwerCategoryVo folwerCategoryVo = folwerCategoryService.queryById(folwerProductVo.getCategoryId());
            if (folwerCategoryVo != null){
                if (!folwerCategoryVo.getParentId().equals(0L)) {
                    FolwerCategoryVo ParentFolwerCategoryVo = folwerCategoryService.queryById(folwerCategoryVo.getParentId());
                    categoryName = ParentFolwerCategoryVo.getCategoryName();
                }
                if (categoryName == null) {
                    categoryName = folwerCategoryVo.getCategoryName();
                } else {
                    categoryName = categoryName + "/" + folwerCategoryVo.getCategoryName();
                }
                folwerProductVo.setCategoryName(categoryName);
            }  else {
                folwerProductVo.setCategoryName("");
            }

            // 设置图片Url
//            if (folwerProductVo.getProductListPictureUrl() != null && !folwerProductVo.getProductListPictureUrl().isEmpty()) {
//                Collection<Long> ossIds = new ArrayList<>();
//                ossIds.add(Long.valueOf(folwerProductVo.getProductListPictureUrl()));
//                Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
//                if (!stringStringMap.isEmpty()) {
//                    // 设置图片Url
//                    folwerProductVo.setProductListPicture(stringStringMap.get(folwerProductVo.getProductListPictureUrl()));
//                }
//            }

//            if (folwerProductVo.getNormsType().equals(1L)) {
//                List<FolwerSkuVo> folwerSkuVos = folwerSkuService.queryListByProdId(folwerProductVo.getId());
////                List<FolwerSkuVo> folwerSkuVos = intermediateBean.getFolwerSkuVos(folwerProductVo.getId());
//                folwerProductVo.setProdSKU(folwerSkuVos);
//            }
        }
        return folwerProductVo;
    }

    /**
     * 分页查询商品管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 商品管理分页列表
     */
    @Override
    public TableDataInfo<FolwerProductVo> queryPageList(FolwerProductBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerProduct> lqw = buildQueryWrapper(bo);
        Page<FolwerProductVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        if (!result.getRecords().isEmpty()){
            for (FolwerProductVo vo : result.getRecords()){
                if (vo.getId() != null){
                    // 设置分类名称
                    String categoryName = null;
                    FolwerCategoryVo folwerCategoryVo = folwerCategoryService.queryById(vo.getCategoryId());
                    if(folwerCategoryVo == null){
                        throw new RuntimeException(vo.getCategoryId() + "分类不存在");
                    }
                    if(!folwerCategoryVo.getParentId().equals(0L) && folwerCategoryVo.getParentId() != null && folwerCategoryVo.getParentId().toString().length() >= 19){
                        FolwerCategoryVo ParentFolwerCategoryVo = folwerCategoryService.queryById(folwerCategoryVo.getParentId());
                        categoryName = ParentFolwerCategoryVo.getCategoryName();
                    }
                    if(categoryName == null){
                        categoryName = folwerCategoryVo.getCategoryName();
                    }else {
                        categoryName = categoryName + "/" + folwerCategoryVo.getCategoryName();
                    }
                    vo.setCategoryName(categoryName);
                }else {
                    vo.setCategoryName("");
                }
            }

//            List<Long> longList = new ArrayList<>();
//            result.getRecords().forEach(record ->{
//                if (record.getProductListPictureUrl() != null && !record.getProductListPictureUrl().isEmpty()){
//                    longList.add(Long.valueOf(record.getProductListPictureUrl()));
//                }
//                //多规格
//                if (record.getNormsType().equals(1L)){
//                    List<FolwerSkuVo> folwerSkuVos = folwerSkuService.queryListByProdId(record.getId());
////                    List<FolwerSkuVo> folwerSkuVos = intermediateBean.getFolwerSkuVos(record.getId());
//                    record.setProdSKU(folwerSkuVos);
//                }
//            });

//            if (!longList.isEmpty()){
//                Map<String, String> longStringMap = sysOssService.listUrlByIds(longList);
//                if (!longStringMap.isEmpty()){
//                    // 设置图片Url
//                    result.getRecords().forEach(record ->
//                        record.setProductListPicture(longStringMap.get(record.getProductListPictureUrl()))
//                    );
//                }
//            }
        }
        return TableDataInfo.build(result);
    }

    /**
     *
     * 查询符合条件的商品管理列表
     *
     * @param bo 查询条件
     * @return 商品管理列表
     */
    @Override
    public List<FolwerProductVo> queryList(FolwerProductBo bo) {
        LambdaQueryWrapper<FolwerProduct> lqw = buildQueryWrapper(bo);
        List<FolwerProductVo> folwerProductVos = baseMapper.selectVoList(lqw);
        for (FolwerProductVo vo : folwerProductVos){
            // 设置分类名称
            String categoryName = null;
            FolwerCategoryVo folwerCategoryVo = folwerCategoryService.queryById(vo.getCategoryId());
            if (folwerCategoryVo != null){
                if (!folwerCategoryVo.getParentId().equals(0L)) {
                    FolwerCategoryVo ParentFolwerCategoryVo = folwerCategoryService.queryById(folwerCategoryVo.getParentId());
                    categoryName = ParentFolwerCategoryVo.getCategoryName();
                }
                if (categoryName == null) {
                    categoryName = folwerCategoryVo.getCategoryName();
                } else {
                    categoryName = categoryName + "/" + folwerCategoryVo.getCategoryName();
                }
                vo.setCategoryName(categoryName);
            }  else {
                vo.setCategoryName("");
            }

//            if (vo.getProductListPictureUrl() != null && !vo.getProductListPictureUrl().isEmpty())
//            {
//                Collection<Long> ossIds = new ArrayList<>();
//
//                ossIds.add(Long.valueOf(vo.getProductListPictureUrl()));
//                Map<String, String> stringStringMap = sysOssService.listUrlByIds(ossIds);
//                if (!stringStringMap.isEmpty()){
//                    // 设置图片Url
//                    vo.setProductListPicture(stringStringMap.get(vo.getProductListPictureUrl()));
//                }
//            }

//            if (vo.getNormsType().equals(1L)) {
//                List<FolwerSkuVo> folwerSkuVos = folwerSkuService.queryListByProdId(vo.getId());
//                vo.setProdSKU(folwerSkuVos);
//            }

        }
        return folwerProductVos;
    }

    private LambdaQueryWrapper<FolwerProduct> buildQueryWrapper(FolwerProductBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerProduct> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getProductName()), FolwerProduct::getProductName, bo.getProductName());
        lqw.eq(bo.getId() != null, FolwerProduct::getId, bo.getId());
        lqw.eq(StringUtils.isNotBlank(bo.getProductListPictureUrl()), FolwerProduct::getProductListPictureUrl, bo.getProductListPictureUrl());
        lqw.eq(bo.getCategoryId() != null, FolwerProduct::getCategoryId, bo.getCategoryId());
        lqw.eq(bo.getOriPrice() != null, FolwerProduct::getOriPrice, bo.getOriPrice());
        lqw.eq(bo.getSoldNum() != null, FolwerProduct::getSoldNum, bo.getSoldNum());
        lqw.eq(bo.getTotalStocks() != null, FolwerProduct::getTotalStocks, bo.getTotalStocks());
        lqw.eq(bo.getStatus() != null, FolwerProduct::getStatus, bo.getStatus());
        lqw.eq(bo.getIfRefund() != null, FolwerProduct::getIfRefund, bo.getIfRefund());
        lqw.eq(bo.getSeq() != null, FolwerProduct::getSeq, bo.getSeq());
        lqw.eq(bo.getIsRecommend() != null, FolwerProduct::getIsRecommend, bo.getIsRecommend());

        lqw.eq(bo.getIfEarlyWarning() != null, FolwerProduct::getIfEarlyWarning, bo.getIfEarlyWarning());
        lqw.eq(bo.getInventoryEarlyWarningNum() != null, FolwerProduct::getInventoryEarlyWarningNum, bo.getInventoryEarlyWarningNum());
        lqw.eq(bo.getInventoryEarlyWarningProportion() != null, FolwerProduct::getInventoryEarlyWarningProportion, bo.getInventoryEarlyWarningProportion());

        lqw.like(StringUtils.isNotBlank(bo.getColor()), FolwerProduct::getColor, bo.getColor());
        lqw.like(StringUtils.isNotBlank(bo.getColorCode()), FolwerProduct::getColorCode, bo.getColorCode());
        lqw.like(StringUtils.isNotBlank(bo.getColorPic()), FolwerProduct::getColorPic, bo.getColorPic());
        lqw.like(StringUtils.isNotBlank(bo.getLevel()), FolwerProduct::getLevel, bo.getLevel());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerProduct::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    /**
     * 新增商品管理
     *
     * @param bo 商品管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerProductBo bo) {
        FolwerProduct FolwerProduct = new FolwerProduct();
        BeanUtils.copyProperties(bo,FolwerProduct);
//        FolwerProduct.setWeight(Double.parseDouble(bo.getWeight()));
        if (bo.getDeliveryPrice() != null){
            FolwerProduct.setDeliveryPrice(new BigDecimal(bo.getDeliveryPrice()));
        }else {
            FolwerProduct.setDeliveryPrice(new BigDecimal(0));
        }

        validEntityBeforeSave(FolwerProduct);
        boolean flag = baseMapper.insert(FolwerProduct) > 0;
        if (flag) {
            bo.setId(FolwerProduct.getId());
        }
        return flag;
    }

    /**
     * 修改商品管理
     *
     * @param bo 商品管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerProductBo bo) {
        FolwerProduct update = MapstructUtils.convert(bo, FolwerProduct.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerProduct entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除商品管理信息
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

    /**
     * 校验并批量修改商品状态
     *
     * @param ids     待修改的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否修改成功
     */
    @Override
    public Boolean updateStatusByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }

        if (ids.size() == 0)
        {
            return false;
        }

        for (Long id : ids)
        {
            FolwerProduct product = baseMapper.selectById(id);
            product.setStatus((Long.valueOf(product.getStatus() == 1 ? 0 : 0)));
            baseMapper.updateById(product);
        }

        return true;
    }
}
