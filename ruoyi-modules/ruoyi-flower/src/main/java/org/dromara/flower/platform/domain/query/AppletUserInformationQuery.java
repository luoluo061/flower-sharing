package org.dromara.flower.platform.domain.query;

import lombok.Data;
/**
 * 小程序用户查询条件
 */
@Data
public class AppletUserInformationQuery {
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机号
     */
    private String phone;


    /**
     * 用户类型
     */
    private String userType;



}
