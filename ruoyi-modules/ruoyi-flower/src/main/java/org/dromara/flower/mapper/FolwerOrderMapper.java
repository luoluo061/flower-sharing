package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.FolwerOrder;
import org.dromara.flower.domain.vo.FolwerOrderInfoVo;
import org.dromara.flower.domain.vo.FolwerOrderVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 订单Mapper接口
 *
 * @author Lion Li
 * @date 2024-12-25
 */
public interface FolwerOrderMapper extends BaseMapperPlus<FolwerOrder, FolwerOrderVo> {

     FolwerOrderInfoVo selectOrderInfoVoById(@Param("orderNumber") Long orderId);

}
