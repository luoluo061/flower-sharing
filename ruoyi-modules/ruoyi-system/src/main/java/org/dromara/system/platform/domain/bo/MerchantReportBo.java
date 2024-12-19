package org.dromara.system.platform.domain.bo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MerchantReportBo {
    private LocalDate startTime;

    private LocalDate endTime;


}
