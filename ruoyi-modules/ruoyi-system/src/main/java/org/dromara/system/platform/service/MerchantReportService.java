package org.dromara.system.platform.service;


import org.dromara.system.platform.domain.bo.MerchantReportBo;
import org.dromara.system.platform.domain.vo.MerchantReportVo;
import org.springframework.stereotype.Service;

@Service
public interface MerchantReportService {
    MerchantReportVo report(MerchantReportBo merchantReportBo);
}
