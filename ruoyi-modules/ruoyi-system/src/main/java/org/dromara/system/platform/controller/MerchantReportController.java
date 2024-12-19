package org.dromara.system.platform.controller;

import lombok.RequiredArgsConstructor;
import org.dromara.common.core.domain.R;
import org.dromara.system.platform.domain.bo.MerchantReportBo;
import org.dromara.system.platform.domain.vo.MerchantReportVo;
import org.dromara.system.platform.service.impl.MerchantReportServiceImpl;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商户统计
 *
 */
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class MerchantReportController {
    private final MerchantReportServiceImpl merchantReportService;

    @PostMapping
    public R<MerchantReportVo> getReport(@RequestBody MerchantReportBo bo){
        return R.ok(merchantReportService.report(bo)) ;
    }

}
