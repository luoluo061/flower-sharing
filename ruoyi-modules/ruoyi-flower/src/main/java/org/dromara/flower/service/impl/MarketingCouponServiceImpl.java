package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.MarketingCouponReceive;
import org.dromara.flower.mapper.MarketingCouponReceiveMapper;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.service.IFolwerCouponService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MarketingCouponBo;
import org.dromara.flower.domain.vo.MarketingCouponVo;
import org.dromara.flower.domain.MarketingCoupon;
import org.dromara.flower.mapper.MarketingCouponMapper;
import org.dromara.flower.service.IMarketingCouponService;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 优惠卷管理Service业务层处理
 *
 * @author chy
 * @date 2025-01-08
 */
@RequiredArgsConstructor
@Service
public class MarketingCouponServiceImpl implements IMarketingCouponService {

    private final MarketingCouponMapper baseMapper;

    private final AppletUserInformationMapper appletUserInformationMapper;


    private final MarketingCouponReceiveMapper couponReceiveMapper;


    /**
     * 查询优惠卷管理
     *
     * @param id 主键
     * @return 优惠卷管理
     */
    @Override
    public MarketingCouponVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询优惠卷管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠卷管理分页列表
     */
    @Override
    public TableDataInfo<MarketingCouponVo> queryPageList(MarketingCouponBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MarketingCoupon> lqw = buildQueryWrapper(bo);
        lqw.orderByAsc(MarketingCoupon::getSorting);

        Page<MarketingCouponVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        // 刷新已过期的优惠券
        List<MarketingCouponVo> records = result.getRecords();
        for (MarketingCouponVo record : records) {
            Date endTime = record.getEndTime();
            Date  currentTime= new Date();
            if (endTime.before(currentTime) || record.getSurplusNumber().equals(0L)){
                record.setState(0L);
                UpdateWrapper<MarketingCoupon> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",record.getId());
                updateWrapper.set("state",record.getState());

                if (baseMapper.update(updateWrapper)<0) throw new ServiceException("刷新失败!");
            }
        }


        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的优惠卷管理列表
     *
     * @param bo 查询条件
     * @return 优惠卷管理列表
     */
    @Override
    public List<MarketingCouponVo> queryList(MarketingCouponBo bo) {
        LambdaQueryWrapper<MarketingCoupon> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MarketingCoupon> buildQueryWrapper(MarketingCouponBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MarketingCoupon> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MarketingCoupon::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getCouponName()), MarketingCoupon::getCouponName, bo.getCouponName());
        lqw.eq(StringUtils.isNotBlank(bo.getCouponDescription()), MarketingCoupon::getCouponDescription, bo.getCouponDescription());
        lqw.eq(bo.getApplicableCategory() != null, MarketingCoupon::getApplicableCategory, bo.getApplicableCategory());
        lqw.eq(StringUtils.isNotBlank(bo.getClassificationId()), MarketingCoupon::getClassificationId, bo.getClassificationId());
        lqw.eq(StringUtils.isNotBlank(bo.getGoodsId()), MarketingCoupon::getGoodsId, bo.getGoodsId());
        lqw.eq(bo.getCouponType() != null, MarketingCoupon::getCouponType, bo.getCouponType());
        lqw.eq(bo.getCouponKind() != null, MarketingCoupon::getCouponKind, bo.getCouponKind());
        lqw.eq(bo.getStartTime() != null, MarketingCoupon::getStartTime, bo.getStartTime());
        lqw.eq(bo.getEndTime() != null, MarketingCoupon::getEndTime, bo.getEndTime());
        lqw.eq(bo.getFullReductionAmount() != null, MarketingCoupon::getFullReductionAmount, bo.getFullReductionAmount());
        lqw.eq(bo.getCouponSum() != null, MarketingCoupon::getCouponSum, bo.getCouponSum());
        lqw.eq(bo.getCouponNumber() != null, MarketingCoupon::getCouponNumber, bo.getCouponNumber());
        lqw.eq(bo.getSorting() != null, MarketingCoupon::getSorting, bo.getSorting());
        lqw.eq(bo.getState() != null, MarketingCoupon::getState, bo.getState());
        lqw.eq(StringUtils.isNotBlank(bo.getSpecificMembershipLevel()), MarketingCoupon::getSpecificMembershipLevel, bo.getSpecificMembershipLevel());
        lqw.eq(StringUtils.isNotBlank(bo.getSpecificUsersId()), MarketingCoupon::getSpecificUsersId, bo.getSpecificUsersId());
        return lqw;
    }

    /**
     * 新增优惠卷管理
     *
     * @param bo 优惠卷管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MarketingCouponBo bo) {
        MarketingCoupon add = MapstructUtils.convert(bo, MarketingCoupon.class);
        validEntityBeforeSave(add);

        // 设置剩余数量
        add.setSurplusNumber(add.getCouponNumber());
        boolean flag = baseMapper.insert(add) > 0;
        // 设置状态的默认值为0
        add.setState(0L);


        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改优惠卷管理
     *
     * @param bo 优惠卷管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MarketingCouponBo bo) {
        MarketingCoupon update = MapstructUtils.convert(bo, MarketingCoupon.class);
        validEntityBeforeSave(update);

        Long difference = 0L;


        // 修改剩余优惠卷数量
        if (update.getCouponNumber() !=null){
            MarketingCoupon marketingCoupon = baseMapper.selectById(bo.getId());
            //1.增加了优惠券数量
            if (update.getCouponNumber()>marketingCoupon.getCouponNumber()){
                difference= update.getCouponNumber()-marketingCoupon.getCouponNumber();
                update.setSurplusNumber(marketingCoupon.getSurplusNumber()+difference);
            }
            //2.减少了优惠券数量
            else {
                difference=marketingCoupon.getCouponNumber()-update.getCouponNumber();
                Long newSurplus=marketingCoupon.getSurplusNumber()- difference;
                if (newSurplus<0L) throw new ServiceException("优惠卷数量过少，请重新设置");
                update.setSurplusNumber(newSurplus);

            }
        }
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MarketingCoupon entity){
        //TODO 做一些数据校验,如唯一约束

        if (entity.getEndTime().before(entity.getStartTime()))
            throw new ServiceException("请重新输入优惠券使用时间！");




    }

    /**
     * 校验并批量删除优惠卷管理信息
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
     * 删除单个优惠卷
     * @param id
     * @return
     */

    @Override
    @Transactional
    public boolean deleteOneById(Long id) {
        MarketingCouponVo marketingCouponVo = baseMapper.selectVoById(id);
        if (ObjectUtils.isEmpty(marketingCouponVo)) throw new ServiceException("该数据不存在");

        QueryWrapper<MarketingCouponReceive> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coupon_id",marketingCouponVo.getId());
        List<MarketingCouponReceive> marketingCouponReceives = couponReceiveMapper.selectList(queryWrapper);
        if (!marketingCouponReceives.isEmpty() && marketingCouponReceives!=null)
            throw new ServiceException("该优惠卷已有用户领取，不能删除，可以选择关闭发放");



        return baseMapper.deleteById(id) >0 ;
    }


    /**
     * 切换优惠卷状态
     * @param id
     * @return
     * 优惠券状态（0关闭，1开启）
     */
    @Override
    public boolean updateState(Long id) {
        MarketingCoupon marketingCoupon = baseMapper.selectById(id);
        if (ObjectUtils.isEmpty(marketingCoupon)) throw new ServiceException("优惠券不存在，删除失败");


        // 设置 优惠券为开放领取中
        if (marketingCoupon.getState()==0) {
            // 剩余数量已经用完，用户重新修改优惠券数量
            if (marketingCoupon.getSurplusNumber() == 0L)
                throw new ServiceException("优惠券数量派发完毕，请先修改数量！");


            //优惠券有效时间已过期，用户重新修改优惠券时间
            Date endTime = marketingCoupon.getEndTime();
            Date currentTime = new Date();
            if (endTime.before(currentTime)) throw new ServiceException("优惠券时间已过期，请重新修改优惠券使用时间");

        }
        UpdateWrapper<MarketingCoupon> updateWrapper = new UpdateWrapper<>();
        Long state = marketingCoupon.getState();
        updateWrapper.eq("id",id);
        state=((state == 0)?1L:0);
        updateWrapper.set("state",state);

        return baseMapper.update(updateWrapper)>0;
    }


    /**
     * 根据会员用户id，显示待领取的优惠券
     * @param id
     * @return
     * 优惠券种类（0普通优惠卷，1定向优惠卷）
     */
    @Override
    public List<MarketingCouponVo> queryPageUserList(Long id) {
        //1. 查询当前用户信息
        QueryWrapper queryUserWrapper = new QueryWrapper<AppletUserInformation>();
        queryUserWrapper.eq("user_id",id);
        AppletUserInformation appletUserInformation = appletUserInformationMapper.selectOne(queryUserWrapper);
        if (ObjectUtils.isEmpty(appletUserInformation)){
            throw  new ServiceException("该用户不存在");
        }
        //会员等级id
        Long memberLevelId = appletUserInformation.getMemberLevelId();


        //2. 返回的优惠卷集合
        ArrayList<MarketingCouponVo> result = new ArrayList<>();

        //3. 所有发放的优惠券
        QueryWrapper<MarketingCoupon> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("state",1);
        List<MarketingCouponVo> marketingCouponsAll = baseMapper.selectVoList(queryWrapper);

        //4.排除用户已领取的优惠卷
        QueryWrapper<MarketingCouponReceive> queryReceiveWrapper = new QueryWrapper<>();
        queryReceiveWrapper.eq("user_id",id);
        List<MarketingCouponReceive> marketingCouponReceives = couponReceiveMapper.selectList(queryReceiveWrapper);
        List<Long> couponids = marketingCouponReceives.stream().map(MarketingCouponReceive::getCouponId).collect(Collectors.toList());


        for (MarketingCouponVo marketingCouponVo:marketingCouponsAll) {
            //1.刷新已过期的优惠卷，剔除已过期的优惠券 2. 优惠卷份额已领取完了
            Date endTime = marketingCouponVo.getEndTime();
            Date currenTime = new Date();
            boolean equals = marketingCouponVo.getSurplusNumber().equals(0L);
            if (endTime.before(currenTime) || equals) {
                UpdateWrapper<MarketingCoupon> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id", marketingCouponVo.getId());
                //关闭优惠券
                updateWrapper.set("state", 0);
                baseMapper.update(updateWrapper);
                continue;
            }



            //3.排除用户已领取的优惠卷
            if (couponids.contains(marketingCouponVo.getId())) continue;


            //1. 普通优惠券
            if (marketingCouponVo.getCouponKind() == 0L) {
                result.add(marketingCouponVo);
            }
            //2. 定向优惠券
            else if (marketingCouponVo.getCouponKind() == 1L) {
                //2.1 定向会员等级
                if (StringUtils.isNotEmpty(marketingCouponVo.getSpecificMembershipLevel())) {
                    Long[] array = Arrays.stream(marketingCouponVo.getSpecificMembershipLevel().split(","))
                        .map(String::trim)
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .toArray(Long[]::new);
                    if (Arrays.stream(array).anyMatch(x -> x.equals(memberLevelId) )){
                        result.add(marketingCouponVo);
                    }

                }

                //2.2 定向用户
                if (StringUtils.isNotEmpty(marketingCouponVo.getSpecificUsersId())) {
                    Long[] array = Arrays.stream(marketingCouponVo.getSpecificUsersId().split(","))
                        .map(String::trim)
                        .mapToLong(Long::parseLong)
                        .boxed()
                        .toArray(Long[]::new);

                    if (Arrays.stream(array).anyMatch(x->x.equals(id)))
                        result.add(marketingCouponVo);
                }


            }
        }

        return result;
    }
}
