package org.dromara.system.platform.domain.vo;

import lombok.Data;

import java.util.List;

@Data
public class MerchantReportVo {
   private Long internalMerchant;
   private Long externalMerchant;
   private Long expiryMerchant;

   List<InternalMerchantVo> internalMerchantFromDay;

   List<ExternalMerchantVo> externalMerchantFromDay;
}
