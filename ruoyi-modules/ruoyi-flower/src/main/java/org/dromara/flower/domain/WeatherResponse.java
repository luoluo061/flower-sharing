package org.dromara.flower.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

// 忽略接口中未定义的字段（避免解析失败）
@JsonIgnoreProperties(ignoreUnknown = true)
@Data // Lombok 注解，自动生成 getter/setter/toString 等
public class WeatherResponse {
    private Integer status; // 状态码（200=成功）
    private String message; // 提示信息
    private CityInfo cityInfo; // 城市信息
    private WeatherData data; // 天气数据

    // 内部类：城市信息
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CityInfo {
        private String city; // 城市名
        private String citykey; // 城市编码
        private String parent; // 上级城市
        private String updateTime; // 更新时间
    }

    // 内部类：天气数据
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WeatherData {
        private String shidu; // 湿度
        private Integer pm25; // PM2.5
        private Integer pm10; // PM10
        private String quality; // 空气质量
        private String wendu; // 温度
        private List<Forecast> forecast; // 未来天气预报（可再定义 Forecast 类）
    }

    // 内部类：未来天气预报（示例字段，根据接口返回补充）
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Forecast {
        private String date; // 日期
        private String high; // 最高温
        private String low; // 最低温
        private String type; // 天气类型（晴/雨等）
    }
}
