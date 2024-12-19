package org.dromara.system.platform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.enums.Status;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;

import org.dromara.system.platform.domain.AppletUserInformation;
import org.dromara.system.platform.domain.bo.AppletUserInformationBo;

import org.dromara.system.platform.domain.query.AppletUserInformationQuery;
import org.dromara.system.platform.domain.vo.AppletUserInformationVo;
import org.dromara.system.platform.domain.vo.AppletUserOrderNumVo;
import org.dromara.system.platform.domain.vo.AppletUserOrderVo;
import org.dromara.system.platform.mapper.AppletUserInformationMapper;
import org.dromara.system.platform.service.IAppletUserInformationService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

/**
 * 小程序用户信息Service业务层处理
 *
 * @author LionLi
 * @date 2024-11-19
 */
@RequiredArgsConstructor
@Service
public class AppletUserInformationServiceImpl implements IAppletUserInformationService {

    private final AppletUserInformationMapper baseMapper;

    /**
     * 查询小程序用户信息
     *
     * @param id 主键
     * @return 小程序用户信息
     */
    @Override
    public AppletUserInformationVo queryById(Long id){
        /*AppletUserInformationVo appletUserInformationvo = baseMapper.selectVoById(id);
        AppletUserInfoVo appletUserInfoVo = new AppletUserInfoVo();
        BeanUtil.copyProperties(appletUserInformationvo,appletUserInfoVo);

        AppletUserRank appletUserRank = rankMapper.selectById(appletUserInformationvo.getId());
        AppletUserGroup appletUserGroup = groupMapper.selectById(appletUserInformationvo.getGroupId());
        if(ObjectUtil.isNotNull(appletUserRank)){
            appletUserInfoVo.setRankName(appletUserRank.getRankName());
        }
        if(ObjectUtil.isNotNull(appletUserGroup)){
            appletUserInfoVo.setGroupName(appletUserGroup.getGroupName());
        }

        appletUserInfoVo.setLabelName(appletUserLabelService.listByUserId(appletUserInfoVo.getId()));*/

        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询小程序用户信息列表
     *
     * @param query        查询条件
     * @param pageQuery 分页参数
     * @return 小程序用户信息分页列表
     */
    @Override
    public TableDataInfo<AppletUserInformationVo> queryPageList(AppletUserInformationQuery query, PageQuery pageQuery) {
        LambdaQueryWrapper<AppletUserInformation> lqw = buildQueryWrapper(query);
        Page<AppletUserInformationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        return TableDataInfo.build(result);

        /*Page<AppletUserInformationVo> page = new Page<>(pageQuery.getPageNum(), pageQuery.getPageSize());

        IPage<AppletUserInformationVo> appletUserInformationVoIPage = baseMapper.selectUsersByPage(page, query);
        return TableDataInfo.build(appletUserInformationVoIPage);*/

    }

    /**
     * 查询符合条件的小程序用户信息列表
     *
     * @param query 查询条件
     * @return 小程序用户信息列表
     */
    @Override
    public List<AppletUserInformationVo> queryList(AppletUserInformationQuery query) {
        LambdaQueryWrapper<AppletUserInformation> lqw = buildQueryWrapper(query);
        return baseMapper.selectVoList(lqw);
        //return baseMapper.queryList(query);
    }

    private LambdaQueryWrapper<AppletUserInformation> buildQueryWrapper(AppletUserInformationQuery query) {
        LambdaQueryWrapper<AppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(query.getUserType() != null, AppletUserInformation::getUserType, query.getUserType());
        lqw.like(StringUtils.isNotBlank(query.getName()), AppletUserInformation::getName, query.getName());

        lqw.eq(StringUtils.isNotBlank(query.getPhone()), AppletUserInformation::getPhone, query.getPhone());
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

    @Override
    public AppletUserInformationVo getByPhone(String phone) {
        LambdaQueryWrapper<AppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(AppletUserInformation::getPhone, phone);
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
            return baseMapper.updateStatus(id,status);
        }
        return false;
    }

    /**
     * 获取小程序用户订单
     * @param id
     * @return
     */
    @Override
    public List<AppletUserOrderVo> getAppletUserOrderById(Long id) {
        return baseMapper.selectOrder(id);
    }

    /**
     * 获取小程序用户订单数
     * @param id
     * @return
     */
    @Override
    public AppletUserOrderNumVo getAppletUserOrderNumById(Long id) {
        return baseMapper.selectOrderNum(id);
    }
}
