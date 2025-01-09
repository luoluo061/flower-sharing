package org.dromara.flower.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.flower.domain.MemberPurchaseRecord;
import org.dromara.flower.domain.vo.MemberPurchaseRecordVo;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;

/**
 * 会员购买记录Mapper接口
 *
 * @author chzl
 * @date 2024-12-24
 */
public interface MemberPurchaseRecordMapper extends BaseMapperPlus<MemberPurchaseRecord, MemberPurchaseRecordVo> {

    void updateOtherMemberInfoByUserID(@Param("userId") Long userId, @Param("id") Long id);
}
