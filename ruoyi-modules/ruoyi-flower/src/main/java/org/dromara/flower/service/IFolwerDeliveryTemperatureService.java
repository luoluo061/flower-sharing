package org.dromara.flower.service;

import org.dromara.flower.domain.vo.FolwerDeliveryTemperatureVo;
import org.dromara.flower.domain.bo.FolwerDeliveryTemperatureBo;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;

import java.util.Collection;
import java.util.List;

/**
 * 城市温度Service接口
 *
 * @author mlhxj
 * @date 2025-09-04
 */
public interface IFolwerDeliveryTemperatureService {

    /**
     * 查询城市温度
     *
     * @param temperatureId 主键
     * @return 城市温度
     */
    FolwerDeliveryTemperatureVo queryById(Long temperatureId);

    /**
     * 分页查询城市温度列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 城市温度分页列表
     */
    TableDataInfo<FolwerDeliveryTemperatureVo> queryPageList(FolwerDeliveryTemperatureBo bo, PageQuery pageQuery);

    /**
     * 查询符合条件的城市温度列表
     *
     * @param bo 查询条件
     * @return 城市温度列表
     */
    List<FolwerDeliveryTemperatureVo> queryList(FolwerDeliveryTemperatureBo bo);

    /**
     * 新增城市温度
     *
     * @param bo 城市温度
     * @return 是否新增成功
     */
    Boolean insertByBo(FolwerDeliveryTemperatureBo bo);

    /**
     * 修改城市温度
     *
     * @param bo 城市温度
     * @return 是否修改成功
     */
    void updateByBo(FolwerDeliveryTemperatureBo bo);

    /**
     * 校验并批量删除城市温度信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid);

    /**
     * 获取天气信息
     *
     * @param cityCode 城市编码
     * @return 天气信息
     */
    Double getWeatherByTemperature(String cityCode);
}
