package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.AppletUserAuthBo;
import org.dromara.flower.domain.vo.AppletUserAuthVo;
import org.dromara.flower.domain.AppletUserAuth;
import org.dromara.flower.mapper.AppletUserAuthMapper;
import org.dromara.flower.service.IAppletUserAuthService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序用户信息认证Service业务层处理
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@RequiredArgsConstructor
@Service
public class AppletUserAuthServiceImpl implements IAppletUserAuthService {

    private final AppletUserAuthMapper baseMapper;

    /**
     * 查询小程序用户信息认证
     *
     * @param authId 主键
     * @return 小程序用户信息认证
     */
    @Override
    public AppletUserAuthVo queryById(Long authId){
        return baseMapper.selectVoById(authId);
    }

    /**
     * 分页查询小程序用户信息认证列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息认证分页列表
     */
    @Override
    public TableDataInfo<AppletUserAuthVo> queryPageList(AppletUserAuthBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppletUserAuth> lqw = buildQueryWrapper(bo);
        Page<AppletUserAuthVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序用户信息认证列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息认证列表
     */
    @Override
    public List<AppletUserAuthVo> queryList(AppletUserAuthBo bo) {
        LambdaQueryWrapper<AppletUserAuth> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppletUserAuth> buildQueryWrapper(AppletUserAuthBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppletUserAuth> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, AppletUserAuth::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getMerchantName()), AppletUserAuth::getMerchantName, bo.getMerchantName());
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantType()), AppletUserAuth::getMerchantType, bo.getMerchantType());
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantAuthPic()), AppletUserAuth::getMerchantAuthPic, bo.getMerchantAuthPic());
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantFacePic()), AppletUserAuth::getMerchantFacePic, bo.getMerchantFacePic());
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantPic()), AppletUserAuth::getMerchantPic, bo.getMerchantPic());
        lqw.like(StringUtils.isNotBlank(bo.getContactName()), AppletUserAuth::getContactName, bo.getContactName());
        lqw.eq(StringUtils.isNotBlank(bo.getCardFrontPic()), AppletUserAuth::getCardFrontPic, bo.getCardFrontPic());
        lqw.eq(StringUtils.isNotBlank(bo.getCardBackPic()), AppletUserAuth::getCardBackPic, bo.getCardBackPic());
        lqw.eq(StringUtils.isNotBlank(bo.getContactCard()), AppletUserAuth::getContactCard, bo.getContactCard());
        lqw.eq(StringUtils.isNotBlank(bo.getContactPhone()), AppletUserAuth::getContactPhone, bo.getContactPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getDistrict()), AppletUserAuth::getDistrict, bo.getDistrict());
        lqw.eq(StringUtils.isNotBlank(bo.getAddDetail()), AppletUserAuth::getAddDetail, bo.getAddDetail());
        lqw.eq(bo.getStatus() != null, AppletUserAuth::getStatus, bo.getStatus());
        lqw.eq(bo.getIsPass() != null, AppletUserAuth::getIsPass, bo.getIsPass());
        return lqw;
    }

    /**
     * 新增小程序用户信息认证
     *
     * @param bo 小程序用户信息认证
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(AppletUserAuthBo bo) {
        AppletUserAuth add = MapstructUtils.convert(bo, AppletUserAuth.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAuthId(add.getAuthId());
        }
        return flag;
    }

    /**
     * 修改小程序用户信息认证
     *
     * @param bo 小程序用户信息认证
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(AppletUserAuthBo bo) {
        AppletUserAuth update = MapstructUtils.convert(bo, AppletUserAuth.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppletUserAuth entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除小程序用户信息认证信息
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
