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
import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.domain.FolwerOrderRefund;
import org.dromara.flower.mapper.FolwerOrderRefundMapper;
import org.dromara.flower.service.IFolwerOrderRefundService;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 订单退款Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-25
 */
@RequiredArgsConstructor
@Service
public class FolwerOrderRefundServiceImpl implements IFolwerOrderRefundService {

    private final FolwerOrderRefundMapper baseMapper;

    /**
     * 查询订单退款
     *
     * @param refundId 主键
     * @return 订单退款
     */
    @Override
    public FolwerOrderRefundVo queryById(Long refundId){
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
    public TableDataInfo<FolwerOrderRefundVo> queryPageList(FolwerOrderRefundBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerOrderRefund> lqw = buildQueryWrapper(bo);
        Page<FolwerOrderRefundVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的订单退款列表
     *
     * @param bo 查询条件
     * @return 订单退款列表
     */
    @Override
    public List<FolwerOrderRefundVo> queryList(FolwerOrderRefundBo bo) {
        LambdaQueryWrapper<FolwerOrderRefund> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerOrderRefund> buildQueryWrapper(FolwerOrderRefundBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerOrderRefund> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getUserId() != null, FolwerOrderRefund::getUserId, bo.getUserId());
        lqw.like(StringUtils.isNotBlank(bo.getUserName()), FolwerOrderRefund::getUserName, bo.getUserName());
        lqw.eq(bo.getMemberLevelId() != null, FolwerOrderRefund::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(StringUtils.isNotBlank(bo.getOrderId()), FolwerOrderRefund::getOrderId, bo.getOrderId());
        lqw.eq(bo.getActualTotal() != null, FolwerOrderRefund::getActualTotal, bo.getActualTotal());
        lqw.eq(bo.getRefundStatus() != null, FolwerOrderRefund::getRefundStatus, bo.getRefundStatus());
        lqw.eq(bo.getStatus() != null, FolwerOrderRefund::getStatus, bo.getStatus());
        lqw.eq(bo.getApplyType() != null, FolwerOrderRefund::getApplyType, bo.getApplyType());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundMsg()), FolwerOrderRefund::getRefundMsg, bo.getRefundMsg());
        lqw.eq(bo.getRefundAmount() != null, FolwerOrderRefund::getRefundAmount, bo.getRefundAmount());
        lqw.eq(bo.getRefundTime() != null, FolwerOrderRefund::getRefundTime, bo.getRefundTime());
        lqw.eq(StringUtils.isNotBlank(bo.getBuyerMsg()), FolwerOrderRefund::getBuyerMsg, bo.getBuyerMsg());
        lqw.eq(StringUtils.isNotBlank(bo.getRefundRemark()), FolwerOrderRefund::getRefundRemark, bo.getRefundRemark());
        return lqw;
    }

    /**
     * 新增订单退款
     *
     * @param bo 订单退款
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerOrderRefundBo bo) {
        FolwerOrderRefund add = MapstructUtils.convert(bo, FolwerOrderRefund.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setRefundId(add.getRefundId());
        }
        return flag;
    }

    /**
     * 修改订单退款
     *
     * @param bo 订单退款
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(FolwerOrderRefundBo bo) {
        FolwerOrderRefund update = MapstructUtils.convert(bo, FolwerOrderRefund.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerOrderRefund entity){
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
