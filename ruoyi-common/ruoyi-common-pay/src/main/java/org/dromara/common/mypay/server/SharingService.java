package org.dromara.common.mypay.server;

import com.wechat.pay.java.service.profitsharing.model.*;
import org.dromara.common.mypay.domain.PayProfitsharingParam;

public interface SharingService {

    /***
     * 添加分账接收方
     * @param type
     * @param account
     * @param relationType
     * @return
     */
    AddReceiverResponse addReceiver(String type, String account, String relationType);

    /***
     * 删除分账接收方
     * @param type
     * @param account
     * @return
     */
    //    @Transactional
    DeleteReceiverResponse deleteReceiver(String type, String account);

    /***
     * 单次分账请求
     * @param payProfitsharingParam
     * @param unfreezeUnsplit
     * @return
     */
    //    @Transactional
    OrdersEntity ordersSharing(PayProfitsharingParam payProfitsharingParam, String unfreezeUnsplit);

    /***
     * 查询分账结果
     * @param outOrderNo
     * @param transactionId
     * @return
     */
    //    @Transactional
    OrdersEntity sharingResult(String outOrderNo, String transactionId);

    /***
     * 退款单号查询分账结果
     * @param orderId
     * @param returnMchid
     * @param description
     * @return
     */
    ReturnOrdersEntity returnSharing(String orderId, String returnMchid, String description);

    /***
     * 查询分账回退结果
     * @param outReturnNo
     * @param outOrderNo
     * @return
     */
    ReturnOrdersEntity returnSharingResult(String outReturnNo, String outOrderNo);

    /***
     * 解冻剩余资金
     * @param transactionId
     * @param outOrderNo
     * @param description
     * @return
     */
    OrdersEntity unfreeze(String transactionId, String outOrderNo, String description);

    /**
     * 查询剩余待分金额
     * @param transactionId 微信订单号
     * @return 结果
     * 可调用此接口查询订单剩余待分金额
     */
    QueryOrderAmountResponse sharingAmountBalance(String transactionId);

    /**
     * 查询分账比例
     * @param mchId 商户号
     * @return 结果
     */
    QueryMerchantRatioResponse sharingRatio(String mchId);

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
    SplitBillResponse downloadBill(String billDate, String billType);
}
