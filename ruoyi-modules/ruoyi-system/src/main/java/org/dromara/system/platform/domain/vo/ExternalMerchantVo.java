package org.dromara.system.platform.domain.vo;

import lombok.Data;

import java.time.LocalDate;
@Data
public class ExternalMerchantVo {
    private LocalDate reportDate;

    private Long number;
}
