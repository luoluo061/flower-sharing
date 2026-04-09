package org.dromara.flower.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mypay.domain.RefundAmount;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.flower.domain.FolwerOrderRefund;
import org.dromara.flower.domain.bo.FolwerOrderRefundBo;
import org.dromara.flower.domain.vo.FolwerOrderRefundInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.flower.mapper.FolwerOrderRefundMapper;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.IFolwerOrderRefundService;
import org.dromara.flower.service.domain.OrderRefundDomainService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 璁㈠崟閫€娆維ervice涓氬姟灞傚鐞?
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
    private final OrderRefundDomainService orderRefundDomainService;

    @Override
    public FolwerOrderRefundVo queryById(Long refundId) {
        FolwerOrderRefundVo folwerOrderRefundVo = baseMapper.selectVoById(refundId);
        if (folwerOrderRefundVo != null) {
            AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(folwerOrderRefundVo.getUserId());
            if (appletUserInformationVo != null) {
                folwerOrderRefundVo.setUserPhone(appletUserInformationVo.getPhone());
            }
        }
        return folwerOrderRefundVo;
    }

    @Override
    public FolwerOrderRefundInfoVo queryInfoById(Long refundId) {
        FolwerOrderRefundInfoVo folwerOrderRefundInfoVo = baseMapper.selectOrderRefundInfoVoById(refundId);
        if (folwerOrderRefundInfoVo != null) {
            switch (folwerOrderRefundInfoVo.getStatus().intValue()) {
                case 0:
                    folwerOrderRefundInfoVo.setStatusStr("寰呬粯娆?");
                    break;
                case 1:
                    folwerOrderRefundInfoVo.setStatusStr("宸叉敮浠?");
                    break;
                case 2:
                    folwerOrderRefundInfoVo.setStatusStr("宸插彇娑?");
                    break;
                case 3:
                    folwerOrderRefundInfoVo.setStatusStr("宸查€€娆?");
                    break;
                case 4:
                    folwerOrderRefundInfoVo.setStatusStr("鎷掔粷閫€娆?");
                    break;
                case 5:
                    folwerOrderRefundInfoVo.setStatusStr("寰呭彂璐?");
                    break;
                case 6:
                    folwerOrderRefundInfoVo.setStatusStr("寰呮敹璐?");
                    break;
                case 7:
                    folwerOrderRefundInfoVo.setStatusStr("寰呰瘎浠?");
                    break;
                default:
                    break;
            }

            switch (folwerOrderRefundInfoVo.getPayType().intValue()) {
                case 0:
                    folwerOrderRefundInfoVo.setPayTypeStr("鎵嬪姩浠ｄ粯");
                    break;
                case 1:
                    folwerOrderRefundInfoVo.setPayTypeStr("寰俊鏀粯");
                    break;
                case 2:
                    folwerOrderRefundInfoVo.setPayTypeStr("鏀粯瀹?");
                    break;
                default:
                    break;
            }

            switch (folwerOrderRefundInfoVo.getApplyType().intValue()) {
                case 1:
                    folwerOrderRefundInfoVo.setApplyTypeStr("鎷掔粷閫€娆?");
                    break;
                case 2:
                    folwerOrderRefundInfoVo.setApplyTypeStr("鍚屾剰閫€娆?");
                    break;
                default:
                    break;
            }
            FolwerOrderRefundVo folwerOrderRefundVo = this.queryById(refundId);
            AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(folwerOrderRefundVo.getUserId());
            folwerOrderRefundInfoVo.setUserPhone(appletUserInformationVo.getPhone());
        }
        if (folwerOrderRefundInfoVo.getRefundPic() != null) {
            String[] splitArray = folwerOrderRefundInfoVo.getRefundPic().split(",");
            List<String> splitList = Arrays.asList(splitArray);
            folwerOrderRefundInfoVo.setRefundMsgPic(splitList);
            folwerOrderRefundInfoVo.setRefundPic(null);
        }

        return folwerOrderRefundInfoVo;
    }

    @Override
    public TableDataInfo<FolwerOrderRefundVo> queryPageList(FolwerOrderRefundBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerOrderRefund> lqw = buildQueryWrapper(bo);
        Page<FolwerOrderRefundVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

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

    @Override
    public Boolean updateByBo(FolwerOrderRefundBo bo) {
        FolwerOrderRefund update = MapstructUtils.convert(bo, FolwerOrderRefund.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    private void validEntityBeforeSave(FolwerOrderRefund entity) {
        // TODO data validation hook for future Stage 2 cleanup.
    }

    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            // TODO business validation hook for future Stage 2 cleanup.
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    @Override
    public FolwerOrderRefundVo submitRefundOrders(Long refundId) throws Exception {
        FolwerOrderRefundInfoVo refundInfoVo = queryInfoById(refundId);
        WxRefundRequest wxRefundRequest = new WxRefundRequest();
        wxRefundRequest.setOutTradeNo(String.valueOf(refundInfoVo.getOrderId()));
        wxRefundRequest.setOutRefundNo(String.valueOf(refundId));
        RefundAmount refundAmount = new RefundAmount();
        refundAmount.setRefund(refundInfoVo.getRefundAmount());
        refundAmount.setTotal(refundInfoVo.getActualTotal());
        refundAmount.setCurrency("CNY");
        wxRefundRequest.setAmount(refundAmount);

        Refund refund = payService.refundOrder(wxRefundRequest);
        Long targetRefundStatus = resolveRefundStatus(refund);
        if (targetRefundStatus == null) {
            return null;
        }

        FolwerOrderRefundVo refundVo = this.queryById(refundId);
        FolwerOrderRefundBo mutation = orderRefundDomainService.prepareRefundStatusMutation(
            refundVo,
            targetRefundStatus,
            refund.getSuccessTime()
        );
        if (updateByBo(mutation)) {
            refundVo.setRefundStatus(mutation.getRefundStatus());
            refundVo.setRefundTime(mutation.getRefundTime());
            return refundVo;
        }
        return null;
    }

    private Long resolveRefundStatus(Refund refund) {
        if (refund == null || refund.getStatus() == null) {
            return null;
        }
        if (Status.SUCCESS.equals(refund.getStatus())) {
            return 1L;
        }
        if (Status.PROCESSING.equals(refund.getStatus())) {
            return 2L;
        }
        if (Status.ABNORMAL.equals(refund.getStatus())) {
            return 3L;
        }
        if (Status.CLOSED.equals(refund.getStatus())) {
            return 4L;
        }
        return null;
    }
}
