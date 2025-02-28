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
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderSetBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderSetVo;
import org.dromara.flowerapplet.domain.FolwerAppletOrderSet;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderSetMapper;
import org.dromara.flowerapplet.service.IFolwerAppletOrderSetService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单设置Service业务层处理
 *
 * @author mlhxj
 * @date 2025-02-28
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletOrderSetServiceImpl implements IFolwerAppletOrderSetService {

    private final FolwerAppletOrderSetMapper baseMapper;

    /**
     * 查询订单设置
     *
     * @param id 主键
     * @return 订单设置
     */
    @Override
    public FolwerAppletOrderSetVo queryById(Long id){
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
    public TableDataInfo<FolwerAppletOrderSetVo> queryPageList(FolwerAppletOrderSetBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletOrderSet> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletOrderSetVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单设置列表
     *
     * @param bo 查询条件
     * @return 订单设置列表
     */
    @Override
    public List<FolwerAppletOrderSetVo> queryList(FolwerAppletOrderSetBo bo) {
        LambdaQueryWrapper<FolwerAppletOrderSet> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletOrderSet> buildQueryWrapper(FolwerAppletOrderSetBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletOrderSet> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getFreeShippingPrice() != null, FolwerAppletOrderSet::getFreeShippingPrice, bo.getFreeShippingPrice());
        lqw.eq(bo.getStartPrice() != null, FolwerAppletOrderSet::getStartPrice, bo.getStartPrice());
        lqw.like(StringUtils.isNotBlank(bo.getRefundName()), FolwerAppletOrderSet::getRefundName, bo.getRefundName());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundPhone()), FolwerAppletOrderSet::getRefundPhone, bo.getRefundPhone());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundAddr()), FolwerAppletOrderSet::getRefundAddr, bo.getRefundAddr());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundMsg()), FolwerAppletOrderSet::getRefundMsg, bo.getRefundMsg());
        lqw.eq(bo.getCouponRefund() != null, FolwerAppletOrderSet::getCouponRefund, bo.getCouponRefund());
        lqw.eq(StringUtils.isNotBlank(bo.getTerm()), FolwerAppletOrderSet::getTerm, bo.getTerm());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderCancel()), FolwerAppletOrderSet::getOrderCancel, bo.getOrderCancel());
        lqw.eq(StringUtils.isNotBlank(bo.getAutoDvy()), FolwerAppletOrderSet::getAutoDvy, bo.getAutoDvy());
        return lqw;
    }

    /**
     * 新增订单设置
     *
     * @param bo 订单设置
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerAppletOrderSetBo bo) {
        FolwerAppletOrderSet add = MapstructUtils.convert(bo, FolwerAppletOrderSet.class);
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
    public Boolean updateByBo(FolwerAppletOrderSetBo bo) {
        FolwerAppletOrderSet update = MapstructUtils.convert(bo, FolwerAppletOrderSet.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletOrderSet entity){
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
