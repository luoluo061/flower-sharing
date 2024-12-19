package org.dromara.system.platform.mapper;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.system.platform.domain.AppletUserInformation;
import org.dromara.system.platform.domain.query.AppletUserInformationQuery;
import org.dromara.system.platform.domain.vo.AppletUserInformationVo;
import org.dromara.system.platform.domain.vo.AppletUserOrderNumVo;
import org.dromara.system.platform.domain.vo.AppletUserOrderVo;

import java.util.List;

/**
 * 小程序用户信息Mapper接口
 *
 * @author LionLi
 * @date 2024-11-19
 */
public interface AppletUserInformationMapper extends BaseMapperPlus<AppletUserInformation, AppletUserInformationVo> {

    /*boolean updateGroupAndRank(AppletUserInformationEditBo bo);*/


    IPage<AppletUserInformationVo> selectUsersByPage(Page<AppletUserInformationVo> page,@Param("query") AppletUserInformationQuery query);

    List<AppletUserInformationVo> queryList(@Param("query") AppletUserInformationQuery query);

    boolean updateStatus(@Param("id")Long id,@Param("status") String status);

    List<AppletUserOrderVo> selectOrder(@Param("userId") Long userId);

    AppletUserOrderNumVo selectOrderNum(@Param("userId") Long userId);

}
