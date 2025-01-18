package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.MemberExchangeRecord;
import org.dromara.flower.domain.vo.MemberExchangeRecordVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 会员中心--兑换记录Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface MemberExchangeRecordMapper extends BaseMapperPlus<MemberExchangeRecord, MemberExchangeRecordVo> {

    Long selectExchangeRecord(@Param("userId") Long userId);
}
