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
import org.dromara.flower.domain.bo.FolwerOrderSetBo;
import org.dromara.flower.domain.vo.FolwerOrderSetVo;
import org.dromara.flower.domain.FolwerOrderSet;
import org.dromara.flower.mapper.FolwerOrderSetMapper;
import org.dromara.flower.service.IFolwerOrderSetService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单设置Service业务层处理
 *
 * @author Lion Li
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class FolwerOrderSetServiceImpl implements IFolwerOrderSetService {

    private final FolwerOrderSetMapper baseMapper;

    /**
     * 查询订单设置
     *
     * @param id 主键
     * @return 订单设置
     */
    @Override
    public FolwerOrderSetVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询订单设置列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单设置分页列表
     */
    @Override
    public TableDataInfo<FolwerOrderSetVo> queryPageList(FolwerOrderSetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerOrderSet> lqw = buildQueryWrapper(bo);
        Page<FolwerOrderSetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单设置列表
     *
     * @param bo 查询条件
     * @return 订单设置列表
     */
    @Override
    public List<FolwerOrderSetVo> queryList(FolwerOrderSetBo bo) {
        LambdaQueryWrapper<FolwerOrderSet> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerOrderSet> buildQueryWrapper(FolwerOrderSetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerOrderSet> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getFreeShippingPrice() != null, FolwerOrderSet::getFreeShippingPrice, bo.getFreeShippingPrice());
        lqw.like(StringUtils.isNotBlank(bo.getRefundName()), FolwerOrderSet::getRefundName, bo.getRefundName());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundPhone()), FolwerOrderSet::getRefundPhone, bo.getRefundPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundAddr()), FolwerOrderSet::getRefundAddr, bo.getRefundAddr());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundMsg()), FolwerOrderSet::getRefundMsg, bo.getRefundMsg());
        lqw.eq(bo.getCouponRefund() != null, FolwerOrderSet::getCouponRefund, bo.getCouponRefund());
        lqw.eq(StringUtils.isNotBlank(bo.getTerm()), FolwerOrderSet::getTerm, bo.getTerm());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderCancel()), FolwerOrderSet::getOrderCancel, bo.getOrderCancel());
        lqw.eq(StringUtils.isNotBlank(bo.getAutoDvy()), FolwerOrderSet::getAutoDvy, bo.getAutoDvy());
        return lqw;
    }

    /**
     * 新增订单设置
     *
     * @param bo 订单设置
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerOrderSetBo bo) {
        FolwerOrderSet add = MapstructUtils.convert(bo, FolwerOrderSet.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改订单设置
     *
     * @param bo 订单设置
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerOrderSetBo bo) {

        FolwerOrderSetVo old = queryById(bo.getId());
        FolwerOrderSet update = MapstructUtils.convert(old, FolwerOrderSet.class);

        if (bo.getFreeShippingPrice() != null){
            update.setFreeShippingPrice(bo.getFreeShippingPrice());
        }
        if (bo.getRefundName() != null){
            update.setRefundName(bo.getRefundName());
        }
        if (bo.getRefundPhone() != null) {
            update.setRefundPhone(bo.getRefundPhone());
        }
        if (bo.getRefundAddr() != null){
            update.setRefundAddr(bo.getRefundAddr());
        }
        if (bo.getRefundMsg() != null){
            update.setRefundMsg(bo.getRefundMsg());
        }
        if (bo.getCouponRefund() != null){
            update.setCouponRefund(bo.getCouponRefund());
        }
        if (bo.getTerm() != null){
            update.setTerm(bo.getTerm());
        }
        if (bo.getOrderCancel() != null){
            update.setOrderCancel(bo.getOrderCancel());
        }
        if (bo.getAutoDvy() != null){
            update.setAutoDvy(bo.getAutoDvy());
        }

        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerOrderSet entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除订单设置信息
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
