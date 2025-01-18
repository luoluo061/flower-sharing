package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.FolwerCreditGetrecords;
import org.dromara.flower.domain.vo.FolwerCreditGetrecordsVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 积分获取记录Mapper接口
 *
 * @author mlhxj
 * @date 2024-12-27
 */
public interface FolwerCreditGetrecordsMapper extends BaseMapperPlus<FolwerCreditGetrecords, FolwerCreditGetrecordsVo> {

    Long getReditGetrecords(@Param("userId") Long userId);
}
