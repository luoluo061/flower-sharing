package org.dromara.system.platform.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.system.platform.domain.MerchantInformation;
import org.dromara.system.platform.domain.vo.MerchantInformationVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 商户信息Mapper接口
 *
 * @author Lion Li
 * @date 2024-12-05
 */
public interface MerchantInformationMapper extends BaseMapperPlus<MerchantInformation, MerchantInformationVo> {
    boolean updateStatus(@Param("status") String status,@Param("id") Long id);

}
