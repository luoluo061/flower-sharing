package org.dromara.common.mypay.server.impl;

import com.wechat.pay.java.service.profitsharing.ProfitsharingService;
import com.wechat.pay.java.service.profitsharing.model.*;
import org.dromara.common.core.exception.ServiceException;
import org.dromara.common.mypay.config.properties.PayProperties;
import org.dromara.common.mypay.constant.SharingReceiverRelationType;
import org.dromara.common.mypay.constant.SharingReceiverType;
import org.dromara.common.mypay.domain.PayProfitsharingParam;
import org.dromara.common.mypay.server.SharingService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SharingServiceImpl implements SharingService {
    private ProfitsharingService profitsharingService;

    private PayProperties wxProperties;

    public static final String TRUE_UNFREEZE_UNSPLIT = "0";
    public static final String FALSE_UNFREEZE_UNSPLIT = "1";

    /**
     * 添加分账接收方
     * @param type 接收方类型
     * @param account 接收方账号
     * @param relationType 分账关系类型
     * @return 结果
     * 商户发起添加分账接收方请求，建立分账接收方列表。后续可通过发起分账请求，将分账方商户结算后的资金，分到该分账接收方
     * 商户需确保向微信支付传输用户身份信息和账号标识信息做一致性校验已合法征得用户授权
     */
    @Override
    public AddReceiverResponse addReceiver(String type, String account, String relationType) {

        AddReceiverRequest request = new AddReceiverRequest();
        // 设置APPID
        request.setAppid(wxProperties.getAppId());
        // 设置分账接收方账号
        request.setAccount(account);

        // 设置分账接收方
        if(SharingReceiverType.MERCHANT_ID.equals(type)){
            request.setType(ReceiverType.MERCHANT_ID);
        } else if (SharingReceiverType.PERSONAL_OPENID.equals(type)) {
            request.setType(ReceiverType.PERSONAL_OPENID);
        } else if (SharingReceiverType.PERSONAL_SUB_OPENID.equals(type)) {
            request.setType(ReceiverType.PERSONAL_SUB_OPENID);
        }

        // 设置分账关系类型
        if (SharingReceiverRelationType.SERVICE_PROVIDER.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.SERVICE_PROVIDER);
        } else if (SharingReceiverRelationType.STORE.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.STORE);
        } else if (SharingReceiverRelationType.STAFF.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.STAFF);
        } else if (SharingReceiverRelationType.STORE_OWNER.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.STORE_OWNER);
        } else if (SharingReceiverRelationType.PARTNER.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.PARTNER);
        } else if (SharingReceiverRelationType.HEADQUARTER.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.HEADQUARTER);
        } else if (SharingReceiverRelationType.BRAND.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.BRAND);
        } else if (SharingReceiverRelationType.DISTRIBUTOR.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.DISTRIBUTOR);
        } else if (SharingReceiverRelationType.USER.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.USER);
        } else if (SharingReceiverRelationType.SUPPLIER.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.SUPPLIER);
        } else if (SharingReceiverRelationType.CUSTOM.equals(relationType)) {
            request.setRelationType(ReceiverRelationType.CUSTOM);
        }

        try {
            // 请求微信服务器添加分账接收方
            AddReceiverResponse response = profitsharingService.addReceiver(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }

    }

    /**
     * 删除分账接收方
     * @param type 接收方类型
     * @param account 接收方账号
     * @return 结果
     * 商户发起删除分账接收方请求。删除后，不支持将分账方商户结算后的资金，分到该分账接收方
     */
    @Override
//    @Transactional
    public DeleteReceiverResponse deleteReceiver(String type, String account) {

        DeleteReceiverRequest request = new DeleteReceiverRequest();
        // 设置APPID
        request.setAppid(wxProperties.getAppId());
        // 设置分账接收方账号
        request.setAccount(account);

        // 设置分账接收方类型
        if(SharingReceiverType.MERCHANT_ID.equals(type)){
            request.setType(ReceiverType.MERCHANT_ID);
        } else if (SharingReceiverType.PERSONAL_OPENID.equals(type)) {
            request.setType(ReceiverType.PERSONAL_OPENID);
        } else if (SharingReceiverType.PERSONAL_SUB_OPENID.equals(type)) {
            request.setType(ReceiverType.PERSONAL_SUB_OPENID);
        }

        try {
            // 请求微信服务器删除分账接收方
            DeleteReceiverResponse response = profitsharingService.deleteReceiver(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }

    }

    /**
     * 单次分账请求
     *
     * @return 结果
     * 微信订单支付成功后，商户发起分账请求，将结算后的资金分到分账接收方
     * 对同一笔订单最多能发起50次分账请求，每次请求最多分给50个接收方
     * 此接口采用异步处理模式，即在接收到商户请求后，会先受理请求再异步处理，最终的分账结果可以通过查询分账接口获取
     * 商户需确保向微信支付传输用户身份信息和账号标识信息做一致性校验已合法征得用户授权
     */
    @Override
//    @Transactional
    public OrdersEntity ordersSharing(PayProfitsharingParam payProfitsharingParam, String unfreezeUnsplit) {
        CreateOrderRequest request = new CreateOrderRequest();
        String outOrderNo = payProfitsharingParam.getOutOrderNo();//System.currentTimeMillis() + String.valueOf(new Random().nextInt(999999));

        // 设置APPID
        request.setAppid(wxProperties.getAppId());
        // 设置微信订单号
        request.setTransactionId(payProfitsharingParam.getTransactionId());
        // 设置商户订单号
        request.setOutOrderNo(outOrderNo);

        List<CreateOrderReceiver> receivers = new ArrayList<>();
        // 设置分账接收方, 可以为多个分账接收方, 这里测试只设置一个
        CreateOrderReceiver receiver1 = new CreateOrderReceiver();
        // 设置分账接收方类型
        receiver1.setType(payProfitsharingParam.getType());
        // 设置分账接收方账号, 换成自己的接收方账号
        receiver1.setAccount(payProfitsharingParam.getAccount());
        // 设置分账金额, 这里测试金额为1分, 默认最高只能分订单的30%
        receiver1.setAmount(payProfitsharingParam.getAmount());
        // 设置分账描述
        receiver1.setDescription(payProfitsharingParam.getDescription());
        // 设置分账接收方列表
        receivers.add(receiver1);
        request.setReceivers(receivers);

        // 设置是否解冻剩余资金
        if (TRUE_UNFREEZE_UNSPLIT.equals(unfreezeUnsplit)) request.setUnfreezeUnsplit(Boolean.TRUE);
        if (FALSE_UNFREEZE_UNSPLIT.equals(unfreezeUnsplit)) request.setUnfreezeUnsplit(Boolean.FALSE);

        try {
            // 请求微信服务器发起分账请求
            OrdersEntity response = profitsharingService.createOrder(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }

    }

    /**
     * 查询分账结果
     * @param outOrderNo 商户分账单号
     * @param transactionId 微信订单号
     * @return 结果
     * 发起分账请求后，可调用此接口查询分账结果
     * 发起解冻剩余资金请求后，可调用此接口查询解冻剩余资金的结果
     */
    @Override
//    @Transactional
    public OrdersEntity sharingResult(String outOrderNo, String transactionId) {
        QueryOrderRequest request = new QueryOrderRequest();

        // 1. 设置微信订单号
        request.setTransactionId(transactionId);
        // 2. 设置商户订单号
        request.setOutOrderNo(outOrderNo);

        try {
            // 请求微信服务器查询分账结果
            OrdersEntity response = profitsharingService.queryOrder(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }


    }

    /**
     * 请求分账回退
     * @param orderId 微信分账单号
     * @param returnMchid 回退商户号
     * @param description 回退描述
     * @return 结果
     * 如果订单已经分账，在退款时，可以先调此接口，将已分账的资金从分账接收方的账户回退给分账方，再发起退款。
     * 分账回退以原分账单为依据，支持多次回退，申请回退总金额不能超过原分账单分给该接收方的金额
     * 此接口采用同步处理模式，即在接收到商户请求后，会实时返回处理结果
     * 对同一笔分账单最多能发起20次分账回退请求
     * 退款和分账回退没有耦合，分账回退可以先于退款请求，也可以后于退款请求
     * 此功能需要接收方在商户平台-交易中心-分账-分账接收设置下，开启同意分账回退后，才能使用
     * 不支持针对“分账到零钱”的分账单发起分账回退。
     * 分账回退的时限是180天。
     */
    @Override
    public ReturnOrdersEntity returnSharing(String orderId, String returnMchid, String description) {
        CreateReturnOrderRequest request = new CreateReturnOrderRequest();
        // 生成商户回退单号
        String outReturnNo = System.currentTimeMillis() + String.valueOf(new Random().nextInt(999999));

        // 设置微信分账单号
        request.setOrderId(orderId);
        // 设置回退商户号
        request.setReturnMchid(returnMchid);
        // 设置商户回退单号
        request.setOutReturnNo(outReturnNo);
        // 设置回退金额, 这里测试金额为1分
        request.setAmount(1L);
        // 设置回退描述
        request.setDescription(description);

        try {
            // 请求微信服务器发起分账回退请求
            ReturnOrdersEntity response = profitsharingService.createReturnOrder(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }

    }

    /**
     * 查询分账回退结果
     * @param outReturnNo 商户回退单号
     * @param outOrderNo 商户订单号
     * @return 结果
     * 商户需要核实回退结果，可调用此接口查询回退结果。
     * 如果分账回退接口返回状态为处理中，可调用此接口查询回退结果
     */
    @Override
    public ReturnOrdersEntity returnSharingResult(String outReturnNo, String outOrderNo) {
        QueryReturnOrderRequest request = new QueryReturnOrderRequest();
        // 1. 设置商户回退单号
        request.setOutReturnNo(outReturnNo);
        // 2. 设置商户订单号
        request.setOutOrderNo(outOrderNo);

        try {
            // 请求微信服务器查询分账回退结果
            ReturnOrdersEntity response = profitsharingService.queryReturnOrder(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }
    }

    /**
     * 解冻剩余资金
     * @param transactionId 微信订单号
     * @param outOrderNo 商户分账单号
     * @param description 描述信息
     * @return 结果
     * 不需要进行分账的订单，可直接调用本接口将订单的金额全部解冻给本商户
     * 调用分账接口后，需要解冻剩余资金时，调用本接口将剩余的分账金额全部解冻给本商户
     * 此接口采用异步处理模式，即在接收到商户请求后，优先受理请求再异步处理，最终的分账结果可以通过查询分账接口获取
     */
    @Override
    public OrdersEntity unfreeze(String transactionId, String outOrderNo, String description) {
        UnfreezeOrderRequest request = new UnfreezeOrderRequest();

        // 1. 设置微信订单号
        request.setTransactionId(transactionId);
        // 2. 设置商户订单号
        request.setOutOrderNo(outOrderNo);
        // 3. 设置描述信息
        request.setDescription(description);

        try {
            // 请求微信服务器发起解冻剩余资金请求
            OrdersEntity response = profitsharingService.unfreezeOrder(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }
    }

    /**
     * 查询剩余待分金额
     * @param transactionId 微信订单号
     * @return 结果
     * 可调用此接口查询订单剩余待分金额
     */
    @Override
    public QueryOrderAmountResponse sharingAmountBalance(String transactionId) {
        QueryOrderAmountRequest request = new QueryOrderAmountRequest();

        // 设置微信订单号
        request.setTransactionId(transactionId);

        try {
            // 请求微信服务器查询剩余待分金额
            QueryOrderAmountResponse response = profitsharingService.queryOrderAmount(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }
    }

    /**
     * 查询分账比例
     * @param mchId 商户号
     * @return 结果
     */
    @Override
    public QueryMerchantRatioResponse sharingRatio(String mchId) {

        QueryMerchantRatioRequest request = new QueryMerchantRatioRequest();
        // 设置分账接收方商户号
        request.setSubMchid(mchId);

        try {
            // 请求微信服务器查询分账比例
            QueryMerchantRatioResponse response = profitsharingService.queryMerchantRatio(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }
    }

    /**
     * 下载分账账单
     * @param billDate 分账账单日期
     * @param billType 分账账单类型
     * @return 结果
     * 微信支付按天提供分账账单文件，商户可以通过该接口获取账单文件的下载地址。文件内包含分账相关的金额、时间等信息，供商户核对到账等情况
     * 微信侧未成功的分账单不会出现在对账单中。
     * 对账单中涉及金额的字段单位为“元”；
     * 对账单接口只能下载三个月以内的账单。
     * 账单文件包括明细数据和汇总数据两部分，每一部分都包含一行表头和若干行具体数据。
     * 明细数据每一行对应一笔分账或一笔回退，同时每一个数据前加入了字符`，以避免数据被Excel按科学计数法处理。如需汇总金额等数据，可以批量替换掉该字符
     */
    @Override
    public SplitBillResponse downloadBill(String billDate, String billType) {
        SplitBillRequest request = new SplitBillRequest();
        // 1. 设置账单日期
        request.setBillDate(billDate);
        if(billType != null && !billType.isEmpty()){
            // 2. 设置账单类型
            request.setTarType(SplitBillTarType.GZIP);
        }

        try {
            // 请求微信服务器下载分账账单
            SplitBillResponse response = profitsharingService.splitBill(request);
            return response;
        } catch (ServiceException e) {
            return null;
        }
    }

}
