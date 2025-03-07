package org.dromara.flower.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.wechat.pay.java.service.refund.model.Amount;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import jakarta.annotation.Resource;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.common.mypay.domain.RefundAmount;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.flower.domain.FolwerDelivery;
import org.dromara.flower.domain.vo.FolwerOrderRefundInfoVo;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.domain.FolwerOrderRefund;
import org.dromara.flower.mapper.FolwerOrderRefundMapper;
import org.dromara.flower.service.IFolwerOrderRefundService;

import java.text.SimpleDateFormat;
import java.util.Arrays;
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

    @Resource
    private final IPayService payService;

    private final IAppletUserInformationService appletUserInformationService;


    /**
     * 查询订单退款
     *
     * @param refundId 主键
     * @return 订单退款
     */
    @Override
    public FolwerOrderRefundVo queryById(Long refundId){
        FolwerOrderRefundVo folwerOrderRefundVo = baseMapper.selectVoById(refundId);
        if (folwerOrderRefundVo != null){
            AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(folwerOrderRefundVo.getUserId());
            if (appletUserInformationVo != null){
                folwerOrderRefundVo.setUserPhone(appletUserInformationVo.getPhone());
            }
        }
        return folwerOrderRefundVo;
    }

    /**
     * 查询订单退款详情
     * @param refundId
     * @return
     */
    @Override
    public FolwerOrderRefundInfoVo queryInfoById(Long refundId) {
        FolwerOrderRefundInfoVo folwerOrderRefundInfoVo = baseMapper.selectOrderRefundInfoVoById(refundId);
        if (folwerOrderRefundInfoVo != null){
            switch (folwerOrderRefundInfoVo.getStatus().intValue()) {
                case 0:
                    folwerOrderRefundInfoVo.setStatusStr("待付款");
                    break;
                case 1:
                    folwerOrderRefundInfoVo.setStatusStr("已支付");
                    break;
                case 2:
                    folwerOrderRefundInfoVo.setStatusStr("已取消");
                    break;
                case 3:
                    folwerOrderRefundInfoVo.setStatusStr("已退款");
                    break;
                case 4:
                    folwerOrderRefundInfoVo.setStatusStr("拒绝退款");
                    break;
                case 5:
                    folwerOrderRefundInfoVo.setStatusStr("待发货");
                    break;
                case 6:
                    folwerOrderRefundInfoVo.setStatusStr("待收货");
                    break;
                case 7:
                    folwerOrderRefundInfoVo.setStatusStr("待评价");
                    break;
            }

            switch (folwerOrderRefundInfoVo.getPayType().intValue()){
                case 0:
                    folwerOrderRefundInfoVo.setPayTypeStr("手动代付");
                    break;
                case 1:
                    folwerOrderRefundInfoVo.setPayTypeStr("微信支付");
                    break;
                case 2:
                    folwerOrderRefundInfoVo.setPayTypeStr("支付宝");
                    break;
            }

            switch (folwerOrderRefundInfoVo.getApplyType().intValue()){
                case 1:
                    folwerOrderRefundInfoVo.setApplyTypeStr("拒绝退款");
                    break;
                case 2:
                    folwerOrderRefundInfoVo.setApplyTypeStr("同意退款");
                    break;
            }
            FolwerOrderRefundVo folwerOrderRefundVo = this.queryById(refundId);
            AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(folwerOrderRefundVo.getUserId());
            folwerOrderRefundInfoVo.setUserPhone(appletUserInformationVo.getPhone());
        }
        // 使用split方法按逗号分割字符串
        if (folwerOrderRefundInfoVo.getRefundPic() != null){
            String[] splitArray = folwerOrderRefundInfoVo.getRefundPic().split(",");
            // 将String数组转换为List
            List<String> splitList = Arrays.asList(splitArray);
            folwerOrderRefundInfoVo.setRefundMsgPic(splitList);
            folwerOrderRefundInfoVo.setRefundPic(null);
        }

        return folwerOrderRefundInfoVo;
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
        lqw.between(bo.getStartTime() != null && bo.getEndTime() != null, FolwerOrderRefund::getCreateTime, bo.getStartTime(), bo.getEndTime());
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

    @Override
    public FolwerOrderRefundVo submitRefundOrders(Long refundId) throws Exception {
        FolwerOrderRefundInfoVo folwerOrderRefundInfoVo = queryInfoById(refundId);
        WxRefundRequest wxRefundRequest = new WxRefundRequest();
        wxRefundRequest.setOutTradeNo(String.valueOf(folwerOrderRefundInfoVo.getOrderId()));
        wxRefundRequest.setOutRefundNo(String.valueOf(refundId));
        RefundAmount refundAmount = new RefundAmount();
        refundAmount.setRefund(folwerOrderRefundInfoVo.getRefundAmount());
        refundAmount.setTotal(folwerOrderRefundInfoVo.getActualTotal());
        refundAmount.setCurrency("CNY");
        wxRefundRequest.setAmount(refundAmount);
        Refund refund = payService.refundOrder(wxRefundRequest);
//                log.info("请求退款返回：" + refund);
        //接收退款返回参数
        //  Status status = refund.getStatus();
        if (Status.SUCCESS.equals(refund.getStatus().SUCCESS)) {
            //说明退款成功，开始接下来的业务操作
            //你的业务代码，根据请求返回状态修改对应订单状态
            FolwerOrderRefundVo folwerOrderRefundVo = this.queryById(refundId);
            FolwerOrderRefundBo bo = BeanUtil.copyProperties(folwerOrderRefundVo, FolwerOrderRefundBo.class);
            bo.setRefundStatus(1L);
//            if (refund.getSuccessTime() != null){
//                SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//                bo.setRefundTime(simpleDateFormat.parse(refund.getSuccessTime()));
//            }
            bo.setRefundTime(refund.getSuccessTime());
            Boolean b = updateByBo(bo);
            if (b){
                folwerOrderRefundVo.setRefundStatus(bo.getRefundStatus());
                return folwerOrderRefundVo;
            }
//            return R.ok("退款成功");
        }
        if (Status.PROCESSING.equals(refund.getStatus().PROCESSING)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            FolwerOrderRefundVo folwerOrderRefundVo = this.queryById(refundId);
            FolwerOrderRefundBo bo = BeanUtil.copyProperties(folwerOrderRefundVo, FolwerOrderRefundBo.class);
            bo.setRefundStatus(2L);
            Boolean b = updateByBo(bo);
            if (b){
                folwerOrderRefundVo.setRefundStatus(bo.getRefundStatus());
                return folwerOrderRefundVo;
            }
//            return R.ok("退款中");
        }
        if (Status.ABNORMAL.equals(refund.getStatus().ABNORMAL)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            FolwerOrderRefundVo folwerOrderRefundVo = this.queryById(refundId);
            FolwerOrderRefundBo bo = BeanUtil.copyProperties(folwerOrderRefundVo, FolwerOrderRefundBo.class);
            bo.setRefundStatus(3L);
            Boolean b = updateByBo(bo);
            if (b){
                folwerOrderRefundVo.setRefundStatus(bo.getRefundStatus());
                return folwerOrderRefundVo;
            }
//            return R.fail("退款异常");
        }
        if (Status.CLOSED.equals(refund.getStatus().CLOSED)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            FolwerOrderRefundVo folwerOrderRefundVo = this.queryById(refundId);
            FolwerOrderRefundBo bo = BeanUtil.copyProperties(folwerOrderRefundVo, FolwerOrderRefundBo.class);
            bo.setRefundStatus(4L);
            Boolean b = updateByBo(bo);
            if (b){
                folwerOrderRefundVo.setRefundStatus(bo.getRefundStatus());
                return folwerOrderRefundVo;
            }
//            return  R.fail("退款关闭");
        }
        return null;
    }
}
