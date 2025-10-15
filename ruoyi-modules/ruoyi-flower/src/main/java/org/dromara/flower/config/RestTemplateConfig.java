package org.dromara.flower.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // 标记为配置类
public class RestTemplateConfig {
    // 注入 RestTemplate 到 Spring 容器
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
