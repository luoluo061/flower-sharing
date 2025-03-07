package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerOrder;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerPickAddrBo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.dromara.flower.domain.FolwerPickAddr;
import org.dromara.flower.mapper.FolwerPickAddrMapper;
import org.dromara.flower.service.IFolwerPickAddrService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 用户配送地址Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class FolwerPickAddrServiceImpl implements IFolwerPickAddrService {

    private final FolwerPickAddrMapper baseMapper;

    /**
     * 查询用户配送地址
     *
     * @param addrId 主键
     * @return 用户配送地址
     */
    @Override
    public FolwerPickAddrVo queryById(Long addrId){
        return baseMapper.selectVoById(addrId);
    }

    /**
     * 分页查询用户配送地址列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 用户配送地址分页列表
     */
    @Override
    public TableDataInfo<FolwerPickAddrVo> queryPageList(FolwerPickAddrBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerPickAddr> lqw = buildQueryWrapper(bo);
        Page<FolwerPickAddrVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的用户配送地址列表
     *
     * @param bo 查询条件
     * @return 用户配送地址列表
     */
    @Override
    public List<FolwerPickAddrVo> queryList(FolwerPickAddrBo bo) {
        LambdaQueryWrapper<FolwerPickAddr> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerPickAddr> buildQueryWrapper(FolwerPickAddrBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerPickAddr> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getAddrName()), FolwerPickAddr::getAddrName, bo.getAddrName());
        lqw.eq(StringUtils.isNotBlank(bo.getAddr()), FolwerPickAddr::getAddr, bo.getAddr());
        lqw.eq(StringUtils.isNotBlank(bo.getMobile()), FolwerPickAddr::getMobile, bo.getMobile());
        lqw.eq(bo.getProvinceId() != null, FolwerPickAddr::getProvinceId, bo.getProvinceId());
        lqw.eq(StringUtils.isNotBlank(bo.getProvince()), FolwerPickAddr::getProvince, bo.getProvince());
        lqw.eq(bo.getCityId() != null, FolwerPickAddr::getCityId, bo.getCityId());
        lqw.eq(bo.getUserId() != null, FolwerPickAddr::getUserId, bo.getUserId());
        lqw.eq(StringUtils.isNotBlank(bo.getCity()), FolwerPickAddr::getCity, bo.getCity());
        lqw.eq(bo.getAreaId() != null, FolwerPickAddr::getAreaId, bo.getAreaId());
        lqw.eq(StringUtils.isNotBlank(bo.getArea()), FolwerPickAddr::getArea, bo.getArea());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerPickAddr::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    /**
     * 新增用户配送地址
     *
     * @param bo 用户配送地址
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerPickAddrBo bo) {
        FolwerPickAddr add = MapstructUtils.convert(bo, FolwerPickAddr.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAddrId(add.getAddrId());
        }
        return flag;
    }

    /**
     * 修改用户配送地址
     *
     * @param bo 用户配送地址
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerPickAddrBo bo) {
        FolwerPickAddr update = MapstructUtils.convert(bo, FolwerPickAddr.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerPickAddr entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除用户配送地址信息
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
