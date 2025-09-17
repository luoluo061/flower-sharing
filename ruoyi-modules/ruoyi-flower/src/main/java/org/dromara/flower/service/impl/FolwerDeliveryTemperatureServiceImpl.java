package org.dromara.flower.service.impl;

import cn.hutool.core.bean.BeanUtil;
import org.dromara.common.core.utils.MapstructUtils;
import org.dromara.common.core.utils.StringUtils;
import org.dromara.common.mybatis.core.page.TableDataInfo;
import org.dromara.common.mybatis.core.page.PageQuery;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.dromara.flower.domain.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.dromara.flower.domain.bo.FolwerDeliveryTemperatureBo;
import org.dromara.flower.domain.vo.FolwerDeliveryTemperatureVo;
import org.dromara.flower.domain.FolwerDeliveryTemperature;
import org.dromara.flower.mapper.FolwerDeliveryTemperatureMapper;
import org.dromara.flower.service.IFolwerDeliveryTemperatureService;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Collection;

/**
 * 城市温度Service业务层处理
 *
 * @author mlhxj
 * @date 2025-09-04
 */
@RequiredArgsConstructor
@Service
public class FolwerDeliveryTemperatureServiceImpl implements IFolwerDeliveryTemperatureService {

    private final FolwerDeliveryTemperatureMapper baseMapper;

    // 基础 URL（RESTful 风格，后续拼接 city_code）
    private static final String WEATHER_BASE_URL = "http://t.weather.itboy.net/api/weather/city/";

    @Autowired // 注入配置好的 RestTemplate
    private RestTemplate restTemplate;

    /**
     * 查询城市温度
     *
     * @param temperatureId 主键
     * @return 城市温度
     */
    @Override
    public FolwerDeliveryTemperatureVo queryById(Long temperatureId){
        return baseMapper.selectVoById(temperatureId);
    }

    /**
     * 分页查询城市温度列表
     *
     * @param bo        查询条件
     * @param pageQuery 分页参数
     * @return 城市温度分页列表
     */
    @Override
    public TableDataInfo<FolwerDeliveryTemperatureVo> queryPageList(FolwerDeliveryTemperatureBo bo, PageQuery pageQuery) {
        LambdaQueryWrapper<FolwerDeliveryTemperature> lqw = buildQueryWrapper(bo);
        Page<FolwerDeliveryTemperatureVo> result = baseMapper.selectVoPage(pageQuery.build(), lqw);
        return TableDataInfo.build(result);
    }

    /**
     * 查询符合条件的城市温度列表
     *
     * @param bo 查询条件
     * @return 城市温度列表
     */
    @Override
    public List<FolwerDeliveryTemperatureVo> queryList(FolwerDeliveryTemperatureBo bo) {
        LambdaQueryWrapper<FolwerDeliveryTemperature> lqw = buildQueryWrapper(bo);
        return baseMapper.selectVoList(lqw);
    }

    private LambdaQueryWrapper<FolwerDeliveryTemperature> buildQueryWrapper(FolwerDeliveryTemperatureBo bo) {
        Map<String, Object> params = bo.getParams();
        LambdaQueryWrapper<FolwerDeliveryTemperature> lqw = Wrappers.lambdaQuery();
        lqw.eq(bo.getTemperatureId() != null, FolwerDeliveryTemperature::getTemperatureId, bo.getTemperatureId());
        lqw.eq(bo.getCityCode() != null, FolwerDeliveryTemperature::getCityCode, bo.getCityCode());
        lqw.like(StringUtils.isNotBlank(bo.getCityName()), FolwerDeliveryTemperature::getCityName, bo.getCityName());
        lqw.eq(bo.getAreaCode() != null, FolwerDeliveryTemperature::getAreaCode, bo.getAreaCode());
        lqw.eq(StringUtils.isNotBlank(bo.getTemperature()), FolwerDeliveryTemperature::getTemperature, bo.getTemperature());
        return lqw;
    }

    /**
     * 新增城市温度
     *
     * @param bo 城市温度
     * @return 是否新增成功
     */
    @Override
    public Boolean insertByBo(FolwerDeliveryTemperatureBo bo) {
        FolwerDeliveryTemperature add = MapstructUtils.convert(bo, FolwerDeliveryTemperature.class);
        validEntityBeforeSave(add);
        boolean flag = baseMapper.insert(add) > 0;
        if (flag) {
            bo.setTemperatureId(add.getTemperatureId());
        }
        return flag;
    }

    /**
     * 修改城市温度
     *
     * @param bo 城市温度
     * @return 是否修改成功
     */
    @Override
    @Async
    public void updateByBo(FolwerDeliveryTemperatureBo bo) {
        List<FolwerDeliveryTemperatureVo> folwerDeliveryTemperatureVos = this.queryList(bo);
        FolwerDeliveryTemperatureVo folwerDeliveryTemperatureVo = folwerDeliveryTemperatureVos.get(0);
        String cityCode = folwerDeliveryTemperatureVo.getCityCode().toString();
        WeatherResponse weatherByCityCode = getWeatherByCityCode(cityCode);
        folwerDeliveryTemperatureVo.setTemperature(weatherByCityCode.getData().getWendu());
        FolwerDeliveryTemperature update = BeanUtil.copyProperties(folwerDeliveryTemperatureVo, FolwerDeliveryTemperature.class);
        validEntityBeforeSave(update);
        boolean b = baseMapper.updateById(update) > 0;
    }

    /**
     * 修改城市温度
     */
    // 使用cron表达式，每天凌晨1点执行任务
//    @Scheduled(cron = "0 0 1 * * *")
//    @Async
//    public void updateByBo() {
//        List<FolwerDeliveryTemperatureVo> folwerDeliveryTemperatureVos = this.queryList(new FolwerDeliveryTemperatureBo());
//        for (FolwerDeliveryTemperatureVo folwerDeliveryTemperatureVo : folwerDeliveryTemperatureVos){
//            Long cityCode = folwerDeliveryTemperatureVo.getCityCode();
//
//        }
//
//        FolwerDeliveryTemperature update = MapstructUtils.convert(bo, FolwerDeliveryTemperature.class);
//        validEntityBeforeSave(update);
//        boolean b = baseMapper.updateById(update) > 0;
//    }


    @Override
    public Double getWeatherByTemperature(String cityCode) {
        WeatherResponse weatherByCityCode = getWeatherByCityCode(cityCode);
        if (weatherByCityCode == null) {
            return null;
        }
        String wendu = weatherByCityCode.getData().getWendu();
        return Double.valueOf(wendu);
    }

    /**
     * 发送 GET 请求获取天气数据
     * @param cityCode 9位城市编码（如 101030100）
     * @return 天气接口返回的 JSON 字符串（可后续转为实体类）
     */
    public WeatherResponse getWeatherByCityCode(String cityCode) {
        // 1. 校验 city_code（可选，确保是9位数字，避免无效请求）
        if (cityCode == null || !cityCode.matches("\\d{9}")) {
            throw new IllegalArgumentException("城市编码必须是9位数字！");
        }

        // 2. 拼接最终请求 URL（RESTful 风格：基础URL + city_code）
        String requestUrl = WEATHER_BASE_URL + cityCode;

        // 3. 发送 GET 请求，接收响应（String 类型：直接获取 JSON 字符串）
        // 若需转为实体类，可将第二个参数改为自定义实体类.class（如 WeatherResponse.class）
        return restTemplate.getForObject(requestUrl, WeatherResponse.class);
    }

    /**
     * 保存前的数据校验
     */
    private void validEntityBeforeSave(FolwerDeliveryTemperature entity){
        //TODO 做一些数据校验,如唯一约束
    }

    /**
     * 校验并批量删除城市温度信息
     *
     * @param ids     待删除的主键集合
     * @param isValid 是否进行有效性校验
     * @return 是否删除成功
     */
    @Override
    public Boolean deleteWithValidByIds(Collection<Long> ids, Boolean isValid) {
        if(isValid){
            //TODO 做一些业务上的校验,判断是否需要校验
        }
        return baseMapper.deleteByIds(ids) > 0;
    }
}
