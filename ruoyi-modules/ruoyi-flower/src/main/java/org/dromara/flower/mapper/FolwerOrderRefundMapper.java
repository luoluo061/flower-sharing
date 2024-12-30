package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.FolwerOrderRefund;
import org.dromara.flower.domain.vo.FolwerOrderInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderRefundInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderRefundVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 订单退款Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-25
 */
public interface FolwerOrderRefundMapper extends BaseMapperPlus<FolwerOrderRefund, FolwerOrderRefundVo> {

    FolwerOrderRefundInfoVo selectOrderRefundInfoVoById(@Param("orderId") Long orderId);
}
