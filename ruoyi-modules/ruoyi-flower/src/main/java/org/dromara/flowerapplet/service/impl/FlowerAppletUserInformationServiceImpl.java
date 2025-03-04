package org.dromara.flowerapplet.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserInformationBo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletUserInformationVo;
import org.dromara.flowerapplet.domain.FlowerAppletUserInformation;
import org.dromara.flowerapplet.mapper.FlowerAppletUserInformationMapper;
import org.dromara.flowerapplet.service.IFlowerAppletUserInformationService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序用户信息Service业务层处理
 *
 * @author mlhxj
 * @date 2025-03-03
 */
@RequiredArgsConstructor
@Service
public class FlowerAppletUserInformationServiceImpl implements IFlowerAppletUserInformationService {

    private final FlowerAppletUserInformationMapper baseMapper;

    /**
     * 查询小程序用户信息
     *
     * @param userId 主键
     * @return 小程序用户信息
     */
    @Override
    public FlowerAppletUserInformationVo queryById(Long userId){
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
    public TableDataInfo<FlowerAppletUserInformationVo> queryPageList(FlowerAppletUserInformationBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerAppletUserInformation> lqw = buildQueryWrapper(bo);
        Page<FlowerAppletUserInformationVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序用户信息列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息列表
     */
    @Override
    public List<FlowerAppletUserInformationVo> queryList(FlowerAppletUserInformationBo bo) {
        LambdaQueryWrapper<FlowerAppletUserInformation> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerAppletUserInformation> buildQueryWrapper(FlowerAppletUserInformationBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerAppletUserInformation> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, FlowerAppletUserInformation::getDeptId, bo.getDeptId());
        lqw.eq(StringUtils.isNotBlank(bo.getMemberId()), FlowerAppletUserInformation::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), FlowerAppletUserInformation::getName, bo.getName());
        lqw.like(StringUtils.isNotBlank(bo.getNickName()), FlowerAppletUserInformation::getNickName, bo.getNickName());
        lqw.eq(bo.getAvatarUrl() != null, FlowerAppletUserInformation::getAvatarUrl, bo.getAvatarUrl());
        lqw.eq(StringUtils.isNotBlank(bo.getUserType()), FlowerAppletUserInformation::getUserType, bo.getUserType());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), FlowerAppletUserInformation::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getIdNumber()), FlowerAppletUserInformation::getIdNumber, bo.getIdNumber());
        lqw.eq(StringUtils.isNotBlank(bo.getOpenid()), FlowerAppletUserInformation::getOpenid, bo.getOpenid());
        lqw.eq(bo.getStatus() != null, FlowerAppletUserInformation::getStatus, bo.getStatus());
        lqw.eq(StringUtils.isNotBlank(bo.getWechatNumber()), FlowerAppletUserInformation::getWechatNumber, bo.getWechatNumber());
        lqw.eq(bo.getGroupId() != null, FlowerAppletUserInformation::getGroupId, bo.getGroupId());
        lqw.eq(bo.getMemberLevelId() != null, FlowerAppletUserInformation::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(bo.getGender() != null, FlowerAppletUserInformation::getGender, bo.getGender());
        lqw.eq(bo.getPoints() != null, FlowerAppletUserInformation::getPoints, bo.getPoints());
        lqw.eq(bo.getAmount() != null, FlowerAppletUserInformation::getAmount, bo.getAmount());
        lqw.eq(bo.getTotal() != null, FlowerAppletUserInformation::getTotal, bo.getTotal());
        lqw.eq(bo.getPromotion() != null, FlowerAppletUserInformation::getPromotion, bo.getPromotion());
        lqw.eq(bo.getGold() != null, FlowerAppletUserInformation::getGold, bo.getGold());
        lqw.eq(bo.getParentId() != null, FlowerAppletUserInformation::getParentId, bo.getParentId());
        lqw.eq(StringUtils.isNotBlank(bo.getDistrict()), FlowerAppletUserInformation::getDistrict, bo.getDistrict());
        lqw.eq(StringUtils.isNotBlank(bo.getAddDetail()), FlowerAppletUserInformation::getAddDetail, bo.getAddDetail());
        lqw.eq(StringUtils.isNotBlank(bo.getBirthday()), FlowerAppletUserInformation::getBirthday, bo.getBirthday());
        return lqw;
    }

    /**
     * 新增小程序用户信息
     *
     * @param bo 小程序用户信息
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FlowerAppletUserInformationBo bo) {
        FlowerAppletUserInformation add = MapstructUtils.convert(bo, FlowerAppletUserInformation.class);
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
    public Boolean updateByBo(FlowerAppletUserInformationBo bo) {
        FlowerAppletUserInformation update = MapstructUtils.convert(bo, FlowerAppletUserInformation.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerAppletUserInformation entity){
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
}
