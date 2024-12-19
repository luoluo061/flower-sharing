package org.dromara.system.platform.domain.query;

import lombok.Data;
/**
 *商户信息查询条件
 */
@Data
public class MerchantInformationQuery {
    /**
     * 商户姓名
     */

    private String merchantName;
    /**
     * 联系电话
     */

    private String phone;
    /**
     * 商户类型
     */

    private String merchantType;
    /**
     * 合作类型
     */
    private String cooperationType;

}
