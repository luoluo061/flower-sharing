package org.dromara.flowerapplet.service.impl;

import cn.hutool.core.util.IdUtil;
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
import org.dromara.common.mypay.domain.WxJsapiResponse;
import org.dromara.common.mypay.domain.WxPayRequest;
import org.dromara.common.mypay.domain.WxRefundRequest;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.common.mypay.utils.IpUtils;
import org.dromara.common.satoken.utils.LoginHelper;
import org.dromara.flower.domain.CoursesPurchaseRecords;
import org.dromara.flower.domain.bo.CoursesPurchaseRecordsBo;
import org.dromara.flower.domain.vo.CoursesPurchaseRecordsVo;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.flower.mapper.CoursesPurchaseRecordsMapper;
import org.dromara.flower.platform.domain.vo.AppletUserInformationVo;
import org.dromara.flower.platform.service.IAppletUserInformationService;
import org.dromara.flower.service.ICoursesPurchaseRecordsService;
import org.dromara.flowerapplet.domain.PayParam;
import org.dromara.flowerapplet.service.ICoursesAppletPurchaseRecordsService;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 课程管理-课程购买记录Service业务层处理
 *
 * @author mlhxj
 * @date 2024-12-27
 */
@RequiredArgsConstructor
@Service
public class CoursesAppletPurchaseRecordsServiceImpl implements ICoursesAppletPurchaseRecordsService {

    private final CoursesPurchaseRecordsMapper baseMapper;
    private final IAppletUserInformationService appletUserInformationService;

    @Resource
    private final IPayService payService;
    /**
     * 查询课程管理-课程购买记录
     *
     * @param id 主键
     * @return 课程管理-课程购买记录
     */
    @Override
    public CoursesPurchaseRecordsVo queryById(Long id){
        return baseMapper.selectVoById(id);
    }

    /**
     * 分页查询课程管理-课程购买记录列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 课程管理-课程购买记录分页列表
     */
    @Override
    public TableDataInfo<CoursesPurchaseRecordsVo> queryPageList(CoursesPurchaseRecordsBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<CoursesPurchaseRecords> lqw = buildQueryWrapper(bo);
        Page<CoursesPurchaseRecordsVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的课程管理-课程购买记录列表
     *
     * @param bo 查询条件
     * @return 课程管理-课程购买记录列表
     */
    @Override
    public List<CoursesPurchaseRecordsVo> queryList(CoursesPurchaseRecordsBo bo) {
        LambdaQueryWrapper<CoursesPurchaseRecords> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<CoursesPurchaseRecords> buildQueryWrapper(CoursesPurchaseRecordsBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<CoursesPurchaseRecords> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getDeptId() != null, CoursesPurchaseRecords::getDeptId, bo.getDeptId());
        lqw.like(StringUtils.isNotBlank(bo.getCode()), CoursesPurchaseRecords::getCode, bo.getCode());
        lqw.like(StringUtils.isNotBlank(bo.getCoursesName()), CoursesPurchaseRecords::getCoursesName, bo.getCoursesName());
        lqw.like(StringUtils.isNotBlank(bo.getMemberId()), CoursesPurchaseRecords::getMemberId, bo.getMemberId());
        lqw.eq(bo.getAppletUserInformationId() != null, CoursesPurchaseRecords::getAppletUserInformationId, bo.getAppletUserInformationId());
        lqw.like(StringUtils.isNotBlank(bo.getName()), CoursesPurchaseRecords::getName, bo.getName());
        lqw.eq(bo.getPrice() != null, CoursesPurchaseRecords::getPrice, bo.getPrice());
        lqw.eq(bo.getStatus() != null, CoursesPurchaseRecords::getStatus, bo.getStatus());
        return lqw;
    }

    /**
     * 新增课程管理-课程购买记录
     *
     * @param bo 课程管理-课程购买记录
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(CoursesPurchaseRecordsBo bo) {
        CoursesPurchaseRecords add = MapstructUtils.convert(bo, CoursesPurchaseRecords.class);
        validEntityBeforeSave(add);
        // TODO 后期有必要在生成有意义编号 生成课程购买记录code
        add.setCode(IdUtil.fastSimpleUUID());
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setId(add.getId());
        }
        return flag;
    }

    /**
     * 修改课程管理-课程购买记录
     *
     * @param bo 课程管理-课程购买记录
     * @return 是否修改成功
     */
    @Override
    public Boolean updateByBo(CoursesPurchaseRecordsBo bo) {
        CoursesPurchaseRecords update = MapstructUtils.convert(bo, CoursesPurchaseRecords.class);
        validEntityBeforeSave(update);
        return baseMapper.updateById(update) > 0;
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(CoursesPurchaseRecords entity){
        if (entity == null){
            throw new RuntimeException("购买课程失败");
        }
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除课程管理-课程购买记录信息
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
    public R<WxJsapiResponse> submitOrders(PayParam payParam) throws Exception {
        CoursesPurchaseRecordsVo recordVo = baseMapper.selectVoById(payParam.getOrderNumbers());
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
        if (loginUser == null || !Objects.equals(loginUser.getUserId(), recordVo.getCreateBy())) {
            throw new RuntimeException("支付失败");
        }
        WxPayRequest payJSAPIParam = new WxPayRequest();
        payJSAPIParam.setClientIp(IpUtils.getIpAddr());
        payJSAPIParam.setOutTradeNo(recordVo.getCode());
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
