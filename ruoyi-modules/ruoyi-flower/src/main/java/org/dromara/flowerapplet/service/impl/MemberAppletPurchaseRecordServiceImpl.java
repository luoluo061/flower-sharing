package org.dromara.flowerapplet.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wechat.pay.java.service.refund.model.Refund;
import com.wechat.pay.java.service.refund.model.Status;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.common.core.domain.model.LoginUser;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.PageQuery;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.common.mypay.domain.WxPayRequest;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.common.mypay.utils.IpUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.MarketingMemberPromotionPecord;
import org.dromara.flower.domain.MarketingMemberPromotionPlan;
import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.bo.MemberPurchaseRecordBo;
import org.dromara.flower.domain.vo.MarketingMemberPromotionPlanVo;
import org.dromara.flower.domain.vo.MemberLevelVo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flower.mapper.MarketingMemberPromotionPlanMapper;
import org.dromara.flower.mapper.MemberLevelMapper;
import org.dromara.flower.mapper.MemberPurchaseRecordMapper;
import org.dromara.flower.platform.domain.AppletUserInformation;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.mapper.AppletUserInformationMapper;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.IMemberPurchaseRecordService;
import org.dromara.flowerapplet.domain.PayParam;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.service.IMemberAppletPurchaseRecordService;
import org.dromara.system.mapper.SysOssMapper;
import org.dromara.system.service.impl.SysOssServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 会员购买记录Service业务层处理
 *
 * @author chzl
 * @date 2024-12-24
 */
@RequiredArgsConstructor
@Service
public class MemberAppletPurchaseRecordServiceImpl implements IMemberAppletPurchaseRecordService {

    private final MemberPurchaseRecordMapper baseMapper;
    private final AppletUserInformationMapper userInformationMapper;
    private final MemberLevelMapper memberLevelMapper;
    private final IAppletUserInformationService appletUserInformationService;
    private final MarketingMemberPromotionPlanMapper marketingMemberPromotionPlanMapper;

    @Resource
    private final IPayService payService;

    private final static Long ZERO = 0L;
    private final static Long ONE = 1L;

    /**
     * 查询会员购买记录
     *
     * @param createBy 创建用户ID
     * @return 会员购买记录
     */
    @Override
    public MemberPurchaseRecordVo queryById(Long createBy) {
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = new LambdaQueryWrapper<>();
        lqw.eq(MemberPurchaseRecord::getCreateBy, createBy);
        lqw.eq(MemberPurchaseRecord::getStatus, 1);
        return baseMapper.selectVoOne(lqw);
    }

    /**
     * 分页查询会员购买记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 会员购买记录分页列表
     */
    @Override
    public TableDataInfo<MemberPurchaseRecordVo> queryPageList(MemberPurchaseRecordBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = buildQueryWrapper(bo);
        Page<MemberPurchaseRecordVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        // 查询会员图标
        if (!result.getRecords().isEmpty()) {
            List<Long> list = result.getRecords().stream()
                .map(MemberPurchaseRecordVo::getMemberLevelId)
                .filter(Objects::nonNull)
                .distinct()
                .toList();
            List<MemberLevelVo> memberLevelVos = new ArrayList<>();
            if (!list.isEmpty()) {
                memberLevelVos = memberLevelMapper.selectMemberLevelIds(list);
            }
            if (!memberLevelVos.isEmpty()) {
                Map<Long, MemberLevelVo> collect = memberLevelVos.stream()
                    .collect(Collectors.toMap(
                        MemberLevelVo::getId,
                        member -> member,
                        (existing, replacement) -> existing
                    ));
                result.getRecords().forEach(v -> {
                    if (collect.containsKey(v.getMemberLevelId())) {
                        v.setMemberLevelVo(collect.get(v.getMemberLevelId()));
                    }
                });
            }
        }
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的会员购买记录列表
     *
     * @param bo 查询条件
     * @return 会员购买记录列表
     */
    @Override
    public List<MemberPurchaseRecordVo> queryList(MemberPurchaseRecordBo bo) {
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<MemberPurchaseRecord> buildQueryWrapper(MemberPurchaseRecordBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<MemberPurchaseRecord> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, MemberPurchaseRecord::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getOrderCode()), MemberPurchaseRecord::getOrderCode, bo.getOrderCode());
        lqw.like(StringUtils.isNotBlank(bo.getMemberId()), MemberPurchaseRecord::getMemberId, bo.getMemberId());
        lqw.like(StringUtils.isNotBlank(bo.getMemberName()), MemberPurchaseRecord::getMemberName, bo.getMemberName());
        lqw.eq(StringUtils.isNotBlank(bo.getPhone()), MemberPurchaseRecord::getPhone, bo.getPhone());
        lqw.eq(bo.getMemberLevelId() != null, MemberPurchaseRecord::getMemberLevelId, bo.getMemberLevelId());
        lqw.eq(StringUtils.isNotBlank(bo.getGrade()), MemberPurchaseRecord::getGrade, bo.getGrade());
        lqw.like(StringUtils.isNotBlank(bo.getGradeName()), MemberPurchaseRecord::getGradeName, bo.getGradeName());
        lqw.eq(bo.getPrice() != null, MemberPurchaseRecord::getPrice, bo.getPrice());
        return lqw;
    }

    /**
     * 新增会员购买记录
     *
     * @param bo 会员购买记录
     * @return 是否新增成功
     */
    @Override
    public MemberPurchaseRecordVo insertByBo(MemberPurchaseRecordBo bo) {
        MemberPurchaseRecord add = MapstructUtils.convert(bo, MemberPurchaseRecord.class);
        if (add == null) {
            return new MemberPurchaseRecordVo();
        }
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());

        }
        return MapstructUtils.convert(add, MemberPurchaseRecordVo.class);
    }

    /**
     * 修改会员购买记录
     *
     * @param bo 会员购买记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(MemberPurchaseRecordBo bo) {
        MemberPurchaseRecord update = MapstructUtils.convert(bo, MemberPurchaseRecord.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(MemberPurchaseRecord entity) {
        Date date = new Date();
        entity.setCreateTime(date);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);

        // 获取增加一年后的日期
        Date nextYearDate = calendar.getTime();
        entity.setEndTime(nextYearDate);
        entity.setStatus(ZERO);
        entity.setPayStatus(ZERO);
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除会员购买记录信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if (isValid) {
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }

    /**
     * @param bo 会员购买记录
     * @return
     */
    @Override
    @Transactional
    public Boolean payLaterUpdateByBo(MemberPurchaseRecordBo bo) {
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (loginUser == null) {
            return false;
        }
        if (bo.getPayInfo() == null) {
            return false;
        }
        if (!ONE.equals(bo.getPayStatus())) {
            return false;
        }
        MemberPurchaseRecord update = MapstructUtils.convert(bo, MemberPurchaseRecord.class);
        update.setStatus(ONE);
        update.setPayStatus(ONE);
        // 修改其余会员购买记录状态为  0 关闭
        baseMapper.updateOtherMemberInfoByUserID(loginUser.getUserId(), update.getId());
        // 修改会员基础信息中的会员等级
        AppletUserInformation auf = new AppletUserInformation();
        auf.setUserId(loginUser.getUserId());
        auf.setMemberLevelId(update.getMemberLevelId());
        userInformationMapper.updateById(auf);
        // 更新会员推广记录表信息
        updateMarketingMemberPromotionRecord(update, loginUser);
        baseMapper.updateById(update);
        return true;
    }

    // 更新会员推广记录表信息
    private void updateMarketingMemberPromotionRecord(MemberPurchaseRecord update, LoginUser loginUser) {
        AppletUserInformationVo app = userInformationMapper.selectVoById(loginUser.getUserId());
        // 查询推广计划信息
        LambdaQueryWrapper<MarketingMemberPromotionPlan> lqw = new LambdaQueryWrapper<MarketingMemberPromotionPlan>();
        lqw.eq(MarketingMemberPromotionPlan::getStatus, 1);
        lqw.eq(MarketingMemberPromotionPlan::getCategoryDetailsId, app.getMemberLevelId());
        lqw.le(MarketingMemberPromotionPlan::getActivityEnd, new Date());
        lqw.ge(MarketingMemberPromotionPlan::getResidue, 0);
        lqw.ge(MarketingMemberPromotionPlan::getSurplusRewar, 0);
        MarketingMemberPromotionPlanVo mo = marketingMemberPromotionPlanMapper.selectVoById(lqw);
        // 没有mo就不更新数据
        if (mo != null) {
            MarketingMemberPromotionPlan plan = new MarketingMemberPromotionPlan();
            MarketingMemberPromotionPecord promotionRecord = new MarketingMemberPromotionPecord();
            plan.setId(mo.getId());
            plan.setResidue(mo.getResidue() - 1L);
            if (plan.getRewardAmount() - update.getPrice() > 0) {
                plan.setRewardAmount(plan.getRewardAmount() - update.getPrice());
                promotionRecord.setPromotionCashback(BigDecimal.valueOf(mo.getRewardAmount()));
                // TODO 未完成
            }
            if (mo.getResidue() - ONE == ZERO) {
                plan.setStatus(ZERO);
            }
            marketingMemberPromotionPlanMapper.updateById(plan);
        }
    }

    @Override
    public R<WxJsapiResponse> submitOrders(PayParam payParam) throws Exception {
        MemberPurchaseRecordVo recordVo = this.queryById(payParam.getOrderNumbers());
        if (recordVo == null) {
            throw new RuntimeException("订单不存在");
        }
        if (recordVo.getStatus() != 0) {
            throw new RuntimeException("订单状态错误");
        }

        AppletUserInformationVo appletUserInformationVo = appletUserInformationService.queryById(recordVo.getCreateBy());
        if (appletUserInformationVo == null) {
            throw new RuntimeException("用户不存在");
        }
        LoginUser loginUser = LoginHelper.getLoginUser();
        if (loginUser == null || loginUser.getUserId() != recordVo.getCreateBy()) {
            throw new RuntimeException("支付失败");
        }
        WxPayRequest payJSAPIParam = new WxPayRequest();
        payJSAPIParam.setClientIp(IpUtils.getIpAddr());
        payJSAPIParam.setOutTradeNo(recordVo.getOrderCode());
        payJSAPIParam.setAmount(recordVo.getPrice());
        payJSAPIParam.setOpenId(appletUserInformationVo.getOpenid());
        payJSAPIParam.setDescription(loginUser.getUserId() + "会员购买");
        WxJsapiResponse wxJsapiResponse = payService.JsapiOrder(payJSAPIParam);
        if (wxJsapiResponse == null) {
            R.fail("支付失败");
        }
        return R.ok(wxJsapiResponse);
    }

    @Override
    public R<String> refundOrder(WxRefundRequest wxRefundRequest) throws Exception {
        Refund refund = payService.refundOrder(wxRefundRequest);
//                log.info("请求退款返回：" + refund);
        //接收退款返回参数
        //  Status status = refund.getStatus();
        if (Status.SUCCESS.equals(refund.getStatus().SUCCESS)) {
            //说明退款成功，开始接下来的业务操作
            //你的业务代码，根据请求返回状态修改对应订单状态
            return R.ok("退款成功");
        }
        if (Status.PROCESSING.equals(refund.getStatus().PROCESSING)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            return R.ok("退款中");
        }
        if (Status.ABNORMAL.equals(refund.getStatus().ABNORMAL)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            return R.fail("退款异常");
        }
        if (Status.CLOSED.equals(refund.getStatus().CLOSED)) {
            //你的业务代码，根据请求返回状态修改对应订单状态
            return R.fail("退款关闭");
        }
        return null;
    }
}
