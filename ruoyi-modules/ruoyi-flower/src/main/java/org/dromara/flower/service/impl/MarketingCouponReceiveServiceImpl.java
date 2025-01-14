package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.checkerframework.checker.units.qual.A;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.core.validate.AddGroup;
import org.dromara.common.core.validate.EditGroup;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.bo.AppCouponRecordBo;
import org.dromara.flower.domain.bo.AppIsFlowerCouponsBo;
import org.dromara.flower.domain.bo.AppOrderConsumeBo;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.MarketingCouponReceiveBo;
import org.dromara.flower.domain.vo.MarketingCouponReceiveVo;
import org.dromara.flower.domain.MarketingCouponReceive;
import org.dromara.flower.mapper.MarketingCouponReceiveMapper;
import org.dromara.flower.service.IMarketingCouponReceiveService;

import java.util.*;

import static kotlin.reflect.jvm.internal.impl.builtins.StandardNames.FqNames.list;

/**
 * 优惠卷领取记录Service业务层处理
 *
 * @author chy
 * @date 2025-01-08
 */
@RequiredArgsConstructor
@Service
public class MarketingCouponReceiveServiceImpl implements IMarketingCouponReceiveService {

    private final MarketingCouponReceiveMapper baseMapper;

    /**
     * 查询优惠卷领取记录
     *
     * @param id 主键
     * @return 优惠卷领取记录
     */
    @Override
    public MarketingCouponReceiveVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询优惠卷领取记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠卷领取记录分页列表
     */
    @Override
    public TableDataInfo<MarketingCouponReceiveVo> queryPageList(MarketingCouponReceiveBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MarketingCouponReceive> lqw = buildQueryWrapper(bo);
        Page<MarketingCouponReceiveVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);

        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的优惠卷领取记录列表
     *
     * @param bo 查询条件
     * @return 优惠卷领取记录列表
     */
    @Override
    public List<MarketingCouponReceiveVo> queryList(MarketingCouponReceiveBo bo) {
        LambdaQueryWrapper<MarketingCouponReceive> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MarketingCouponReceive> buildQueryWrapper(MarketingCouponReceiveBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MarketingCouponReceive> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MarketingCouponReceive::getDeptId, bo.getDeptId());
        lqw.eq(bo.getCouponId() != null, MarketingCouponReceive::getCouponId, bo.getCouponId());
        lqw.eq(bo.getUserId() != null, MarketingCouponReceive::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), MarketingCouponReceive::getUserName, bo.getUserName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), MarketingCouponReceive::getPhone, bo.getPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getIcon()), MarketingCouponReceive::getIcon, bo.getIcon());
        lqw.eq(bo.getState() != null, MarketingCouponReceive::getState, bo.getState());
        return lqw;
    }

    /**
     * 新增优惠卷领取记录 == 用户领取优惠券、发放优惠券
     *
     * @param bo 优惠卷领取记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(MarketingCouponReceiveBo bo) {
        MarketingCouponReceive add = MapstructUtils.convert(bo, MarketingCouponReceive.class);
        validEntityBeforeSave(add);
        add.setState(0L);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改优惠卷领取记录
     *
     * @param bo 优惠卷领取记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MarketingCouponReceiveBo bo) {
        MarketingCouponReceive update = MapstructUtils.convert(bo, MarketingCouponReceive.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MarketingCouponReceive entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除优惠卷领取记录信息
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
     * 根据优惠券id显示领取记录
     * @param id
     * @param pageQuery
     * @return
     */

    @Override
    public TableDataInfo<MarketingCouponReceiveVo> queryPageListByCouponId(Long id, PageQuery pageQuery) {
        QueryWrapper<MarketingCouponReceive> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("coupon_id",id);
        Page<MarketingCouponReceiveVo> result = baseMapper.selectVoPage(pageQuery.build(), queryWrapper);

        return TableDataInfo.build(result);
    }


    /**
     * 小程序用户查询自己 0已领取、1已使用、2已失效的优惠券
     * @param appCouponRecordBo
     * @return
     */
    @Override
    public TableDataInfo<MarketingCouponReceiveVo> queryUserStateList(AppCouponRecordBo appCouponRecordBo) {
        List<MarketingCouponReceiveVo> marketingCouponlist = new ArrayList<>();

        List<MarketingCouponReceiveVo> marketingCouponReceiveVos = baseMapper.queryUserStateList(appCouponRecordBo);

        for (MarketingCouponReceiveVo marketingCouponReceive:marketingCouponReceiveVos){
            //刷新过期时间的优惠券
            Date endTime = marketingCouponReceive.getMarketingCoupon().getEndTime();
            Date currenTime = new Date();
            if (endTime.before(currenTime)){
                marketingCouponReceive.setState(2L);
                UpdateWrapper<MarketingCouponReceive> updateWrapper = new UpdateWrapper<>();
                updateWrapper.eq("id",marketingCouponReceive.getId());
                updateWrapper.set("state",marketingCouponReceive.getState());
                baseMapper.update(updateWrapper);
            }

            if (marketingCouponReceive.getState().equals(appCouponRecordBo.getState()))
                marketingCouponlist.add(marketingCouponReceive);

        }


        return TableDataInfo.build(marketingCouponlist);
    }


    /**
     *
     * 当前用户查询该商品可用优惠卷列表
     * @param appOrderConsumeBo
     * @return
     * 适用商品分类（0所有商品，1特定分类，2特定商品）
     */
    @Override
    public List<MarketingCouponReceiveVo> queryUserConsumeList(AppOrderConsumeBo appOrderConsumeBo) {
        List<MarketingCouponReceiveVo> list=new ArrayList<>();


        List<MarketingCouponReceiveVo> marketingCouponReceiveVos = baseMapper.queryUserConsumeList(appOrderConsumeBo);
        for (MarketingCouponReceiveVo marketingCouponReceiveVo : marketingCouponReceiveVos) {
            //1. "0"所有商品
            if (marketingCouponReceiveVo.getMarketingCoupon().getApplicableCategory()==0L){
                list.add(marketingCouponReceiveVo);
            }
            //2. "1"特定分类
             else if (marketingCouponReceiveVo.getMarketingCoupon().getApplicableCategory()==1L) {
                Long[] classificationIdArray = Arrays.stream(marketingCouponReceiveVo.getMarketingCoupon().getClassificationId().split(","))
                    .map(String::trim)
                    .mapToLong(Long::parseLong)
                    .boxed()
                    .toArray(Long[]::new);
                 Long[] categoryId = appOrderConsumeBo.getCategoryId();

                boolean b = Arrays.stream(classificationIdArray).anyMatch(x -> Arrays.stream(categoryId).anyMatch(y -> x.equals(y)));
                if (b) {
                    list.add(marketingCouponReceiveVo);
                }

            }
            //3. "2" 特定商品
            else if (marketingCouponReceiveVo.getMarketingCoupon().getApplicableCategory()==2L){
                Long[] goodIdArray = Arrays.stream(marketingCouponReceiveVo.getMarketingCoupon().getGoodsId().split(","))
                    .map(String::trim)
                    .mapToLong(Long::parseLong)
                    .boxed()
                    .toArray(Long[]::new);
                Long[] productIds = appOrderConsumeBo.getProductIds();

                boolean b = Arrays.stream(goodIdArray).anyMatch(x -> Arrays.stream(productIds).anyMatch(y -> x.equals(y)));
                if (b){
                    list.add(marketingCouponReceiveVo);
                }
            }
        }



        return list;


    }




    /**
     * 查询该商品是否拥有花劵
     * @param appIsFlowerCouponsBo
     * @return
     * （0所有商品，1特定分类，2特定商品）
     */
    @Override
    public boolean isFlowerCoupons(AppIsFlowerCouponsBo appIsFlowerCouponsBo) {
        List<MarketingCouponReceiveVo> flowerCoupons = baseMapper.isFlowerCoupons(appIsFlowerCouponsBo);

        if (flowerCoupons.isEmpty() || flowerCoupons==null)return false;



        for (MarketingCouponReceiveVo flowerCoupon : flowerCoupons) {
            // 适用于所有商品的花劵 0
            if (flowerCoupon.getMarketingCoupon().getApplicableCategory()==0L){
                return  true;
            }
            // 适用于特定分类的花劵
            else if (flowerCoupon.getMarketingCoupon().getApplicableCategory()==1L){
                Long[] array = Arrays.stream(flowerCoupon.getMarketingCoupon().getSpecificUsersId().split(","))
                    .map(String::trim)
                    .mapToLong(Long::parseLong)
                    .boxed()
                    .toArray(Long[]::new);
                if (Arrays.stream(array).anyMatch(x->x==appIsFlowerCouponsBo.getCategoryId()))
                    return true;


            }
            //适用于特定商品的花劵
            else if (flowerCoupon.getMarketingCoupon().getApplicableCategory()==2L) {
                Long[] array = Arrays.stream(flowerCoupon.getMarketingCoupon().getGoodsId().split(","))
                    .map(String::trim)
                    .mapToLong(Long::parseLong)
                    .boxed()
                    .toArray(Long[]::new);

                if (Arrays.stream(array).anyMatch(x->x==appIsFlowerCouponsBo.getProductId()))
                    return true;


            }


    }

        return false;

    }


}
