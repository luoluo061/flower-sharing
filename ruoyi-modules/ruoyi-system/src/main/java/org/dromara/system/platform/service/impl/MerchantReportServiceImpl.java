package org.dromara.system.platform.service.impl;

import lombok.AllArgsConstructor;

import org.dromara.system.platform.domain.bo.MerchantReportBo;
import org.dromara.system.platform.domain.vo.MerchantReportVo;
import org.dromara.system.platform.mapper.MerchantReportMapper;
import org.dromara.system.platform.service.MerchantReportService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MerchantReportServiceImpl implements MerchantReportService {
    private final MerchantReportMapper merchantReportMapper;

    @Override
    public MerchantReportVo report(MerchantReportBo bo) {

        return merchantReportMapper.selectReport(bo);
    }
}
