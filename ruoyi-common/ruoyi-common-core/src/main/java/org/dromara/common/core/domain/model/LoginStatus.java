package org.dromara.common.core.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 登录状态
 */
@Data
@AllArgsConstructor
public class LoginStatus {

    //登录状态
    private String loginStatus;

    //状态说明
    private String description;
}
