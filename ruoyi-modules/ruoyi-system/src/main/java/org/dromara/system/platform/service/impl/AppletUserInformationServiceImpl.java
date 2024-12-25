package org.dromara.system.platform.service.impl;

import org.dromara.common.core.enums.Status;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.system.platform.domain.AppletUserInformation;
import org.dromara.system.platform.domain.bo.AppletUserInformationBo;
import org.dromara.system.platform.domain.vo.AppletUserInformationVo;
import org.dromara.system.platform.mapper.AppletUserInformationMapper;
import org.dromara.system.platform.service.IAppletUserInformationService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序用户信息Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class AppletUserInformationServiceImpl implements IAppletUserInformationService {

    private final AppletUserInformationMapper baseMapper;

    /**
     * 查询小程序用户信息
     *
     * @param userId 主键
     * @return 小程序用户信息
     */
    @Override
    public AppletUserInformationVo queryById(Long userId){
        return baseMapper.selectVoById(userId);
    }

    /**
     * 分页查询小程序用户信息列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息分页列表
     */
    @Override
    public TableDataInfo<AppletUserInformationVo> queryPageList(AppletUserInformationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<AppletUserInformation> lqw = buildQueryWrapper(bo);
        Page<AppletUserInformationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序用户信息列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息列表
     */
    @Override
    public List<AppletUserInformationVo> queryList(AppletUserInformationBo bo) {
        LambdaQueryWrapper<AppletUserInformation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<AppletUserInformation> buildQueryWrapper(AppletUserInformationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<AppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, AppletUserInformation::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getMemberId()), AppletUserInformation::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), AppletUserInformation::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getNickName()), AppletUserInformation::getNickName, bo.getNickName());
        lqw.eq(bo.getAvatarUrl() != null, AppletUserInformation::getAvatarUrl, bo.getAvatarUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getUserType()), AppletUserInformation::getUserType, bo.getUserType());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), AppletUserInformation::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getIdNumber()), AppletUserInformation::getIdNumber, bo.getIdNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getOpenid()), AppletUserInformation::getOpenid, bo.getOpenid());
        lqw.eq(bo.getStatus() != null, AppletUserInformation::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getWechatNumber()), AppletUserInformation::getWechatNumber, bo.getWechatNumber());
        lqw.eq(bo.getGroupId() != null, AppletUserInformation::getGroupId, bo.getGroupId());
        lqw.eq(bo.getMemberLevelId() != null, AppletUserInformation::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(bo.getGender() != null, AppletUserInformation::getGender, bo.getGender());
        lqw.eq(bo.getPoints() != null, AppletUserInformation::getPoints, bo.getPoints());
        lqw.eq(bo.getAmount() != null, AppletUserInformation::getAmount, bo.getAmount());
        lqw.eq(bo.getTotal() != null, AppletUserInformation::getTotal, bo.getTotal());
        lqw.eq(bo.getPromotion() != null, AppletUserInformation::getPromotion, bo.getPromotion());
        lqw.eq(bo.getGold() != null, AppletUserInformation::getGold, bo.getGold());
        lqw.eq(bo.getParentId() != null, AppletUserInformation::getParentId, bo.getParentId());
        lqw.eq(StringUtils.isNotBlank(bo.getDistrict()), AppletUserInformation::getDistrict, bo.getDistrict());
        lqw.eq(StringUtils.isNotBlank(bo.getAddDetail()), AppletUserInformation::getAddDetail, bo.getAddDetail());
        return lqw;
    }

    /**
     * 新增小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(AppletUserInformationBo bo) {
        AppletUserInformation add = MapstructUtils.convert(bo, AppletUserInformation.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setUserId(add.getUserId());
        }
        return flag;
    }

    /**
     * 修改小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(AppletUserInformationBo bo) {
        AppletUserInformation update = MapstructUtils.convert(bo, AppletUserInformation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(AppletUserInformation entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除小程序用户信息信息
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
    public AppletUserInformationVo getByPhone(String phone) {
        LambdaQueryWrapper<AppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(AppletUserInformation::getPhone, phone);
        AppletUserInformationVo appletUserInformationVo = baseMapper.selectVoOne(lqw);
        return appletUserInformationVo;
    }

    /**
     * 通过openid获取user
     * @param openId
     * @return
     */
    @Override
    public AppletUserInformationVo getByOpenId(String openId) {
        LambdaQueryWrapper<AppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(AppletUserInformation::getOpenid, openId);
        AppletUserInformationVo appletUserInformationVo = baseMapper.selectVoOne(lqw);
        return appletUserInformationVo;
    }

    /**
     * 更新用户状态
     * @param id
     * @param status
     * @return
     */
    @Override
    public boolean updateStatus(Long id, String status) {
        if(Status.DISABLE.equals(status) || Status.ENABLE.equals(status)){
            return false;
        }
        return false;
    }

}
