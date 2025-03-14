package org.dromara.flower.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.bo.AppletUserAuthBo;
import org.dromara.flower.domain.vo.AppletUserAuthVo;
import org.dromara.flower.platform.domain.bo.AppletUserInformationBo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.IAppletUserAuthService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.AppletUserAuthlogBo;
import org.dromara.flower.domain.vo.AppletUserAuthlogVo;
import org.dromara.flower.domain.AppletUserAuthlog;
import org.dromara.flower.mapper.AppletUserAuthlogMapper;
import org.dromara.flower.service.IAppletUserAuthlogService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序用户信息认证记录Service业务层处理
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@RequiredArgsConstructor
@Service
public class AppletUserAuthlogServiceImpl implements IAppletUserAuthlogService {

    private final AppletUserAuthlogMapper baseMapper;

    private final IAppletUserAuthService appletUserAuthService;

    private final IAppletUserInformationService appletUserInformationService;

    /**
     * 查询小程序用户信息认证记录
     *
     * @param authlogId 主键
     * @return 小程序用户信息认证记录
     */
    @Override
    public AppletUserAuthlogVo queryById(Long authlogId){
        return baseMapper.selectVoById(authlogId);
    }

    /**
     * 分页查询小程序用户信息认证记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息认证记录分页列表
     */
    @Override
    public TableDataInfo<AppletUserAuthlogVo> queryPageList(AppletUserAuthlogBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppletUserAuthlog> lqw = buildQueryWrapper(bo);
        Page<AppletUserAuthlogVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序用户信息认证记录列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息认证记录列表
     */
    @Override
    public List<AppletUserAuthlogVo> queryList(AppletUserAuthlogBo bo) {
        LambdaQueryWrapper<AppletUserAuthlog> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppletUserAuthlog> buildQueryWrapper(AppletUserAuthlogBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppletUserAuthlog> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, AppletUserAuthlog::getUserId, bo.getUserId());
        lqw.eq(bo.getAuthId() != null, AppletUserAuthlog::getAuthId, bo.getAuthId());
        lqw.eq(bo.getStatus() != null, AppletUserAuthlog::getStatus, bo.getStatus());
        lqw.eq(bo.getIsPass() != null, AppletUserAuthlog::getIsPass, bo.getIsPass());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), AppletUserAuthlog::getRemarks, bo.getRemarks());
        lqw.eq(bo.getAuthTime() != null, AppletUserAuthlog::getAuthTime, bo.getAuthTime());
        return lqw;
    }

    /**
     * 新增小程序用户信息认证记录
     *
     * @param bo 小程序用户信息认证记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(AppletUserAuthlogBo bo) {
        AppletUserAuthlog add = MapstructUtils.convert(bo, AppletUserAuthlog.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAuthlogId(add.getAuthlogId());
        }
        return flag;
    }

    /**
     * 修改小程序用户信息认证记录
     *
     * @param bo 小程序用户信息认证记录
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public Boolean updateByBo(AppletUserAuthlogBo bo) {
        bo.setAuthTime(new Date());
        AppletUserAuthlog update = MapstructUtils.convert(bo, AppletUserAuthlog.class);
        validEntityBeforeSave(update);
        if (baseMapper.updateById(update) > 0){
            AppletUserAuthVo appletUserAuthVo = appletUserAuthService.queryById(update.getAuthId());
            AppletUserAuthBo appletUserAuthBo = BeanUtil.copyProperties(appletUserAuthVo, AppletUserAuthBo.class);
            appletUserAuthBo.setStatus(update.getStatus());
            appletUserAuthBo.setIsPass(update.getIsPass());
            Boolean b = appletUserAuthService.updateByBo(appletUserAuthBo);
            if (b){
                if (appletUserAuthBo.getIsPass() == 1L){
                    AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(appletUserAuthBo.getUserId());
                    AppletUserInformationBo appletUserInformationBo = BeanUtil.copyProperties(appletUserInformationVo, AppletUserInformationBo.class);
                    appletUserInformationBo.setIsAuth(1L);
                    return appletUserInformationService.updateByBo(appletUserInformationBo);
                }
                return false;
            }
        }
        return false;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppletUserAuthlog entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除小程序用户信息认证记录信息
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
