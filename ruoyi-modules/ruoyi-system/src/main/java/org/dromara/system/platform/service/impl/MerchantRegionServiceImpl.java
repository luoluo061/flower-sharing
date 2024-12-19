package org.dromara.system.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;

import org.dromara.system.platform.domain.MerchantRegion;
import org.dromara.system.platform.domain.bo.MerchantRegionBo;
import org.dromara.system.platform.domain.vo.MerchantRegionVo;
import org.dromara.system.mapper.SysRegionMapper;
import org.dromara.system.platform.service.IMerchantRegionService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 行政区划代码Service业务层处理
 *
 * @author LionLi
 * @date 2024-11-11
 */
@RequiredArgsConstructor
@Service
public class MerchantRegionServiceImpl implements IMerchantRegionService {

    private final SysRegionMapper baseMapper;

    /**
     * 查询行政区划代码
     *
     * @param id 主键
     * @return 行政区划代码
     */
    @Override
    public MerchantRegionVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询行政区划代码列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 行政区划代码分页列表
     */
    @Override
    public TableDataInfo<MerchantRegionVo> queryPageList(MerchantRegionBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MerchantRegion> lqw = buildQueryWrapper(bo);
        Page<MerchantRegionVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的行政区划代码列表
     *
     * @param bo 查询条件
     * @return 行政区划代码列表
     */
    @Override
    public List<MerchantRegionVo> queryList(MerchantRegionBo bo) {
        LambdaQueryWrapper<MerchantRegion> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MerchantRegion> buildQueryWrapper(MerchantRegionBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MerchantRegion> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getCreatedBy() != null, MerchantRegion::getCreateBy, bo.getCreatedBy());
        lqw.eq(bo.getCreatedTime() != null, MerchantRegion::getCreateTime, bo.getCreatedTime());
        lqw.eq(bo.getUpdatedBy() != null, MerchantRegion::getUpdateBy, bo.getUpdatedBy());
        lqw.eq(bo.getUpdatedTime() != null, MerchantRegion::getUpdateTime, bo.getUpdatedTime());
        lqw.eq(StringUtils.isNotBlank(bo.getProvinceCode()), MerchantRegion::getProvinceCode, bo.getProvinceCode());
        lqw.eq(StringUtils.isNotBlank(bo.getCityCode()), MerchantRegion::getCityCode, bo.getCityCode());
        lqw.eq(StringUtils.isNotBlank(bo.getCountyCode()), MerchantRegion::getCountyCode, bo.getCountyCode());
        lqw.eq(StringUtils.isNotBlank(bo.getTownCode()), MerchantRegion::getTownCode, bo.getTownCode());
        lqw.eq(StringUtils.isNotBlank(bo.getStreetCode()), MerchantRegion::getStreetCode, bo.getStreetCode());
        lqw.like(StringUtils.isNotBlank(bo.getStreetName()), MerchantRegion::getStreetName, bo.getStreetName());
        lqw.eq(StringUtils.isNotBlank(bo.getStreetType()), MerchantRegion::getStreetType, bo.getStreetType());
        lqw.eq(bo.getLevelType() != null, MerchantRegion::getLevelType, bo.getLevelType());
        lqw.eq(bo.getEnabledDate() != null, MerchantRegion::getEnabledDate, bo.getEnabledDate());
        lqw.eq(bo.getCreatedDate() != null, MerchantRegion::getCreatedDate, bo.getCreatedDate());
        lqw.eq(bo.getDisabledDate() != null, MerchantRegion::getDisabledDate, bo.getDisabledDate());
        lqw.eq(StringUtils.isNotBlank(bo.getDisabledMark()), MerchantRegion::getDisabledMark, bo.getDisabledMark());
        lqw.eq(bo.getIsEnabled() != null, MerchantRegion::getIsEnabled, bo.getIsEnabled());
        lqw.eq(bo.getRegionType() != null, MerchantRegion::getRegionType, bo.getRegionType());
        return lqw;
    }

    /**
     * 新增行政区划代码
     *
     * @param bo 行政区划代码
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MerchantRegionBo bo) {
        MerchantRegion add = MapstructUtils.convert(bo, MerchantRegion.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改行政区划代码
     *
     * @param bo 行政区划代码
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MerchantRegionBo bo) {
        MerchantRegion update = MapstructUtils.convert(bo, MerchantRegion.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MerchantRegion entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除行政区划代码信息
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

    @Override
    public List<MerchantRegionVo> getRegionList(Long id) {
        if(id == null || id <= 0){
            id = 1L;
        }
        LambdaQueryWrapper<MerchantRegion> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MerchantRegion::getParentId, id);
        List<MerchantRegionVo> sysRegions = baseMapper.selectVoList(wrapper, MerchantRegionVo.class);
        return sysRegions;
    }
}
