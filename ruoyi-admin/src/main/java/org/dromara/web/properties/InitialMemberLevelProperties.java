package org.dromara.web.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 初始会员ID
 *
 * @author: chazonglin
 * @date: 2024/12/25 15:20
 */
@Component
@ConfigurationProperties(prefix = "member")
@Data
public class InitialMemberLevelProperties {

    /**
     * 初始会员id
     */
    private String initialId;

}
