package org.dromara.flowerapplet.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import com.wechat.pay.java.service.payments.model.Transaction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.HttpStatus;
import org.dromara.common.core.domain.R;
import org.dromara.common.mypay.server.IPayService;
import org.dromara.flowerapplet.domain.vo.FolwerAppletOrderVo;
import org.dromara.flowerapplet.service.IFolwerAppletOrderService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("/wxpayback")
public class WxPayCallbackController {

    private final IPayService payService;

    private final IFolwerAppletOrderService folwerAppletOrderService;

    /***
     * 微信小程序支付回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SaIgnore
    @PostMapping("/pay/payCallback")
    public R<FolwerAppletOrderVo> callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Object ret = payService.confirmOrder(request,response);
            FolwerAppletOrderVo folwerAppletOrderVo = folwerAppletOrderService.payCallbackOrder((Transaction) ret);
            return R.ok("操作成功",folwerAppletOrderVo);
        } catch (Exception ex) {
            return R.fail("操作失败", null);
        }
    }

    /***
     * 微信小程序退款回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SaIgnore
    @PostMapping("/pay/refundCallback")
    public R<String> refundCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Object ret = payService.refundNotify(request);
            return R.ok("操作成功",ret.toString());
        } catch (Exception ex) {
            return R.fail("操作失败", ex.getLocalizedMessage());
        }
    }
}
