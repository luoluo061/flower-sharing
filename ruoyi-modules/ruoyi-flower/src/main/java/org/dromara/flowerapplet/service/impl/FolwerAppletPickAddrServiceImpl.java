package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.FolwerPickAddr;
import org.dromara.flower.domain.bo.FolwerPickAddrBo;
import org.dromara.flower.domain.vo.FolwerPickAddrVo;
import org.dromara.flower.mapper.FolwerPickAddrMapper;
import org.dromara.flowerapplet.service.IFolwerAppletPickAddrService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 用户配送地址Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class FolwerAppletPickAddrServiceImpl implements IFolwerAppletPickAddrService {

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
        lqw.eq(StringUtils.isNotBlank(bo.getCity()), FolwerPickAddr::getCity, bo.getCity());
        lqw.eq(bo.getAreaId() != null, FolwerPickAddr::getAreaId, bo.getAreaId());
        lqw.eq(StringUtils.isNotBlank(bo.getArea()), FolwerPickAddr::getArea, bo.getArea());
        lqw.eq(bo.getUserId() != null, FolwerPickAddr::getUserId, bo.getUserId());
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
        LoginUser loginUser = getLoginUser();
        if (loginUser == null){
            return false;
        }
        FolwerPickAddr add = MapstructUtils.convert(bo, FolwerPickAddr.class);
        add.setStatus(1L);
        // 判断当前用户是否有默认地址 不存在设置当前记录位
        LambdaQueryWrapper<FolwerPickAddr> lqw = new LambdaQueryWrapper<>();
        lqw.eq(FolwerPickAddr::getCreateBy, loginUser.getUserId());
        lqw.eq(FolwerPickAddr::getStatus, 1);
        FolwerPickAddrVo folwerPickAddrVo = baseMapper.selectVoOne(lqw);
        if (folwerPickAddrVo != null){
            add.setStatus(0L);
        }
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

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户的信息，如果用户未登录则返回 null
     */
    private LoginUser getLoginUser() {
        LoginUser loginUser;
        try {
            loginUser = LoginHelper.getLoginUser();
        } catch (Exception e) {
            log.warn("自动注入警告 => 用户未登录");
            return new LoginUser();
        }
        return loginUser;
    }

}
