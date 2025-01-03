package org.dromara.flower.service.impl;

import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.FolwerProduct;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerCouponBo;
import org.dromara.flower.domain.vo.FolwerCouponVo;
import org.dromara.flower.domain.FolwerCoupon;
import org.dromara.flower.mapper.FolwerCouponMapper;
import org.dromara.flower.service.IFolwerCouponService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 优惠券管理Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-03
 */
@RequiredArgsConstructor
@Service
public class FolwerCouponServiceImpl implements IFolwerCouponService {

    private final FolwerCouponMapper baseMapper;

    /**
     * 查询优惠券管理
     *
     * @param couponId 主键
     * @return 优惠券管理
     */
    @Override
    public FolwerCouponVo queryById(Long couponId){
        return baseMapper.selectVoById(couponId);
    }

    /**
     * 分页查询优惠券管理列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 优惠券管理分页列表
     */
    @Override
    public TableDataInfo<FolwerCouponVo> queryPageList(FolwerCouponBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerCoupon> lqw = buildQueryWrapper(bo);
        Page<FolwerCouponVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的优惠券管理列表
     *
     * @param bo 查询条件
     * @return 优惠券管理列表
     */
    @Override
    public List<FolwerCouponVo> queryList(FolwerCouponBo bo) {
        LambdaQueryWrapper<FolwerCoupon> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerCoupon> buildQueryWrapper(FolwerCouponBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerCoupon> lqw = Wrappers.lambdaQuery();
        lqw.like(StringUtils.isNotBlank(bo.getCouponName()), FolwerCoupon::getCouponName, bo.getCouponName());
        lqw.eq(bo.getCategoryId() != null, FolwerCoupon::getCategoryId, bo.getCategoryId());
        lqw.like(StringUtils.isNotBlank(bo.getCategoryName()), FolwerCoupon::getCategoryName, bo.getCategoryName());
        lqw.eq(StringUtils.isNotBlank(bo.getCouponType()), FolwerCoupon::getCouponType, bo.getCouponType());
        lqw.eq(StringUtils.isNotBlank(bo.getCouponDate()), FolwerCoupon::getCouponDate, bo.getCouponDate());
        lqw.eq(bo.getType() != null, FolwerCoupon::getType, bo.getType());
        lqw.eq(bo.getSeq() != null, FolwerCoupon::getSeq, bo.getSeq());
        lqw.eq(bo.getTotalStocks() != null, FolwerCoupon::getTotalStocks, bo.getTotalStocks());
        lqw.eq(bo.getTotal() != null, FolwerCoupon::getTotal, bo.getTotal());
        lqw.eq(bo.getStatus() != null, FolwerCoupon::getStatus, bo.getStatus());
        lqw.eq(bo.getIsShow() != null, FolwerCoupon::getIsShow, bo.getIsShow());
        lqw.eq(StringUtils.isNotBlank(bo.getRemarks()), FolwerCoupon::getRemarks, bo.getRemarks());
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerCoupon::getCreateTime, bo.getStartTime(), bo.getEndTime());
        return lqw;
    }

    /**
     * 新增优惠券管理
     *
     * @param bo 优惠券管理
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerCouponBo bo) {
        FolwerCoupon add = MapstructUtils.convert(bo, FolwerCoupon.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setCouponId(add.getCouponId());
        }
        return flag;
    }

    /**
     * 修改优惠券管理
     *
     * @param bo 优惠券管理
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerCouponBo bo) {
        FolwerCoupon update = MapstructUtils.convert(bo, FolwerCoupon.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerCoupon entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除优惠券管理信息
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
