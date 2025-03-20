package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserAuthlogBo;
import org.dromara.flowerapplet.service.IFlowerAppletUserAuthlogService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FlowerAppletUserAuthBo;
import org.dromara.flowerapplet.domain.vo.FlowerAppletUserAuthVo;
import org.dromara.flowerapplet.domain.FlowerAppletUserAuth;
import org.dromara.flowerapplet.mapper.FlowerAppletUserAuthMapper;
import org.dromara.flowerapplet.service.IFlowerAppletUserAuthService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 小程序用户信息认证Service业务层处理
 *
 * @author mlhxj
 * @date 2025-03-14
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FlowerAppletUserAuthServiceImpl implements IFlowerAppletUserAuthService {

    private final FlowerAppletUserAuthMapper baseMapper;

    private final IFlowerAppletUserAuthlogService flowerAppletUserAuthlogService;

    /**
     * 查询小程序用户信息认证
     *
     * @param authId 主键
     * @return 小程序用户信息认证
     */
    @Override
    public FlowerAppletUserAuthVo queryById(Long authId){
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
    public TableDataInfo<FlowerAppletUserAuthVo> queryPageList(FlowerAppletUserAuthBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FlowerAppletUserAuth> lqw = buildQueryWrapper(bo);
        Page<FlowerAppletUserAuthVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的小程序用户信息认证列表
     *
     * @param bo 查询条件
     * @return 小程序用户信息认证列表
     */
    @Override
    public List<FlowerAppletUserAuthVo> queryList(FlowerAppletUserAuthBo bo) {
        LambdaQueryWrapper<FlowerAppletUserAuth> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FlowerAppletUserAuth> buildQueryWrapper(FlowerAppletUserAuthBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FlowerAppletUserAuth> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FlowerAppletUserAuth::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getMerchantName()), FlowerAppletUserAuth::getMerchantName, bo.getMerchantName());
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantType()), FlowerAppletUserAuth::getMerchantType, bo.getMerchantType());
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantAuthPic()), FlowerAppletUserAuth::getMerchantAuthPic, bo.getMerchantAuthPic());
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantFacePic()), FlowerAppletUserAuth::getMerchantFacePic, bo.getMerchantFacePic());
        lqw.eq(StringUtils.isNotBlank(bo.getMerchantPic()), FlowerAppletUserAuth::getMerchantPic, bo.getMerchantPic());
        lqw.like(StringUtils.isNotBlank(bo.getContactName()), FlowerAppletUserAuth::getContactName, bo.getContactName());
        lqw.eq(StringUtils.isNotBlank(bo.getCardFrontPic()), FlowerAppletUserAuth::getCardFrontPic, bo.getCardFrontPic());
        lqw.eq(StringUtils.isNotBlank(bo.getCardBackPic()), FlowerAppletUserAuth::getCardBackPic, bo.getCardBackPic());
        lqw.eq(StringUtils.isNotBlank(bo.getContactCard()), FlowerAppletUserAuth::getContactCard, bo.getContactCard());
        lqw.eq(StringUtils.isNotBlank(bo.getContactPhone()), FlowerAppletUserAuth::getContactPhone, bo.getContactPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getDistrict()), FlowerAppletUserAuth::getDistrict, bo.getDistrict());
        lqw.eq(StringUtils.isNotBlank(bo.getAddDetail()), FlowerAppletUserAuth::getAddDetail, bo.getAddDetail());
        lqw.eq(bo.getStatus() != null, FlowerAppletUserAuth::getStatus, bo.getStatus());
        lqw.eq(bo.getIsPass() != null, FlowerAppletUserAuth::getIsPass, bo.getIsPass());
        return lqw;
    }

    /**
     * 新增小程序用户信息认证
     *
     * @param bo 小程序用户信息认证
     * @return 是否新增成功
     */
    @Override
    @Transactional
    public Boolean insertByBo(FlowerAppletUserAuthBo bo) {
//        FlowerAppletUserAuth add = MapstructUtils.convert(bo, FlowerAppletUserAuth.class);
        if (bo.getUserId() == null){
            throw new RuntimeException("用户ID不能为空");
        }
        FlowerAppletUserAuthBo authBo = new FlowerAppletUserAuthBo();
        authBo.setUserId(bo.getUserId());
        List<FlowerAppletUserAuthVo> flowerAppletUserAuthVos = this.queryList(authBo);
        if (flowerAppletUserAuthVos.size() > 0){
            throw new RuntimeException("用户已提交认证");
//            return false;
        }
        FlowerAppletUserAuth add = BeanUtil.copyProperties(bo, FlowerAppletUserAuth.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setAuthId(String.valueOf(add.getAuthId()));

            FlowerAppletUserAuthlogBo flowerAppletUserAuthlogBo = new FlowerAppletUserAuthlogBo();
            flowerAppletUserAuthlogBo.setUserId(add.getUserId());
            flowerAppletUserAuthlogBo.setAuthId(add.getAuthId());
            flowerAppletUserAuthlogBo.setStatus(0L);
            return flowerAppletUserAuthlogService.insertByBo(flowerAppletUserAuthlogBo);
        }
        return false;
    }

    /**
     * 修改小程序用户信息认证
     *
     * @param bo 小程序用户信息认证
     * @return 是否修改成功
     */
    @Override
    @Transactional
    public Boolean updateByBo(FlowerAppletUserAuthBo bo) {
//        FlowerAppletUserAuth update = MapstructUtils.convert(bo, FlowerAppletUserAuth.class);
        if (bo.getUserId() == null){
            throw new RuntimeException("用户ID不能为空");
        }
        FlowerAppletUserAuth update = BeanUtil.copyProperties(bo, FlowerAppletUserAuth.class);
        validEntityBeforeSave(update);
        if(baseMapper.updateById(update) > 0){
            FlowerAppletUserAuthlogBo flowerAppletUserAuthlogBo = new FlowerAppletUserAuthlogBo();
            flowerAppletUserAuthlogBo.setUserId(update.getUserId());
            flowerAppletUserAuthlogBo.setAuthId(update.getAuthId());
            flowerAppletUserAuthlogBo.setStatus(0L);
            return flowerAppletUserAuthlogService.insertByBo(flowerAppletUserAuthlogBo);
        }
        return false;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FlowerAppletUserAuth entity){
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
