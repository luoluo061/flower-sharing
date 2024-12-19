package org.dromara.system.platform.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.system.platform.domain.bo.MerchantReportBo;
import org.dromara.system.platform.domain.vo.MerchantReportVo;
import org.mapstruct.Mapper;


@Mapper
public interface MerchantReportMapper {
    MerchantReportVo selectReport(@Param("bo") MerchantReportBo bo);
}
