package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.springframework.stereotype.Service;
import org.dromara.flowerapplet.domain.bo.FolwerAppletOrderRefundBo;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderRefundVo;
import org.dromara.flowerapplet.domain.FolwerAppletOrderRefund;
import org.dromara.flowerapplet.mapper.FolwerAppletOrderRefundMapper;
import org.dromara.flowerapplet.service.IFolwerAppletOrderRefundService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单退款Service业务层处理
 *
 * @author mlhxj
 * @date 2025-01-15
 */
@RequiredArgsConstructor
@Service
public class FolwerAppletOrderRefundServiceImpl implements IFolwerAppletOrderRefundService {

    private final FolwerAppletOrderRefundMapper baseMapper;

    private final IFolwerAppletOrderService folwerAppletOrderService;

    /**
     * 查询订单退款
     *
     * @param refundId 主键
     * @return 订单退款
     */
    @Override
    public FolwerAppletOrderRefundVo queryById(Long refundId){
        return baseMapper.selectVoById(refundId);
    }

    /**
     * 分页查询订单退款列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 订单退款分页列表
     */
    @Override
    public TableDataInfo<FolwerAppletOrderRefundVo> queryPageList(FolwerAppletOrderRefundBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerAppletOrderRefund> lqw = buildQueryWrapper(bo);
        Page<FolwerAppletOrderRefundVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单退款列表
     *
     * @param bo 查询条件
     * @return 订单退款列表
     */
    @Override
    public List<FolwerAppletOrderRefundVo> queryList(FolwerAppletOrderRefundBo bo) {
        LambdaQueryWrapper<FolwerAppletOrderRefund> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerAppletOrderRefund> buildQueryWrapper(FolwerAppletOrderRefundBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerAppletOrderRefund> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerAppletOrderRefund::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerAppletOrderRefund::getUserName, bo.getUserName());
        lqw.eq(bo.getMemberLevelId() != null, FolwerAppletOrderRefund::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderId()), FolwerAppletOrderRefund::getOrderId, bo.getOrderId());
        lqw.eq(bo.getActualTotal() != null, FolwerAppletOrderRefund::getActualTotal, bo.getActualTotal());
        lqw.eq(bo.getRefundStatus() != null, FolwerAppletOrderRefund::getRefundStatus, bo.getRefundStatus());
        lqw.eq(bo.getStatus() != null, FolwerAppletOrderRefund::getStatus, bo.getStatus());
        lqw.eq(bo.getApplyType() != null, FolwerAppletOrderRefund::getApplyType, bo.getApplyType());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundMsg()), FolwerAppletOrderRefund::getRefundMsg, bo.getRefundMsg());
        lqw.eq(bo.getRefundAmount() != null, FolwerAppletOrderRefund::getRefundAmount, bo.getRefundAmount());
        lqw.eq(bo.getRefundTime() != null, FolwerAppletOrderRefund::getRefundTime, bo.getRefundTime());
        lqw.eq(StringUtils.isNotBlank(bo.getBuyerMsg()), FolwerAppletOrderRefund::getBuyerMsg, bo.getBuyerMsg());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundRemark()), FolwerAppletOrderRefund::getRefundRemark, bo.getRefundRemark());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundRemarkPic()), FolwerAppletOrderRefund::getRefundRemarkPic, bo.getRefundRemarkPic());
        return lqw;
    }

    /**
     * 新增订单退款
     *
     * @param bo 订单退款
     * @return 是否新增成功
     */
    @Override
    public String insertByBo(FolwerAppletOrderRefundBo bo) throws Exception {
        FolwerAppletOrderRefund add = MapstructUtils.convert(bo, FolwerAppletOrderRefund.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRefundId(add.getRefundId());
            FolwerAppletOrderVo folwerAppletOrderVo = folwerAppletOrderService.queryOrder(bo.getOrderId());
            FolwerAppletOrderBo folwerAppletOrderBo = BeanUtil.copyProperties(folwerAppletOrderVo, FolwerAppletOrderBo.class);
            folwerAppletOrderBo.setIsRefund(2L);
            Boolean b = folwerAppletOrderService.updateByBo(folwerAppletOrderBo);
        }
        return add.getRefundId().toString();
    }

    /**
     * 修改订单退款
     *
     * @param bo 订单退款
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerAppletOrderRefundBo bo) {
        FolwerAppletOrderRefund update = MapstructUtils.convert(bo, FolwerAppletOrderRefund.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerAppletOrderRefund entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除订单退款信息
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
