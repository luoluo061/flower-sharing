package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.MemberPointsExchangeGold;
import org.dromara.flower.domain.vo.MemberPointsExchangeGoldVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 会员中心--积分兑换为金币Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface MemberPointsExchangeGoldMapper extends BaseMapperPlus<MemberPointsExchangeGold, MemberPointsExchangeGoldVo> {

    Long selectPointsCount(@Param("userId") Long userId);
}
