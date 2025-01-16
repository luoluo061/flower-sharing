package org.dromara.flowerapplet.controller;

import cn.dev33.satoken.annotation.SaIgnore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.common.core.constant.HttpStatus;
import org.dromara.common.core.domain.R;
import org.dromara.common.mypay.server.IPayService;
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

    /***
     * 微信小程序支付回调
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @SaIgnore
    @PostMapping("/pay/payCallback")
    public R<String> callBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        try {
            Object ret = payService.confirmOrder(request,response);
            return R.ok("操作成功",ret.toString());
        } catch (Exception ex) {
            return R.fail("操作失败", ex.getLocalizedMessage());
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
