package org.dromara.flower.mapper;

import org.dromara.flower.domain.FolwerCreditOrder;
import org.dromara.flower.domain.vo.FolwerCreditOrderInfoVo;
import org.dromara.flower.domain.vo.FolwerCreditOrderVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.springframework.data.repository.query.Param;

/**
 * 积分订单Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface FolwerCreditOrderMapper extends BaseMapperPlus<FolwerCreditOrder, FolwerCreditOrderVo> {

    FolwerCreditOrderInfoVo selectCreditOrderInfoVoById(@Param("orderId") Long orderId);

}
