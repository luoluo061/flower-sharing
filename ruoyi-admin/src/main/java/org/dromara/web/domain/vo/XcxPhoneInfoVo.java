package org.dromara.web.domain.vo;

import lombok.Data;

/**
 * 小程序用户电话信息
 */
@Data
public class XcxPhoneInfoVo {

    private String phoneNumber;

    private String purePhoneNumber;

    private String countryCode;

    private String watermark;
}
