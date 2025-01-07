package org.dromara.system.mapper;

import org.apache.ibatis.annotations.Param;
import org.dromara.common.mybatis.core.mapper.BaseMapperPlus;
import org.dromara.common.mybatis.handler.MapResultHandler;
import org.dromara.system.domain.SysOss;
import org.dromara.system.domain.vo.SysOssVo;

import java.util.List;

/**
 * 文件上传 数据层
 *
 * @author Lion Li
 */
public interface SysOssMapper extends BaseMapperPlus<SysOss, SysOssVo> {

    void getIdMapUrlByIds(@Param("resultHandler") MapResultHandler<Long, String> resultHandler, @Param("ids") List<Long> ids);
}
